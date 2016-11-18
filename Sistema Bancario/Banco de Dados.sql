CREATE DATABASE agioBank;
USE agioBank;
CREATE TABLE
conta(
	banco VARCHAR(45) NOT NULL,
	numero INTEGER(6) NOT NULL AUTO_INCREMENT,
	agencia INTEGER(6) NOT NULL,
	senha INTEGER(4) NOT NULL,
	titular VARCHAR(45) NOT NULL,
	saldo DECIMAL(19,2) NOT NULL,
	PRIMARY KEY (numero)
);

CREATE TABLE
movimentacao(
	id INTEGER(4) UNIQUE AUTO_INCREMENT,
	fromBanco VARCHAR(45) NOT NULL,
	fromNumero INTEGER(6) NOT NULL,
	fromAgencia INTEGER(6) NOT NULL,
	valor DECIMAL(19,2) NOT NULL,
	tipoMovimentacao VARCHAR(20) NOT NULL,
	date TIMESTAMP
);

CREATE table 
login(
	usuario varchar(50) not null,
    senha integer(8) not null,
    primary key(usuario)
    );
