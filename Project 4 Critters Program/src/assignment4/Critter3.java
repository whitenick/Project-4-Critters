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

public class Critter3 extends Critter{
	int dir;
	boolean encounter;
	
	@Override
	public String toString() { return "R"; }
	
	public Critter3 (){
		dir = Critter.getRandomInt(8);
	}

	@Override
	public void doTimeStep() {
		run(dir);
		
		if(this.getEnergy() > Params.start_energy + 50){
			Critter3 newRabbit = new Critter3();
			reproduce(newRabbit, newRabbit.dir);
		}
		dir = Critter.getRandomInt(8);
	}

	@Override
	public boolean fight(String oponent) {
		encounter = true;
		if(!oponent.equals("R")){
			return true;
		}
		if(this.getEnergy() > Params.start_energy + 20){
			Critter3 newRabbit = new Critter3();
			reproduce(newRabbit, newRabbit.dir);
		}
		return false;
	}
	
	public static void runStats(java.util.List<Critter> rabbits) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : rabbits) {
			Critter3 r = (Critter3) obj;
			if(r.dir == 2){
				total_straight++;
			}else if(r.dir == 1 || r.dir == 0 || r.dir == 7){
				total_right++;
			}else if(r.dir == 6){
				total_back++;
			}else{
				total_left++;
			}
			System.out.println("" + r.getEnergy() + "" + r.encounter);
		}
		System.out.print("" + rabbits.size() + " total Rabbits    ");
		System.out.print("" + total_straight / (0.01 * rabbits.size()) + "% straight   ");
		System.out.print("" + total_back / (0.01 * rabbits.size()) + "% back   ");
		System.out.print("" + total_right / (0.01 * rabbits.size()) + "% right   ");
		System.out.print("" + total_left / (0.01 * rabbits.size()) + "% left   ");
		System.out.println();
	}

}
