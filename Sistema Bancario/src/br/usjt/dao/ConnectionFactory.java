package br.usjt.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import br.usjt.util.Utils;

public class ConnectionFactory {
	private static Connection conn = null;

	public static Connection openFactory() {
		try {
				Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(Utils.rb.getString("DB_URL") + Utils.rb.getString("DB_NAME"),
					Utils.rb.getString("DB_USER"), Utils.rb.getString("DB_PASSWORD"));
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean closeFactory() {
		try {
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}