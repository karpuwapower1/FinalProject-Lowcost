package by.training.karpilovich.lowcost.repository.impl;

import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;
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
import by.training.karpilovich.lowcost.factory.SpecificationFactory;
import by.training.karpilovich.lowcost.repository.CityRepository;
import by.training.karpilovich.lowcost.specification.Specification;
import by.training.karpilovich.lowcost.util.CityByCountryAndNameComparator;
import by.training.karpilovich.lowcost.util.message.MessageType;

public class CityRepositoryImpl implements CityRepository {

	private final Lock lock = new ReentrantLock();
	private final AtomicBoolean isRepositoryInitialized = new AtomicBoolean(false);

	private static final Logger LOGGER = LogManager.getLogger(CityRepositoryImpl.class);

	private SortedSet<City> cities = new ConcurrentSkipListSet<>(new CityByCountryAndNameComparator());

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
				cities.addAll(cityDAO.getAllCities());
				isRepositoryInitialized.set(true);
			} catch (DAOException e) {
				LOGGER.fatal("Exception while initializing a repository " + e);
				throw new RepositoryException(MessageType.INTERNAL_ERROR.getMessage());
			} finally {
				lock.unlock();
			}
		}
	}

	public static CityRepository getInstance() {
		return CityRepositoryInstanceHolder.INSTANCE;
	}

	@Override
	public void add(City city) throws RepositoryException {
		checkRepositoryBeenInitialized();
		cities.add(city);
	}

	@Override
	public void delete(City city) throws RepositoryException {
		checkRepositoryBeenInitialized();
		cities.remove(city);
	}

	@Override
	public void update(City city) throws RepositoryException {
		checkRepositoryBeenInitialized();
		SortedSet<City> removedCity = getCities(
				SpecificationFactory.getInstance().getQuerySpecificationById(city.getId()));
		if (removedCity.isEmpty()) {
			throw new RepositoryException(MessageType.NO_SUCH_CITY.getMessage());
		}
		cities.remove(removedCity.first());
		cities.add(city);
	}

	@Override
	public SortedSet<City> getCities(Specification specification) throws RepositoryException {
		checkRepositoryBeenInitialized();
		return specification.specify(cities);
	}

	private void checkRepositoryBeenInitialized() throws RepositoryException {
		if (!isRepositoryInitialized.get()) {
			LOGGER.fatal("Repository hasn't been initializad");
			throw new RepositoryException(MessageType.INTERNAL_ERROR.getMessage());
		}
	}
}