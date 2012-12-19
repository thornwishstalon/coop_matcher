package entity;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;


/**
 * 
 *
 */
public class DataHelper {
	/**
	 * 
	 */
	private HashMap<String, Suspect> suspects;
	
	/**
	 * 
	 * @param dataFilePath
	 */
	public DataHelper(String dataFilePath){
		
		initSuspects(dataFilePath);
		
	}
	
	/**
	 * 
	 * @param dataFilePath
	 */
	private void initSuspects(String dataFilePath){
		suspects= new HashMap<String, Suspect>();
		try {
			CSVReader reader= new CSVReader(new FileReader(dataFilePath));
			String[] nextLine;
			Suspect tmp;

			// skip lines holding the column-headers
			reader.readNext();
			reader.readNext();
			
		
			
			// read the csv-data-set
			while((nextLine = reader.readNext()) != null) {
				System.out.println(nextLine[0]);
				tmp= new Suspect(nextLine);
				
				//dump data in map
				suspects.put(nextLine[0], tmp);
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//##############################
	//GETTER
	//##############################
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Suspect getByName(String name){
		return suspects.get(name);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Suspect> getEntries(){
		ArrayList<Suspect> resultList= new ArrayList<Suspect>();
		
		Set<String> keys= suspects.keySet();
		for(String key: keys){
			resultList.add(suspects.get(key));
		}
		return resultList;
		
	}
}
