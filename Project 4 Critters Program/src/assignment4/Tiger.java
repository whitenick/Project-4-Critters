package assignment4;
/* CRITTERS <MyClass.java>
 * EE422C Project 4 submission by
 * Nicholas White
 * NWW295
 * 16545
 * Javier Cortes
 * jc74593
 * 16445
 * Slip days used: <0>
 * Fall 2016
 */

public class Tiger extends Critter {
	int dir;
	boolean encounter;
	boolean goStraight = true;
	
	@Override
	public String toString() { return "T"; }
	
	public Tiger(){
		dir = Critter.getRandomInt(8);
	}

	@Override
	public void doTimeStep() {
		run(dir);
		
		if(this.getEnergy() > Params.start_energy + 100){
			Tiger newTiger = new Tiger();
			reproduce(newTiger, newTiger.dir);
		}
		if(goStraight){
			dir = 2;
		}else{
			dir = Critter.getRandomInt(8);
		}
		goStraight = !goStraight;
	}

	@Override
	public boolean fight(String oponent) {
		encounter = true;
		if(!oponent.equals("T")){
			return true;
		}
		
		if(this.getEnergy() > Params.start_energy + 50){
			Tiger newTiger = new Tiger();
			reproduce(newTiger, newTiger.dir);
		}
		
		return false;
	}
	
	public static void runStats(java.util.List<Critter> tigers) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : tigers) {
			Tiger t = (Tiger) obj;
			if(t.dir == 2){
				total_straight++;
			}else if(t.dir == 1 || t.dir == 0 || t.dir == 7){
				total_right++;
			}else if(t.dir == 6){
				total_back++;
			}else{
				total_left++;
			}
			System.out.println("" + t.getEnergy() + "" + t.encounter);
		}
		System.out.print("" + tigers.size() + " total Rabbits    ");
		System.out.print("" + total_straight / (0.01 * tigers.size()) + "% straight   ");
		System.out.print("" + total_back / (0.01 * tigers.size()) + "% back   ");
		System.out.print("" + total_right / (0.01 * tigers.size()) + "% right   ");
		System.out.print("" + total_left / (0.01 * tigers.size()) + "% left   ");
		System.out.println();
	}

}
