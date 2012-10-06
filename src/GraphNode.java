import java.util.HashMap;


public class GraphNode  extends GraphElement{
	
	private String id;
	private String label;
	private double size;
	private double x;
	private double y;
	private String color;
	private HashMap<String,String> attributes;
			
	public GraphNode(String id) {
		this.id=id;
		label="";
		size=1;
		x = 100 - 200*Math.random();
		y = 100 - 200*Math.random();
		color="";
		attributes = new HashMap<String,String>();
	}
	
	public void putAttribute(String key, String value) {
		attributes.put(key, value);
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public HashMap<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	

}
