package br.usjt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.usjt.to.ContaTO;
import br.usjt.to.UsuarioTO;

public class UsuarioDAO {
	

	public void novoUsuario(UsuarioTO to) {
		NamedParameterStatement p = new NamedParameterStatement(ConnectionFactory.openFactory());
		try {
			if (validar(to)) {
				System.out.println("Usuario ja Existente");
			} else {
				// Create a Conta
				String SQL = "INSERT INTO login (usuario,senha) "
						+ "VALUES (:usuario, :senha);";

				p.prepareNamedParameterStatement(SQL);
				p.setString("usuario", to.getUsuario());
				p.setInt("senha", to.getSenha());
				p.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean validar(UsuarioTO to){
		
		NamedParameterStatement p = new NamedParameterStatement(ConnectionFactory.openFactory());
		try {
			String SQL = "SELECT * FROM login "
					+ "WHERE usuario=:usuario AND senha=:senha;";
			p.prepareNamedParameterStatement(SQL);
			p.setString("usuario", to.getUsuario());
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
		
		
		
		
}
