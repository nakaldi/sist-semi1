package kims.semi1.model;

public class Building {
	private int buildingId;
	private String name;
	
	public Building() {
	}

	public Building(int buildingId, String name) {
		super();
		this.buildingId = buildingId;
		this.name = name;
	}

	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
