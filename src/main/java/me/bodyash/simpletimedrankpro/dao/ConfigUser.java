package me.bodyash.simpletimedrankpro.dao;

public interface ConfigUser {
	void loadUsersConfig();
	void createUsersConfig();
	public void addUser(User u);
	public User getUserData(String username);
	public void setUserTimeExpired(String name);

}
