package se.user.post;

import java.util.List;
import java.util.Map;

public class CalendarDay {
	public String date;
	public long currentVersion;
	public long previousVersion;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public long getCurrentVersion() {
		return currentVersion;
	}
	public void setCurrentVersion(long currentVersion) {
		this.currentVersion = currentVersion;
	}
	public long getPreviousVersion() {
		return previousVersion;
	}
	public void setPreviousVersion(long previousVersion) {
		this.previousVersion = previousVersion;
	}

}
