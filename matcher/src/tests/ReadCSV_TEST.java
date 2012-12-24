package tests;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Test;

import entity.DataHelper;

public class ReadCSV_TEST {
	private DataHelper helper=null;
	
	@After
	public void tearDown(){
		helper=null;
	}
	
	@Test//(expected = FileNotFoundException.class)
	public void read_invalid(){
		 helper=new DataHelper("data/foooooBA.csv");
		 assertTrue(helper.getEntries().isEmpty());
	}
	
	@Test
	public void read_valid(){
		helper= new DataHelper("data/TEST.csv");
		
		//TODO
	}
	
	@Test
	public void read_TEST_file(){
		helper= new DataHelper("data/TEST.csv");
		
		System.out.println(helper.getCategoriesAsString());
	}
	
	@Test
	public void read_TEST_file_withWeights(){
		helper= new DataHelper("data/TEST.csv", "data/weights_TEST.csv");
		
		System.out.println(helper.getCategoriesAsString());
	}
	
}
