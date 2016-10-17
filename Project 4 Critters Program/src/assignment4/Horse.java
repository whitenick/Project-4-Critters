package assignment4;

public class Horse extends Critter {
	
	
	@Override 
	public String toString() {
		return "H";
	}
	
	private int dir;
	
	public Horse() {
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String opponent) {
		
		if(this.getEnergy()>100) {
			return true;
		}
		return false;
		
	}
	
	@Override 
	public void doTimeStep() {
		run(dir);
		run(dir);
	}
	
	
}
