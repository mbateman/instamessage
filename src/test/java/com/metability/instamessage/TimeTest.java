package com.metability.instamessage;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class TimeTest {

	@Test
	public void shouldBeAbleToCountTime() {
		long second = 1000;
		long postTime = System.currentTimeMillis() - (second * 30);
		assertEquals("(30 seconds ago)", Timeline.timeSince(postTime));
		postTime = System.currentTimeMillis() - (second * 90);
		assertEquals("(1 minute ago)", Timeline.timeSince(postTime));
		postTime = System.currentTimeMillis() - (second * 180);
		assertEquals("(3 minutes ago)", Timeline.timeSince(postTime));
	}
	
	@Test
	public void listMessages() throws InterruptedException {
		List<Message> messages = new ArrayList<>();
		Message message1 = new Message("Last", new User("John"), System.currentTimeMillis() - 1500);
		Message message2 = new Message("Middle", new User("Jim"), System.currentTimeMillis() - 1000);
		Message message3 = new Message("First", new User("Jean"), System.currentTimeMillis());
		messages.add(message1);
		messages.add(message2);
		messages.add(message3);

		Collections.sort(messages, new Comparator<Message>() {
			@Override
			public int compare(Message message1, Message message2) {
				return (int) (message2.getTime() - message1.getTime());
			}
		});
		
		assertThat(messages.get(0).getLine().startsWith("First"), is(true));
		assertThat(messages.get(1).getLine().startsWith("Middle"), is(true));
		assertThat(messages.get(2).getLine().startsWith("Last"), is(true));
	}
}
