import java.io.FileReader;

import javax.json.JsonObject;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonArray;

import java.net.URL;
import java.io.InputStream;


public class Test
{
	public void getData() throws Exception
	{
		String url = "http://api.erg.kcl.ac.uk/AirQuality/Annual/Map/Json";
		URL data_url = new URL(url);
		InputStream is = data_url.openStream();
		JsonReader rdr = Json.createReader(is);
		
		JsonObject jsonfile = rdr.readObject();
		JsonObject maps = jsonfile.getJsonObject("Maps");
		JsonArray map = maps.getJsonArray("Map");
		for(JsonObject result : map.getValuesAs(JsonObject.class))
		{
			System.out.print(result.getString("@SpeciesCode") + " ");
		}
		
	}
	
	public static void main(String[] args) throws Exception 
	{
		Test newTest = new Test();
		newTest.getData();
	}
	
	
}