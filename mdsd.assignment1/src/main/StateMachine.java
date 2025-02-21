// language: java
package main;

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
		if (currentState == null) {
			System.out.println("Current state is null, cannot transition.");
			return;
		}
		currentState = newState;
		System.out.println("Transitioned to: " + newState.getName());
	}

	public StateMachine state(String name) {
		State existing = findStateByName(name);
		if (existing != null) {
			currentState = existing;
			logger.info("State exists: " + currentState.getName());
		} else {
			logger.info("Creating state: " + name);
			currentState = new State(name);
			logger.info("State created: " + currentState.getName());
			states.add(currentState);
			logger.info("State added to list: " + currentState.getName());
		}
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
		// If target state doesn't exist, create a placeholder.
		State targetState = findStateByName(targetStateName);
		if (targetState == null) {
			logger.info("Target state not found: " + targetStateName + ". Creating placeholder.");
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
		logger.info("Initializing variable " + varName + " to: " + variables.get(varName));
		return this;
	}

	public StateMachine set(String varName, int value) {
		if (variables.containsKey(varName)) {
			variables.put(varName, value);
		}
		// If setting on a transition, delegate to the last transition
		if (currentState != null && !currentState.getTransitions().isEmpty()) {
			Transition last = currentState.getTransitions().get(currentState.getTransitions().size() - 1);
			last.setSetOperation(varName, value);
		}
		logger.info("Setting variable " + varName + " to: " + variables.get(varName));
		return this;
	}

	public StateMachine increment(String varName) {
		if (variables.containsKey(varName)) {
			variables.put(varName, variables.get(varName) + 1);
		}
		if (currentState != null && !currentState.getTransitions().isEmpty()) {
			Transition last = currentState.getTransitions().get(currentState.getTransitions().size() - 1);
			last.setIncrementOperation(varName);
		}
		logger.info("Incrementing variable " + varName + " to: " + variables.get(varName));
		return this;
	}

	public StateMachine decrement(String varName) {
		if (variables.containsKey(varName)) {
			variables.put(varName, variables.get(varName) - 1);
		}
		if (currentState != null && !currentState.getTransitions().isEmpty()) {
			Transition last = currentState.getTransitions().get(currentState.getTransitions().size() - 1);
			last.setDecrementOperation(varName);
		}
		logger.info("Decrementing variable " + varName + " to: " + variables.get(varName));
		return this;
	}

	public StateMachine ifEquals(String varName, int value) {
		Transition last = currentState.getTransitions().get(currentState.getTransitions().size() - 1);
		last.setConditionEquals(varName, value);
		logger.info("Adding condition: " + varName + "=" + value);
		return this;
	}

	public StateMachine ifLessThan(String varName, int value) {
		Transition last = currentState.getTransitions().get(currentState.getTransitions().size() - 1);
		last.setConditionLessThan(varName, value);
		logger.info("Adding condition: " + varName + "<" + value);
		return this;
	}

	public StateMachine ifGreaterThan(String varName, int value) {
		Transition last = currentState.getTransitions().get(currentState.getTransitions().size() - 1);
		last.setConditionGreaterThan(varName, value);
		logger.info("Adding condition: " + varName + ">" + value);
		return this;
	}
}