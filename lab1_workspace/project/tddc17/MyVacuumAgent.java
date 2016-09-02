package tddc17;


import aima.core.environment.liuvacuum.*;
import aima.core.agent.Action;
import aima.core.agent.AgentProgram;
import aima.core.agent.Percept;
import aima.core.agent.impl.*;

import java.util.Random;

class MyAgentState
{
	public int[][] world = new int[30][30];
	public int initialized = 0;
	final int UNKNOWN 	= 0;
	final int WALL 		= 1;
	final int CLEAR 	= 2;
	final int DIRT		= 3;
	final int HOME		= 4;
	final int ACTION_NONE 			= 0;
	final int ACTION_MOVE_FORWARD 	= 1;
	final int ACTION_TURN_RIGHT 	= 2;
	final int ACTION_TURN_LEFT 		= 3;
	final int ACTION_SUCK	 		= 4;
	
	public boolean findHome = true;
	
	public int agent_x_position = 1;
	public int agent_y_position = 1;
	public int agent_last_action = ACTION_NONE;
	public int agent_next_action = ACTION_NONE;
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	public int agent_direction = EAST;
	
	
	MyAgentState()
	{
		for (int i=0; i < world.length; i++)
			for (int j=0; j < world[i].length ; j++)
				world[i][j] = UNKNOWN;
		world[1][1] = HOME;
		agent_last_action = ACTION_NONE;
	}
	// Based on the last action and the received percept updates the x & y agent position
	public void updatePosition(DynamicPercept p)
	{
		Boolean bump = (Boolean)p.getAttribute("bump");

		if (agent_last_action==ACTION_MOVE_FORWARD && !bump)
	    {
			switch (agent_direction) {
			case MyAgentState.NORTH:
				agent_y_position--;
				break;
			case MyAgentState.EAST:
				agent_x_position++;
				break;
			case MyAgentState.SOUTH:
				agent_y_position++;
				break;
			case MyAgentState.WEST:
				agent_x_position--;
				break;
			}
	    }
		
	}
	
	public void updateWorld(int x_position, int y_position, int info)
	{
		System.out.println("Update world got x: " + x_position + "And y: " + y_position + "Info: " + info);
		world[x_position][y_position] = info;
	}
	
	public void printWorldDebug()
	{
		for (int i=0; i < world.length; i++)
		{
			for (int j=0; j < world[i].length ; j++)
			{
				if (world[j][i]==UNKNOWN)
					System.out.print(" ? ");
				if (world[j][i]==WALL)
					System.out.print(" # ");
				if (world[j][i]==CLEAR)
					System.out.print(" . ");
				if (world[j][i]==DIRT)
					System.out.print(" D ");
				if (world[j][i]==HOME)
					System.out.print(" H ");
			}
			System.out.println("");
		}
	}
}

class MyAgentProgram implements AgentProgram {
	
	private Action rotateRight() {
		state.agent_last_action = state.ACTION_TURN_RIGHT;
		state.agent_direction = ((state.agent_direction+1) % 4);
		return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
	}
	
	private Action rotateLeft() {
		state.agent_last_action = state.ACTION_TURN_LEFT;
	    state.agent_direction = ((state.agent_direction-1) % 4);
	    if (state.agent_direction<0) 
	    	state.agent_direction +=4;
		return LIUVacuumEnvironment.ACTION_TURN_LEFT;
	}
	
	private Action rotateEast(){
		if (state.agent_direction == 3 || state.agent_direction == 0) {
			return rotateRight();
		}
		else if (state.agent_direction == 2){
			return rotateLeft();
		}
		System.out.println("Dir is east: " + state.agent_direction);
		state.agent_last_action = state.ACTION_MOVE_FORWARD;
		return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
	}
	
	private Action findHome(){
		if (state.agent_y_position != 1) {
    		if (state.agent_direction == 0) {
    			state.agent_last_action=state.ACTION_MOVE_FORWARD;
	    		return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
    		}
    		else if (state.agent_direction == 1){
    			return rotateLeft();
    		}
    		else {
    			return rotateRight();
    		}
    	}
    	else if (state.agent_x_position != 1) {
    		if (state.agent_direction == 3) {
    			state.agent_last_action=state.ACTION_MOVE_FORWARD;
	    		return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
    		}
    		else if (state.agent_direction == 0){
    			return rotateLeft();
    		}
    		else {
    			return rotateRight();
    		}
    	}
		System.out.println("I should really not be here");
		state.agent_last_action = state.ACTION_MOVE_FORWARD;
		return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
	}
	
	private boolean inCorner(){
		if(state.world[state.agent_x_position+1][state.agent_y_position] == state.WALL && state.world[state.agent_x_position][state.agent_y_position+1] == state.WALL) {
			System.out.println("IN THE BOTTOM RIGHT CORNER!");
			return true;
		}
		return false;
	}

	private int initnialRandomActions = 10;
	private Random random_generator = new Random();
	
	// Here you can define your variables!
	public int iterationCounter = 100; // Changed this boundary! Was 10 before.
	public MyAgentState state = new MyAgentState();
	
