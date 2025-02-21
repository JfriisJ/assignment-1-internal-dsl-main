package main.metamodel;

import java.util.ArrayList;
import java.util.List;

public class Machine {

	private List<State> states;
	private State initialState;
	private List<Transition> transitions;
	private List<Integer> integers = new ArrayList<>(); // Initialize the list


	public Machine(List<State> states, State initialState, List<Transition> transitions) {
		this.states = states;
		this.initialState = initialState;
		this.transitions = transitions;
	}

	public List<State> getStates() {
		// TODO Auto-generated method stub
		return this.states;
	}

	public State getInitialState() {
		// TODO Auto-generated method stub
		return this.initialState;
	}

	public State getState(String string) {
		// TODO Auto-generated method stub
		return states.stream().filter(state -> state.getName().equals(string)).findFirst().orElse(null);
	}

	public int numberOfIntegers() {
		// TODO Auto-generated method stub
		return integers.size();
	}

	public boolean hasInteger(String string) {
		// TODO Auto-generated method stub
		// it should return true if the integer with the given name exists
		return integers.stream().anyMatch(integer -> integer.toString().equals(string));
	}
}

