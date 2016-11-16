package br.usjt.test;

import br.usjt.dao.ConnectionFactory;

public class ConnectionTest {
	public static void main(String[] args) throws InterruptedException {
		ConnectionFactory.openFactory();
		Thread.sleep(500);
		ConnectionFactory.closeFactory();
	}
}

