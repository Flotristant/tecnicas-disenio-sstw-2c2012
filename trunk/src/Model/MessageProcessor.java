package Model;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageProcessor {
	
	public 	List<Model.Message> process(List<Model.Message> mesagge) {
		Iterator<Model.Message> it = mesagge.iterator();
		while (it.hasNext()) {
			Model.Message m = it.next();
			
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
	
		List<Model.Message> lista = new ArrayList<Model.Message>();
		Model.Message respuesta = new Model.Message("pruebatecnicas@gmail.com","pruebatecnicas@hotmail.com","Respuesta","HOOla");
		respuesta.addCC("gusttavo_fco@hotmail.com");
		lista.add(respuesta);
		return lista;
	}
	
}
