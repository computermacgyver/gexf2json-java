package uk.ac.ox.oii.gexf2json;
import com.allaire.cfx.* ;

public class cfgexf2json implements CustomTag {
 public void processRequest( Request request, Response response )
    throws Exception {
    String inputFile = request.getAttribute( "gexf" ) ;
    String outputFile = request.getAttribute( "json" ) ;
    gexf2json.gexfTojson(inputFile,outputFile);
    
 }
}
