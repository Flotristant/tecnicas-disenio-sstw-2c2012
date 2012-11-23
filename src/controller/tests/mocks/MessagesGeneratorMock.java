package controller.tests.mocks;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import model.Message;

public class MessagesGeneratorMock {
	

	
	public List<model.Message> getSpamMessages() {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("from@hotmail.com", "to@gmail.com", "Spam", "blabla");
		model.Message m1 = new model.Message("from@hotmail.com", "to@gmail.com", "", "blabla");
		model.Message m2 = new model.Message("from@hotmail.com", "to@gmail.com", "ALTA-MMMMATERIA-7510", "blabla");
		model.Message m3 = new model.Message("from@hotmail.com", "to@gmail.com", "Alta-grupo", "blabla");
		model.Message m4 = new model.Message("from@hotmail.com", "to@gmail.com", "ALTA-MATERIA-5a10", "blabla");
		model.Message m5 = new model.Message("from@hotmail.com", "to@gmail.com", "[ENTR1GA-TP-1]", "");
		model.Message m6 = new model.Message("from@hotmail.com", "to@gmail.com", "Consulta", "blabla");
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
		model.Message m = new model.Message("from@hotmail.com", "to@gmail.com", "[ALTA-MATERIA-7510] 90100-Sasa Rara", "");
		model.Message m2 = new model.Message("from2@hotmail.com", "to@gmail.com", "[ALTA-MATERIA-7510] 80000-Roro Ruru", "");
		messages.add(m);
		messages.add(m2);	
		return messages;
	}
	
	public List<model.Message> getAltaMateriaMessagesInvalid() {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("from@hotmail.com", "to@gmail.com", "[ALTA-MATERIA-7510] 90100-Sasa Rara", "");
		model.Message m2 = new model.Message("from2@hotmail.com", "to@gmail.com", "[ALTA-MATERIA-7510] 80000-Roro Ruru", "");
		model.Message m3 = new model.Message("from3@hotmail.com", "to@gmail.com", "[ALTA-MATERIA-7520] 90100-Sasa Rara", "");
		messages.add(m);
		messages.add(m2);
		messages.add(m3);	
		return messages;
	}
	
	public List<model.Message> getAltaGruposMessagesValid(String path_attach, String file_name) {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("from@hotmail.com", "to@gmail.com", "[ALTA-GRUPO]", "");
		HashMap<String, String> hash = new HashMap<String,String>();
		hash.put(file_name, path_attach);
		m.addAttachments(hash);
		messages.add(m);
		return messages;
	}
	
	public List<model.Message> getAltaGruposMessagesWithFakeMail() {
		List<model.Message> messages = new ArrayList<Message>();
		//Mail incorrecto
		model.Message m = new model.Message("trucho@hotmail.com", "to@gmail.com", "[ALTA-GRUPO]", "");
		messages.add(m);
		return messages;
	}
	
	public List<model.Message> getAltaGruposMessagesWithNoAttach() {
		List<model.Message> messages = new ArrayList<Message>();
		//Mail correcto pero sin attach
		model.Message m = new model.Message("from@hotmail.com", "to@gmail.com", "[ALTA-GRUPO]", "");
		messages.add(m);
		return messages;
	}
	
	public List<model.Message> getAltaGruposMessagesWithMoreThanOneAttach(String path_attach, String file_name) {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("from@hotmail.com", "to@gmail.com", "[ALTA-GRUPO]", "");
		HashMap<String, String> hash = new HashMap<String,String>();
		hash.put(file_name, path_attach);
		hash.put("otroAttach", "/home/blabla/");
		m.addAttachments(hash);
		messages.add(m);
		return messages;
	}
	
	public List<Message> getEntregaTpMessagesValid(String path_attach, String file_name) {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("from@hotmail.com", "to@gmail.com", "[ENTREGA-TP-1]", "");
		HashMap<String, String> hash = new HashMap<String,String>();
		hash.put(file_name, path_attach);
		m.addAttachments(hash);
		messages.add(m);
		return messages;
	}

	public List<Message> getEntregaTpMessagesInvalid(String path_attach, String file_name) {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("fakemail@hotmail.com", "to@gmail.com", "[ENTREGA-TP-1]", "");
		HashMap<String, String> hash = new HashMap<String,String>();
		hash.put(file_name, path_attach);
		m.addAttachments(hash);
		messages.add(m);
		return messages;
	}
	

	

}
