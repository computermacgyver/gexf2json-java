import java.util.HashMap;


public class GraphEdge extends GraphElement{
	
	private String id;
	private String label;
	private String source;
	private String target;
	private HashMap<String,String> attributes;
	
	public GraphEdge(String id) {
		this.id=id;
		label=id;
		source="";
		target="";
		attributes=new HashMap<String,String>();
	}
	
	

}
