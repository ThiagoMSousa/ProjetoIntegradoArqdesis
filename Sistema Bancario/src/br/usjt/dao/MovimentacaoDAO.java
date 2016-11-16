package br.usjt.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.usjt.model.Banco;
import br.usjt.model.TipoMovimentacao;
import br.usjt.to.ContaTO;
import br.usjt.to.MovimentacaoTO;

public class MovimentacaoDAO {

	public boolean novaMovimentacao(MovimentacaoTO mTO) {
		try {
			NamedParameterStatement p = new NamedParameterStatement(ConnectionFactory.openFactory());
			String SQL = "INSERT INTO movimentacao "
					+ "(fromBanco, fromNumero, fromAgencia, valor, tipoMovimentacao, date) "
					+ "VALUES (:fromBanco, :fromNumero, :fromAgencia, :valor, :tipoMovimentacao, :date);";
			p.prepareNamedParameterStatement(SQL);
			p.setString("fromBanco", mTO.getFromConta().getBanco().toString());
			p.setInt("fromNumero", mTO.getFromConta().getNumero());
			p.setInt("fromAgencia", mTO.getFromConta().getAgencia());
			p.setBigDecimal("valor", mTO.getValor());
			p.setString("tipoMovimentacao", mTO.getTipoMovimentacao().toString());
			p.setTimestamp("date", mTO.getDate());
			p.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeFactory();
		}
		return false;
	}

	public List<MovimentacaoTO> getMovimentacoes(ContaTO mTO) {
		List<MovimentacaoTO> list = new ArrayList<>();
		try {
			NamedParameterStatement p = new NamedParameterStatement(ConnectionFactory.openFactory());
			String SQL = "SELECT * FROM movimentacao "
					+ "WHERE (fromBanco=:banco AND fromNumero=:numero AND fromAgencia=:agencia);";
			p.prepareNamedParameterStatement(SQL);
			p.setString("banco", mTO.getBanco().toString());
			p.setInt("numero", mTO.getNumero());
			p.setInt("agencia", mTO.getAgencia());
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				TipoMovimentacao tipoMovimentacao = TipoMovimentacao.valueOf(rs.getString("tipoMovimentacao"));
				Banco banco = Banco.valueOf(rs.getString("fromBanco"));
				list.add(new MovimentacaoTO(new ContaTO(banco, rs.getInt("fromNumero"), rs.getInt("fromAgencia")),
						rs.getBigDecimal("valor"), tipoMovimentacao, rs.getTimestamp("date")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeFactory();
		}
		return list;
	}

	public MovimentacaoTO getMovimentacao(Integer id) {
		MovimentacaoTO mTO = null;
		try {
			NamedParameterStatement p = new NamedParameterStatement(ConnectionFactory.openFactory());
			String SQL = "SELECT * FROM movimentacao "
					+ "WHERE id=:id;";
			p.prepareNamedParameterStatement(SQL);
			p.setInt("id", id);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				TipoMovimentacao tipoMovimentacao = TipoMovimentacao.valueOf(rs.getString("tipoMovimentacao"));
				Banco banco = Banco.valueOf(rs.getString("fromBanco"));
				mTO = new MovimentacaoTO(new ContaTO(banco, rs.getInt("fromNumero"), rs.getInt("fromAgencia")),
						rs.getBigDecimal("valor"), tipoMovimentacao, rs.getTimestamp("date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeFactory();
		}
		return mTO;
	}
}
