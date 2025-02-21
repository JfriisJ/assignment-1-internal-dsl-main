package main;

import main.metamodel.ConditionType;
import main.metamodel.Machine;
import main.metamodel.State;
import main.metamodel.Transition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class StateMachine {

	private State currentState;
	private State initialState;
	private List<State> states = new ArrayList<>();
	private String event;
	private Map<String, Integer> variables = new HashMap<>();

	Logger logger = Logger.getLogger(StateMachine.class.getName());
	public Machine build() {
		return new Machine(new ArrayList<>(states), initialState, variables);
	}


	public void transition(State newState) {
		// Ensure current state is not null before transitioning
		if (currentState == null) {
			System.out.println("Current state is null, cannot transition.");
			return;
		}

		// Perform the transition logic here
		currentState = newState;
		System.out.println("Transitioned to: " + newState);
	}



	public StateMachine state(String name) {
		logger.info("Creating state: " + name);
		this.currentState = new State(name);
		logger.info("State created: " + this.currentState.getName());
		states.add(this.currentState);  // Add the state to the list
		logger.info("State added to list: " + this.currentState.getName());
		return this;
	}


	public StateMachine initial() {
		if (currentState != null) {
			initialState = currentState;
			logger.info("Setting initial state: " + initialState.getName());
		}
		return this;
	}

	public StateMachine when(String event) {
		this.event = event;
		return this;
	}

	public StateMachine to(String targetStateName) {
		State targetState = findStateByName(targetStateName);
		if (targetState == null) {
			System.out.println("Target state not found: " + targetStateName);
			return this; // Return early if the state is not found
		}

		if (currentState != null) {
			transition(targetState);
		} else {
			System.out.println("Current state is null, cannot perform transition.");
		}
		return this;
	}

	private State findStateByName(String targetStateName) {
		for (State state : states) {
			if (state.getName().equals(targetStateName)) {
				return state;
			}
		}
		return null; // Return null if not found
	}


	public StateMachine integer(String varName) {
		// Initialize the variable
		variables.put(varName, 0); // Default value can be 0
		logger.info("Initializing variable " + varName + " to: " + variables.get(varName));
		return this;
	}

	public StateMachine set(String varName, int value) {
		if (variables.containsKey(varName)) {
			variables.put(varName, value);
		}
		logger.info("Setting variable " + varName + " to: " + variables.get(varName));
		return this; // Return 'this' for fluent chaining
	}

	public StateMachine increment(String varName) {
		if (variables.containsKey(varName)) {
			variables.put(varName, variables.get(varName) + 1);
		}
		logger.info("Incrementing variable " + varName + " to: " + variables.get(varName));
		return this; // Return 'this' for fluent chaining
	}
	public StateMachine decrement(String varName) {
		if (variables.containsKey(varName)) {
			variables.put(varName, variables.get(varName) - 1);
		}
		logger.info("Decrementing variable " + varName + " to: " + variables.get(varName));
		return this; // Return 'this' for fluent chaining
	}

	public StateMachine ifEquals(String varName, int value) {
		// Store the condition so we can evaluate it later
		currentState.addCondition(varName, value, ConditionType.EQUALS);
		logger.info("Adding condition: " + varName + "=" + value);
		return this; // Return 'this' for fluent chaining
	}


	public StateMachine ifLessThan(String varName, int value) {
		currentState.addCondition(varName, value, ConditionType.LESS_THAN);
		logger.info("Adding condition: " + varName + "<" + value);
		return this;
	}

	public StateMachine ifGreaterThan(String varName, int value) {
		currentState.addCondition(varName, value, ConditionType.GREATER_THAN);
		logger.info("Adding condition: " + varName + ">" + value);
		return this;
	}


}
