package Control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import Model.Acesso;

@Path("/CodigoAcesso")
public class CodigoAcesso {
	
	@POST
	@Path("/ValidarCodigo")
	@Produces
	@Consumes
	public String ValidarCodigo(String json){
		
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
			String indice = jsonObject.get("indice").toString().replaceAll("\"", "");
			String codigoAcesso = jsonObject.get("codigoAcesso").toString().replaceAll("\"", "");
			ArrayList<Acesso> acessos = Model.Acesso.carregaAcessos();
			Acesso acesso= acessos.get(Integer.parseInt(indice));
			
			if (!acesso.getAcesso().equals(codigoAcesso) && !acesso.getAcesso().equals("sem")) {
				acesso.setTentativas(acesso.getTentativas() + 1);
				acesso.setDataTentativa(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				itens.put("acessou", false);
				itens.put("cliCodigo", "");
				if (acesso.getTentativas() == 3) {
					itens.put("mensagem", rbn.getString("control.codigoAcesso.codigoErrado") + "\n\n" + rbn.getString("control.codigoAcesso.bloqueado"));
					itens.put("bloqueado", true);
					acesso.setBloqueio();
				}else{
					itens.put("mensagem", rbn.getString("control.codigoAcesso.codigoErrado"));
					itens.put("bloqueado", false);
				}
			} else {
				itens.put("acessou", true);
				itens.put("bloqueado", false);
				if (acesso.getAcesso().equals("sem")) {
					acesso.setAcesso(codigoAcesso);
					itens.put("mensagem", rbn.getString("control.codigoAcesso.codigoCadastrado"));
				}else{
					itens.put("mensagem", rbn.getString("control.codigoAcesso.logado"));
				}
				acesso.setDesbloqueio();
				Model.Cliente cliente1 = Model.Cliente.consultarCliente(Integer.parseInt(acesso.getAgencia() + acesso.getNumConta()));
				itens.put("cliCodigo", cliente1.getCodigo());
			}
			
			new Model.Acesso().gravarAcessos(acessos);
			
		} catch (Exception erro) {
			itens.put("acessou", false);
			itens.put("bloqueado", false);
			itens.put("mensagem", rbn.getString("control.erroTry") + "\n\n" + erro.getMessage());
			itens.put("cliCodigo", "");
		}
		
		return (new Gson().toJson(itens));
	}
}
