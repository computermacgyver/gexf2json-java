import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;

public class gexf2json {

	public static void main(String[] args) {
		if (args.length!=2) {
			System.err.println("usage java -jar input.gexf output.json");//[large]
			System.exit(1);
		}
		//Path filename=Paths.get(args[0]);
		String filename=(args[0]);
		String outputFile=(args[1]);
		//boolean readInPieces=false;
		//if (args.length==3 && "large".equalsIgnoreCase(args[2])) readInPieces=true;
		
		/*BufferedReader br = new BufferedReader(new FileReader(filename));
		StringBuilder sb;
		String line  = br.readLine();
		while ( line != null) {
			sb.append(line);
		}*/
		Document doc = XmlUtil.fileToXml(filename);
		
		////////////////////////////////////////////////////////////////////////
		//					Attribute Dictionaries							  //
		////////////////////////////////////////////////////////////////////////

		//ArrayList nodesAttributes = new ArrayList();//#The list of attributes of the nodes of the graph that we build in json
		HashMap<String,String> nodesAttributesDict=new HashMap<String,String>();
		//edgesAttributes = []#The list of attributes of the edges of the graph that we build in json
		HashMap<String,String> edgesAttributesDict=new HashMap<String,String>();

		//#In the gexf (that is an xml), the list of xml nodes 'attributes' (note the plural 's')
		NodeList attributesNodes = doc.getElementsByTagName("attributes");
		for (int i=0; i<attributesNodes.getLength(); i++) {
			Element attributesNode = (Element)attributesNodes.item(i);
			if ("node".equalsIgnoreCase(attributesNode.getAttribute("class"))) {
				NodeList attributeNodes = attributesNode.getElementsByTagName("attribute");//#The list of xml nodes 'attribute' (no 's')
				for (int j=0; j<attributeNodes.getLength(); j++) {
					Element attributeNode = (Element) attributeNodes.item(j);
					String id = attributeNode.getAttribute("id");
					String title = attributeNode.getAttribute("title");
					//String type = attributeNodes.getAttribute("type");
					//String attribute = {"id":id, "title":title, "type":type}
					//nodesAttributes.append(attribute)
					nodesAttributesDict.put(id, title);
				}
			} else if ("edge".equalsIgnoreCase(attributesNode.getAttribute("class"))) {
				//#The list of xml nodes 'attribute' (no 's')
				NodeList attributeNodes = attributesNode.getElementsByTagName("attribute");
				for (int j=0; j<attributeNodes.getLength(); j++) {
					Element attributeNode = (Element) attributeNodes.item(j);
					//#Each xml node 'attribute'
					String id = attributeNode.getAttribute("id");
				  	String title = attributeNode.getAttribute("title");
				  	//String type = attributeNode.getAttribute("type");
				  	
				  	//attribute = {"id":id, "title":title, "type":type}
				  	//edgesAttributes.append(attribute)
				  	edgesAttributesDict.put(id, title);
				}
			}
		}

		
		
		
		////////////////////////////////////////////////////////////////////////
		//							Nodes									  //
		////////////////////////////////////////////////////////////////////////
		
		
		HashSet<GraphElement> jNodes = new HashSet<GraphElement>();
		
		//The list of xml nodes 'nodes' (plural)
		NodeList nodesNodes = doc.getElementsByTagName("nodes");

		for (int i=0; i<nodesNodes.getLength(); i++) {
			Element nodes = (Element)nodesNodes.item(i);
			
			//The list of xml nodes 'node' (no 's')
			NodeList listNodes = nodes.getElementsByTagName("node");
			for (int j=0; j<listNodes.getLength(); j++) {
				Element nodeEl = (Element)listNodes.item(j);
				//Each xml node 'node' (no 's')
				String id = nodeEl.getAttribute("id");
				//String title = nodeEl.getAttribute("title");
				String label = (nodeEl.hasAttribute("label"))? nodeEl.getAttribute("label")  : id;
			  
				//viz
				double size = 1;
				double x = 100 - 200*Math.random();
				double y = 100 - 200*Math.random();
				String color="";

				//SAH: Original JS code tested for size.length in ternary; is len(...)!=0 appropriate replacement?
				NodeList sizeNodes = nodeEl.getElementsByTagName("size");
				sizeNodes = (sizeNodes.getLength()!=0)? sizeNodes : nodeEl.getElementsByTagNameNS("*","size");
				if (sizeNodes.getLength()>0) {
					Element sizeNode = (Element)sizeNodes.item(0);
					size = Double.valueOf(sizeNode.getAttribute("value"));
				}
				
				//SAH save as sizeNodes
				NodeList positionNodes = nodeEl.getElementsByTagName("position");
				positionNodes = (positionNodes.getLength()!=0)? positionNodes : nodeEl.getElementsByTagNameNS("*","position");
				if(positionNodes.getLength()>0) {
					Element positionNode = (Element)positionNodes.item(0);
					x = Double.valueOf(positionNode.getAttribute("x"));
					y = Double.valueOf(positionNode.getAttribute("y"));
				}
				
				
				//SAH: really couldn't this be a function by now; same as above
				NodeList colorNodes = nodeEl.getElementsByTagName("color");
				colorNodes = (colorNodes.getLength()!=0)? colorNodes : nodeEl.getElementsByTagNameNS("*","color");
				if(colorNodes.getLength()>0) {
					Element colorNode = (Element)colorNodes.item(0);
					/*color = '#'+rgb2hex(int(colorNode.getAttribute('r')),
								int(colorNode.getAttribute('g')),
								int(colorNode.getAttribute('b')))
					*/
					color = String.format("rgb(%s, %s, %s)",
							colorNode.getAttribute("r"),
							colorNode.getAttribute("g"),
							colorNode.getAttribute("b")
							);
				}
				
				//Create Node
				//node = {"id":id,"label":label, "size":size, "x":x, "y":y, "attributes":{}, "color":color};  #The graph node
				/*JSONObject node = new JSONObject(), attributes = new JSONObject();
				try {
					node.put("id", id);
					node.put("label", label);
					node.put("size", size);
					node.put("x", x);
					node.put("y", y);
					node.put("color", color);
					node.put("attributes",attributes);					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				GraphNode node = new GraphNode(id);
				//Collection node = new ArrayList();
				//node.add(5);
				node.setLabel(label);
				node.setSize(size);
				node.setX(x);
				node.setY(y);
				node.setColor(color);
				
				
			  
				//Attribute values
				NodeList attvalueNodes = nodeEl.getElementsByTagName("attvalue");
				for (int k=0; k<attvalueNodes.getLength(); k++) {
					Element attvalueNode = (Element)attvalueNodes.item(k);
					String attr = attvalueNode.getAttribute("for");
					String val = attvalueNode.getAttribute("value");
					//node["attributes"][nodesAttributesDict[attr]]=val
					/*try {
						attributes.put(attr, val);
						//attributes.put(nodesAttributesDict.get(attr), val);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					node.putAttribute(nodesAttributesDict.get(attr), val);
				}

				//jsonNodes.put(node);
				jNodes.add(node);
			}//end <node>
		}//end <nodes>
		
		////////////////////////////////////////////////////////////////////////
		//							Edges									  //
		////////////////////////////////////////////////////////////////////////
		
		//JSONArray jsonEdges = new JSONArray();
		HashSet<GraphElement> jsonEdges = new HashSet<GraphElement>();
		NodeList edgesNodes = doc.getElementsByTagName("edges");
		for (int i=0; i<edgesNodes.getLength(); i++) {
			Element edgesNode = (Element)edgesNodes.item(i);
			NodeList edgeNodes = edgesNode.getElementsByTagName("edge");
			for (int j=0; j<edgeNodes.getLength(); j++) {
				Element edgeNode = (Element)edgeNodes.item(j);

				//String source = edgeNode.getAttribute("source");
				//String target = edgeNode.getAttribute("target");
				//String label = edgeNode.getAttribute("label");
				String id = (edgeNode.hasAttribute("id")) ? edgeNode.getAttribute("id") : String.valueOf(j);

				
				GraphEdge edge = new GraphEdge(id);
				
				//Attributes within <edge> tag
				NamedNodeMap attrs = edgeNode.getAttributes();
				Node item;
				for (int k=0; k<attrs.getLength(); k++) {
					item=attrs.item(k);
					String n = item.getNodeName();
					String val = item.getNodeValue();
					switch(n) {
					case "source":
						edge.setSource(val);
						break;
					case "target":
						edge.setTarget(val);
						break;
					case "label":
						edge.setLabel(val);
						break;
					default:
						edge.putAttribute(n,val);
					}
				}
				
				/*NodeList attvalueNodes = edgeNode.getElementsByTagName("attvalue");
				for (int k=0; k<attvalueNodes.getLength(); k++) {
					Element attvalueNode = (Element)attvalueNodes.item(k);
					String attr = attvalueNode.getAttribute("for");
					String val = attvalueNode.getAttribute("value");
					//edge["attributes"][edgesAttributesDict[attr]]=val
					try {
						attributes.put(attr, val);
						//attributes.put(edgesAttributesDict.get(attr), val);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}*/

				jsonEdges.add(edge);
			}//end <edge>
		}//end <edges>
		
		
		//Combine nodes and edges
		/*JSONObject json = new JSONObject();
		try {
			json.put("edges", jsonEdges);
			json.put("nodes", jsonNodes);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		HashMap<String,HashSet<GraphElement>> json = new HashMap<String,HashSet<GraphElement>>();
		json.put("nodes", jNodes);
		json.put("edges",jsonEdges);
		
		
		//output is outputFile
		Gson gson = new Gson();
		FileWriter writer;
		try {
			writer = new FileWriter(outputFile);
			gson.toJson(json, writer);
			//writer.write(jsonEdges.toString(4));
			//json.write(writer);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}

		
	}
}