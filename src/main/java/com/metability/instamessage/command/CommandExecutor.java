package com.metability.instamessage.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Optional;
import com.metability.instamessage.Message;
import com.metability.instamessage.Timeline;
import com.metability.instamessage.User;
import com.metability.instamessage.Users;

public class CommandExecutor {

	private Users users;

	public CommandExecutor() {
		users = Users.init();
	}

	public void execute(String line) {
		execute(line, System.currentTimeMillis());
	}

	public void execute(String line, long time) {
		String username = line.split(" ")[0];
		
		User user = Optional.fromNullable(users.findUser(username)).or(
				users.addUser(new User(username)));

		if (line.split(" ").length == 1) {
			listMessages(user.getMessages(), false);
			return;
		}

		String command = line.split(" ")[1];
		switch (command) {
		case "wall":
			listAllMessages(user);
			break;
		case "follows":
			follow(line.split(" ")[0], line.split(" ")[2]);
			break;
		default:
			postMessage(line, time);
		}
	}

	private void listAllMessages(User user) {
		List<Message> messages = new ArrayList<>();
		messages.addAll(user.getMessages());
		for (User following : user.getFollowing()) {
			messages.addAll(following.getMessages());
		}
		sort(messages);
		listMessages(messages, true);
	}

	private void sort(List<Message> messages) {
		Collections.sort(messages, new Comparator<Message>() {
			public int compare(Message message1, Message message2) {
				int compare = 0;
				if (message1.getTime() > message2.getTime()) {
					compare = -1;
				} else if (message1.getTime() < message2.getTime()){
					compare = 1;
				}
				return compare;
			}
		});
	}
	
	private void listMessages(List<Message> messages, boolean prefixed) {
		for (Message message : messages) {
			String prefix = prefixed ? message.getUser().getUsername() + " - " : "";
			System.out.println(
					prefix  + message.getLine() + " "
					+ Timeline.timeSince(message.getTime()));
		}
	}

	private void postMessage(String line, long time) {
		if (line.contains("->")) {
			String username = line.split("->")[0].trim();
			String messageText = line.split("->")[1].trim();
			User user = users.findUser(username);
			if (!(null == user)) {
				post(messageText, user, time);
			}
		}
	}

	private void post(String messageText, User user, long time) {
		Message message = new Message(messageText, user, time);
		user.getMessages().add(message);
	}

	private void follow(String follower, String followee) {
		User followingUser = Optional.fromNullable(users.findUser(follower))
				.or(users.addUser(new User(follower)));
		User followeeUser = Optional.fromNullable(users.findUser(followee)).or(
				users.addUser(new User(followee)));
		followingUser.getFollowing().add(followeeUser);
	}
}
