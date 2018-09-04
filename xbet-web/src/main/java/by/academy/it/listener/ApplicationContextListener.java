package by.academy.it.listener;

import by.academy.it.command.CommandFactory;
import by.academy.it.dao.factory.*;
import by.academy.it.service.ServiceFactory;
import by.academy.it.service.ServiceFactoryImpl;
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
    private ConnectionPool pool;

    /**
     * Initializes connection pool.
     *
     * @param sce ServletContextEvent.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Start of the application");
        try {
            pool = new ConnectionPoolImpl();
            pool.init();
            DaoFactory daoFactory = new DaoFactoryImpl(pool);
            ServiceFactory serviceFactory = new ServiceFactoryImpl(daoFactory);
            CommandFactory.setServiceFactory(serviceFactory);
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
