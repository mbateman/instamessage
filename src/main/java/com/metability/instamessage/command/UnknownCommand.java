package com.metability.instamessage.command;

public class UnknownCommand implements Command {

	private String command;

	public UnknownCommand(String command) {
		this.command = command;
	}

	@Override
	public void execute() {
        String cr = System.getProperty("os.name").matches("(W|w)indows.*") ? "\r\n" : "\n";
        StringBuilder builder = new StringBuilder();
        builder.append("Unknown command [" + command + "]");
        builder.append(cr);
        builder.append("Here are the available commands:");
        builder.append(cr);
        builder.append("<username> to list a user's messages");
        builder.append(cr);
        builder.append("<username> wall to list own messages as wells followed users' messages");
        builder.append(cr);
        builder.append("<username> -> message to post a message");
        builder.append(cr);
        builder.append("<username> follows <username> to follow another user");
        System.out.println(builder.toString());
	}
}
