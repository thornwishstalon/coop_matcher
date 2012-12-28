

import matcher.Matcher;
import entity.DataHelper;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//initialize internal DataStructure
		DataHelper helper=null;


		String target= args[0];
		String data= args[1];
		String weights = args[2];
		
		System.out.println("TARGET: "+target);
		System.out.println("DATA: "+data);		
		System.out.println("WEIGHTS: "+weights);
				
		
		helper= new DataHelper(data, weights);

		//let the fun begin
		Matcher  matcher= new Matcher(helper);

		//TODO
		matcher.setTargetName(target);

		//write output
		matcher.createMatchFile();

	}

}
