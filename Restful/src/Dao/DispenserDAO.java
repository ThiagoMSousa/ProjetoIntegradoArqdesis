package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DispenserDAO {

	public static To.DispenserTO consultarNotas() throws Exception{
		Connection con = null;
		try{
			con = Factory.ConnectionFactory.obtemConexao();
			String sql = "SELECT * FROM DISPENSER";
			ResultSet rs = con.prepareStatement(sql).executeQuery();
			To.DispenserTO to = null;
			if (rs.next()){
				to = new To.DispenserTO(
					rs.getInt("disNota10"),
					rs.getInt("disNota20"),
					rs.getInt("disNota50")
				);
			}
			con.close();
			return to;
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

	public static void alterarNotas(To.DispenserTO dispenser) throws Exception {
		Connection con = null;
		try{
			con = Factory.ConnectionFactory.obtemConexao();
			String sql = "UPDATE DISPENSER SET disNota10 = ?, disNota20 = ?, disNota50 = ?;";
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, dispenser.getNota10());
			st.setInt(2, dispenser.getNota20());
			st.setInt(3, dispenser.getNota50());
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