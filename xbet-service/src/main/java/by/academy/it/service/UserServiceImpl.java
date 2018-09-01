package by.academy.it.service;

import by.academy.it.dao.DAOException;
import by.academy.it.dao.TransactionalDao;
import by.academy.it.dao.UserDao;
import by.academy.it.entity.Bet;
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
import java.util.Objects;

/**
 * This class works with {@link by.academy.it.dao.UserDao} and {@link by.academy.it.dao.TransactionalDao} class.
 *
 */
class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserDao userDao;
    private TransactionalDao transactionalDao;

    /**
     * Constructs an instance of the {@code UserService}.
     */
    UserServiceImpl(UserDao userDao, TransactionalDao transactionalDao) {
        this.userDao = userDao;
        this.transactionalDao = transactionalDao;
        logger.info("UserService has been created");
    }


    /**
     * Authenticates user's login.
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
    public void checkNewUserLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    private boolean isPasswordCorrectForLogin(String login, String password) throws ServiceException {
        try {
            User user = userDao.findByLogin(login);
            if (user != null && password.equals(String.valueOf(user.getPassword()))) {
                return true;
            }
        } catch (DAOException e) {
            logger.error("UserService cannot check password", e);
            throw new ServiceException("UserService cannot check password", e);
        }
        return false;
    }


    /**
     * Withdraws user's money and creates a bet entry.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws ServletException if the request could not be handled.
     * @throws IOException if an input or output error is detected.
     */
    public void confirmBet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String matchIdParam = request.getParameter(Constants.MATCH_ID);
        String betParam = request.getParameter(Constants.BET);
        String amountParam = request.getParameter(Constants.AMOUNT);
        if (Utils.isValidString(matchIdParam) && Utils.isValidString(betParam) && Utils.isValidString(amountParam)) {
            try {
                int matchId = Integer.parseInt(matchIdParam);
                double amount = Double.parseDouble(amountParam);
                String[] betParams = betParam.split("/");
                double betValue = Double.parseDouble(betParams[1]);

                HttpSession session = request.getSession();
                User user = (User) session.getAttribute(Constants.USER);
                double balance = user.getBalance() - amount;

                Bet bet = new Bet();
                bet.setUser_id(user.getId());
                bet.setMatch_id(matchId);
                bet.setBetResult(betParams[0]);
                bet.setBet(betValue);
                bet.setMoney(amount);
                bet.setStatus("active");

                logger.debug("start of transaction");
                transactionalDao.placeBet(user.getLogin(), balance, bet);
                logger.debug("transaction is finished");
                user.setBalance(balance);

                logger.info("bet has been placed");
                request.setAttribute(Constants.CONFIRM_MESSAGE, Constants.SUCCESS);
                request.getRequestDispatcher(Utils.getReferrerPath(request)).forward(request, response);

            } catch (Exception e) {
                logger.error("An exception occurred during create bet operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.BET_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.warn("Bet parameters are not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.BET_PARAM_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Updates user balance through {@link by.academy.it.dao.UserDao}.
     *
     * @param login the user's login.
     * @param amount money to add or remove.
     * @return the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    private User updateUserBalance(String login, double amount) throws ServiceException {
        User user;
        try {
            user = userDao.findByLogin(login);
            if (user != null) {
                double balance = user.getBalance() + amount;
                userDao.updateBalance(login, balance);
                user.setBalance(balance);
                logger.info("user's balance has been updated");
            } else {
                logger.error("UserService cannot updateBalance user's balance");
                throw new ServiceException("UserService cannot updateBalance user's balance");
            }
        } catch (DAOException e) {
            logger.error("UserService cannot updateBalance a user", e);
            throw new ServiceException("UserService cannot updateBalance a user", e);
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

        } catch (Exception e) {
            logger.error("UserService cannot delete a user", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.DELETE_USER_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Retrieves a user by login through {@link by.academy.it.dao.UserDao}.
     *
     * @param login the user's login.
     * @return the {@link by.academy.it.entity.User} entity.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    private User findUserByLogin(String login) throws ServiceException {
        User user;
        try {
            user = userDao.findByLogin(login);
        } catch (DAOException e) {
            logger.error("UserService cannot find a user", e);
            throw new ServiceException("UserService cannot find a user", e);
        }
        return user;
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

                    request.setAttribute("users", list);
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
        String balance = request.getParameter(Constants.BALANCE);
        String role = request.getParameter(Constants.ROLE);

        if (Utils.isValidString(login) && Utils.isValidString(password) && Utils.isValidString(firstName)
                && Utils.isValidString(lastName) && Utils.isValidString(email) && Utils.isValidString(balance)
                && Utils.isValidString(role)) {
            try {
                User user = new User();
                user.setLogin(login);
                user.setPassword(password.toCharArray());
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setBalance(Double.parseDouble(balance));
                user.setRole(Integer.parseInt(role));
                userDao.create(user);

                logger.info("user has been created");
                request.getSession().setAttribute(Constants.USER_MESSAGE, Constants.CREATE_USER_MESSAGE);
                response.sendRedirect( request.getContextPath() + Constants.MAIN + Constants.GET + Constants.USERS);

            } catch (Exception e) {
                logger.error("An exception occurred during create user operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.CREATE_USER_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.error("Create user data are not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.REGISTRATION_ERROR);

            response.sendRedirect(request.getContextPath() + Constants.ERROR);
        }
    }


    /**
     * Retrieves a user by id through {@link by.academy.it.dao.UserDao} and sends a redirect to 'update user' page.
     *
     * @param request {@code HttpServletRequest} request.
     * @param response  {@code HttpServletResponse} response.
     * @throws IOException if an input or output error is detected.
     */
    public void showUpdateUserPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String key = request.getParameter(Constants.KEY);
        if (Utils.isValidString(key)) {
            try {
                int id = Integer.parseInt(key);
                User user = userDao.findById(id);
                if (user != null) {
                    user.setPassword(null);
                    request.getSession().setAttribute(Constants.UPDATED_USER, user);
                    logger.info("user has been retrieved");

                    response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.UPDATE_USER);

                } else {
                    logger.warn("User with such id has not been found");
                    request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.USER_NOT_FOUND);

                    response.sendRedirect(request.getContextPath() + Constants.ERROR);
                }
            } catch (Exception e) {
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
        try {
            int id = Integer.parseInt(idParam);
            double balance = Double.parseDouble(balanceParam);
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
            if (Objects.equals(user.getId(), sessionUser.getId())) {
                session.setAttribute(Constants.USER, user);
            }

            logger.info("user has been updated");
            session.setAttribute(Constants.USER_MESSAGE, Constants.UPDATE_USER_MESSAGE);
            response.sendRedirect(request.getContextPath() + Constants.MAIN + Constants.GET + Constants.USERS);

        } catch (Exception e) {
            logger.error("An exception occurred during update user operation", e);
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.SHOW_UPDATE_USER_ERROR);

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

        //TODO: delete code below
        try {
            User user = findUserByLogin("admin");
            user.setPassword(null);
            request.getSession().setAttribute("user", user);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

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
    public void checkBalance(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
            if (Utils.isValidString(password) && isPasswordCorrectForLogin(login, password)) {
                User user = findUserByLogin(login);
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
        } catch (ServiceException e) {
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
        if (Utils.isValidString(param)) {
            try {
                double amount = Double.parseDouble(param);
                User sessionUser = (User) request.getSession().getAttribute(Constants.USER);
                User user = updateUserBalance(sessionUser.getLogin(), amount);
                sessionUser.setBalance(user.getBalance());

                response.sendRedirect(Utils.getReferrerURI(request));

            } catch (NumberFormatException e) {
                logger.error("Wrong number format", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.NUMBER_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);

            } catch (ServiceException e) {
                logger.error("An exception occurred during top up balance operation", e);
                request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.TOPUP_ERROR);

                response.sendRedirect(request.getContextPath() + Constants.ERROR);
            }
        } else {
            logger.error("Amount parameter is not valid");
            request.getSession().setAttribute(Constants.ERROR_MESSAGE, Constants.AMOUNT_ERROR);

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

        if (Utils.isValidString(login) && Utils.isValidString(password) && Utils.isValidString(firstName)
                && Utils.isValidString(lastName) && Utils.isValidString(email)) {
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
                user = findUserByLogin(user.getLogin());
                user.setPassword(null);
                request.getSession().setAttribute(Constants.USER, user);
                logger.info("user has been registered");

                response.sendRedirect(request.getContextPath() + Constants.HOME);

            } catch (ServiceException | DAOException e) {
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
