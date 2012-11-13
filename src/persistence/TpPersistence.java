package persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import persistence.ITpPersistence;

public class TpPersistence implements ITpPersistence {
	
	
	private DBTpPersistence dataBaseTp;

	public TpPersistence() {
		this.dataBaseTp = new DBTpPersistence();
	}
	@Override
	public void saveTp(String codigoMateria, String sender, Integer tpNumber,
			Map<String, String> attachments) throws Exception {
		
		String pathToSave;
		
		for (Integer padron : this.dataBaseTp.getPadronesFromGroupOfTheSender(codigoMateria, sender)) {
			pathToSave = codigoMateria + "/TPs/" + tpNumber + padron + "/";
			for (String key : attachments.keySet()) {
				String path = attachments.get(key);
				
				try {
					File inFile = new File(path + key);
					File outFile = new File(pathToSave + key);

					FileInputStream in = new FileInputStream(inFile);
					FileOutputStream out = new FileOutputStream(outFile);

					int byteOfData;
					while( (byteOfData = in.read() ) != -1)
						out.write(byteOfData);

					in.close();
					out.close();
				} catch(IOException e) {
					System.err.println("Hubo un error de entrada/salida!!!");
				}
			}
			this.dataBaseTp.saveTpInDB(codigoMateria, padron, tpNumber, pathToSave);
		}
	}
}
