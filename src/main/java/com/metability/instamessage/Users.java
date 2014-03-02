package com.metability.instamessage;

import java.util.ArrayList;
import java.util.List;

public class Users {

	private static Users users;
	private List<User> userList = new ArrayList<>();

	private Users() {
	}
	
	public static Users init() {
		users = null;
		return getInstance();
	}
	
	public static Users getInstance() {
		if (users == null) {
			users = new Users();
		}
		return users;
	}

	public List<User> getUserList() {
		return userList;
	}

	public User addUser(User user) {
		userList.add(user);
		return user;
	}

	public User getUser(User user) {
		return userList.get(userList.indexOf(user));
	}

	public User findUser(String username) {
		for (User user : userList) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

}
