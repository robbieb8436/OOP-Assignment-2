package org.uob.a2;

import org.uob.a2.engine.Context;
import org.uob.a2.engine.Engine;
import org.uob.a2.engine.SimulationState;
import org.uob.a2.parser.Command;
import org.uob.a2.parser.Parser;
import java.util.Scanner;

public class Main {
	// set booleans and signal for quitting
    private static boolean gameOver = false;
    private static final String QUIT_SIGNAL = "true";

    public static void main(String[] args) {
		// initialise variables
        SimulationState state = new SimulationState();
        Engine engine = new Engine(state);
        Context context = new Context(engine, state); 
        Parser parser = new Parser();
        
        // scanner for inputs
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Space Mining Simulator");
        System.out.println("Welcome. Type 'help' for commands.");
        
        while (!gameOver) {
            System.out.print(">> "); // command prompt
            
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                String result = "";
                
                try {
					// parse and execute command
                    Command cmd = parser.parse(input);
                    result = cmd.execute(context);
                    
                    if (result.equals(QUIT_SIGNAL)) {
                        gameOver = true;
                    }

					// if not over then print the result
                    if (!gameOver) {
                        System.out.println(result);
                    }
                    
                } catch (Exception e) { // catch any errors
                    System.err.println("A critical error occurred: " + e.getMessage());
                }
            } else {
                gameOver = true;
            }
        }
        // close scanner
        scanner.close();
    }
}