/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4;

import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	
	private static List<Integer> xCoor = new java.util.ArrayList<Integer>();
	private static List<Integer> yCoor = new java.util.ArrayList<Integer>();


	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	protected final void walk(int direction) {
		makeMov(direction);
		energy =- Params.walk_energy_cost;
	}
	
	protected final void run(int direction) {
		makeMov(direction);
		makeMov(direction);
		energy =- Params.run_energy_cost;
	}
	
	protected final void makeMov(int direction) {
		switch (direction) {
		case 0: x_coord++;
				x_coord = x_coord%Params.world_width;
				break;
		case 1: x_coord++;
				x_coord = x_coord%Params.world_width;
				y_coord--;
				y_coord = y_coord%Params.world_height;
				break;
		case 2: y_coord--;
				y_coord = y_coord%Params.world_height;
				break;
		case 3: y_coord--;
				y_coord = y_coord%Params.world_height;
				x_coord--;
				x_coord = x_coord%Params.world_width;
				break;
		case 4: x_coord--;
				x_coord = x_coord%Params.world_width;
				break;
		case 5: y_coord++;
				y_coord = y_coord%Params.world_height;
				x_coord--;
				x_coord = x_coord%Params.world_width;
				break;
		case 6: y_coord++;
				y_coord = y_coord%Params.world_height;
				break;
		case 7: x_coord++;
				x_coord = x_coord%Params.world_width;
				y_coord++;
				y_coord = y_coord%Params.world_height;
				break;
		}
	}
	
	protected final void reproduce(Critter offspring, int direction){
		makeMov(direction);
		babies.add(offspring);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			String critterClass = myPackage + "." + critter_class_name;
			Critter obj = (Critter) Class.forName(critterClass).newInstance();
			obj.x_coord = getRandomInt(Params.world_width);
			obj.y_coord = getRandomInt(Params.world_height);
			obj.energy = Params.start_energy;
			
			xCoor.add(obj.x_coord);
			yCoor.add(obj.y_coord);
			population.add(obj);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		String critterClass = myPackage + "." + critter_class_name;
		for(int i = 0; i < population.size(); i++){
			Critter critter = population.get(i);
			
		}
		
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
	}
	
	private static void handleFight(Critter a, Critter b){
		int aOldX = a.x_coord;
		int aOldY = a.y_coord;
		
		int bOldX = b.x_coord;
		int bOldY = b.y_coord;
		
		boolean aFight = a.fight(b.toString());
		boolean bFight = b.fight(a.toString());
		
		if(!aFight){
			
		}
		
		if((a.x_coord == b.x_coord) && (a.y_coord == b.y_coord)){
			int aRoll;
			int bRoll;
			
			if(aFight){
				aRoll = getRandomInt(a.energy);
			}else
				aRoll = 0;
			
			if(bFight){
				bRoll = getRandomInt(b.energy);
			}else
				bRoll = 0;
			
			if(aRoll > bRoll){
				a.energy += b.energy/2;
				population.remove(b);
			}
			else if(bRoll > aRoll){
				b.energy += a.energy/2;
				population.remove(a);
			}
			else if(aRoll == bRoll){
				int ran = getRandomInt(2);
				if(ran == 0){
					a.energy += b.energy/2;
					population.remove(b);
				}else{
					b.energy += a.energy/2;
					population.remove(a);
				}
			}
		}
	}
	
	public static void worldTimeStep() {
		for(int i = 0; i<population.size(); i++){
			Critter thisCritter = population.get(i);
			thisCritter.doTimeStep();
		}
		
		for(int i = 0; i<population.size(); i++){
			Critter aCritter = population.get(i);
			for(int j = 0; j<population.size(); j++){
				if(i != j){
					Critter bCritter = population.get(j);
					if((aCritter.x_coord == bCritter.x_coord) 
					&& (aCritter.y_coord == bCritter.y_coord)){
						//TODO: Make critters fight, remove a dead critter, continue to fight
						handleFight(aCritter, bCritter);
					}
				}
			}
		}
		
		population.addAll(babies);
		
		for(int i = 0; i<population.size(); i++){
			Critter thisCritter = population.get(i);
			thisCritter.energy -= Params.rest_energy_cost;
			if(thisCritter.energy <= 0){
				xCoor.remove(i);
				yCoor.remove(i);
				population.remove(i);
			}
		}
	}
	
	private static void printRBorder(){
		for(int i = 0; i < Params.world_width; i++){
			System.out.print("-");
		}
	}
	
	private static int findNextCritter(int row, int critterIndex){
		return yCoor.subList(critterIndex+1, yCoor.size()).indexOf(row);
	}
	
	private static void printRow(int row){
		Critter critter = null;
		int critterIndex = -1;
		int critterX = -1;
		int i = 0;
		
		critterIndex = findNextCritter(row, critterIndex);
		if(critterIndex != -1){
			critter = population.get(critterIndex);
			critterX = population.get(critterIndex).x_coord;
		}
		
		System.out.print("|");
		while(i < Params.world_width){
			System.out.print(" ");
			
			
			if(i == critterX){
				System.out.print(critter);
				
				critterIndex = findNextCritter(row, critterIndex);
				if(critterIndex != -1){
					critter = population.get(critterIndex);
					critterX = population.get(critterIndex).x_coord;
				}
				i++;
			}
			
			i++;
		}
		System.out.print("|");
	}
	
	public static void displayWorld() {
		System.out.print("+");
		printRBorder();
		System.out.print("+");
		
		System.out.println();
		int row = 0;
		while(row < Params.world_height){
			printRow(row);
			System.out.println();
			row ++;
		}
		
		System.out.print("+");
		printRBorder();
		System.out.print("+");
		System.out.println();
	}
}
