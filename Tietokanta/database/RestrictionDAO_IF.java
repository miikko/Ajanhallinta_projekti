package database;

import java.util.List;

interface RestrictionDAO_IF {

	boolean createRestriction(Restriction restriction);
	
	boolean updateRestriction(Restriction restriction);
	
	boolean deleteRestriction(Restriction restriction);
	
	List<Restriction> readRestrictions(int userId);
	
	List<Restriction> readRestrictions(String weekday, int userId);
	
	Restriction readRestriction(String progName, String weekday, int userId);
}
