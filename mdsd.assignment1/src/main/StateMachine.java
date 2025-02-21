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

	public StateMachine initialState(State initialState) {
		this.initialState = initialState;
		return this;
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
		if (this.currentState != null) {
			this.initialState = this.currentState;
		}
		return this;
	}

	public StateMachine when(String event) {
		this.event = event;
		return this;
	}

	public StateMachine to(String targetStateName) {
		State targetState = findStateByName(targetStateName);
		Transition transition = new Transition(this.event, targetState);
		this.currentState.addTransition(transition);
		return this;
	}

	private State findStateByName(String targetStateName) {
		for (State state : this.states) {
			if (state.getName().equals(targetStateName)) {
				return state;
			}
		}
		return null; // Return null if not found
	}

	public StateMachine integer(String varName) {
		// Initialize the variable
		variables.put(varName, 0); // Default value can be 0
		return this; // Ensure we return 'this' for fluent chaining
	}

	public StateMachine set(String varName, int value) {
		if (variables.containsKey(varName)) {
			variables.put(varName, value);
		}
		return this; // Again, return 'this' for fluent chaining
	}

	public StateMachine increment(String varName) {
		if (variables.containsKey(varName)) {
			variables.put(varName, variables.get(varName) + 1);
		}
		return this;
	}

	public StateMachine decrement(String varName) {
		if (variables.containsKey(varName)) {
			variables.put(varName, variables.get(varName) - 1);
		}
		return this;
	}

	public StateMachine ifEquals(String varName, int value) {
		// Store the condition so we can evaluate it later
		this.currentState.addCondition(varName, value, ConditionType.EQUALS);
		return this; // Return this to allow chaining
	}

	public StateMachine ifLessThan(String varName, int value) {
		this.currentState.addCondition(varName, value, ConditionType.LESS_THAN);
		return this;
	}

	public StateMachine ifGreaterThan(String varName, int value) {
		this.currentState.addCondition(varName, value, ConditionType.GREATER_THAN);
		return this;
	}


}
