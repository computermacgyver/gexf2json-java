package uk.ac.ox.oii.gexf2json;
import com.allaire.cfx.* ;

public class cfgexf2json implements CustomTag {
 public void processRequest( Request request, Response response )
    throws Exception {
    String inputFile = request.getAttribute( "gexf" ) ;
    String outputFile = request.getAttribute( "json" ) ;
   // gexf2json.gexfTojson(inputFile,outputFile);
	gexf2json thing = new gexf2json(inputFile,outputFile);
	new Thread(thing).start();
 }
 
 public void parse(String gexf, String json) {
		gexf2json thing = new gexf2json(gexf,json);
		new Thread(thing).start();
 }
 
 
}
