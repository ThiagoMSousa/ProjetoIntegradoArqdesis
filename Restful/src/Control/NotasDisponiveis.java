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

@Path("/NotasDisponiveis")
public class NotasDisponiveis {
	
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
			Map<String, Object> aux = Model.Dispenser.notasELimites(rbn);
			
			String nota50 = rbn.getString("control.telaNotas.prefixo") + " " + aux.get("valorFormatadoNota50") + " " +
				rbn.getString("control.telaNotas.sufixo") + " " + aux.get("porcentagemNota50") + "% (" + 
				aux.get("quantidadeNota50") + "/" +aux.get("limiteNota50") + ")";
			
			String nota20 = rbn.getString("control.telaNotas.prefixo") + " " + aux.get("valorFormatadoNota20") + " " +
				rbn.getString("control.telaNotas.sufixo") + " " + aux.get("porcentagemNota20") + "% (" + 
				aux.get("quantidadeNota20") + "/" +aux.get("limiteNota20") + ")";
			
			String nota10 = rbn.getString("control.telaNotas.prefixo") + " " + aux.get("valorFormatadoNota10") + " " +
				rbn.getString("control.telaNotas.sufixo") + " " + aux.get("porcentagemNota10") + "% (" + 
				aux.get("quantidadeNota10") + "/" +aux.get("limiteNota10") + ")";

			itens.put("consultou", true);
			itens.put("nota50", nota50);
			itens.put("nota20", nota20);
			itens.put("nota10", nota10);
			itens.put("mensagem", "");
		} catch (Exception erro) {
			itens.put("consultou", false);
			itens.put("nota50", null);
			itens.put("nota20", null);
			itens.put("nota10", null);
			itens.put("mensagem", rbn.getString("control.erroTry") + "\n\n" + erro.getMessage());
		}
		
		return (new Gson().toJson(itens));
	}
}
