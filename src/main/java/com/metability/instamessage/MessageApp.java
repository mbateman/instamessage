package com.metability.instamessage;

import java.util.Scanner;

import com.metability.instamessage.command.CommandFactory;

public class MessageApp {
	

	public static void main(String[] args) {
		execute();
	}

	private static void execute() {
		Scanner sc = new Scanner(System.in);
		CommandFactory factory = CommandFactory.getInstance();
		for (prompt(); sc.hasNextLine(); prompt()) {
		    String line = sc.nextLine();

			if (line.equals("exit")) {
				break;
			}

			factory.getCommand(line).execute();
		}
		sc.close();
	}

	private static void prompt() {
		System.out.print("> ");
	}

}
