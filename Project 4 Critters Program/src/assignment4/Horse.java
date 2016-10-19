package assignment4;

public class Horse extends Critter {
	
	
	@Override 
	public String toString() {
		return "H";
	}
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	private boolean encounter;
	
	
	public Horse() {
		for (int k = 0; k < 8; k++) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
		
	}
	
	public boolean fight(String opponent) {
		
		if(getEnergy()>100) {
			return true;
		}
		
		if(opponent.equals("@")){
			return true;
		}
		dir = Critter.getRandomInt(8);
		encounter = true;
		run(dir);
		return false;
		
	}
	
	@Override 
	public void doTimeStep() {
		run(dir);
		run(dir);
		
		if (getEnergy() > 100) {
			Horse child = new Horse();
			for (int k = 0; k < 8; k++) {
				child.genes[k] = this.genes[k];
			}
			int g = Critter.getRandomInt(8);
			while (child.genes[g] == 0) {
				g = Critter.getRandomInt(8);
			}
			child.genes[g]--;
			g = Critter.getRandomInt(8);
			child.genes[g]++;
			reproduce(child, Critter.getRandomInt(8));
			reproduce(child, Critter.getRandomInt(8));
		}
		
		/* new direction based on our genes */
		int roll = Critter.getRandomInt(GENE_TOTAL);
		int turn = 0;
		while (genes[turn] <= roll) {
			roll = roll - genes[turn];
			turn = turn + 1;
		}
		assert(turn < 8);
		
		dir = (dir + turn) % 8;
		
	}
	
	public static void runStats(java.util.List<Critter> craigs) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : craigs) {
			Horse c = (Horse) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
			System.out.println("" + c.getEnergy() + "" + c.encounter);
		}
		System.out.print("" + craigs.size() + " total Horses    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * craigs.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * craigs.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * craigs.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * craigs.size()) + "% left   ");
		System.out.println();
	}
	
	
}

	
	

