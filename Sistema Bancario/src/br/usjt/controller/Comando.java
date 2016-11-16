package br.usjt.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Comando {
	
	public String executa(HttpServletRequest req, HttpServletResponse resp);
	
}
