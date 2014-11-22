package bean;
import javax.persistence.* ;

/**
 * @author nhchdhr
 * 
 * This class represents the Research Group entity and provides getters and setters for its fields
 * Student entity is mapped to Research_Group table in the database 
 *
 */

@Entity
@Table(name="RESEARCH_GROUP")
public class ResearchGroup {

	@Id
	@Column(name="GROUP_ID")
	private int groupId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
