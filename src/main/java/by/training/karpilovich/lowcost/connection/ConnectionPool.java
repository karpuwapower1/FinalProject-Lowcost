package by.training.karpilovich.lowcost.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.exception.ConnectionPoolException;

public class ConnectionPool {

	private static final String DB_PROPERTIES = "db.properties";
	private static final String USER_PROPERTY = "user";
	private static final String PASSWORD_PROPERTY = "password";
	private static final String URL_PROPERTY = "url";
	private static final String DRIVER_PROPERTY = "driver";
	private static final String POOL_SIZE_PROPERTY = "poolsize";
	private static final String TIMEOUT_PROPERTY = "timeout";

	private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

	private String user;
	private String password;
	private String url;
	private int poolSize;
	private int timeout;
	private String driver;
	private BlockingQueue<ProxyConnection> available;
	private List<ProxyConnection> unavailable;
	private final Lock lock = new ReentrantLock();
	private final AtomicBoolean isPoolInitialized = new AtomicBoolean(false);

	private ConnectionPool() {
	}

	public static final class PoolConnectionInstanceHolder {
		public static final ConnectionPool INSTANCE = new ConnectionPool();
	}

	public static ConnectionPool getInstance() {
		return PoolConnectionInstanceHolder.INSTANCE;
	}

	public void init() throws ConnectionPoolException {
		if (lock.tryLock() && !isPoolInitialized.get()) {
			try {
				loadProperties();
				Class.forName(driver);
				available = new ArrayBlockingQueue<>(poolSize);
				unavailable = new ArrayList<>(poolSize);
				fillPool();
				isPoolInitialized.set(true);
				lock.unlock();
			} catch (ClassNotFoundException e) {
				LOGGER.fatal(e);
				throw new ConnectionPoolException(e);
			}
		}
	}

	public ProxyConnection getConnection() throws ConnectionPoolException {
		if (!isPoolInitialized.get()) {
			LOGGER.warn("Pool hasn't been initialized yet");
			throw new ConnectionPoolException("Pool hasn't been initialized yet");
		}
		try {
			ProxyConnection connection = available.poll(timeout, TimeUnit.SECONDS);
			if (connection != null) {
				unavailable.add(connection);
				return connection;
			}
			throw new ConnectionPoolException();
		} catch (InterruptedException e) {
			LOGGER.warn(e);
			throw new ConnectionPoolException(e);
		}
	}

	public void releaseConnection(ProxyConnection connection) throws ConnectionPoolException {
		if (!isPoolInitialized.get()) {
			LOGGER.warn("Pool hasn't been initialized yet");
			throw new ConnectionPoolException("Pool hasn't been initialized yet");
		}
		if (!unavailable.remove(connection)) {
			LOGGER.warn("Connection doesn't belong to this pool");
			throw new ConnectionPoolException("Connection doesn't belong to this pool");
		}
		available.offer(connection);
	}

	public void destroy() throws ConnectionPoolException {
		try {
			for (int i = 0; i < poolSize; i++) {
				available.take().closeConnection();
			}
		} catch (SQLException | InterruptedException e) {
			LOGGER.warn(e);
			throw new ConnectionPoolException(e);
		}
		deregisterDriver();
	}

	private void fillPool() throws ConnectionPoolException {
		try {
			for (int i = 0; i < poolSize; i++) {
				available.offer(new ProxyConnection(DriverManager.getConnection(url, user, password)));
			}
		} catch (SQLException e) {
			LOGGER.fatal(e);
			throw new ConnectionPoolException(e);
		}
	}

	private void loadProperties() throws ConnectionPoolException {
		Properties properties = new Properties();
		try (InputStream stream = getClass().getClassLoader().getResourceAsStream(DB_PROPERTIES)) {
			properties.load(stream);
			driver = properties.getProperty(DRIVER_PROPERTY);
			user = properties.getProperty(USER_PROPERTY);
			password = properties.getProperty(PASSWORD_PROPERTY);
			url = properties.getProperty(URL_PROPERTY);
			poolSize = Integer.parseInt(properties.getProperty(POOL_SIZE_PROPERTY));
			timeout = Integer.parseInt(properties.getProperty(TIMEOUT_PROPERTY));
		} catch (IOException | NumberFormatException e) {
			LOGGER.fatal(e);
			throw new ConnectionPoolException(e);
		}
	}
	
	private void deregisterDriver() throws ConnectionPoolException {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		try {
		while (drivers.hasMoreElements()) {
			DriverManager.deregisterDriver(drivers.nextElement());
		}
		} catch (SQLException e) {
			throw new ConnectionPoolException(e);
		}
	}
}
