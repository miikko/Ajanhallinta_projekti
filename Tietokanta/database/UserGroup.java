package database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class includes user groups.
 * 
 * @author Arttuhal
 * @since 27/04/2019
 */

@Entity
@Table(name="UserGroup_Table")
public class UserGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int groupId;
	
	@Column(name ="userId")
	private int userId;
	
	@Column(name ="adminId")
	private int adminId;
	
	public UserGroup() {
		super();
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	
	
	
	
}
