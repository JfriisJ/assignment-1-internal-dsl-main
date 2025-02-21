package main.metamodel;

import java.util.ArrayList;
import java.util.List;

public class State {

	private String name;
	private List<Transition> transitions;
	private List<StateCondition> conditions;

	public State(String name) {
		this.name = name;
		this.transitions = new ArrayList<>(); // Initialize the list
		this.conditions = new ArrayList<>();

	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public List<Transition> getTransitions() {
		return this.transitions; // Return the list of transitions
	}

	public Transition getTransitionByEvent(String event) {
		// Return the first transition that matches the event name, or null if not found
		return transitions.stream()
				.filter(transition -> transition.getEvent().equals(event))
				.findFirst()
				.orElse(null);
	}

	public void addTransition(Transition transition) {
		this.transitions.add(transition); // Add a new transition to the list
	}

	public void addCondition(String varName, int value, ConditionType type) {
		this.conditions.add(new StateCondition(varName, value, type));
	}

	public List<StateCondition> getConditions() {
		return this.conditions;
	}
}
