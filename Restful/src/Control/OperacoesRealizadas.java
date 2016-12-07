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

@Path("/OperacoesRealizadas")
public class OperacoesRealizadas {
	
	@POST
	@Path("/Consultar")
	@Produces
	@Consumes
	public String Consultar(String json){
		
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
			Map<String, Object> aux = Model.Operacao.consultarOperacoesDiarias();
			
			itens.put("consultou", true);
			itens.put("porcentagem1", aux.get("porcentagem1"));
			itens.put("porcentagem2", aux.get("porcentagem2"));
			itens.put("porcentagem3", aux.get("porcentagem3"));
			itens.put("porcentagem4", aux.get("porcentagem4"));
			itens.put("porcentagem5", aux.get("porcentagem5"));
			itens.put("mensagem", "");
		} catch (Exception erro) {
			itens.put("consultou", false);
			itens.put("porcentagem1", null);
			itens.put("porcentagem2", null);
			itens.put("porcentagem3", null);
			itens.put("porcentagem4", null);
			itens.put("porcentagem5", null);
			itens.put("mensagem", rbn.getString("control.erroTry") + "\n\n" + erro.getMessage());
		}
		
		return (new Gson().toJson(itens));
	}
}
