package Control;

import java.util.ArrayList;
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

@Path("/ConsultarExtrato")
public class ConsultarExtrato {
	
	@POST
	@Path("/Pesquisar")
	@Produces
	@Consumes
	public String Pesquisar(String json){
		
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
			String dias = jsonObject.get("textDias").toString().replaceAll("\"", "");
			Model.Cliente cliente = Model.Cliente.consultarCliente(Integer.parseInt(cliCodigo));

			ArrayList<ArrayList<To.ListaOperacoesTO>> listaAux = Model.Operacao.consultarExtrato(cliente.getSaldo(), cliente.getCodigo(), Integer.parseInt(dias), rbn);
			ArrayList<To.ListaOperacoesTO> lista = Model.Operacao.transformaListaSimples(listaAux);
			
			if (lista.size() > 0){
				itens.put("temItens", true);
				itens.put("lista", lista);
				itens.put("mensagem", "");
			}else{
				itens.put("temItens", false);
				itens.put("lista", null);
				itens.put("mensagem", rbn.getString("view.telaConsultarExtrato.semItens"));
			}
		} catch (Exception erro) {
			itens.put("temItens", false);
			itens.put("lista", null);
			itens.put("mensagem", rbn.getString("control.erroTry") + "\n\n" + erro.getMessage());
		}
		
		return (new Gson().toJson(itens));
	}
}
