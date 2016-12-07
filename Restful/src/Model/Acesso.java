package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Acesso {

	private String agencia, numConta, senha, acesso, dataBloqueio, dataTentativa;
	private boolean status;
	private int tentativas;

	public Acesso(String agencia, String numConta, String senha, String acesso, boolean status, String dataBloqueio, String dataTentativa, int tentativas) {
		setAgencia(agencia);
		setNumConta(numConta);
		setSenha(senha);
		setAcesso(acesso);
		setStatus(status);
		setDataBloqueio(dataBloqueio);
		setDataTentativa(dataTentativa);
		setTentativas(tentativas);
	}
	
	public Acesso(String agencia, String numConta, String senha) {
		setAgencia(agencia);
		setNumConta(numConta);
		setSenha(senha);
	}
	
	public Acesso() {}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	
	public void setNumConta(String numConta) {
		this.numConta = numConta;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void setAcesso(String acesso) {
		this.acesso = acesso;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public void setDataBloqueio(String dataBloqueio) {
		this.dataBloqueio = dataBloqueio;
	}
	
	public void setDataTentativa(String dataTentativa) {
		this.dataTentativa = dataTentativa;
	}
	
	public void setTentativas(int tentativas) {
		this.tentativas = tentativas;
	}
	
	public void setBloqueio() {
		status = false;
		dataBloqueio = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		dataTentativa = "00/00/0000";
		tentativas = 0;
	}
	
	public void setDesbloqueio() {
		status = true;
		dataBloqueio = "00/00/0000";
		dataTentativa = "00/00/0000";
		tentativas = 0;
	}

	public String getAgencia() {
		return agencia;
	}
	
	public String getNumConta() {
		return numConta;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public String getAcesso() {
		return acesso;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public String getDataBloqueio() {
		return dataBloqueio;
	}
	
	public String getDataTentativa() {
		return dataTentativa;
	}
	
	public int getTentativas() {
		return tentativas;
	}

	public static ArrayList<Acesso> carregaAcessos() throws ClassNotFoundException, IOException {
		ArrayList<Acesso> acessos = AcessoTxt.carregaAcessos();
		return acessos;
	}
	
	public void gravarAcessos(ArrayList<Acesso> array) throws ClassNotFoundException, IOException {
		new AcessoTxt().gravaAcessos(array);
	}

	public void desbloquearConta(SimpleDateFormat formato, Date agoraDate) throws ParseException{		
		if (!getStatus()) {
			Date antesDate = formato.parse(getDataBloqueio());
			if (agoraDate.after(antesDate)) {
				setStatus(true);
				setDataBloqueio("00/00/0000");
				setDataTentativa("00/00/0000");
				setTentativas(0);
			}
		}
	}
	
	public void atualizaDataTentativa(SimpleDateFormat formato, Date agoraDate) throws ParseException{
		if (getDataTentativa().equals("00/00/0000")) {
			Date antesDate = formato.parse(getDataTentativa());
			if (agoraDate.after(antesDate)) {
				setDataTentativa("00/00/0000");
			}
		}
	}
	
	public int efetuarLogin(ArrayList<Acesso> array) {
		int inicio = 0, fim = array.size() - 1;
		while (inicio <= fim) {
			int meio = (inicio + fim) / 2;
			if (array.get(meio).getNumConta().equals(getNumConta())) {
				if (array.get(meio).getAgencia().equals(getAgencia())
						&& array.get(meio).getSenha().equals(getSenha())) {
					if (array.get(meio).getStatus()) {
						return meio;
					} else {
						return -2;
					}
				}
			}
			if (Integer.parseInt(getNumConta()) > Integer.parseInt(array.get(meio).getNumConta())) {
				inicio = meio + 1;
			} else {
				fim = meio - 1;
			}
		}
		return -1;
	}
	
	public static String[] preparaBotoes(ResourceBundle rbn){
		String btn1 = "", btn2 = "", btn3 = "", btn4 = "", btn5 = "";
		String[] textos = {
			rbn.getString("view.telaCodigoAcesso.btnUm"),
			rbn.getString("view.telaCodigoAcesso.btnDois"),
			rbn.getString("view.telaCodigoAcesso.btnTres"),
			rbn.getString("view.telaCodigoAcesso.btnQuatro"),
			rbn.getString("view.telaCodigoAcesso.btnCinco")
		};
			
		for (int i = 0; i < 5;) {
			int numero = (int) (Math.random() * 5);
			if (!textos[numero].equals("")) {
				if (i == 0)			btn1 = textos[numero];
				else if (i == 1)	btn2 = textos[numero];
				else if (i == 2)	btn3 = textos[numero];
				else if (i == 3)	btn4 = textos[numero];
				else if (i == 4)	btn5 = textos[numero];
				i++;
				textos[numero] = "";
			}
		}
		
		return new String[]{btn1, btn2, btn3, btn4, btn5};
	}
	
	private static class AcessoTxt {

		private static Scanner input;
		private static Formatter output;

		public void gravaAcessos(ArrayList<Acesso> array) throws ClassNotFoundException, IOException {
			output = new Formatter(obterCaminho());
			String textoParaCriptografar = "";
			for (int i = 0; i < array.size(); i++) {
				textoParaCriptografar += array.get(i).getAgencia() + " " + array.get(i).getNumConta() + " "
						+ array.get(i).getSenha() + " " + array.get(i).getAcesso() + " " + array.get(i).getStatus()
						+ " " + array.get(i).getDataBloqueio() + " " + array.get(i).getDataTentativa() + " "
						+ array.get(i).getTentativas() + "\n";
			}
			output.format(Cryptografia.criptografar(textoParaCriptografar));
			if (output != null)
				output.close();
		}

		public static ArrayList<Acesso> carregaAcessos() throws ClassNotFoundException, IOException {
			ArrayList<Acesso> array = new ArrayList<Acesso>();
			input = new Scanner(new File(obterCaminho()));
			String texto = "";
			int i = 0;
			while (input.hasNext())
				texto += input.next();
			String acessosText = new Cryptografia().descriptografa(texto);
			while (i != acessosText.length()) {
				Acesso acesso = new Acesso();
				acesso.setAgencia(acessosText.substring(i, i + 4));
				i += 5;
				acesso.setNumConta(acessosText.substring(i, i + 8));
				i += 9;
				acesso.setSenha(acessosText.substring(i, i + 4));
				i += 5;
				if (acessosText.substring(i, i + 3).equals("sm")) {
					acesso.setAcesso("sem");
					i += 4;
				} else {
					acesso.setAcesso(acessosText.substring(i, i + 3));
					i += 4;
				}
				if (acessosText.substring(i, i + 3).equals("tru")) {
					acesso.setStatus(true);
					i += 5;
				} else {
					acesso.setStatus(false);
					i += 6;
				}
				acesso.setDataBloqueio(acessosText.substring(i, i + 10));
				i += 11;
				acesso.setDataTentativa(acessosText.substring(i, i + 10));
				i += 11;
				acesso.setTentativas(Integer.parseInt(acessosText.substring(i, i + 1)));
				i += 2;
				array.add(acesso);
			}
			if (input != null)
				input.close();
			return array;
		}
		
		private static String obterCaminho(){
			String caminho = (Acesso.class.getResource("")+"").substring(6).replace("%20", " ");
			return caminho.substring(0, caminho.length()-6) + "Recursos/ACESSO.txt";
		}
	}

	private static class Cryptografia {
		public static String criptografar(String texto) throws FileNotFoundException, IOException, ClassNotFoundException {
			String retorno = "";
			ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(new File(obterCaminho())));
			int iDummy = (Integer) objectInput.readObject();
			objectInput.close();
			byte[] textoCifrado = texto.getBytes("ISO-8859-1");
			for (int i = 0; i < texto.getBytes("ISO-8859-1").length; i++) {
				textoCifrado[i] = (byte) (textoCifrado[i] + iDummy);
			}
			retorno = new String(textoCifrado, "ISO-8859-1");
			return retorno;
		}

		public String descriptografa(String texto) throws FileNotFoundException, IOException, ClassNotFoundException {
			String retorno = "";
			ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(new File(obterCaminho())));
			int iDummy = (Integer) objectInput.readObject();
			objectInput.close();
			byte[] textoDecifrado = texto.getBytes("ISO-8859-1");
			for (int i = 0; i < texto.getBytes("ISO-8859-1").length; i++) {
				textoDecifrado[i] = (byte) (textoDecifrado[i] - iDummy);
			}
			retorno = new String(textoDecifrado, "ISO-8859-1");
			return retorno;
		}
		
		private static String obterCaminho(){
			String caminho = (Acesso.class.getResource("")+"").substring(6).replace("%20", " ");
			return caminho.substring(0, caminho.length()-6) + "Recursos/chave.dummy";
		}
	}
}