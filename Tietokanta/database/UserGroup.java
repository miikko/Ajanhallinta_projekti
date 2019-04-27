package database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
@Table(name = "UserGroup_Table")
public class UserGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int groupId;

	@Column(name = "userId")
	private int userId;

	@Column(name = "adminId")
	private int adminId;

	@Column(name = "groupName")
	private String groupName;

	@ElementCollection
	@Column(name = "userIds")
	private List<Integer> userIds = new ArrayList<Integer>();

	public UserGroup() {
		super();
	}

	public UserGroup(int adminId, String groupName) {
		super();
		this.adminId = adminId;
		this.groupName = groupName;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public void addUserId(int userId) {
		userIds.add(userId);
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
