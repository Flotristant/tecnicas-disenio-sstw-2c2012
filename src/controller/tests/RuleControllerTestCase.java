package controller.tests;

import java.util.ArrayList;

import junit.framework.Assert;

import model.factories.mocks.ActionRuleFactoryMock;
import model.factories.mocks.RuleFactoryMock;

import org.junit.Before;
import org.junit.Test;

import persistence.RuleXmlManager;
import persistence.XmlFileManager;

import controller.RuleController;
import controller.tests.mocks.XmlFileManagerMock;
import controller.tests.mocks.XmlManagerMock;



public class RuleControllerTestCase {

	private XmlFileManagerMock xmlFileManager;

	private XmlManagerMock ruleXmlManager;
	
	@Before
	public void setUp() throws Exception {
		this.xmlFileManager = new XmlFileManagerMock();
		this.ruleXmlManager = new XmlManagerMock(); 
	}
	@Test
	public void testShouldCreateEveryRules() {
		RuleController controller = this.createController();
		
	}
//	{
//		PatternRule pattern = new PatternRule();
//
//		Assert.assertNull(pattern.searchPattern("[ALTA-MATERIA-75.08] 9097-catalina"));
//		Assert.assertNull(pattern.searchPattern("[ENTREGA-TP]"));
//		Assert.assertNull(pattern.searchPattern("[CONSULTA] TEMA1"));
//		Assert.assertNull(pattern.searchPattern("[ALTA-GRUPO5]"));
//	}
//	
//	@Test
//	public void testShouldPatternRuleReturnRuleAltaMateria()
//	{
//		PatternRule pattern = new PatternRule();
//
//		Assert.assertTrue(pattern.searchPattern("[ALTA-MATERIA-75.08] 90976-catalina") instanceof RuleAltaMateria);
//	}
//	
//	@Test
//	public void testShouldPatternRuleReturnRuleEntregaTp()
//	{
//		PatternRule pattern = new PatternRule();
//
//		Assert.assertTrue(pattern.searchPattern("[ENTREGA-TP-9]") instanceof RuleEntregaTp);
//	}
//	
//	@Test
//	public void testShouldPatternRuleReturnRuleConsultaTema()
//	{
//		PatternRule pattern = new PatternRule();
//		
//		Assert.assertTrue(pattern.searchPattern("[CONSULTA-PUBLICA] TEMA1") instanceof RuleConsultaTema);
//		Assert.assertTrue(pattern.searchPattern("[CONSULTA-PRIVADA] TEMA2") instanceof RuleConsultaTema);
//	}
//	
//	@Test
//	public void testShouldPatternRuleReturnRuleAltaGrupo()
//	{
//		PatternRule pattern = new PatternRule();
//
//		Assert.assertTrue(pattern.searchPattern("[ALTA-GRUPO]") instanceof RuleAltaGrupo);
//	}
	
	private RuleController createController(){
		return new RuleController(this.xmlFileManager, this.ruleXmlManager);
	}
}
