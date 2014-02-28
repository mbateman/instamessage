package com.metability.instamessage;

public class Timeline {

	public static String timeSince(long postTime) {
		long now = System.currentTimeMillis();
		long elapsed = now - postTime;
		int elapsedTime = convertToMinutes(elapsed);
		if (elapsedTime < 1) {
			int seconds = convertToSeconds(elapsed);
			return "(" +seconds + " seconds ago)";
		} else if (elapsedTime >= 1 && elapsedTime < 2) {
			return "(1 minute ago)";
		} else {
			return "(" + elapsedTime + " minutes ago)";
		}
	}

	private static int convertToSeconds(long timeInMillis) {
		return (int) (timeInMillis / 1000);
	}

	private static int convertToMinutes(long timeInMillis) {
		return (int) (timeInMillis / (1000 * 60));
	}
	
	public static long secondsAgo(int seconds) {
		return System.currentTimeMillis() - (1000 * seconds);
	}

	public static long minutesAgo(int minutes) {
		return System.currentTimeMillis() - (1000 * 60 * minutes);
	}
}
