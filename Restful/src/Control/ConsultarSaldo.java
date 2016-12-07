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

@Path("/ConsultarSaldo")
public class ConsultarSaldo {
	
	@POST
	@Path("/AbrirTela")
	@Produces
	@Consumes
	public String AbrirTela(String json){
		
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
			Model.Cliente cliente = Model.Cliente.consultarCliente(Integer.parseInt(cliCodigo));
			
			String erroCadastrarOperacao = cadastrarOperacao(cliente, rbn);
			
			if (!erroCadastrarOperacao.equals("")){
				itens.put("consultou", false);
				itens.put("saldo", null);
				itens.put("mensagem", erroCadastrarOperacao);
			}else{
				itens.put("consultou", true);
				itens.put("saldo", rbn.getString("view.telaConsultarSaldo.saldo") + " " + rbn.getString("control.moeda") + " " + Unit.Unit.preparaValor(cliente.getSaldo()+"", rbn));
				itens.put("mensagem", "");
			}
		} catch (Exception erro) {
			itens.put("consultou", false);
			itens.put("saldo", null);
			itens.put("mensagem", rbn.getString("control.erroTry") + "\n\n" + erro.getMessage());
		}
		
		return (new Gson().toJson(itens));
	}
	
	public String cadastrarOperacao(Model.Cliente cliente, ResourceBundle rbn) {
		try {
			new Model.Operacao(
				0,
				cliente.getCodigo(),
				new Date(),
				0.0,
				1
			).cadastrar();
			return "";
		} catch (Exception erro) {
			return rbn.getString("control.erroTry") + "\n\n" + erro.getMessage();
		}
	}
}
