package matcher;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import au.com.bytecode.opencsv.CSVWriter;

import entity.Category;
import entity.DataHelper;
import entity.MatcherSuspect;
import entity.Suspect;

/**
 * 
 *
 */
public class Matcher {
	private DataHelper helper=null;
	private String targetName="";
	private ArrayList<MatcherSuspect> matches;
	
	public Matcher(DataHelper helper){
		this.helper=helper;
		matches= new ArrayList<MatcherSuspect>();
	}

	/**
	 * 
	 * @param targetName
	 */
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	/**
	 * 
	 */
	public void createMatchFile(){
		if(targetName.isEmpty()){
			return;
		}
		//System.out.println(createFileName());


		Suspect target=helper.getByName(targetName);

		if(target==null)
			return;




		// match each entry towards the target
		for(Suspect s: helper.getEntries()){
			if(!s.getName().equals(target.getName()))
				matches.add(new MatcherSuspect(s.getName(), match(target, s)));

		}
		
		//order result list by distance
		Collections.sort(matches);
		
		//write to file
		writeFile(createFileName(), matches);




	}
	/**
	 * 
	 * @param file
	 * @param matches
	 */

	private void writeFile(String file, ArrayList<MatcherSuspect> matches){
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(file), '\t');

			//write
			for(MatcherSuspect s: matches){
				writer.writeNext(s.toLine());
			}
			
			//CLOSING
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param target
	 * @param suspect
	 * @return
	 */
	private float match(Suspect target,Suspect suspect){
		Category[] order= helper.getCategoryOrder();

		float distance=target.getEntries().length;

		int[] mateEntries= suspect.getEntries();
		int a=0,b=0;

		for(int i=0; i<target.getEntries().length; i++){
			a=target.getEntries()[i];
			b=mateEntries[i];

			if(a == b){
				distance = (distance-1)*order[i].getWeight();
			}else {
				distance = (distance+1)*order[i].getWeight();
			}
		}
		return distance;
	}

	/**
	 * 
	 * @return
	 */
	private String createFileName(){
		SimpleDateFormat df= new  SimpleDateFormat("yy_ddMM_kkmmss"); //TIMESTAMP format	
		return "data/matches/"+df.format(new Date(System.currentTimeMillis()))+"_"+targetName.replace(" ", "_")+".csv";
	}





}
