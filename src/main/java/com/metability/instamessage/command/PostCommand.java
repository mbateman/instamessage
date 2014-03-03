package com.metability.instamessage.command;

import com.google.common.base.Optional;
import com.metability.instamessage.Message;
import com.metability.instamessage.User;
import com.metability.instamessage.Users;

public class PostCommand implements Command {

	private String command;
	private long time;

	public PostCommand(String command, long time) {
		this.command = command;
		this.time = time;
	}

	@Override
	public void execute() {
		postMessage(command, time);
	}

	private void postMessage(String line, long time) {
		Users users = Users.getInstance();
		if (line.contains("->")) {
			String username = line.split("->")[0].trim();
			String messageText = line.split("->")[1].trim();
			User user = Optional.fromNullable(
				users.findUser(username)).or(users.addUser(new User(username)));
			post(messageText, user, time);
		}
	}

	private void post(String messageText, User user, long time) {
		Message message = new Message(messageText, user, time);
		user.getMessages().add(message);
	}

}
