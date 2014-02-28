package com.metability.instamessage;

public class Message {

	private String line;
	private User user;
	private long time;

	public Message(String line, User user, long time) {
		this.line = line;
		this.user = user;
		this.time = time;
	}

	public String getLine() {
		return line;
	}

	public User getUser() {
		return user;
	}

	public long getTime() {
		return time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (time ^ (time >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (time != other.time)
			return false;
		return true;
	}
}
