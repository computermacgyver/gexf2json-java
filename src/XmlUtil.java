

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
				builder = factory.newDocumentBuilder();
			} catch (Exception e) {
				System.err.println("Cannot create Document Builder");
				return null;
			}
		}
		return builder;
	}
	
	public static Node fragmentToXML(String xml) {
		/*Input is xml fragment starting with <page> and ending with </page>*/
		DocumentBuilder b = getDocumentBuilder();
		Document xmlDoc=null;
		try {
	        InputSource is = new InputSource(new StringReader(xml));
	        xmlDoc = b.parse(is);
		} catch (Exception e) {
			System.err.println("Cannot parse WikiArticle fragment.");
			e.printStackTrace();
			return null;
		}
		return xmlDoc.getFirstChild();
	}
	
	public static Document fileToXml(String filename) {
		/*Input is xml fragment starting with <page> and ending with </page>*/
		DocumentBuilder b = getDocumentBuilder();
		Document xmlDoc=null;
		try {
	        //InputSource is = new InputSource(new StringReader(xml));
	        xmlDoc = b.parse(filename);
		} catch (Exception e) {
			System.err.println("Cannot parse WikiArticle fragment.");
			e.printStackTrace();
			return null;
		}
		return xmlDoc;
	}

}
