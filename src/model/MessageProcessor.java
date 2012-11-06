package model;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageProcessor {
	
	public 	List<model.Message> process(List<model.Message> mesagge) {
		Iterator<model.Message> it = mesagge.iterator();
		while (it.hasNext()) {
			model.Message m = it.next();
			
			System.out.print("FROM: " + m.getFrom());
			System.out.print(" \n");
			System.out.print("TO: " + m.getTo());
			System.out.print(" \n");
			System.out.print("CC: " + m.getToCC());
		
			System.out.print(" \n");
			System.out.print("SUBJECT: " + m.getSubject());
			System.out.print(" \n");
			System.out.print("BODY: " + m.getBody());
			System.out.print(" \n");
		}
	
		List<model.Message> lista = new ArrayList<model.Message>();
		model.Message respuesta = new model.Message("pruebatecnicas@gmail.com","pruebatecnicas@hotmail.com","Respuesta","HOOla");
		respuesta.addCC("gusttavo_fco@hotmail.com");
		lista.add(respuesta);
		return lista;
	}
	
}
