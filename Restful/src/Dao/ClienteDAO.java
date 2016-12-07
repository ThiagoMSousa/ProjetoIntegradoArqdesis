package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
	
	public static Model.Cliente consultar(int codigo) throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM Cliente WHERE cliCodigo = ?";
		Connection con = Factory.ConnectionFactory.obtemConexao();
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, codigo);
		ResultSet rs = st.executeQuery();
		Model.Cliente cliente = null;
		while (rs.next()) {
			cliente = new Model.Cliente(
				rs.getInt("cliCodigo"),
				rs.getString("cliNome"), 
				rs.getString("cliAgencia"),
				rs.getString("cliConta"), 
				rs.getDouble("cliSaldo"), 
				rs.getString("cliBanco")
			);
		}
		con.close();
		return cliente;
	}

	public static void alterarSaldo(To.ClienteTO cliente) throws Exception {
		Connection con = null;
		try {
			con = Factory.ConnectionFactory.obtemConexao();
			String sql = "UPDATE Cliente SET cliSaldo = ? WHERE cliCodigo = ?;";
			PreparedStatement st = con.prepareStatement(sql);
			st.setDouble(1, cliente.getSaldo());
			st.setInt(2, cliente.getCodigo());
			st.executeUpdate();
			con.commit();
			con.close();
		} catch (Exception e) {
			try {
				con.rollback();
				con.close();
			} catch (Exception e1) {
				throw new Exception();
			}
			throw new Exception();
		}
	}
}