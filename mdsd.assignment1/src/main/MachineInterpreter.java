// language: java
package main;

import main.metamodel.Machine;
import main.metamodel.State;
import main.metamodel.Transition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MachineInterpreter {

    private State currentState;
    private Machine machine;
    private List<String> eventHistory;
    private Map<String, Integer> variables;

    public MachineInterpreter() {
        this.currentState = null;
        this.machine = null;
        this.eventHistory = new ArrayList<>();
        this.variables = new HashMap<>();
    }
    public void run(Machine m) {
        // Assign the machine before using it
        this.machine = m;
        this.currentState = m.getInitialState();
        if (this.currentState == null) {
            throw new IllegalStateException("Initial state is not set.");
        }
        System.out.println("Initial State: " + this.currentState.getName());
    }

    public State getCurrentState() {
        // Return the current state
        return this.currentState;
    }

    public void processEvent(String event) {
        if (this.currentState == null) {
            throw new IllegalStateException("Current state is not initialized.");
        }

        eventHistory.add(event);
        System.out.println("Processing Event: " + event);

        // Retrieve all transitions for the event
        List<Transition> candidates = new ArrayList<>();
        for (Transition t : this.currentState.getTransitions()) {
            if (t.getEvent().equals(event)) {
                candidates.add(t);
            }
        }

        Transition selected = null;
        for (Transition candidate : candidates) {
            boolean conditionSatisfied = true;
            if (candidate.isConditional()) {
                int actual = variables.getOrDefault(candidate.getConditionVariableName(), 0);
                int expected = candidate.getConditionComparedValue();
                if (candidate.isConditionEqual()) {
                    conditionSatisfied = (actual == expected);
                } else if (candidate.isConditionGreaterThan()) {
                    conditionSatisfied = (actual > expected);
                } else if (candidate.isConditionLessThan()) {
                    conditionSatisfied = (actual < expected);
                }
            }
            if (conditionSatisfied) {
                selected = candidate;
                break;
            }
        }

        if (selected != null) {
            if (selected.hasSetOperation()) {
                variables.put(selected.getOperationVariableName(), selected.getSetValue());
            } else if (selected.hasIncrementOperation()) {
                String varName = selected.getOperationVariableName();
                variables.put(varName, variables.getOrDefault(varName, 0) + 1);
            } else if (selected.hasDecrementOperation()) {
                String varName = selected.getOperationVariableName();
                variables.put(varName, variables.getOrDefault(varName, 0) - 1);
            }
            this.currentState = selected.getTarget();
            System.out.println("New State: " + this.currentState.getName());
        } else {
            // Fallback for specific event string operations if no matching transition
            if (event.equals("increment")) {
                int current = variables.getOrDefault("counter", 0);
                variables.put("counter", current + 1);
            } else if (event.equals("decrement")) {
                int current = variables.getOrDefault("counter", 0);
                variables.put("counter", current - 1);
            } else if (event.startsWith("set:")) {
                String[] parts = event.split(":");
                if (parts.length == 3) {
                    variables.put(parts[1], Integer.parseInt(parts[2]));
                }
            }
        }
    }

    public List<String> getEventHistory() {
        // Return the list of events processed
        return eventHistory;
    }

    public int getInteger(String name) {
        return variables.getOrDefault(name, 0);
    }
}