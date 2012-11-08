package controller.tests.mocks;

import model.Rule;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persistence.IXmlManager;

public class XmlManagerMock implements IXmlManager<Iterable<Rule>> {

	@Override
	public Element getElementFromItem(Iterable<Rule> item, Document document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Rule> getItemFromXmlElement(Element element)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
