package kims.semi1.model;

public class ClassSchedule {
	private int scheduleId;
	private int courseId;
	private String dayOfWeek;
	private String startTime;
	private String endTime;
	private String unit;

	public ClassSchedule() {
	}

	public ClassSchedule(int scheduleId, int courseId, String dayOfWeek, String startTime, String endTime) {
		super();
		this.scheduleId = scheduleId;
		this.courseId = courseId;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public ClassSchedule(int scheduleId, int courseId, String dayOfWeek, String startTime, String endTime,
			String unit) {
		super();
		this.scheduleId = scheduleId;
		this.courseId = courseId;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
		this.unit = unit;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
