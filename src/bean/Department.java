package bean;


public class Department {
	private int deptId;
	private String name;
	private String mission;
	private String location;
	private String url;
	
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMission() {
		return mission;
	}
	public void setMission(String mission) {
		this.mission = mission;
	}
	
	@Override
	public String toString() {
		
		StringBuffer  sb = new StringBuffer();
		sb.append("The Department details are : " + this.deptId);
		sb.append(" Name : " + name);
		sb.append(" Mission : " + mission + "\n");
		return sb.toString();
	}

}
