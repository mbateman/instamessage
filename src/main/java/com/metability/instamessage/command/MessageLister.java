package com.metability.instamessage.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.metability.instamessage.Message;
import com.metability.instamessage.Timeline;
import com.metability.instamessage.User;

public class MessageLister {

	static void listAllMessages(User user) {
		List<Message> messages = new ArrayList<>();
		messages.addAll(user.getMessages());
		for (User following : user.getFollowing()) {
			messages.addAll(following.getMessages());
		}
		sort(messages);
		listMessages(messages, true);
	}

	private static void sort(List<Message> messages) {
		Collections.sort(messages, new Comparator<Message>() {
			public int compare(Message message1, Message message2) {
				return (int) (message2.getTime() - message1.getTime());
			}
		});
	}

	static void listMessages(List<Message> messages, boolean prefixed) {
		for (Message message : messages) {
			String prefix = prefixed ? message.getUser().getUsername() + " - " : "";
			System.out.println(
					prefix  + message.getLine() + " "
					+ Timeline.timeSince(message.getTime()));
		}
	}
}
