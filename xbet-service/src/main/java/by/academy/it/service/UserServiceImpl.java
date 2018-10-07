package by.academy.it.service;

import by.academy.it.dao.DAOException;
import by.academy.it.dao.UserDao;
import by.academy.it.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * This class works with {@link by.academy.it.dao.UserDao} class.
 *
 */
class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserDao userDao;

    /**
     * Constructs an instance of the {@code UserService}.
     *
     * @param userDao a UserDao instance.
     */
    UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
        logger.info("UserService has been created");
    }


    /**
     * Authenticates user's login through ajax.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    public void authenticateLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter(Constants.KEY);
        if (Utils.isValidString(login)) {
            try {
                if (isLoginExist(login)) {
                    logger.info("this login exists");
                    response.setHeader(Constants.RESULT, Constants.SUCCESS);
                } else {
                    logger.info("this login doesn't exist");
                    response.setHeader(Constants.RESULT, Constants.FAILURE);
                }
            } catch (DAOException e) {
                logger.error("UserService cannot find a user by login", e);
                response.setHeader(Constants.RESULT, null);
            }
        } else {
            logger.warn("this login is not valid");
            response.setHeader(Constants.RESULT, null);
        }
        request.getRequestDispatcher(Utils.getReferrerPath(request)).forward(request, response);
    }


    /**
     * Checks if a user with such login exists in the database.
     *
     * @param login the user's login.
     * @return {@code true} if a user exists or {@code false} otherwise.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    private boolean isLoginExist(String login) throws DAOException {
        User user = userDao.findByLogin(login);
        return user != null;
    }


    /**
     * Checks if login is free.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    public void checkNewUserLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter(Constants.KEY);
        if (Utils.isValidString(login)) {
            try {
                if (!isLoginExist(login)) {
                    logger.info("this login is free");
                    response.setHeader(Constants.RESULT, Constants.SUCCESS);
                } else {
                    logger.info("this login is already taken");
                    response.setHeader(Constants.RESULT, Constants.FAILURE);
                }
            } catch (DAOException e) {
                logger.error("An exception occurred during login validation", e);
                response.setHeader(Constants.RESULT, null);
            }
        } else {
            logger.warn("this login is not valid");
            response.setHeader(Constants.RESULT, null);
        }
        request.getRequestDispatcher(Utils.getReferrerPath(request)).forward(request, response);
    }


    /**
     * Checks if a user with such login and password exists in the database.
     *
     * @param login the user's login.
     * @param password the user's password.
     * @return {@code true} if a user exists or {@code false} otherwise.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    private boolean isPasswordCorrectForLogin(String login, String password) throws DAOException {
        User user = userDao.findByLogin(login);
        if (user != null && password.equals(String.valueOf(user.getPassword()))) {
            return true;
        }
        return false;
    }


    /**
     * Updates user balance through {@link by.academy.it.dao.UserDao}.
     *
     * @param login the user's login.
     * @param amount money to add or remove.
     * @return the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.dao.DAOException if an exception occurred during the operation.
     */
    private User updateUserBalance(String login, double amount) throws DAOException {
        User user;
        user = userDao.findByLogin(login);
        if (user != null) {
            double balance = user.getBalance() + amount;
            userDao.updateBalance(login, balance);
            user.setBalance(balance);
            logger.info("user's balance has been updated");
        }
        return user;
    }


    /**
     * Deletes a user entry through {@link by.academy.it.dao.UserDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String key = request.getParameter(Constants.KEY);
        if (Utils.isValidString(key)) {
            try {
                int id = Integer.parseInt(key);

                HttpSession session = request.getSession();
                User sessionUser = (User) session.getAttribute(Constants.USER);
                if (sessionUser.getId() != id) {
                    userDao.delete(id);
                    logger.info("user has been deleted");
                    session.setAttribute(Constants.USER_MESSAGE, Constants.DELETE_USER_MESSAGE);
                } else {
                    logger.warn("You cannot delete yourself");
                    session.setAttribute(Constants.USER_MESSAGE, Constants.DELETE_YOURSELF_MESSAGE);
                }
                response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET + Constants.USERS);

            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("UserService cannot delete a user", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.DELETE_USER_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("User id parameter is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAM_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Retrieves a list of users through {@link by.academy.it.dao.UserDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    public void showUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageParam = request.getParameter(Constants.PAGE);
        int page = Utils.checkPageParameter(pageParam);
        int startFrom = Utils.calculateSelectStartPosition(page, request, response);
        if (startFrom >= 0) {
            List<User> list;
            try {
                list = userDao.getUsers(startFrom);
                if (!list.isEmpty()) {
                    logger.info("users list has been retrieved");
                    list.forEach(user -> user.setPassword(null));
                    double pages = Math.ceil(userDao.getAmountOfUsers() / 10d);

                    request.setAttribute(Constants.USERS, list);
                    request.setAttribute(Constants.CURRENT_PAGE, page);
                    request.setAttribute(Constants.PAGES, pages);
                    request.getRequestDispatcher(Constants.PATH + Constants.GET + Constants.USERS + Constants.JSP)
                            .forward(request, response);
                } else {
                    logger.warn("Users list is empty");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NO_USERS_ERROR);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                }
            } catch (DAOException e) {
                logger.error("UserService cannot get a users list", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.USERS_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        }
    }


    /**
     * Creates a user entry through {@link by.academy.it.dao.UserDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void createUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter(Constants.LOGIN);
        String password = request.getParameter(Constants.PASSWORD);
        String firstName = request.getParameter(Constants.FIRST_NAME);
        String lastName = request.getParameter(Constants.LAST_NAME);
        String email = request.getParameter(Constants.EMAIL);
        String balanceParam = request.getParameter(Constants.BALANCE);
        String role = request.getParameter(Constants.ROLE);

        Double balance;
        try {
            if (Utils.isValidStrings(login, password, firstName, lastName, email, balanceParam, role) &&
                    validateLogin(login) && validatePassword(password) && validateEmail(email) &&
                    (balance= Double.parseDouble(balanceParam)) >= 0) {

                User user = new User();
                user.setLogin(login);
                user.setPassword(password.toCharArray());
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setBalance(balance);
                user.setRole(Integer.parseInt(role));
                userDao.create(user);

                logger.info("user has been created");
                request.getSession().setAttribute(Constants.USER_MESSAGE, Constants.CREATE_USER_MESSAGE);
                response.sendRedirect( request.getContextPath() + Constants.MAIN + Constants.GET + Constants.USERS);

            } else {
                logger.error("Create user data are not valid");
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.REGISTRATION_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } catch (NumberFormatException e) {
            logger.error("Cannot parse a number parameter", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        } catch (DAOException e) {
            logger.error("An exception occurred during create user operation", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.CREATE_USER_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Validates login value.
     *
     * @param login a value to validate.
     * @return true if login matches the regular expression or false otherwise.
     */
    private boolean validateLogin(String login) {
        return login.matches(Constants.LOGIN_REGEX);
    }


    /**
     * Validates password value.
     *
     * @param password a value to validate.
     * @return true if password matches the regular expression or false otherwise.
     */
    private boolean validatePassword(String password) {
        return password.matches(Constants.PASSWORD_REGEX);
    }


    /**
     * Validates email value.
     *
     * @param email a value to validate.
     * @return true if email matches the regular expression or false otherwise.
     */
    private boolean validateEmail(String email) {
        return email.matches(Constants.EMAIL_REGEX);
    }


    /**
     * Retrieves a user by id through {@link by.academy.it.dao.UserDao} and sends forward to 'update user' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    public void showUpdateUserPage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String key = request.getParameter(Constants.KEY);
        if (Utils.isValidString(key)) {
            try {
                int id = Integer.parseInt(key);
                User user = userDao.findById(id);
                if (user != null) {
                    user.setPassword(null);
                    request.setAttribute(Constants.UPDATED_USER, user);
                    logger.info("user has been retrieved");

                    List<Integer> rolesIds = ServiceFactoryImpl.getIdService().getRolesIds();
                    if (rolesIds != null) {
                        request.setAttribute(Constants.ROLES_IDS, rolesIds);
                        logger.info("roles ids have been retrieved");
                    } else {
                        logger.warn("Roles ids have not been found");
                        request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.ROLES_IDS_NOT_FOUND);
                        response.sendRedirect(request.getContextPath() + Constants.ERROR);
                        return;
                    }
                    request.getRequestDispatcher(Constants.PATH + Constants.UPDATE_USER + Constants.JSP)
                            .forward(request, response);

                } else {
                    logger.warn("User with such id has not been found");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.USER_NOT_FOUND);
                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                }
            } catch (NumberFormatException e) {
                logger.error("Cannot parse a number parameter", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);
                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            } catch (DAOException e) {
                logger.error("An exception occurred during show update user page operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_USER_ERROR);
                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Show update user page operation parameter is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_USER_PARAMETER_ERROR);
            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Retrieves a list of roles' ids and sends forward to 'create user' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    public void showCreateUserPage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            List<Integer> rolesIds = ServiceFactoryImpl.getIdService().getRolesIds();
            if (rolesIds != null) {
                request.setAttribute(Constants.ROLES_IDS, rolesIds);
                logger.info("roles ids have been retrieved");
            } else {
                logger.warn("Roles ids have not been found");
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.ROLES_IDS_NOT_FOUND);
                response.sendRedirect(request.getContextPath() + Constants.ERROR);
                return;
            }
            request.getRequestDispatcher(Constants.PATH + Constants.CREATE_USER + Constants.JSP)
                    .forward(request, response);

        } catch (DAOException e) {
            logger.error("An exception occurred during show update user page operation", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_USER_ERROR);
            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Updates a user entry through {@link by.academy.it.dao.UserDao}.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter(Constants.ID);
        String login = request.getParameter(Constants.LOGIN);
        String firstName = request.getParameter(Constants.FIRST_NAME);
        String lastName = request.getParameter(Constants.LAST_NAME);
        String email = request.getParameter(Constants.EMAIL);
        String balanceParam = request.getParameter(Constants.BALANCE);
        String roleParam = request.getParameter(Constants.ROLE);

        Double balance;
        try {
            if (Utils.isValidStrings(idParam, firstName, lastName, email, roleParam) && validateEmail(email) &&
                    validateLogin(login) && (balance = Double.parseDouble(balanceParam)) >= 0 ) {

                int id = Integer.parseInt(idParam);
                int role = Integer.parseInt(roleParam);

                User user = new User();
                user.setId(id);
                user.setLogin(login);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setBalance(balance);
                user.setRole(role);
                userDao.update(user);

                HttpSession session = request.getSession();
                User sessionUser = (User) session.getAttribute(Constants.USER);
                if (user.getId().equals(sessionUser.getId())) {
                    session.setAttribute(Constants.USER, user);
                }
                logger.info("user has been updated");
                session.setAttribute(Constants.USER_MESSAGE, Constants.UPDATE_USER_MESSAGE);
                response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET + Constants.USERS);

            } else {
                logger.warn("Update user parameters are not valid");
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.PARAMS_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } catch (NumberFormatException e) {
            logger.error("Cannot parse a number parameter", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_PARSE_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        } catch (DAOException e) {
            logger.error("An exception occurred during update user operation", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.UPDATE_USER_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Sets the locale of the application.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void changeLocale(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String lang = request.getParameter(Constants.LANG);
        Locale locale;
        if (Constants.RU.equals(lang)) {
            locale = new Locale(Constants.RU, Constants.RUS);
        } else {
            locale = Locale.US;
        }
        Config.set(request.getSession(), Config.FMT_LOCALE, locale);
        logger.info("locale has been changed - " + locale);

        response.sendRedirect(Utils.getReferrerURI(request));
    }


    /**
     * Checks if the user has enough money to place a bet.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     * @throws ServletException if the request could not be handled.
     */
    public void checkBalance(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String key = request.getParameter(Constants.KEY);
        if (Utils.isValidString(key)) {
            try {
                double amount = Double.parseDouble(key);
                User user = (User) request.getSession().getAttribute(Constants.USER);
                if (amount > 0 && amount <= user.getBalance()) {
                    logger.info("balance check - enough money");
                    response.setHeader(Constants.RESULT, Constants.SUCCESS);
                } else {
                    logger.info("balance check - not enough money");
                    response.setHeader(Constants.RESULT, Constants.NOT_ENOUGH);
                }
            } catch (NumberFormatException e) {
                logger.warn("An exception occurred during check the user's balance operation", e);
                response.setHeader(Constants.RESULT, Constants.NUMBER_ERROR);
            }
        } else {
            logger.error("balance check - not valid key");
            response.setHeader(Constants.RESULT, Constants.FAILURE);
        }
        request.getRequestDispatcher(Utils.getReferrerPath(request)).forward(request, response);
    }


    /**
     * Checks if the user's data are located in the session.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    public void checkIsUserLoggedIn(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (request.getSession().getAttribute(Constants.USER) != null){
            logger.info("user is logged in");
            response.setHeader(Constants.RESULT, Constants.SUCCESS);
        } else {
            logger.info("user is not logged in");
            response.setHeader(Constants.RESULT, Constants.FAILURE);
        }
        request.getRequestDispatcher(Utils.getReferrerPath(request)).forward(request, response);
    }


    /**
     * Puts into the session data about logged user.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    public void loginUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String login = request.getParameter(Constants.LOGIN);
        String password = request.getParameter(Constants.PASSWORD);
        try {
            if (Utils.isValidStrings(login, password) && validateLogin(login) && validatePassword(password)
                    && isPasswordCorrectForLogin(login, password)) {
                User user = userDao.findByLogin(login);
                if (user != null) {
                    user.setPassword(null);
                    request.getSession().setAttribute(Constants.USER, user);
                    logger.info("user has been logged in");

                    response.sendRedirect(request.getContextPath() + Constants.HOME);

                } else {
                    logger.error("user with login [" + login + "] doesn't exist");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.LOGIN_FAILURE);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                }
            } else {
                logger.warn("password is incorrect");
                request.setAttribute(Constants.MESSAGE, Constants.INCORRECT_PASSWORD);

                request.getRequestDispatcher(Utils.getReferrerPath(request)).forward(request, response);
            }
        } catch (DAOException e) {
            logger.error("An exception occurred during login action", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.LOGIN_EXCEPTION);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }

    /**
     * Tops up the user's balance.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void topup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String param = request.getParameter("amount");
        Double amount;
        try {
            if (Utils.isValidString(param) && (amount = Double.parseDouble(param)) > 0) {
                User sessionUser = (User) request.getSession().getAttribute(Constants.USER);
                User user = updateUserBalance(sessionUser.getLogin(), amount);
                if (user == null) {
                    logger.error("Cannot find a user with such login");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.USER_NOT_FOUND);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                    return;
                }
                sessionUser.setBalance(user.getBalance());

                response.sendRedirect(Utils.getReferrerURI(request));

            } else {
                logger.error("Amount parameter is not valid");
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.AMOUNT_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } catch (NumberFormatException e) {
            logger.error("Wrong number format", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);

        } catch (DAOException e) {
            logger.error("An exception occurred during top up balance operation", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.TOPUP_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Creates a new user in the database and puts user's data in the session.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter(Constants.LOGIN);
        String password = request.getParameter(Constants.PASSWORD);
        String firstName = request.getParameter(Constants.FIRST_NAME);
        String lastName = request.getParameter(Constants.LAST_NAME);
        String email = request.getParameter(Constants.EMAIL);

        if (Utils.isValidStrings(login, password, firstName, lastName, email) && validateLogin(login) &&
                validatePassword(password) && validateEmail(email)) {
            User user = new User();
            user.setLogin(login);
            user.setPassword(password.toCharArray());
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setBalance(0d);
            user.setRole(2);
            try {
                userDao.create(user);
                user = userDao.findByLogin(login);
                user.setPassword(null);
                request.getSession().setAttribute(Constants.USER, user);
                logger.info("user has been registered");

                response.sendRedirect(request.getContextPath() + Constants.HOME);

            } catch (DAOException e) {
                logger.error("An exception occurred during login validation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.LOGIN_EXCEPTION);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.error("Registration data are not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.REGISTRATION_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Deletes user's data from the session.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute(Constants.USER, null);
        logger.info("logout is successful");
        response.sendRedirect(request.getContextPath() + Constants.HOME);
    }

}
