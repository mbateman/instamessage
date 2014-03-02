package com.metability.instamessage.command;

import com.metability.instamessage.Users;

public class CommandFactory {

	private static CommandFactory factory;

	public static CommandFactory getInstance() {
		if (factory != null) {
			return factory;
		}
		return new CommandFactory();
	}
	
	public CommandFactory() {
		Users.init();
	}

	public Command getCommand(String command) {
		return getCommand(command, System.currentTimeMillis());
	}
	
	public Command getCommand(String command, long time) {
		if (command.split(" ").length == 1) {
			return new UserMessagesCommand(command);
		} else if (command.contains("wall")) {
			return new WallCommand(command);
		} else if (command.contains("follows")) {
			return new FollowCommand(command);
		} else if (command.contains("->")) {
			return new PostCommand(command, time);
		} else {
			return new UnknownCommand(command);
		}
	}
}
