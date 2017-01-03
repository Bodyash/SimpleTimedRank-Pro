package me.bodyash.simpletimedrankpro.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import me.bodyash.simpletimedrankpro.utils.ConfigUtil;

public class ConfigUserSql implements ConfigUser {

	private String IP;
	private String port;
	private String user;
	private String password;
	private String tableName;
	private String DbName;
	private ConfigUtil config;

	public ConfigUserSql(ConfigUtil config) {
		this.IP = config.getDatabaseIp();
		this.port = config.getDatabasePort();
		this.user = config.getDatabaseUser();
		this.password = config.getDatabasePass();
		this.tableName = config.getDatabaseTable();
		this.DbName = config.getDatabaseDB();
		this.config = config;
	}

	public boolean test() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection connection = DriverManager.getConnection("jdbc:mysql://" + this.IP + ":" + this.port + "/",
				this.user, this.password)) {
			if (connection != null) {
				Bukkit.getLogger().log(Level.INFO, config.getConsoleLogo() + "You made it, take control your database now!");
				this.createUsersConfig();
				return true;
			} else {
				Bukkit.getLogger().log(Level.INFO, config.getConsoleLogo() + "Failed to make connection!");
				return false;
			}
		} catch (SQLException e) {
			Bukkit.getLogger().log(Level.INFO, config.getConsoleLogo() + "Connection Failed! Check output console");
			e.printStackTrace();
			return false;
		}
	}

	public boolean checkIfUserExist(String username) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://" + this.IP + ":" + this.port + "/" + this.DbName, this.user, this.password);
				ResultSet rs = conn
						.prepareStatement("SELECT * FROM " + this.tableName + " WHERE username='" + username + "'")
						.executeQuery()) {
			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public void loadUsersConfig() {
		// Just Empty Method :c

	}

	@Override
	public void createUsersConfig() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Create DB if not exist
		String dbCreate = "CREATE DATABASE IF NOT EXISTS " + this.DbName;
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://" + this.IP + ":" + this.port + "/", this.user,
				this.password); Statement stmt = conn.createStatement()) {
			stmt.execute(dbCreate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// create table in database if not exist
		String sqlCreate = "CREATE TABLE IF NOT EXISTS " + this.tableName
				+ "  (id INT(10) primary key auto_increment not null," + "   username VARCHAR(20) not null,"
				+ "   fromdate bigint not null," + "   untildate bigint not null," + "   oldrank varchar(30) not null,"
				+ "   newrank varchar(30) not null," + "   status int(2) not null )";
		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://" + this.IP + ":" + this.port + "/" + this.DbName, this.user, this.password);
				Statement stmt = conn.createStatement()) {
			stmt.execute(sqlCreate);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Just an SQL Script
		/*
		 * create table users( id INT(10) primary key auto_increment not null,
		 * username VARCHAR(20) not null, fromdate bigint not null, untildate
		 * bigint not null, oldrank varchar(30) not null, newrank varchar(30)
		 * not null, status int(2) not null );
		 */

	}

	@Override
	public void addUser(User u) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (this.checkIfUserExist(u.getUserName())) {
			// Update
			try (Connection conn = DriverManager.getConnection(
					"jdbc:mysql://" + this.IP + ":" + this.port + "/" + this.DbName, this.user, this.password);
					Statement ps = conn.createStatement()) {
				ps.execute("UPDATE " + this.tableName + " SET fromdate=" + u.getFromDate() + ", untildate="
						+ u.getUntilDate() + ", oldrank='" + u.getOldRank() + "', newrank='" + u.getPromotedRank()
						+ "', status=1 WHERE username='" + u.getUserName() + "'");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Add new User (not exist in DB)
			try (Connection conn = DriverManager.getConnection(
					"jdbc:mysql://" + this.IP + ":" + this.port + "/" + this.DbName, this.user, this.password);
					PreparedStatement ps = conn.prepareStatement("Insert into " + this.tableName
							+ " (username, fromdate, untildate, oldrank, newrank, status) values (?, ?, ?, ?, ?, ?)")) {
				ps.setString(1, u.getUserName());
				ps.setLong(2, u.getFromDate());
				ps.setLong(3, u.getUntilDate());
				ps.setString(4, u.getOldRank());
				ps.setString(5, u.getPromotedRank());
				ps.setInt(6, 1);

				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public User getUserData(String username) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://" + this.IP + ":" + this.port + "/" + this.DbName, this.user, this.password);
				ResultSet rs = conn
						.prepareStatement("SELECT * FROM " + this.tableName + " WHERE username='" + username + "'")
						.executeQuery()) {
			if (rs.next()) {
				return new User(rs.getString("username"), rs.getLong("untildate"), rs.getLong("fromdate"),
						rs.getString("newrank"), rs.getString("oldrank"), rs.getInt("status"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void setUserTimeExpired(String name) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://" + this.IP + ":" + this.port + "/" + this.DbName, this.user, this.password);
				Statement st = conn.createStatement()) {
			st.execute("UPDATE " + this.tableName + " SET status=-1 WHERE username='" + name + "'");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
