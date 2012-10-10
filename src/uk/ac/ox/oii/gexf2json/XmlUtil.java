package uk.ac.ox.oii.gexf2json;


import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class XmlUtil {
	
	private static DocumentBuilderFactory factory;
	private static DocumentBuilder builder;

	private static DocumentBuilder getDocumentBuilder() {
		if (builder==null) {
			try {
				factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(true);
				builder = factory.newDocumentBuilder();
			} catch (Exception e) {
				System.err.println("Cannot create Document Builder");
				return null;
			}
		}
		return builder;
	}
	
	public static Document stringToXML(String xml) {
		/*Input is xml fragment starting with <page> and ending with </page>*/
		DocumentBuilder b = getDocumentBuilder();
		Document xmlDoc=null;
		try {
	        InputSource is = new InputSource(new StringReader(xml));
	        xmlDoc = b.parse(is);
		} catch (Exception e) {
			System.err.println("Cannot parse XML string.");
			e.printStackTrace();
			return null;
		}
		return xmlDoc;
	}
	
	public static Document fileToXml(String filename) {
		/*Input is xml fragment starting with <page> and ending with </page>*/
		DocumentBuilder b = getDocumentBuilder();
		Document xmlDoc=null;
		try {
	        //InputSource is = new InputSource(new StringReader(xml));
	        xmlDoc = b.parse(filename);
		} catch (Exception e) {
			System.err.println("Cannot parse XML file.");
			e.printStackTrace();
			return null;
		}
		return xmlDoc;
	}

}
