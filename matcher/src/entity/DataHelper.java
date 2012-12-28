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
	private HashMap<String, Category> categories;
	private int dataSetLength=0;
	private Category[] categoryOrder; 

	/**
	 * 
	 * @param dataFilePath
	 */
	public DataHelper(String dataFilePath){

		init(dataFilePath);

	}
	

	/**
	 * 
	 * @param dataFilePath
	 */
	public DataHelper(String dataFilePath, String weightsDataFilePath){

		init(dataFilePath);
		initWeights(weightsDataFilePath);
		initOrder();
		
		//DEBUG
		//System.out.println(categories.toString());

	}
	/**
	 * 
	 */
	private void initOrder() {
		categoryOrder = new Category[dataSetLength];
		
		Category c=null;
		for(String s: categories.keySet()){
			c= categories.get(s);
			for(Integer i:c.getCategoryItems()){
				categoryOrder[i-1]=c;
			}
			
		}
		
		/*
		 //DEBUG
		for(int i = 0; i<dataSetLength;i++){
			System.out.println(categoryOrder[i].getDescription());
		}
		*/
		
	}
	/**
	 * 
	 * @param weightsDataFilePath
	 */
	private void initWeights(String weightsDataFilePath) {
		try {
			CSVReader reader= new CSVReader(new FileReader(weightsDataFilePath));
			String[] nextLine;
			Category category=null;
			String desc="";
			while((nextLine = reader.readNext()) != null) {
				desc= nextLine[0];
				
				category= categories.get(desc);
				
				if(category!=null){
					category.setWeight(new Float(nextLine[1]));
					//update
					categories.put(desc, category);
				}
			}
	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NumberFormatException e){
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 
	 * @param dataFilePath
	 */
	private void init(String dataFilePath){
		suspects= new HashMap<String, Suspect>();
		categories= new HashMap<String, Category>();

		try {
			CSVReader reader= new CSVReader(new FileReader(dataFilePath));
			String[] nextLine;
			Suspect tmp;

			//read categories
			nextLine=reader.readNext();

			//we assume the first position in this line holds no relevant relevant information due to the data-file-layout
			int i=1; 
			String s="";
			Category currentCat=null;
			
			dataSetLength=nextLine.length - 1;
			
			//System.out.println("setLenght: "+dataSetLength);
			
			while(i<nextLine.length){
				s= nextLine[i];
			
				if(s.length()>0){
					//create new Category and set this one as the currently interesting one
					if(currentCat!=null){
						if(!s.equals(currentCat.getDescription())){
							categories.put(currentCat.getDescription(), currentCat);
							currentCat= new Category(s);
							currentCat.addCategoryItems(i);
						}else{
							currentCat.addCategoryItems(i);
						}
					}else{
						currentCat= new Category(s);
						currentCat.addCategoryItems(i);
					}

				}else{
					//add lineIndex to current Category
					currentCat.addCategoryItems(i);
					
				}
				
				i++;
			}
			if(currentCat!=null)
				categories.put(currentCat.getDescription(), currentCat);
			
			


			// skip line holding the column-headers
			reader.readNext();



			// read the csv-data-set
			while((nextLine = reader.readNext()) != null) {
				//System.out.println(nextLine[0]);
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
	
	public String getCategoriesAsString(){
		Category c=null;
		String out="";
		for(String key: categories.keySet()){
			c=categories.get(key);
			out= out+"\n"+c.toString();
		}
		return out;
	}
	
	public Category[] getCategoryOrder(){
		return categoryOrder;
	}
}
