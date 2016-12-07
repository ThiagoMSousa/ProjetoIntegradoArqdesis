package Control;

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

@Path("/EfetuarTransferencia")
public class EfetuarTransferencia {
	
	@POST
	@Path("/Confirmar")
	@Produces
	@Consumes
	public String Confirmar(String json){
		
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

			String textAgenciaDestino = jsonObject.get("textAgenciaDestino").toString().replaceAll("\"", "");
			String textContaDestino = jsonObject.get("textContaDestino").toString().replaceAll("\"", "");
			String textValorBruto = jsonObject.get("textValorBruto").toString().replaceAll("\"", "");
			
			Model.Cliente cliente = Model.Cliente.consultarCliente(Integer.parseInt(cliCodigo));
			Model.Cliente cliente2 = Model.Cliente.consultarCliente(Integer.parseInt(textAgenciaDestino + textContaDestino));

			new Model.Transferencia(0, 0, null, 0, 0, 0, "", "").cadastrarTransferencia(cliente, cliente2, Double.parseDouble(Unit.Unit.pegaValor(textValorBruto, rbn)));
			itens.put("transferiu", true);
			itens.put("mensagem", rbn.getString("control.efetuarTransferencia.Ok"));
			
		} catch (Exception erro) {
			itens.put("transferiu", false);
			itens.put("mensagem", rbn.getString("control.erroTry") + "\n\n" + erro.getMessage());
		}
		
		return (new Gson().toJson(itens));
	}
	
	@POST
	@Path("/Verificar")
	@Produces
	@Consumes
	public String Verificar(String json){
		
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

			String textAgenciaDestino = jsonObject.get("textAgenciaDestino").toString().replaceAll("\"", "");
			String textContaDestino = jsonObject.get("textContaDestino").toString().replaceAll("\"", "");
			String textValor = jsonObject.get("textValor").toString().replaceAll("\"", "");
			
			Model.Cliente cliente = Model.Cliente.consultarCliente(Integer.parseInt(cliCodigo));
			Model.Cliente cliente2 = Model.Cliente.consultarCliente(Integer.parseInt(textAgenciaDestino + textContaDestino));
			
			String erroVerificar = verificar(cliente, cliente2, textAgenciaDestino, textContaDestino, textValor, rbn);
			
			if (("").equals(erroVerificar)){
				itens.put("valido", true);
				itens.put("mensagem", "");
				itens.put("textAgenciaDestino", textAgenciaDestino);
				itens.put("textContaDestino", textContaDestino);
				itens.put("textNome", cliente2.getNome());
				itens.put("textValor", Unit.Unit.formatarSaldo(textValor, rbn));
				itens.put("textValorBruto", textValor);
			}else{
				itens.put("valido", false);
				itens.put("mensagem", erroVerificar);
				itens.put("textAgenciaDestino", null);
				itens.put("textContaDestino", null);
				itens.put("textNome", null);
				itens.put("textValor", null);
				itens.put("textValorBruto", null);
			}			
		} catch (Exception erro) {
			itens.put("valido", false);
			itens.put("mensagem", rbn.getString("control.erroTry") + "\n\n" + erro.getMessage());
			itens.put("textAgenciaDestino", null);
			itens.put("textContaDestino", null);
			itens.put("textNome", null);
			itens.put("textValor", null);
			itens.put("textValorBruto", null);
		}
		
		return (new Gson().toJson(itens));
	}

	public String verificar(Model.Cliente cliente1, Model.Cliente cliente2, String agenciaDestino, String contaDestino, String valorDestino, ResourceBundle rbn){
		try {
			if (cliente1.getSaldo() >= Double.parseDouble(Unit.Unit.pegaValor(valorDestino, rbn))) {
				int codigo = Integer.parseInt(agenciaDestino + contaDestino);
				if (cliente1.getCodigo() != codigo) {
					cliente2 = Model.Cliente.consultarCliente(codigo);
					if (cliente2 != null) {
						if (cliente1.getBanco().equals(cliente2.getBanco())) {
							return "";
						} else return rbn.getString("control.efetuarTransferencia.bancoDiferente");
					} else return rbn.getString("control.efetuarTransferencia.contaInexistente");
				} else return rbn.getString("control.efetuarTransferencia.contaIgual");
			}else return rbn.getString("control.efetuarTransferencia.saldoInferior");
		} catch (Exception erro) {
			return rbn.getString("control.erroTry") + "\n\n" + erro.getMessage();
		}
	}
}
