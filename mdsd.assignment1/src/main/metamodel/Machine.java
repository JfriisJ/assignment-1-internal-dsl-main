package main.metamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Machine {

	private List<State> states;
	private State initialState;
	private Map<String, Integer> variables;

	public Machine(List<State> states, State initialState, Map<String, Integer> variables) {
		this.states = states;
		this.initialState = initialState;
		this.variables = variables;
	}

	public List<State> getStates() {
		// TODO Auto-generated method stub
		return this.states;
	}

	public State getInitialState() {
		// TODO Auto-generated method stub
		return this.initialState;
	}

	public State getState(String name) {
		for (State state : states) {
			if (state.getName().equals(name)) {
				return state;
			}
		}
		return null;
	}
	public int numberOfIntegers() {
		return variables.size();
	}

	public boolean hasInteger(String varName) {
		return variables.containsKey(varName);
	}
}

