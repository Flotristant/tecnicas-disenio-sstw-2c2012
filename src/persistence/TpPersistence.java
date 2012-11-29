package persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import persistence.ITpPersistence;
import persistence.exceptions.PersistenceException;

public class TpPersistence implements ITpPersistence {
	
	
	private DBTpPersistence dataBaseTp;

	public TpPersistence() {
		this.dataBaseTp = new DBTpPersistence();
	}
	@Override
	public void saveTp(String codigoMateria, String sender, Integer tpNumber,
			Map<String, String> attachments) throws PersistenceException {
		
		String pathToSave;
		try {
			for (Integer padron : this.dataBaseTp.getPadronesFromGroupOfTheSender(codigoMateria, sender)) {
				pathToSave = "./" + codigoMateria + "/TPs/" + padron + "/" + tpNumber + "/";
				File dirs = new File(pathToSave);
				if (!dirs.mkdir())
					dirs.mkdirs();
				for (String key : attachments.keySet()) {
					String path = attachments.get(key);
					
					try {
						File inFile = new File(path + key);
						File outFile = new File(pathToSave + key);
						outFile.createNewFile();
						
						FileInputStream in = new FileInputStream(inFile);
						FileOutputStream out = new FileOutputStream(outFile);
	
						int byteOfData;
						while( (byteOfData = in.read() ) != -1)
							out.write(byteOfData);
						
						in.close();
						out.close();
					} catch(IOException e) {
						e.printStackTrace();
						System.err.println("Hubo un error de entrada/salida");
					}
				}
				this.dataBaseTp.saveTpInDB(codigoMateria, padron, tpNumber, pathToSave);
				
			}
		} catch(Exception e) {
			throw new PersistenceException();
		}
	}
}
