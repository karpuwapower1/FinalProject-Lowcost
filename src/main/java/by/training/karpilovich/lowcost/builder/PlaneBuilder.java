package by.training.karpilovich.lowcost.builder;

import by.training.karpilovich.lowcost.entity.Plane;

public class PlaneBuilder {
	
	private Plane plane;

	public PlaneBuilder() {
		plane = new Plane();
	}
	
	public void setPlaneModel(String model) {
		plane.setModel(model);
	}
	
	public void setPlanePlaceQuantity(int quantity) {
		plane.setPlaceQuantity(quantity);
	}
	
	public Plane getPlane() {
		return plane;
	}

}
