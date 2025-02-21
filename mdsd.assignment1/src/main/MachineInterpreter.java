package main;

import main.metamodel.Machine;
import main.metamodel.State;
import main.metamodel.Transition;

import java.util.ArrayList;
import java.util.List;

public class MachineInterpreter {

    private State currentState;
    private Machine machine;
    private List<String> eventHistory;


    public MachineInterpreter() {
        this.currentState = null;
        this.machine = null;
        this.eventHistory = new ArrayList<>(); // Initialize the event history list
    }

    public void run(Machine m) {
        this.currentState = machine.getInitialState();
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

        // Store the event in the event history
        eventHistory.add(event);

        System.out.println("Processing Event: " + event); // Debugging line
        Transition transition = this.currentState.getTransitionByEvent(event);
        if (transition != null) {
            this.currentState = transition.getTarget();
            System.out.println("New State: " + this.currentState.getName()); // Debugging line
        }
    }

    public List<String> getEventHistory() {
        // Return the list of events processed
        return eventHistory;
    }


    public int getInteger(String name) {
        // Retrieve an integer associated with a state (you can customize how you store integers in states)
        return 0;
    }
}

