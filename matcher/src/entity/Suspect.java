package entity;

/**
 * 
 *
 */
public class Suspect {
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private int[] entries;
	
	/**
	 * 
	 * 
	 * @param line 		String-array holding values from the line origination in the cvs-file
	 */
	public Suspect(String[] line){
		this.name= line[0];
		entries= new int[line.length -1];
		
		
		for(int i=0; i<line.length-1;i++){	
			entries[i]= Integer.valueOf(line[i+1]);
		}
		
	}
	
	
	/**
	 * 
	 * @param mate
	 * @return  s
	 */
	public int matchTo(Suspect mate){
		int directMatches=entries.length;
		
		int[] mateEntries= mate.getEntries();
		int a=0,b=0;
		
		for(int i=0; i<entries.length; i++){
			a=entries[i];
			b=mateEntries[i];
			
			if(a == b){
				directMatches--;
			}else if(a!=b){
				directMatches++;
			}
			
		}
		return  directMatches;
	}


	//##########################
	//GETTER
	//##########################
	
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}


	/**
	 * 
	 * @return
	 */
	public int[] getEntries() {
		return entries;
	}
}
