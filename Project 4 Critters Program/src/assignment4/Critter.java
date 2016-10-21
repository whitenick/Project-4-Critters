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
package assignment4;

import java.util.List;
import java.lang.*;
import java.lang.Exception;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
	
	private static int indexA, indexB, indexAll = 0;

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static List<Critter> runPopulation() {
		return population;
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
	public static boolean encounter;
	
	protected final void walk(int direction) {
		makeMov(direction);
		energy -= Params.walk_energy_cost;
	}
	
	protected final void run(int direction) {
		makeMov(direction);
		makeMov(direction);
		energy -= Params.run_energy_cost;
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
		if(x_coord < 0){
			x_coord += Params.world_width;
		}
		if(y_coord < 0){
			y_coord += Params.world_height;
		}
		x_coord %= Params.world_width;
		y_coord %= Params.world_height;
		
	}
	
	protected final void reproduce(Critter offspring, int direction){
		if(this.energy > Params.min_reproduce_energy){
			offspring.energy = this.energy/2;
			this.energy /= 2;
			makeMov(direction);
			babies.add(offspring);
		}
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
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters. 
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException  {
		List<Critter> result = new java.util.ArrayList<Critter>();
		String critterClass = myPackage + "." + critter_class_name;
		try {
			Class crits = Class.forName(critterClass);
			Critter obj = (Critter) crits.newInstance();
			for(int i = 0; i < population.size(); i++){
				Critter critter = population.get(i);
				if(critter.toString().equals(obj.toString())) {
					result.add(critter);
				}
			}
			Class c = obj.getClass();
			try {
				Method craigMethod = c.getMethod("runStats", List.class);
				try {
					craigMethod.invoke(obj, result);
				} catch (IllegalArgumentException | InvocationTargetException e) {
					throw new InvalidCritterException(critter_class_name);
				}
			} catch (NoSuchMethodException | SecurityException e) {
				throw new InvalidCritterException(critter_class_name);
			}
			
			//runStats(population);
			//c.craigMethod(result);
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			//System.out.println("Invalid Command: ");
			InvalidCritterException except = new InvalidCritterException(critter_class_name);
			throw except;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			//System.out.println("Invalid Command: ");
			//throw e;
			InvalidCritterException except = new InvalidCritterException(critter_class_name);
			throw except;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//System.out.println("Invalid Command: ");
			//throw e;
			InvalidCritterException except = new InvalidCritterException(critter_class_name);
			throw except;
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
			if(new_energy_value <= 0) {
				super.energy = 0;
				xCoor.remove(indexAll);
				yCoor.remove(indexAll);
				population.remove(indexAll);
			}
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			xCoor.set(indexA, new_x_coord);
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			yCoor.set(indexA, new_y_coord);
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
		population.clear();
	}
	
	private static void handleFight(Critter a, Critter b, int aIndex, int bIndex){
		int aOldX = a.x_coord;
		int aOldY = a.y_coord;
		
		int bOldX = b.x_coord;
		int bOldY = b.y_coord;
		
		boolean aFight = a.fight(b.toString());
		boolean bFight = b.fight(a.toString());
		
		if((a.x_coord == b.x_coord) && (a.y_coord == b.y_coord) && (aFight || bFight)){
			int aRoll = 0;
			int bRoll = 0;
			
			if(aFight){
				if(a.energy > 0){
					aRoll = getRandomInt(a.energy);
				}
			}else
				aRoll = 0;
			
			if(bFight){
				if(b.energy > 0){
					bRoll = getRandomInt(b.energy);
				}
			}else
				bRoll = 0;
			
			if(aRoll > bRoll){
				a.energy += b.energy/2;
				xCoor.remove(bIndex);
				yCoor.remove(bIndex);
				population.remove(bIndex);
			}
			else if(bRoll > aRoll){
				b.energy += a.energy/2;
				yCoor.remove(aIndex);
				population.remove(aIndex);
			}
			else if(aRoll == bRoll){
				int ran = getRandomInt(2);
				if(ran == 0){
					a.energy += b.energy/2;
					xCoor.remove(bIndex);
					yCoor.remove(bIndex);
					population.remove(bIndex);
				}else{
					b.energy += a.energy/2;
					xCoor.remove(aIndex);
					yCoor.remove(aIndex);
					population.remove(aIndex);
				}
			}
		}
	}
	
	public static void worldTimeStep() {
		for(int i = 0; i<population.size(); i++){
			Critter thisCritter = population.get(i);
			indexAll = i;
			thisCritter.doTimeStep();
		}
		
		for(int i = 0; i<population.size(); i++){
			Critter thisCritter = population.get(i);
			thisCritter.energy -= Params.rest_energy_cost;
			if(thisCritter.energy <= 0){
				xCoor.remove(i);
				yCoor.remove(i);
				population.remove(i);
			}
		}
		
		for(int i = 0; i<population.size(); i++){
			indexA = i;
			Critter aCritter = population.get(i);
			for(int j = 0; j<population.size(); j++){
				indexB = j;
				if(i != j){
					Critter bCritter = population.get(j);
					if((aCritter.x_coord == bCritter.x_coord) 
					&& (aCritter.y_coord == bCritter.y_coord)){
						//TODO: Make critters fight, remove a dead critter, continue to fight
						handleFight(aCritter, bCritter, i, j);
					}
				}
			}
		}
		
		for(int i = 0; i < babies.size(); i++) {
			xCoor.add(babies.get(i).x_coord);
			yCoor.add(babies.get(i).y_coord);
		}
		population.addAll(babies);
		babies.clear();
		
		for(int i = 0; i<population.size(); i++){
			Critter thisCritter = population.get(i);
			thisCritter.energy -= Params.rest_energy_cost;
			if(thisCritter.energy <= 0){
				xCoor.remove(i);
				yCoor.remove(i);
				population.remove(i);
			}
		}
		
		for(int i = 0; i < Params.refresh_algae_count; i++) {
			try {
				Critter.makeCritter("Algae");
			} catch (InvalidCritterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static void printRBorder(){
		for(int i = 0; i < Params.world_width; i++){
			System.out.print("-");
		}
	}
	
	private static List<Critter> purgeRow(List<Critter> row){
		List<Integer> removeList = new java.util.ArrayList<Integer>();
		for(int i = 0; i < row.size(); i++){
			Critter crit = row.get(i);
			for(int j = i+1; j < row.size(); j++){
				Critter nCrit = row.get(j);
				if(crit.x_coord == nCrit.x_coord){
					removeList.add(j);
				}
			}
			
			for(int k = 0; k < removeList.size(); k++){
				row.remove(removeList.get(k));
			}
			
		}
		
		return row;
	}
	
	private static List<Critter> findRow(int row){
		List<Critter> rowList = new java.util.ArrayList<Critter>();
		
		for(int i = 0; i < yCoor.size(); i++){
			if(yCoor.get(i) == row){
				rowList.add(population.get(i));
			}
		}
		
		return rowList;
	}
	
	private static Critter nextCritter(List<Critter> row){
		Critter tempCrit = null;
		if(row.size() > 0){
			int tempIndex = 0;
			tempCrit = row.get(tempIndex);
			
			for(int i = 0; i < row.size(); i++){
				if(row.get(i).x_coord < tempCrit.x_coord){
					tempIndex = i;
					tempCrit = row.get(tempIndex);
				}
			}
			
			row.remove(tempIndex);
		}
		return tempCrit;
	}
	
	private static void printRow(int row){
		Critter critter = null;
		List<Critter> crittersRow = new java.util.ArrayList<Critter>();
		int critterX = -1;
		int i = 0;
		
		crittersRow = findRow(row);
		crittersRow = purgeRow(crittersRow);
		critter = nextCritter(crittersRow);
		if(critter != null){
			critterX = critter.x_coord;
		}
		
		System.out.print("|");
		while(i < Params.world_width){
			
			if(i == critterX){
				System.out.print(critter);
				
				critter = nextCritter(crittersRow);
				if(critter != null){
					critterX = critter.x_coord;
				}
				i++;
			}else if(i < Params.world_width){
				System.out.print(" ");
				i++;
			}
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
