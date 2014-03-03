package com.metability.instamessage.command;

import com.google.common.base.Optional;
import com.metability.instamessage.User;
import com.metability.instamessage.Users;

public class WallCommand implements Command {

	private String command;

	public WallCommand(String command) {
		this.command = command;
	}

	@Override
	public void execute() {
		String username = command.split(" ")[0];
		Users users = Users.getInstance();
		User user = Optional.fromNullable(
			users.findUser(username)).or(users.addUser(new User(username)));
		MessageLister.listAllMessages(user);
	}
}
