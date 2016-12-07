package Control;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/EfetuarSaque")
public class EfetuarSaque {
	
	@POST
	@Path("/ConsultarNotas")
	@Produces
	@Consumes
	public String ConsultarNotas(String json){
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		
		Map<String, Object> itens = new LinkedHashMap<String, Object>();
		ResourceBundle rbn = ResourceBundle.getBundle("idioma", new Locale("pt", "BR"));
		
		if (("ingles").equals(jsonObject.get("idioma").getAsString())){
			rbn = ResourceBundle.getBundle("idioma", new Locale("en", "US"));
		}else if (("espanhol").equals(jsonObject.get("idioma").getAsString())){
			rbn = ResourceBundle.getBundle("idioma", new Locale("es", "ES"));
		}else if (("portugues").equals(jsonObject.get("idioma").getAsString())){
			rbn = ResourceBundle.getBundle("idioma", new Locale("pt", "BR"));
		}
		
		try {
			itens.put("notas", rbn.getString("view.telaEfetuarSaque.notas") + " " + Model.Saque.notasDisponiveis());
			itens.put("consultou", true);
			itens.put("mensagem", "");
		} catch (Exception erro) {
			itens.put("notas", "");
			itens.put("consultou", false);
			itens.put("mensagem", rbn.getString("control.erroTry") + "\n\n" + erro.getMessage());
		}
		
		return (new Gson().toJson(itens));
	}
	
	@POST
	@Path("/Sacar")
	@Produces
	@Consumes
	public String Sacar(String json){
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		
		Map<String, Object> itens = new LinkedHashMap<String, Object>();
		ResourceBundle rbn = ResourceBundle.getBundle("idioma", new Locale("pt", "BR"));
		
		if (("ingles").equals(jsonObject.get("idioma").getAsString())){
			rbn = ResourceBundle.getBundle("idioma", new Locale("en", "US"));
		}else if (("espanhol").equals(jsonObject.get("idioma").getAsString())){
			rbn = ResourceBundle.getBundle("idioma", new Locale("es", "ES"));
		}else if (("portugues").equals(jsonObject.get("idioma").getAsString())){
			rbn = ResourceBundle.getBundle("idioma", new Locale("pt", "BR"));
		}
			
		try {	
			String cliCodigo = jsonObject.get("cliCodigo").toString().replaceAll("\"", "");
			String textValor = jsonObject.get("textValor").toString().replaceAll("\"", "");
			
			Model.Cliente cliente = Model.Cliente.consultarCliente(Integer.parseInt(cliCodigo));
			
			if (cliente.getSaldo() >= Integer.parseInt(textValor)){
				Model.Saque saque = new Model.Saque(0, cliente.getCodigo(), new Date(), -Double.parseDouble((Unit.Unit.pegaValor(Integer.parseInt(textValor)+"", rbn))), 4);
				try {
					if (saque.validarValor(Integer.parseInt(textValor))){
						int notasV[] = saque.gerarCombinacao(Integer.parseInt(textValor));
						String notas = "";
						if (notasV[0] > 0) notas += "50: " + notasV[0];
						if (notasV[1] > 0) notas += "\n20: " + notasV[1];
						if (notasV[2] > 0) notas += "\n10: " + notasV[2];
						saque.cadastrar();
						cliente.alterarSaldo(Double.parseDouble((Unit.Unit.pegaValor(Integer.parseInt(textValor)+"", rbn))), "-");
						itens.put("sacou", true);
						itens.put("mensagem", rbn.getString("control.efetuarSaque.saqueEfetuado") + "\n\n" + notas);
					}else{
						itens.put("sacou", false);
						itens.put("mensagem", rbn.getString("control.efetuarSaque.notasIndisponiveis"));
					}
				} catch (Exception erro) {
					itens.put("sacou", false);
					itens.put("mensagem", rbn.getString("control.erroTry") + "\n\n" + erro.getMessage());
				}
			}else{
				itens.put("sacou", false);
				itens.put("mensagem", rbn.getString("control.efetuarSaque.saldoInferior"));
			}
		} catch (Exception erro) {
			itens.put("sacou", false);
			itens.put("mensagem", rbn.getString("control.erroTry") + "\n\n" + erro.getMessage());
		}
		
		return (new Gson().toJson(itens));
	}
}
