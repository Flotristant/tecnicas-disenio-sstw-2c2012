package controller.tests.mocks;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import persistence.IXmlFileManager;

public class XmlFileManagerMock implements IXmlFileManager {

	@Override
	public Document read(String filePath) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		return builder.parse(new InputSource(new StringReader("<?xml version='1.0' encoding='UTF-8' standalone='no'?>" +
				"<rules><rule name='RuleAltaGrupo' pattern='\\[ALTA-GRUPO\\]'>" +
		"<action name='ActionValidateSender'/><action name='ActionValidateStudent"+
				"InGroup'/></rule><rule name='RuleAltaMateria' pattern='\\[ALTA-MATE"+
		"RIA-([0-9]{4})\\] ([0-9]{5})-(.*)'><action name='ActionAltaAlumno'/></rule>" +
				"<rule name='RuleConsultaTema' pattern='\\[CONSULTA-((PUBLICA)|(PRIVADA"+
		"))\\] .*'><action name='ActionValidateSender'/></rule><rule name='RuleEntregaTp'"+
				" pattern='\\[ENTREGA-TP-([0-9]+)\\]'><action name='ActionValidateSender'/>"+
		"<action name='ActionSaveTp'/></rule><rule name='RuleSpam' pattern='.*'/></rules>")));
	}

}
