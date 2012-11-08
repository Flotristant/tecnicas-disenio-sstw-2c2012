package persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlFileManager implements IXmlFileManager {

	@Override
	public Document read(String path)
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputSource is;
		Document document = null;
		try 
		{
			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(readFile(path)));
			document = builder.parse(is);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) 
		{
			e.printStackTrace();
		}
		catch (SAXException e) 
		{
			e.printStackTrace();
		}
    
		return document;
	}
	
	private static String readFile(String path) throws IOException {
		File file = new File(path);
		FileInputStream stream = new FileInputStream(file);
		FileChannel fc = null;
		try {
			fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			/* Instead of using default, pass in a decoder. */
			return Charset.defaultCharset().decode(bb).toString();
		}
		finally {
			fc.close();
			stream.close();
		}
	}
}
