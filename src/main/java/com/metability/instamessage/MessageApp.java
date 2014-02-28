package com.metability.instamessage;

import java.util.Scanner;

import com.metability.instamessage.command.Command;

public class MessageApp {
	

	public static void main(String[] args) {
		execute();
	}

	private static void execute() {
		Command command = new Command();
		Scanner sc = new Scanner(System.in);
		for (prompt(); sc.hasNextLine(); prompt()) {
		    String line = sc.nextLine();

			if (line.equals("exit")) {
				break;
			}

			command.execute(line);
		}
		sc.close();
	}

	private static void prompt() {
		System.out.print("> ");
	}

}
