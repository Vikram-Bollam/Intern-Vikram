package full.googlesignin;
import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {
 private static ObjectMapper mapper;
 // what to create only one time json method and call when ever wanted
 static {
	 mapper=new ObjectMapper(); 
 }
 public static String javaToJson(Object obj)
 {
	 String resp=null;
	 try {
		resp=mapper.writeValueAsString(obj);
	} catch (JsonGenerationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonMappingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	return resp;
 }
 // generic return type because to avoid down casting
 public static <T> T jsonToJava(String jsonobj, Class<T> cls)
 {   T result =null;
	 try {
		result = mapper.readValue(jsonobj, cls);
	} catch (JsonParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonMappingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	 return result;
 }
}
