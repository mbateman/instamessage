package com.metability.instamessage;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.metability.instamessage.command.CommandFactory;

public class CommandTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private CommandFactory factory;

	@Before
	public void setUp() {
	    System.setOut(new PrintStream(outContent));
	    factory = CommandFactory.getInstance();
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
		factory.getCommand("Michael -> Hi There!").execute();
		factory.getCommand("Michael").execute();
		assertEquals("Hi There! (0 seconds ago)\n", outContent.toString());
	}

	@Test public void 
	userStartsFollowing() {
		factory.getCommand("Michelle -> Hi There!").execute();
		factory.getCommand("Michael follows Michelle").execute();
		factory.getCommand("Michelle").execute();
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

		factory.getCommand("Alice -> I love the weather today", Timeline.minutesAgo(5)).execute();
		factory.getCommand("Bob -> Oh, we lost!", Timeline.minutesAgo(1)).execute();
		factory.getCommand("Bob -> at least it's sunny", Timeline.minutesAgo(2)).execute();
		factory.getCommand("Alice").execute();
		assertEquals("I love the weather today (5 minutes ago)\n", outContent.toString());
		outContent.reset();
		factory.getCommand("Bob").execute();
		assertEquals(
				"Oh, we lost! (1 minute ago)\n"
				+ "at least it's sunny (2 minutes ago)\n", outContent.toString());
		outContent.reset();
		factory.getCommand("Charlie -> I'm in New York today! Anyone wants to have a coffee?", Timeline.secondsAgo(2)).execute();
		factory.getCommand("Charlie follows Alice").execute();
		factory.getCommand("Charlie wall").execute();
		assertEquals(
				"Charlie - I'm in New York today! Anyone wants to have a coffee? (2 seconds ago)\n"
				+ "Alice - I love the weather today (5 minutes ago)\n", outContent.toString());
		outContent.reset();
		factory.getCommand("Charlie follows Bob").execute();
		Thread.sleep(1000);
		factory.getCommand("Charlie wall").execute();
		assertEquals(
				"Charlie - I'm in New York today! Anyone wants to have a coffee? (3 seconds ago)\n" +
				"Bob - Oh, we lost! (1 minute ago)\n" + 
				"Bob - at least it's sunny (2 minutes ago)\n" + 
				"Alice - I love the weather today (5 minutes ago)\n", outContent.toString());
	}

}