	// moves the Agent to a random start position
	// uses percepts to update the Agent position - only the position, other percepts are ignored
	// returns a random action
	private Action moveToRandomStartPosition(DynamicPercept percept) {
		int action = random_generator.nextInt(6);
		initnialRandomActions--;
		state.updatePosition(percept);
		if(action==0) {
		    return rotateLeft();
		} else if (action==1) {
			return rotateRight();
		} 
		state.agent_last_action=state.ACTION_MOVE_FORWARD;
		return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
	}
	
	
	@Override
	public Action execute(Percept percept) {
		
		// DO NOT REMOVE this if condition!!!
    	if (initnialRandomActions>0) {
    		return moveToRandomStartPosition((DynamicPercept) percept);
    	} else if (initnialRandomActions==0) {
    		// process percept for the last step of the initial random actions
    		initnialRandomActions--;
    		state.updatePosition((DynamicPercept) percept);
			System.out.println("Processing percepts after the last execution of moveToRandomStartPosition()");
			state.agent_last_action=state.ACTION_SUCK;
	    	return LIUVacuumEnvironment.ACTION_SUCK;
    	}
		
    	// This example agent program will update the internal agent state while only moving forward.
    	// START HERE - code below should be modified!
    	    	
    	System.out.println("x=" + state.agent_x_position);
    	System.out.println("y=" + state.agent_y_position);
    	System.out.println("dir=" + state.agent_direction);
    	
		
	    iterationCounter--;
	    
	    if (iterationCounter==0) {
	    	System.out.println("Heeeej!");
	    	return NoOpAction.NO_OP;
	    }
	    DynamicPercept p = (DynamicPercept) percept;
	    Boolean bump = (Boolean)p.getAttribute("bump");
	    Boolean dirt = (Boolean)p.getAttribute("dirt");
	    Boolean home = (Boolean)p.getAttribute("home");
	    System.out.println("percept: " + p);
	    
	    // State update based on the percept value and the last action
	    state.updatePosition((DynamicPercept)percept);
	    if (bump) {
			switch (state.agent_direction) {
			case MyAgentState.NORTH:
				state.updateWorld(state.agent_x_position,state.agent_y_position-1,state.WALL);
				break;
			case MyAgentState.EAST:
				state.updateWorld(state.agent_x_position+1,state.agent_y_position,state.WALL);
				break;
			case MyAgentState.SOUTH:
				state.updateWorld(state.agent_x_position,state.agent_y_position+1,state.WALL);
				break;
			case MyAgentState.WEST:
				state.updateWorld(state.agent_x_position-1,state.agent_y_position,state.WALL);
				break;
			}
	    }
	    if (dirt)
	    	state.updateWorld(state.agent_x_position,state.agent_y_position,state.DIRT);
	    else
	    	state.updateWorld(state.agent_x_position,state.agent_y_position,state.CLEAR);
	    
	    state.printWorldDebug();
	    
	    // Next action selection based on the percept value
	    if (dirt)
	    {
	    	// if dirt -> Suck
	    	System.out.println("DIRT -> choosing SUCK action!");
	    	state.agent_last_action=state.ACTION_SUCK;
	    	return LIUVacuumEnvironment.ACTION_SUCK;
	    }
	    
	    if  (state.findHome == true) {
	    	if(home){ // and map is clear
	    		System.out.println("Home!");
	    		state.findHome = false;
	    		return rotateEast();
	    	} else {
	    		return findHome();
	    	}
	    	
	    } 
	    else if(!inCorner()){
	    	//return suckDirt();
	    }
	    else {
	    	System.out.println("GOING HOME AGAIN");
	    	state.findHome = true; // go home
	    	return findHome();
	    }

	    // Check if we have any pending actions
	    if(state.agent_next_action != state.ACTION_NONE){
    		if(state.agent_next_action == state.ACTION_MOVE_FORWARD) {
    			state.agent_next_action = state.ACTION_NONE;
    			state.agent_last_action = state.ACTION_MOVE_FORWARD;
    			return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
    		}
    		else if(state.agent_next_action == state.ACTION_TURN_RIGHT) {
    			state.agent_next_action = state.ACTION_NONE;
    			return rotateRight();
    		}
    			
    		else if(state.agent_next_action == state.ACTION_TURN_LEFT) {
    			state.agent_next_action = state.ACTION_NONE;
    			return rotateLeft();
    		}
    	}
	    
	    // If bump turn 90 degrees
    	if (bump)
    	{
    		state.agent_next_action = state.ACTION_MOVE_FORWARD;
    		if (state.agent_direction == 1) {
    			return rotateRight();
    		}
    		if (state.agent_direction == 3) {
    			return rotateLeft();
    		}
    	}
    	
//    	if(state.agent_last_action == state.ACTION_TURN_LEFT){
//    		return LIUVacuumEnvironment.ACTION_TURN_LEFT;
//    	} else if(state.agent_last_action == state.ACTION_TURN_RIGHT){
//    		return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
//    	}
    	return rotateEast();
	}
	
}


public class MyVacuumAgent extends AbstractAgent {
    public MyVacuumAgent() {
    	super(new MyAgentProgram());
	}
}
