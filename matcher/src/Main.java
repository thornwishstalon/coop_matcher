

import matcher.Matcher;
import entity.DataHelper;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//initialize internal DataStructure
		
		
		
			DataHelper helper= new DataHelper("data/Interessen_Studenten.csv", "data/weights_TEST.csv");
			Matcher  matcher= new Matcher(helper);
			matcher.setTargetName("Abila Christian");
			matcher.createMatchFile();
		
	}

}
