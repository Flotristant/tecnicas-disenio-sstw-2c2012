package controller.tests.mocks;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;

import model.Message;

public class MessagesGeneratorMock {
	

	
	public List<model.Message> getSpamMessages() {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("from@hotmail.com", "to@gmail.com", "Spam", "blabla");
		model.Message m1 = new model.Message("from@hotmail.com", "to@gmail.com", "", "blabla");
		model.Message m2 = new model.Message("from@hotmail.com", "to@gmail.com", "Basura", "blabla");
		model.Message m3 = new model.Message("from@hotmail.com", "to@gmail.com", "Alta-grupo", "blabla");
		
		messages.add(m);
		messages.add(m1);
		messages.add(m2);
		messages.add(m3);
		return messages;
	}
	
	public List<model.Message> getAltaMateriaMessages() {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("from@hotmail.com", "to@gmail.com", "[ALTA-MATERIA-7510] 90100-Sasa Rara", "");
		model.Message m2 = new model.Message("from2@hotmail.com", "to@gmail.com", "[ALTA-MATERIA-7510] 80000-Roro Ruru", "");
		model.Message m3 = new model.Message("from3@hotmail.com", "to@gmail.com", "[ALTA-MATERIA-7520] 90100-Sasa Rara", "");
		messages.add(m);
		messages.add(m2);
		messages.add(m3);	
		return messages;
	}
	
	public List<model.Message> getAltaGruposMessagesValidos(String path_attach, String file_name) {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("from@hotmail.com", "to@gmail.com", "[ALTA-GRUPO]", "");
		//m.addAttachments(attach)
		HashMap<String, String> hash = new HashMap<String,String>();
		hash.put(path_attach, file_name);
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
	
	public List<model.Message> getEntregaMessages() {
		List<model.Message> messages = new ArrayList<Message>();
		model.Message m = new model.Message("from@hotmail.com", "to@gmail.com", "[ENTREGA-TP-1]", "");
		messages.add(m);
		return messages;
	}
	

	

}
