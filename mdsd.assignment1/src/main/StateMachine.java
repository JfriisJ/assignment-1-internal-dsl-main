package main;

import main.metamodel.ConditionType;
import main.metamodel.Machine;
import main.metamodel.State;
import main.metamodel.Transition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateMachine {

	private State currentState;
	private State initialState;
	private List<State> states = new ArrayList<>();
	private String event;
	private Map<String, Integer> variables = new HashMap<>();



	public Machine build() {
		// Ensure the machine has at least one state and an initial state
		if (this.initialState == null) {
			throw new IllegalStateException("Initial state is not set");
		}

		// Ensure currentState is set to the initial state if not already set
		if (this.currentState == null) {
			this.currentState = this.initialState;
		}

		// Create a list to hold all states
		List<State> states = new ArrayList<>();
		states.add(this.initialState);

		// Get all transitions from the current state (ensure it's not null)
		List<Transition> transitions = this.currentState != null && this.currentState.getTransitions() != null
				? new ArrayList<>(this.currentState.getTransitions())
				: new ArrayList<>();

		// Create and return the Machine object
		return new Machine(states, this.initialState, transitions);
	}

	public StateMachine initialState(State initialState) {
		this.initialState = initialState;
		return this;
	}


	public StateMachine state(String name) {
		this.currentState = new State(name);
		states.add(this.currentState);  // Add the state to the list
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
