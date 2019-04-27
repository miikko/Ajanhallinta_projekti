package database;

import java.util.List;

public interface UserGroupDAO_IF {

	boolean createGroup (UserGroup userGroup);

	boolean updateGroup(UserGroup userGroup);
	
	List<UserGroup> readGroups (int adminId);
	
//	boolean addUser(int userId);
//
//	boolean addAdmin(int adminId);
//	
}
