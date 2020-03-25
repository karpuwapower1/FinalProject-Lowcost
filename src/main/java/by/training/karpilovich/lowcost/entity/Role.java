package by.training.karpilovich.lowcost.entity;

import java.io.Serializable;

public enum Role implements Serializable {

	ADMIN(0), USER(1), GUEST(2);
	
	private int id;
	
	private Role(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

}
