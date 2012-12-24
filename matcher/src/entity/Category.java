package entity;

import java.util.ArrayList;
import java.util.List;


public class Category {
	private String description;
	private float weight=1f; //DEFAULT
	private ArrayList<Integer> categoryItems=null; //DEFAULT
	
	
	
	public Category(String description){
		categoryItems= new ArrayList<Integer>();
		this.description=description;
	}
	
	//#############################
	//GETTER & SETTER
	//#############################
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	

	public ArrayList<Integer> getCategoryItems() {
		return categoryItems;
	}

	public void addCategoryItems(int i) {
		this.categoryItems.add(i);
	}
	
	public String toString(){
		String out=this.description+"\n[";
		for(Integer i:categoryItems){
			out=out+" "+i;
		}
		out=out+"]\nweight: "+weight;
		

		return out;
	}
	
	
	
	
	

}
