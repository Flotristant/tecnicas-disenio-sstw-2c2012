package persistence;

import org.w3c.dom.Document;

public interface IXmlFileManager {

	Document read(String filePath) throws Exception;

}
