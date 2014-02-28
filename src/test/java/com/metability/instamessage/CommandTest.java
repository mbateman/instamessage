package com.metability.instamessage;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.metability.instamessage.command.Command;

public class CommandTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private Command command;

	@Before
	public void setUp() {
	    System.setOut(new PrintStream(outContent));
	    command = new Command();
	}

	@After
	public void cleanUp() {
	    System.setOut(null);
	}

//	User submits commands to the application: 
//		posting: <user name> -> <message> 
//		reading: <user name> 
//		following: <user name> follows <another user> 
//		wall: <user name> wall 
		
	@Test public void 
	userPostsAndReadsMessage() {
		command.execute("Michael -> Hi There!");
		command.execute("Michael");
		assertEquals("Hi There! (0 seconds ago)\n", outContent.toString());
	}

	@Test public void 
	userStartsFollowing() {
		command.execute("Michelle -> Hi There!");
		command.execute("Michael follows Michelle");
		command.execute("Michelle");
		assertEquals("Hi There! (0 seconds ago)\n", outContent.toString());
	}

	@Test public void 
	userCanReadFollowedUsersMessages() throws InterruptedException {
	/* > Alice -> I love the weather today 
		> Bob -> Oh, we lost! 
		> Bob -> at least it's sunny

		> Alice
		I love the weather today (5 minutes ago)
		> Bob
		Oh, we lost! (1 minute ago) 
		at least it's sunny (2 minutes ago)

		> Charlie -> I'm in New York today! Anyone wants to have a coffee? 
		> Charlie follows Alice 
		> Charlie wall
		Charlie - I'm in New York today! Anyone wants to have a coffee? (2 seconds ago) 
		Alice - I love the weather today (5 minutes ago)

		> Charlie follows Bob 
		> Charlie wall
		Charlie - I'm in New York today! Anyone wants to have a coffee? (15 seconds ago) 
		Bob - Oh, we lost! (1 minute ago) 
		Bob - at least it's sunny (2 minutes ago) 
		Alice - I love the weather today (5 minutes ago) */

		command.execute("Alice -> I love the weather today", Timeline.minutesAgo(5));
		command.execute("Bob -> Oh, we lost!", Timeline.minutesAgo(1));
		command.execute("Bob -> at least it's sunny", Timeline.minutesAgo(2));
		command.execute("Alice");
		assertEquals("I love the weather today (5 minutes ago)\n", outContent.toString());
		outContent.reset();
		command.execute("Bob");
		assertEquals(
				"Oh, we lost! (1 minute ago)\n"
				+ "at least it's sunny (2 minutes ago)\n", outContent.toString());
		outContent.reset();
		command.execute("Charlie -> I'm in New York today! Anyone wants to have a coffee?", Timeline.secondsAgo(2));
		command.execute("Charlie follows Alice");
		command.execute("Charlie wall");
		assertEquals(
				"Charlie - I'm in New York today! Anyone wants to have a coffee? (2 seconds ago)\n"
				+ "Alice - I love the weather today (5 minutes ago)\n", outContent.toString());
		outContent.reset();
		command.execute("Charlie follows Bob");
		Thread.sleep(1000);
		command.execute("Charlie wall");
		assertEquals(
				"Charlie - I'm in New York today! Anyone wants to have a coffee? (3 seconds ago)\n" +
				"Bob - Oh, we lost! (1 minute ago)\n" + 
				"Bob - at least it's sunny (2 minutes ago)\n" + 
				"Alice - I love the weather today (5 minutes ago)\n", outContent.toString());
	}

}
