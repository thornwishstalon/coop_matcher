package entity;

public class MatcherSuspect implements Comparable<MatcherSuspect> {
	private String description;
	private float distance;
	
	public MatcherSuspect(String description, float distance){
		this.description= description;
		this.distance=distance;
	}
	
	public float getDistance(){
		return distance;
	}
	
	@Override
	public int compareTo(MatcherSuspect sus) {
		if(sus.getDistance()== this.distance)
			return 0;
		else if(sus.getDistance() < this.distance)
			return 1;
		else return -1;
	}
	
	public String[] toLine(){
		return (description+"#"+distance).split("#");
	}
}

