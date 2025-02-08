package kims.semi1.model;

public class Unit {
	private String unitId;
	private int buildingId;

	public Unit(String unitId, int buildingId) {
		super();
		this.unitId = unitId;
		this.buildingId = buildingId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

}
