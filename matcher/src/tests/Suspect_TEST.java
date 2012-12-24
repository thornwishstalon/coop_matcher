package tests;



import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


import entity.DataHelper;
import entity.Suspect;

public class Suspect_TEST {
	private Suspect sus1=null;
	private Suspect sus2=null;
	private DataHelper helper=null;
	
	@After
	public void tearDown(){
		sus1=null;
		sus2=null;
		helper=null;
	}
	
	
	@Test
	public void compare_High_max(){
		sus1= new Suspect(new String[]{"a","0","0","1","1","0","1","0","0","1",});
		sus2= new Suspect(new String[]{"b","0","0","1","1","0","1","0","0","1",});
		
		int m1= sus1.matchTo(sus2);
		int m2= sus2.matchTo(sus1);
		
		System.out.println(m1+" | "+m2);
		
		assertThat(m1,is(m2));
	}
	
	@Test
	public void compare_LOW_max(){
		sus1= new Suspect(new String[]{"a","1","1","0","0","1","0","1","1","0",});
		sus2= new Suspect(new String[]{"b","0","0","1","1","0","1","0","0","1",});
		
		int m1= sus1.matchTo(sus2);
		int m2= sus2.matchTo(sus1);
		
		System.out.println(m1+" | "+m2);
		
		assertThat(m1, is(m2));
	}
	
	@Test
	public void compare_AVG(){
		sus1= new Suspect(new String[]{"a","0","1","1","0","0","0","1","1","0",});
		sus2= new Suspect(new String[]{"b","0","0","1","1","0","1","1","0","1",});
		
		int m1= sus1.matchTo(sus2);
		int m2= sus2.matchTo(sus1);
		
		System.out.println(m1+" | "+m2);
		
		assertThat(m1, is(m2));
	}
}
