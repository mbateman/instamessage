package com.metability.instamessage.command;

import com.google.common.base.Optional;
import com.metability.instamessage.User;
import com.metability.instamessage.Users;

public class FollowCommand implements Command {

	private String command;

	public FollowCommand(String command) {
		this.command = command;
	}

	@Override
	public String execute() {
		follow(command.split(" ")[0], command.split(" ")[2]);
		return "";
	}

	private void follow(String follower, String followee) {
		Users users = Users.getInstance();
		User followingUser = Optional.fromNullable(users.findUser(follower))
				.or(users.addUser(new User(follower)));
		User followeeUser = Optional.fromNullable(users.findUser(followee)).or(
				users.addUser(new User(followee)));
		followingUser.getFollowing().add(followeeUser);
	}
}
