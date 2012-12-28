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
	/*
	 * if this is true Matcher is matching one specific target
	 * towards all other, if it is false, all entries are used as target
	 * towards all other.
	 */
	private boolean specific = true;

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
		Suspect target = null;

		if (targetName.isEmpty() || targetName.equals("*")) {
			specific = false;
		} else {

			target = helper.getByName(targetName);

			if (target == null) {
				System.out.println("Target in data-set not found!");
				return;
			}
		}


		if (specific) {
			// match each entry towards the target
			for (Suspect s : helper.getEntries()) {
				if (!s.getName().equals(target.getName()))
					matches.add(new MatcherSuspect(s.getName(), match(target, s)));
			}
		} else {
			int i = helper.getEntries().size()/2;
			for(Suspect t: helper.getEntries()){
				i--;
				for (Suspect s : helper.getEntries()) {
					if (!s.getName().equals(t.getName()))
						matches.add(new MatcherSuspect(s.getName() + " - " + t.getName(), match(t, s)));
				}
				if(i==0){
					break;
				}
			}
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

		float distance= 0;//target.getEntries().length;

		int[] susEntries= suspect.getEntries();
		int a=0,b=0;

		for(int i=0; i<target.getEntries().length; i++){
			a=target.getEntries()[i];
			b=susEntries[i];

			if(a==1 && b==1){
				distance= distance+(3*order[i].getWeight());
			}
			else if(a==0 && b==0){
				distance= distance+(0*order[i].getWeight());
			}
			else if(a==1 && b==0){
				distance= distance-(1*order[i].getWeight());
			}
			else if(a==0 && b==1){
				distance= distance-(1*order[i].getWeight());
			}

			/*
			if(a == b){
				distance = distance-(1*order[i].getWeight());
			}else {
				distance = distance+(1*order[i].getWeight());
			}
			 */
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
