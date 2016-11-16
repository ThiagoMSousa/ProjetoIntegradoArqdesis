package br.usjt.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import br.usjt.model.Banco;
import br.usjt.model.TipoMovimentacao;
import br.usjt.to.ContaTO;
import br.usjt.to.MovimentacaoTO;

public class ContaDAO {

	public void novaConta(ContaTO to) {
		NamedParameterStatement p = new NamedParameterStatement(ConnectionFactory.openFactory());
		try {
			if (exists(to)) {
				getNumeroConta(to);
			} else {
				// Create a Conta
				String SQL = "INSERT INTO conta (banco,agencia,senha,titular,saldo) "
						+ "VALUES (:banco, :agencia, :senha, :titular, :saldo);";

				p.prepareNamedParameterStatement(SQL);
				p.setString("banco", to.getBanco().toString());
				p.setInt("agencia", to.getAgencia());
				p.setInt("senha", to.getSenha());
				p.setString("titular", to.getTitular());
				p.setBigDecimal("saldo", to.getSaldo());
				p.execute();
				getNumeroConta(to);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateSenha(ContaTO to, Integer newSenha) {
		NamedParameterStatement p = new NamedParameterStatement(ConnectionFactory.openFactory());
		try {
			if (exists(to) && isValidPassword(to)) {
				String SQL = "UPDATE conta SET senha=:newSenha "
						+ "WHERE banco=:banco AND numero=:numero AND agencia=:agencia AND titular=:titular;";
				p.prepareNamedParameterStatement(SQL);
				p.setInt("newSenha", newSenha);
				p.setString("banco", to.getBanco().toString());
				p.setInt("numero", to.getNumero());
				p.setInt("agencia", to.getAgencia());
				p.setString("titular", to.getTitular());
				p.execute();
				to.setSenha(newSenha);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			p.closeConnection();
		}
	}

	public boolean excluir(ContaTO to) {
		NamedParameterStatement p = new NamedParameterStatement(ConnectionFactory.openFactory());
		try {
			if (exists(to) && isValidPassword(to)) {
				String SQL = "DELETE FROM conta WHERE banco=:banco AND numero=:numero AND agencia=:agencia AND titular=:titular;";
				p.prepareNamedParameterStatement(SQL);
				p.setString("banco", to.getBanco().toString());
				p.setInt("numero", to.getNumero());
				p.setInt("agencia", to.getAgencia());
				p.setString("titular", to.getTitular());
				p.execute();
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			p.closeConnection();
		}
	}

	public void getFromDb(ContaTO to) throws Exception {
		NamedParameterStatement p = new NamedParameterStatement(ConnectionFactory.openFactory());
		try {
			if (exists(to) && isValidPassword(to)) {
				String SQL = "SELECT * FROM conta " + "WHERE banco=:banco AND numero=:numero AND agencia=:agencia;";
				p.prepareNamedParameterStatement(SQL);
				p.setString("banco", to.getBanco().toString());
				p.setInt("numero", to.getNumero());
				p.setInt("agencia", to.getAgencia());
				ResultSet rs = p.executeQuery();
				while (rs.next()) {
					to.setBanco(to.getBanco()).setNumero(to.getNumero()).setAgencia(to.getAgencia())
							.setSaldo(rs.getBigDecimal("saldo")).setSenha(rs.getInt("senha"))
							.setTitular(rs.getString("titular"));
				}
			} else {
				throw new Exception("Usuario Nao Encontrado");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			p.closeConnection();
		}
	}

	public ContaTO getFromDb(int numero) {
		ContaTO contaTO = new ContaTO().setNumero(numero);
		NamedParameterStatement p = new NamedParameterStatement(ConnectionFactory.openFactory());
		try {
			String SQL = "SELECT * FROM conta " + "WHERE numero=:numero;";
			p.prepareNamedParameterStatement(SQL);
			p.setInt("numero", numero);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				contaTO.setBanco(Banco.valueOf(rs.getString("banco")))
					.setAgencia(rs.getInt("agencia"))
					.setSaldo(rs.getBigDecimal("saldo"))
					.setSenha(rs.getInt("senha"))
					.setTitular(rs.getString("titular"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			p.closeConnection();
		}
		return contaTO;
	}
	
	public void getSaldo(ContaTO to) {
		NamedParameterStatement p = new NamedParameterStatement(ConnectionFactory.openFactory());
		try {
			if (exists(to) && (to.getSenha() == null || isValidPassword(to)) ) {
				String SQL = "SELECT saldo FROM conta WHERE banco=:banco AND numero=:numero AND agencia=:agencia;";
				p.prepareNamedParameterStatement(SQL);
				p.setString("banco", to.getBanco().toString());
				p.setInt("numero", to.getNumero());
				p.setInt("agencia", to.getAgencia());
				ResultSet rs = p.executeQuery();
				while (rs.next()) {
					to.setSaldo(rs.getBigDecimal("saldo"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			p.closeConnection();
		}
	}

	// ANOTHER DAO METHODS
	public boolean exists(ContaTO to) {
		NamedParameterStatement p = new NamedParameterStatement(ConnectionFactory.openFactory());
		try {
			if (to.getNumero() == null) {
				String SQL = "SELECT * FROM conta WHERE banco=:banco AND titular=:titular AND agencia=:agencia;";
				p.prepareNamedParameterStatement(SQL);
				p.setString("titular", to.getTitular());
			} else {
				String SQL = "SELECT * FROM conta WHERE banco=:banco AND numero=:numero AND agencia=:agencia;";
				p.prepareNamedParameterStatement(SQL);
				p.setInt("numero", to.getNumero());
			}
			p.setString("banco", to.getBanco().toString());
			p.setInt("agencia", to.getAgencia());
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				to.setNumero(rs.getInt("numero"));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			p.closeConnection();
		}
		return false;
	}

	private boolean isValidPassword(ContaTO to) {
		NamedParameterStatement p = new NamedParameterStatement(ConnectionFactory.openFactory());
		try {
			String SQL = "SELECT * FROM conta "
					+ "WHERE banco=:banco AND numero=:numero AND agencia=:agencia AND senha=:senha;";
			p.prepareNamedParameterStatement(SQL);
			p.setString("banco", to.getBanco().toString());
			p.setInt("numero", to.getNumero());
			p.setInt("agencia", to.getAgencia());
			p.setInt("senha", to.getSenha());
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			p.closeConnection();
		}
		return false;

	}

	public boolean getNumeroConta(ContaTO to) {
		NamedParameterStatement p = new NamedParameterStatement(ConnectionFactory.openFactory());
		try {
			String SQL = "SELECT numero FROM conta WHERE banco=:banco AND agencia=:agencia AND titular=:titular AND senha=:senha;";
			p.prepareNamedParameterStatement(SQL);
			p.setString("banco", to.getBanco().toString());
			p.setInt("agencia", to.getAgencia());
			p.setString("titular", to.getTitular());
			p.setInt("senha", to.getSenha());
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				to.setNumero(rs.getInt("numero"));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			p.closeConnection();
		}
		return false;
	}

	public void updateConta(ContaTO to) {
		NamedParameterStatement p = new NamedParameterStatement(ConnectionFactory.openFactory());
		try {
			if (exists(to) && isValidPassword(to)) {
				String SQL = "UPDATE conta SET titular=:titular "
						+ "WHERE banco=:banco AND numero=:numero AND agencia=:agencia";
				p.prepareNamedParameterStatement(SQL);
				p.setString("banco", to.getBanco().toString());
				p.setInt("numero", to.getNumero());
				p.setInt("agencia", to.getAgencia());
				p.setString("titular", to.getTitular());
				p.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			p.closeConnection();
		}
	}

	public void updateSaldo(ContaTO to, BigDecimal novoSaldo) {
		NamedParameterStatement p = new NamedParameterStatement(ConnectionFactory.openFactory());
		try {
			if (exists(to) && isValidPassword(to)) {
				to.setSaldo(novoSaldo);
				String SQL = "UPDATE conta SET saldo=:saldo WHERE banco=:banco AND numero=:numero AND agencia=:agencia;";
				p.prepareNamedParameterStatement(SQL);
				p.setString("banco", to.getBanco().toString());
				p.setInt("numero", to.getNumero());
				p.setInt("agencia", to.getAgencia());
				p.setBigDecimal("saldo", to.getSaldo());
				p.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			p.closeConnection();
		}
	}

	public void doSaque(ContaTO contaTO, BigDecimal valor) {
		getSaldo(contaTO);
		if (contaTO.getSaldo().compareTo(valor) == 0 || contaTO.getSaldo().compareTo(valor) == 1) {
			MovimentacaoDAO movDAO = new MovimentacaoDAO();
			getSaldo(contaTO);
			BigDecimal novoSaldo = contaTO.getSaldo().subtract(valor);
			updateSaldo(contaTO, novoSaldo);
			movDAO.novaMovimentacao(
					new MovimentacaoTO(contaTO, valor, TipoMovimentacao.SAQUE, Timestamp.valueOf(LocalDateTime.now())));
		} else {
			throw new RuntimeException("Saldo insuficiente para saque");
		}
	}
}