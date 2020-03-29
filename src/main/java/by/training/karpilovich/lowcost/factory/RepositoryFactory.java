package by.training.karpilovich.lowcost.factory;

import by.training.karpilovich.lowcost.exception.RepositoryException;
import by.training.karpilovich.lowcost.repository.CityRepository;
import by.training.karpilovich.lowcost.repository.impl.CityRepositoryImpl;

public class RepositoryFactory {
	
	private RepositoryFactory() {
	}

	private static final class RepositoryFactoryInstanceHolder {
		private static final RepositoryFactory INSTANCE = new RepositoryFactory();
	}

	public static RepositoryFactory getInstance() {
		return RepositoryFactoryInstanceHolder.INSTANCE;
	}
	
	public CityRepository getCityRepository() throws RepositoryException {
		return CityRepositoryImpl.getInstance();
	}

}
