package by.training.karpilovich.lowcost.builder;

import by.training.karpilovich.lowcost.entity.Plane;

public class PlaneBuilder {

	private Plane plane;

	public PlaneBuilder() {
		plane = new Plane();
	}

	public PlaneBuilder setPlaneModel(String model) {
		plane.setModel(model);
		return this;
	}

	public PlaneBuilder setPlanePlaceQuantity(int quantity) {
		plane.setPlaceQuantity(quantity);
		return this;
	}

	public Plane getPlane() {
		return plane;
	}
}