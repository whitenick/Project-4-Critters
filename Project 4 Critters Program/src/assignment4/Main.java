/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Javier Cortes
 * jc74593
 * 16445
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */

package assignment4; // cannot be in default package
import java.util.Scanner;
import java.io.*;
import java.util.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        //Code for accepting input on command prompt
        
        DEBUG = true;
        
        while(DEBUG == true) {
        	List<Critter> critterList = new ArrayList<Critter>();
        	System.out.print("critters> ");
        	String input = kb.nextLine();
        	int count = 0;
        	int seed = 0;
        	//DEBUG for critter count
        	String className = null;
        	Scanner inputScan = new Scanner (input);

        	
        	if(inputScan.hasNext()) {
        		if(inputScan.hasNext("quit")) {
        			DEBUG = false;
        		}
        	
        	
        	
        		else if(inputScan.hasNext("show")) {
        			Critter.displayWorld();
        		}
        	
        	
        	
        		else if(inputScan.hasNext("step")) {
        			inputScan.next();
        			if(inputScan.hasNextInt()) {
        				count = inputScan.nextInt();
        				for(int i = 0; i<count; i++) {
        					Critter.worldTimeStep();
        				}
        			}
        			else {
        				Critter.worldTimeStep();
        			}
        		}
        	
        	
        	
        		else if(inputScan.hasNext("seed")) {
        			inputScan.next();
        			if(inputScan.hasNextInt()) {
        				seed = inputScan.nextInt();
        			}
        		}
        	
        	
        	
        		else if(inputScan.hasNext("make")) {
        			inputScan.next();
        			if(inputScan.hasNext()) {
        				className = inputScan.next();
        				if(inputScan.hasNextInt()) {
        					count = inputScan.nextInt();
        				}
        				if (count>0) {
        					for(int i = 0; i<count; i++) {
        						try {
									Critter.makeCritter(className);
								} catch (InvalidCritterException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
        					}
        				}
        				else {
        					try {
								Critter.makeCritter(className);
							} catch (InvalidCritterException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
        				}
        			
        			}
        			
        		}
        	
        	
        		else if(inputScan.hasNext("stats")) {
        			inputScan.next();
        			if(inputScan.hasNext()) {
        				className = inputScan.next();
        				try {
							critterList = Critter.getInstances(className);
							//Debug for critterList
//							for(int i = 0; i<critterList.size(); i++) {
//								System.out.println(critterList.get(i).toString());
//							}
						} catch (InvalidCritterException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        			}
        		}
        		
        		else System.out.println("Invalid input: Try Again");
        	
        	}
        	
        	else System.out.println("Invalid input: Try Again");
        	
       
        }
        
        System.out.println("GLHF");
        
        /* Write your code above */
        System.out.flush();

    }
}
