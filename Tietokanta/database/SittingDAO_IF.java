package database;

import java.util.Date;
import java.util.Set;

interface SittingDAO_IF {

	boolean createSitting(Sitting sitting);
	
	boolean updateSitting(Sitting sitting);
	
	Set<Sitting> readSittings(Date start_date, Date end_date, int userId);
	
	boolean createWindowTime(WindowTime wt);
	
	boolean updateWindowTime(WindowTime wt);
}
