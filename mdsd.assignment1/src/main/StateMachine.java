// language: java
package main;

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
	private final List<State> states = new ArrayList<>();
	private String event;
	private final Map<String, Integer> variables = new HashMap<>();

	public Machine build() {
		return new Machine(new ArrayList<>(states), initialState, variables);
	}

	public StateMachine state(String name) {
		State existing = findStateByName(name);
		if (existing != null) {
			currentState = existing;
		} else {
			currentState = new State(name);
			states.add(currentState);
		}
		return this;
	}

	public StateMachine initial() {
		if (currentState != null) {
			initialState = currentState;
		}
		return this;
	}

	public StateMachine when(String event) {
		this.event = event;
		return this;
	}

	public StateMachine to(String targetStateName) {
		// If target state doesn't exist, create a placeholder.
		State targetState = findStateByName(targetStateName);
		if (targetState == null) {
			targetState = new State(targetStateName);
			states.add(targetState);
		}
		if (currentState != null) {
			Transition transition = new Transition(this.event, targetState);
			currentState.addTransition(transition);
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
		return null;
	}

	public StateMachine integer(String varName) {
		variables.put(varName, 0);
		return this;
	}

	public StateMachine set(String varName, int value) {
		if (variables.containsKey(varName)) {
			variables.put(varName, value);
		}
		// If setting on a transition, delegate to the last transition
		if (currentState != null && !currentState.getTransitions().isEmpty()) {
			Transition last = currentState.getTransitions().getLast();
			last.setSetOperation(varName, value);
		}
		return this;
	}

	public StateMachine increment(String varName) {
		if (variables.containsKey(varName)) {
			variables.put(varName, variables.get(varName) + 1);
		}
		if (currentState != null && !currentState.getTransitions().isEmpty()) {
			Transition last = currentState.getTransitions().getLast();
			last.setIncrementOperation(varName);
		}
		return this;
	}

	public StateMachine decrement(String varName) {
		if (variables.containsKey(varName)) {
			variables.put(varName, variables.get(varName) - 1);
		}
		if (currentState != null && !currentState.getTransitions().isEmpty()) {
			Transition last = currentState.getTransitions().getLast();
			last.setDecrementOperation(varName);
		}
		return this;
	}

	public StateMachine ifEquals(String varName, int value) {
		Transition last = currentState.getTransitions().getLast();
		last.setConditionEquals(varName, value);
		return this;
	}

	public StateMachine ifLessThan(String varName, int value) {
		Transition last = currentState.getTransitions().getLast();
		last.setConditionLessThan(varName, value);
		return this;
	}

	public StateMachine ifGreaterThan(String varName, int value) {
		Transition last = currentState.getTransitions().getLast();
		last.setConditionGreaterThan(varName, value);
		return this;
	}
}