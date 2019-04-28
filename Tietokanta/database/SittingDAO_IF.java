package database;

import java.time.LocalDateTime;
import java.util.Set;

interface SittingDAO_IF {

	boolean createSitting(Sitting sitting);
	
	boolean updateSitting(Sitting sitting);
	
	Set<Sitting> readSittings(LocalDateTime start_date, LocalDateTime end_date, int userId);
	
	boolean createWindowTime(WindowTime wt);
	
	boolean updateWindowTime(WindowTime wt);

	Set<WindowTime> readWindowTimes(int weekday, int userId);
}
