package controller.tests.mocks;
import java.util.ArrayList;
import java.util.List;

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
		model.Message m2 = new model.Message("from2@hotmail.com", "to@gmail.com", "[ALTA-MATERIA-7510] 90101-Roro Ruru", "");
		model.Message m3 = new model.Message("from3@hotmail.com", "to@gmail.com", "[ALTA-MATERIA-7520] 90102-Sasa Rara", "");
		messages.add(m);
		messages.add(m2);
		messages.add(m3);
		
		return messages;
	}
	
	public List<model.Message> getAltaGruposMessages() {
		List<model.Message> messages = new ArrayList<Message>();
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
