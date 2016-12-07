package Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static Connection obtemConexao() throws SQLException {
		//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/BCPN_D1?user=root");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/bcpn_d1", "alunos", "alunos");
		conn.setAutoCommit(false);
		return conn;
	}
}
