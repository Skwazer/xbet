package by.academy.it.listener;

import by.academy.it.dao.factory.ConnectionPool;
import by.academy.it.dao.factory.ConnectionPoolException;
import by.academy.it.dao.factory.ConnectionPoolImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 */
public class ApplicationContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextListener.class);
    private static final ConnectionPool pool = ConnectionPoolImpl.getInstance();

    /**
     * Initializes connection pool.
     *
     * @param sce ServletContextEvent.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Start of the application");
        try {
            pool.init();
        } catch (ConnectionPoolException e) {
            logger.error("Application hasn't been started", e);
            throw new RuntimeException("Application hasn't been started", e);
        }
    }

    /**
     * Shutdowns connection pool.
     *
     * @param sce ServletContextEvent.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Shutdown of the application");
        try {
            pool.shutdownConnectionPool();
        } catch (ConnectionPoolException e) {
            logger.error("Application hasn't been shutdowned", e);
            throw new RuntimeException("Application hasn't been shutdowned", e);
        }
    }
}
