package Control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import Model.Debito;

@Path("/DebitoAutomatico")
public class DebitoAutomatico {
	
	@POST
	@Path("/Cadastrar")
	@Produces
	@Consumes
	public String Cadastrar(String json){
		
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
			
			String textOperadora = jsonObject.get("textOperadora").toString().replaceAll("\"", "");
			String textDataDebito = jsonObject.get("textDataDebito").toString().replaceAll("\"", "");
			String textConsumidor = jsonObject.get("textConsumidor").toString().replaceAll("\"", "");
			String textValor = jsonObject.get("textValor").toString().replaceAll("\"", "");
			
			Model.Cliente cliente = Model.Cliente.consultarCliente(Integer.parseInt(cliCodigo));
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			Date data = sdf.parse(textDataDebito);
			if (!data.before(new Date())){
				Model.Debito debito = new Debito(
					0,
					data,
					Integer.parseInt(textOperadora),
					Integer.parseInt(textConsumidor),
					Double.parseDouble(Unit.Unit.pegaValor(textValor, rbn)),
					cliente.getCodigo(),
					0
				);
				debito.cadastrar();
				
				itens.put("cadastrou", true);
				itens.put("mensagem", rbn.getString("control.cadastrarDebito.cadastroEfetuado"));
			}else{
				itens.put("cadastrou", false);
				itens.put("mensagem", rbn.getString("control.cadastrarDebito.dataInferior"));
			}
		} catch (Exception erro) {
			itens.put("cadastrou", false);
			itens.put("mensagem", rbn.getString("control.erroTry") + "\n\n" + erro.getMessage());
		}
		
		return (new Gson().toJson(itens));
	}
	
	@POST
	@Path("/Efetuar")
	@Produces
	@Consumes
	public String Efetuar(String json){
		
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
			Date hoje = new Date();
			ArrayList<Model.Debito> lista = new Model.Debito(0, null, 0, 0, 0, 0, 0).consultarEmAberto();
			for (Model.Debito debito: lista) {
				if (getData(hoje).compareTo(getData(debito.getData())) == 0) {
					debito.pagar();
				}
			}
			itens.put("efetuou", true);
			itens.put("mensagem", "");
		} catch (Exception erro) {
			itens.put("efetuou", false);
			itens.put("mensagem", rbn.getString("control.erroTry") + "\n\n" + erro.getMessage());
		}
		
		return (new Gson().toJson(itens));
	}

	private Date getData(Date data) {
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.setTime(data);
		myCalendar.set(Calendar.HOUR_OF_DAY, 0);
		myCalendar.set(Calendar.MINUTE, 0);
		myCalendar.set(Calendar.SECOND, 0);
		myCalendar.set(Calendar.MILLISECOND, 0);
		return myCalendar.getTime();
	}
}
