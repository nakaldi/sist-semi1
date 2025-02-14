package kims.semi1.model;

public class Manager {
	private int managerId;
	private String password;

	public Manager() {
	}

	public Manager(int managerId, String password) {
		super();
		this.managerId = managerId;
		this.password = password;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
