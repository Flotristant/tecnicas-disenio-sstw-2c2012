package controller.tests.mocks;

import org.w3c.dom.Document;

import persistence.IXmlFileManager;
import persistence.tests.TestUtilities;

public class XmlFileManagerMock implements IXmlFileManager {

	private String nameElement = "RuleAltaMateria";
	
	@Override
	public Document read(String filePath) throws Exception {
		
		String xml = "<rule name='RuleAltaMateria' pattern='pattern1'/>";
		
		return TestUtilities.loadXMLFromString(xml);
	}

	public String getAttrElement() {
		return this.nameElement;
	}
}
