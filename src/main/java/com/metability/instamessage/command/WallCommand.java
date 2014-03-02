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
	public String execute() {
		String username = command.split(" ")[0];
		User user = Optional.fromNullable(Users.getInstance().findUser(username)).or(
		Users.getInstance().addUser(new User(username)));
		MessageLister.listAllMessages(user);
		return "";
	}
}
