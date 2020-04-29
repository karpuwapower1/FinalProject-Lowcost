package by.training.karpilovich.lowcost.entity;

import java.io.Serializable;

public class Plane implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String model;
	private int placeQuantity;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getPlaceQuantity() {
		return placeQuantity;
	}

	public void setPlaceQuantity(int placeQuantity) {
		this.placeQuantity = placeQuantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + placeQuantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Plane other = (Plane) obj;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (placeQuantity != other.placeQuantity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [model=" + model + ", placeQuantity=" + placeQuantity + "]";
	}

}
