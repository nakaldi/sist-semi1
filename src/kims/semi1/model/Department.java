package kims.semi1.model;

public class Department {
	private int departmentId;
	private String name;
	private String phone;
	private int buildingId;

	public Department(int departmentId, String name, String phone, int buildingId) {
		super();
		this.departmentId = departmentId;
		this.name = name;
		this.phone = phone;
		this.buildingId = buildingId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

}
