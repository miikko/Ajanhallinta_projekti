package controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import database.DatabaseHandler;
import database.Sitting;

public class HistoryController {
	private LocalDate sDate;
	private LocalDate eDate;
	private List<Integer> userIds;
	private DatabaseHandler dbHandler;

	public HistoryController() {
		this.dbHandler = DatabaseHandler.getInstance();
		userIds = new ArrayList<Integer>();
		sDate = LocalDate.now();
		eDate = LocalDate.now();
	}

	public LocalDate getStartDate() {
		return sDate;
	}

	public void setStartDate(LocalDate sDate) {
		this.sDate = sDate;
	}

	public LocalDate getEndDate() {
		return eDate;
	}

	public void setEndDate(LocalDate eDate) {
		this.eDate = eDate;
	}

	public void setDataSource(List<Integer> userIds) {
		this.userIds = userIds;
	}

	public void setDataSource(int userId) {
		this.userIds = Arrays.asList(userId);
	}

	public Set<Sitting> getData() {
		if (userIds == null) {
			return null;
		}
		Set<Sitting> data = new HashSet<>();
		LocalDateTime sDateTime = LocalDateTime.of(sDate, LocalTime.MIN);
		LocalDateTime eDateTime = LocalDateTime.of(eDate.plusDays(1), LocalTime.MIN);
		for (int userId : userIds) {
			data.addAll(dbHandler.fetchSittings(sDateTime, eDateTime, userId));
		}
		return data;
	}
}
