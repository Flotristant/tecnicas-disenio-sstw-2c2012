package integrateTests;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Message;

public class MailGeneratorMock {

	public List<model.Message> getSpamMessages() {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "Spam", "blabla");
		model.Message m1 = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "", "blabla");
		model.Message m2 = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "ALTA-MMMMATERIA-7510", "blabla");
		model.Message m3 = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "Alta-grupo", "blabla");
		model.Message m4 = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "ALTA-MATERIA-5a10", "blabla");
		model.Message m5 = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "[ENTR1GA-TP-1]", "");
		model.Message m6 = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "Consulta", "blabla");
		messages.add(m);
		messages.add(m1);
		messages.add(m2);
		messages.add(m3);
		messages.add(m4);
		messages.add(m5);
		messages.add(m6);
		return messages;
	}
	
	public List<model.Message> getAltaMateriaMessagesValid() {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "[ALTA-MATERIA-7510] 90100-Pepe Ramirez", "");
		messages.add(m);
		return messages;
	}
	
	public List<model.Message> getAltaMateriaMessagesWithCodeInvalid() {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "[ALTA-MATERIA-7111] 90100-Sasa Rara", "");
		model.Message m2 = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "[ALTA-MATERIA-7546] 80000-Roro Ruru", "");
		messages.add(m);
		messages.add(m2);	
		return messages;
	}
	
	public List<model.Message> getAltaGruposMessagesValid(String path_attach, String file_name) {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "[ALTA-GRUPO]", "");
		HashMap<String, String> hash = new HashMap<String,String>();
		hash.put(file_name, path_attach);
		m.addAttachments(hash);
		messages.add(m);
		return messages;
	}
	
	public List<model.Message> getAltaGruposMessagesWithFakeMail() {
		List<model.Message> messages = new ArrayList<Message>();
		//Mail incorrecto
		model.Message m = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "[ALTA-GRUPO]", "");
		messages.add(m);
		return messages;
	}
	
	public List<model.Message> getAltaGruposMessagesWithNoAttach() {
		List<model.Message> messages = new ArrayList<Message>();
		//Mail correcto pero sin attach
		model.Message m = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "[ALTA-GRUPO]", "");
		messages.add(m);
		return messages;
	}
	
	public List<model.Message> getAltaGruposMessagesWithMoreThanOneAttach(String path_attach, String file_name) {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "[ALTA-GRUPO]", "");
		HashMap<String, String> hash = new HashMap<String,String>();
		hash.put(file_name, path_attach);
		hash.put("otroAttach", "/home/blabla/");
		m.addAttachments(hash);
		messages.add(m);
		return messages;
	}
	
	public List<Message> getEntregaTpMessagesValid(String path_attach, String file_name) {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "[ENTREGA-TP-1]", "");
		HashMap<String, String> hash = new HashMap<String,String>();
		hash.put(file_name, path_attach);
		m.addAttachments(hash);
		messages.add(m);
		return messages;
	}

	public List<Message> getEntregaTpMessagesInvalid(String path_attach, String file_name) {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "[ENTREGA-TP-1]", "");
		messages.add(m);
		return messages;
	}
	
	public List<Message> getAMessageAltaGrupoInvalid() {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "[ALTA-GRUPO]", "");
		messages.add(m);
		return messages;
	}
	
	public List<Message> getAMessageConsultaPublica() {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "[CONSULTA-PUBLICA] Base de datos", "Como se usa el insert?");
		messages.add(m);
		return messages;
	}
	
	public List<Message> getAMessageConsultaPrivadaValid() {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "[CONSULTA-PRIVADA] Punteros", "Que es un puntero?");
		messages.add(m);
		return messages;
	}
	
	public List<Message> getAMesageConsultaTicketClosed() {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("pruebatecnicas@hotmail.com", "pruebatecnicas@gmail.com", "[CONSULTA] 1", "Es un numero");
		messages.add(m);
		return messages;
	}
	
}
