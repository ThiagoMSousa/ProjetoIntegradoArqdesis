package Control;

import java.io.IOException;
import java.text.ParseException;
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

@Path("/Acesso")
public class Acesso {
	
	@POST
	@Path("/Logar")
	@Produces
	@Consumes
	public String Logar(String json){
		
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
			ArrayList<Model.Acesso> acessos = Model.Acesso.carregaAcessos();
			consultarDia(acessos);
	
			String agencia = jsonObject.get("textAgencia").toString().replaceAll("\"", "");
			String conta = jsonObject.get("textConta").toString().replaceAll("\"", "");
			String senha = jsonObject.get("textSenha").toString().replaceAll("\"", "");
				
			if (("99999999").equals(conta) && ("9999").equals(agencia) && ("9999").equals(senha)) {
				itens.put("acessou", true);
				itens.put("administrador", true);
				itens.put("mensagem", rbn.getString("control.acesso.administradora"));
				itens.put("codigoAcesso", "");
			} else {
				Model.Acesso acesso = new Model.Acesso(agencia, conta, senha);
				int indiceAcesso = acesso.efetuarLogin(acessos);
				if (indiceAcesso == -2) {
					itens.put("acessou", false);
					itens.put("administrador", false);
					itens.put("mensagem", rbn.getString("control.acesso.bloqueada"));
					itens.put("codigoAcesso", "");
				} else if (indiceAcesso == -1) {
					itens.put("acessou", false);
					itens.put("administrador", false);
					itens.put("mensagem", rbn.getString("control.acesso.dadosErrados"));
					itens.put("codigoAcesso", "");
				} else {
					itens.put("acessou", true);
					itens.put("administrador", false);
					if (acessos.get(indiceAcesso).getAcesso().equals("sem")){
						itens.put("mensagem", rbn.getString("control.acesso.submitSemCodigo"));
					}else{
						itens.put("mensagem", rbn.getString("control.acesso.submitTemCodigo"));
						
					}
					itens.put("codigoAcesso", indiceAcesso);
				}
			}
		} catch (Exception erro) {
			itens.put("acessou", false);
			itens.put("administrador", false);
			itens.put("mensagem", rbn.getString("control.erroTry") + "\n\n" + erro.getMessage());
			itens.put("codigoAcesso", "");
		}
		
		return (new Gson().toJson(itens));
	}
	
	private static void consultarDia(ArrayList<Model.Acesso> acessos) throws ParseException, ClassNotFoundException, IOException {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date agoraDate = formato.parse(formato.format(new Date()));
		for (int i = 0; i < acessos.size(); i++) {
			acessos.get(i).desbloquearConta(formato, agoraDate);
			acessos.get(i).atualizaDataTentativa(formato, agoraDate);
		}
		new Model.Acesso().gravarAcessos(acessos);
	}
}
