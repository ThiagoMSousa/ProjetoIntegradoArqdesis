package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;

public class OperacaoDAO {

	public int cadastrar(To.OperacaoTO operacao) throws Exception{
		Connection con = null;
		try{
			String sql = "INSERT INTO Operacao VALUES (?,?,?,?,?)";
			con = Factory.ConnectionFactory.obtemConexao();
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, 0);
			pstm.setDate(2, new java.sql.Date(operacao.getData().getTime()));
			pstm.setDouble(3, operacao.getValor());
			pstm.setInt(4, operacao.getTipo());
			pstm.setInt(5, operacao.getCodigoCliente());
			pstm.execute();
			con.commit();
			con.close();
			return consultarUltimaOperacao();
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

	public void cadastrarTransferencia(To.TransferenciaTO transferencia) throws Exception {
		Connection con = null;
		try {
			String sql = "INSERT INTO Transferencia VALUES (?,?,?,?)";
			con = Factory.ConnectionFactory.obtemConexao();
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, 0);
			pstm.setInt(2, consultarUltimaOperacao());
			pstm.setString(3, transferencia.getContaDestino());
			pstm.setString(4, transferencia.getAgenciaDestino());
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

	public int consultarUltimaOperacao() throws Exception {
		Connection con = null;
		try {
			int codigo = -1;
			String sql = "SELECT MAX(opeCodigo) AS opeCodigo FROM Operacao";
			con = Factory.ConnectionFactory.obtemConexao();
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				codigo = rs.getInt("opeCodigo");
			}
			con.close();
			return codigo;
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

	public ArrayList<To.OperacaoTO> consultarExtrato(int id, int dias) throws Exception {
		Connection con = null;
		try {
			ArrayList<To.OperacaoTO> array = new ArrayList<To.OperacaoTO>();
			Date inicio = new Date(), fim = retrocedeOsDias(new Date(), dias);
			String sql = "SELECT * FROM operacao WHERE (cliCodigo = ?) AND (opeData BETWEEN ? AND ?) ORDER BY opeCodigo";
			con = Factory.ConnectionFactory.obtemConexao();
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			st.setDate(2, new java.sql.Date(fim.getTime()));
			st.setDate(3, new java.sql.Date(inicio.getTime()));
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				To.OperacaoTO to = new To.OperacaoTO(
					rs.getInt("opeCodigo"),
					rs.getInt("cliCodigo"),
					rs.getDate("opeData"),
					rs.getDouble("opeValor"),
					rs.getInt("opeTipo")
				);
				array.add(to);
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

	public Map<String, Object> consultarOperacoesDiarias() throws Exception {
		Connection con = null;
		Map<String, Object> itens = new LinkedHashMap<String, Object>();
		for (int i = 0; i < 6; i++){
			try {
				Date inicio = new Date();
				String sql = "";
				if (i == 0){
					sql = "SELECT COUNT(opeCodigo) AS total_op" + i + " FROM operacao WHERE opeData = ?;";
				}else{
					sql = "SELECT COUNT(opeCodigo) AS total_op" + i + " FROM operacao WHERE opeData = ? AND opeTipo = " + i + ";";
				}
				con = Factory.ConnectionFactory.obtemConexao();
				PreparedStatement st = con.prepareStatement(sql);
				st.setDate(1, new java.sql.Date(inicio.getTime()));
				ResultSet rs = st.executeQuery();
				while (rs.next()) {
					itens.put("totalOp" + i, rs.getInt("total_op" + i));
				}
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
		return itens;
	}

	public Date retrocedeOsDias(Date data, int dias) {
		Calendar dataNova = Calendar.getInstance();
		dataNova.setTime(data);
		dataNova.add(GregorianCalendar.DAY_OF_MONTH, -dias);
		return dataNova.getTime();
	}
}