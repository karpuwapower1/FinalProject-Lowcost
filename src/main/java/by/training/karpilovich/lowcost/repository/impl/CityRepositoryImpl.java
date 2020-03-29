package by.training.karpilovich.lowcost.repository.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.dao.CityDAO;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.exception.RepositoryException;
import by.training.karpilovich.lowcost.factory.DAOFactory;
import by.training.karpilovich.lowcost.repository.CityRepository;
import by.training.karpilovich.lowcost.specification.Specification;
import by.training.karpilovich.lowcost.util.MessageType;

public class CityRepositoryImpl implements CityRepository {

	private final Lock lock = new ReentrantLock();
	private final AtomicBoolean isRepositoryInitialized = new AtomicBoolean(false);

	private static final Logger LOGGER = LogManager.getLogger(CityRepositoryImpl.class);

	private List<City> cities;

	private CityRepositoryImpl() {
		
	}

	private static final class CityRepositoryInstanceHolder {
		private static final CityRepositoryImpl INSTANCE = new CityRepositoryImpl();
	}

	public void init() throws RepositoryException {
		if (lock.tryLock() && !isRepositoryInitialized.get()) {
			DAOFactory factory = DAOFactory.getInstance();
			CityDAO cityDAO = factory.getCityDAO();
			try {
				cities = cityDAO.getAllCities();
				isRepositoryInitialized.set(true);
			} catch (DAOException e) {
				LOGGER.fatal("Exception while initializing a repository " + e);
				throw new RepositoryException(MessageType.INTERNAL_ERROR.getType());
			} finally {
				lock.unlock();
			}
		}
	}

	public static CityRepository getInstance() throws RepositoryException {
		return CityRepositoryInstanceHolder.INSTANCE;

	}

	@Override
	public void add(City city) {
		
	}

	@Override
	public void delete(City city) {
		
	}

	@Override
	public void update(City city) {
		
	}

	@Override
	public List<City> getCities(Specification specification) {
		return specification.specify(cities);
	}

}
