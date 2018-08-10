package by.academy.it.service;

import by.academy.it.dao.DAOException;
import by.academy.it.dao.ResultDao;
import by.academy.it.dao.factory.DaoFactory;
import by.academy.it.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class works with {@link by.academy.it.dao.ResultDao} class.
 *
 */
public class ResultService {

    private static final Logger logger = LoggerFactory.getLogger(ResultService.class);
    private ResultDao resultDao = DaoFactory.getInstance().getResultDao();
    private static ResultService instance;

    /**
     * Prohibits creating instance of class outside the class.
     */
    private ResultService() {
    }


    /**
     * Creates {@code ResulService} instance if it is not created and returns it.
     *
     * @return {@code ResulService} instance.
     */
    public static ResultService getInstance() {
        if (instance == null) {
            instance = new ResultService();
            logger.info("ResultService instance has been created");
        }
        return instance;
    }


    /**
     * Creates a match result entry through {@link by.academy.it.dao.ResultDao}.
     *
     * @param result the {@link by.academy.it.entity.Result} entity.
     * @throws by.academy.it.service.ServiceException if an exception occurred during the operation.
     */
    public void createResult(Result result) throws ServiceException {
        try {
            resultDao.create(result);
        } catch (DAOException e) {
            logger.error("ResultService cannot create a result", e);
            throw new ServiceException("ResultService cannot create a result");
        }
    }

}
