package by.academy.it.dao.factory;

import by.academy.it.dao.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

/**
 * This class handles created connections, because creating a network connection to a database is an expensive operation.
 */
public class ConnectionPoolImpl implements ConnectionPool {

    private final static Logger logger = LoggerFactory.getLogger(ConnectionPoolImpl.class);

    private String url;
    private String username;
    private String password;

    private int maxConnections;
    private BlockingQueue<Connection> availableConnections;
    private BlockingQueue<Connection> usedConnections;

    /**
     * Creates an instance of the connection pool using properties from database.properties file.
     *
     * @throws by.academy.it.dao.factory.ConnectionPoolException if an error occurred during the operation.
     */
    public ConnectionPoolImpl() throws ConnectionPoolException {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
            url = properties.getProperty(Constants.DB_URL);
            username = properties.getProperty(Constants.DB_USERNAME);
            password = properties.getProperty(Constants.DB_PASSWORD);
            String driver = properties.getProperty(Constants.DRIVER);
            String maxConnections = properties.getProperty(Constants.MAX_CONNECTIONS);
            try {
                this.maxConnections = Integer.parseInt(maxConnections);
            } catch (NumberFormatException e) {
                logger.error("Cannot read the connection pool max size", e);
                this.maxConnections = 40;
            }
            Class.forName(driver);
            availableConnections = new ArrayBlockingQueue<>(this.maxConnections);
            usedConnections = new ArrayBlockingQueue<>(this.maxConnections);
            logger.info("The connection pool has been created");

        } catch (ClassNotFoundException | IOException e) {
            logger.error("The connection pool hasn't been created", e);
            throw new ConnectionPoolException("The connection pool hasn't been created", e);
        }
    }


    /**
     * Initializes the connection pool.
     *
     * @throws by.academy.it.dao.factory.ConnectionPoolException if an error occurred during the operation.
     */
    @Override
    public void init() throws ConnectionPoolException {
        try {
            for (int i = 0; i < maxConnections; i++) {
                Connection connection = DriverManager.getConnection(url, username, password);
                PooledConnection pooledConnection = new PooledConnection(connection);

                availableConnections.add(pooledConnection);
            }
            logger.info("The connection pool has been initialized");
        } catch (SQLException e) {
            logger.error("The connection pool hasn't been initialized", e);
            throw new ConnectionPoolException("The connection pool hasn't been initialized", e);
        }
    }


    /**
     * Shutdowns the connection pool.
     *
     * @throws by.academy.it.dao.factory.ConnectionPoolException if an error occurred during the operation.
     */
    @Override
    public void shutdownConnectionPool() throws ConnectionPoolException {
        ArrayList<Connection> connectionsToDelete = new ArrayList<>();

        connectionsToDelete.addAll(availableConnections);
        availableConnections.clear();
        connectionsToDelete.addAll(usedConnections);
        usedConnections.clear();
        try {
            for (Connection con : connectionsToDelete) {
                if (!con.getAutoCommit()) {
                    con.commit();
                }
                ((PooledConnection) con).reallyClose();
            }
            logger.info("The connection pool has been closed");
        } catch (SQLException e) {
            logger.error("The connection pool hasn't been closed", e);
            throw new ConnectionPoolException("The connection pool hasn't been closed", e);
        }
    }


    /**
     * Returns an available connection.
     *
     * @return a {@code java.sql.Connection} implementation.
     * @throws by.academy.it.dao.factory.ConnectionPoolException if an exception occurred during the operation.
     */
    @Override
    public Connection getConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = availableConnections.take();
            usedConnections.add(connection);
        } catch (Exception e) {
            logger.error("Cannot get a connection", e);
            throw new ConnectionPoolException("Cannot get a connection", e);
        }
        return connection;
    }


    /**
     * An implementation of the Connection interface.
     */
    private class PooledConnection implements Connection {
        private Connection connection;

        PooledConnection(Connection c) throws SQLException {
            this.connection = c;
            this.connection.setAutoCommit(true);
        }

        void reallyClose() throws SQLException {
            connection.close();
        }

        @Override
        public void close() throws SQLException {
            if (connection.isClosed()) {
                throw new SQLException("Attempting to close closed connection");
            }

            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }

            connection.setAutoCommit(true);

            if (!usedConnections.remove(this)) {
                throw new SQLException("Error deleting connection from the given away connections pool");
            }

            if (!availableConnections.offer(this)) {
                throw new SQLException("Error allocating connection in the pool");
            }
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements)
                throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
                throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
                throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
                                             int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
                throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
                                                  int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        @Override
        public void setClientInfo(String name, String value)
                throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            connection.setHoldability(holdability);
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint(name);
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return connection.isWrapperFor(iface);
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return connection.unwrap(iface);
        }

        @Override
        public void abort(Executor arg0) throws SQLException {
            connection.abort(arg0);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public void releaseSavepoint(Savepoint arg0) throws SQLException {
            connection.releaseSavepoint(arg0);
        }

        @Override
        public void rollback(Savepoint arg0) throws SQLException {
            connection.rollback(arg0);
        }

        @Override
        public void setClientInfo(Properties arg0)
                throws SQLClientInfoException {
            connection.setClientInfo(arg0);
        }

        @Override
        public void setNetworkTimeout(Executor arg0, int arg1)
                throws SQLException {
            connection.setNetworkTimeout(arg0, arg1);
        }

        @Override
        public void setSchema(String arg0) throws SQLException {
            connection.setSchema(arg0);
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
            connection.setTypeMap(arg0);
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }
    }

}
