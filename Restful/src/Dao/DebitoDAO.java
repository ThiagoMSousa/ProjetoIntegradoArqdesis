package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class DebitoDAO {

	public void cadastrar(To.DebitoTO debito) throws Exception{
		Connection con = null;
		try{
			String sql = "INSERT INTO Debito VALUES (?,?,?,?,?,?, null)";
			con = Factory.ConnectionFactory.obtemConexao();
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, 0);
			pstm.setDate(2, new java.sql.Date(debito.getData().getTime()));
			pstm.setInt(3, debito.getOperadora());
			pstm.setInt(4, debito.getConsumidor());
			pstm.setDouble(5, debito.getValor());
			pstm.setInt(6, debito.getCodigoCliente());
			pstm.execute();
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

	public ArrayList<Model.Debito> consultarEmAberto() throws Exception {
		Connection con = null;
		try {
			ArrayList<Model.Debito> array = new ArrayList<Model.Debito>();
			String sql = "SELECT * FROM Debito WHERE opeCodigo IS NULL";
			con = Factory.ConnectionFactory.obtemConexao();
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Model.Debito debito = new Model.Debito(
					rs.getInt("debCodigo"),
					new Date(rs.getDate("debData").getTime()),
					rs.getInt("debOperadora"),
					rs.getInt("debConsumidor"),
					rs.getDouble("debValor"),
					rs.getInt("cliCodigo"),
					0
				);
				array.add(debito);
			}
			con.close();
			return array;
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

	public static void pagar(To.DebitoTO debito) throws Exception {
		Connection con = null;
		try {
			con = Factory.ConnectionFactory.obtemConexao();
			String sql = "UPDATE Debito SET opeCodigo = ? WHERE debCodigo = ?;";
			PreparedStatement st = con.prepareStatement(sql);
			st.setDouble(1, debito.getCodigoOperacao());
			st.setInt(2, debito.getCodigo());
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