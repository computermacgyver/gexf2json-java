import java.util.HashMap;


public class GraphElement {
	
	private String id;
	private HashMap<String,String> attributes;
	
	public GraphElement(String id) {
		this.id=id;
		attributes=new HashMap<String,String>();
	}
	
	public void putAttribute(String key, String value) {
		attributes.put(key, value);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<String, String> getAttributes() {
		return attributes;
	}

	public void putAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}

	
	
}
