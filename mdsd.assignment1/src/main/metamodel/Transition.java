package main.metamodel;

public class Transition{

	private String event;
	private State target;
	private boolean setOperation = false; // Initialize as not operation
	private boolean incrementOperation = false;
	private boolean decrementOperation = false;
	private Object operationVariableName;
	private boolean conditional = false; // Initialize as not conditional
	private Object conditionVariableName;
	private Integer conditionComparedValue;
	private boolean conditionEqual = false;
	private boolean conditionGreaterThan = false;
	private boolean conditionLessThan = false;

	public Transition(String event, State targetState) {
		this.event = event;
		this.target = targetState;
	}


	public Object getEvent() {
		// TODO Auto-generated method stub
		return this.event;
	}

	public State getTarget() {
		// TODO Auto-generated method stub
		return this.target;
	}

	public boolean hasSetOperation() {
		// TODO Auto-generated method stub
		return this.setOperation;
	}

	public boolean hasIncrementOperation() {
		// TODO Auto-generated method stub
		return this.incrementOperation;
	}

	public boolean hasDecrementOperation() {
		// TODO Auto-generated method stub
		return this.decrementOperation;
	}

	public Object getOperationVariableName() {
		// TODO Auto-generated method stub
		return this.operationVariableName;
	}

	public boolean isConditional() {
		// TODO Auto-generated method stub
		return this.conditional;
	}

	public Object getConditionVariableName() {
		// TODO Auto-generated method stub
		return this.conditionVariableName;
	}

	public Integer getConditionComparedValue() {
		// TODO Auto-generated method stub
		return this.conditionComparedValue;
	}

	public boolean isConditionEqual() {
		// TODO Auto-generated method stub
		return this.conditionEqual;
	}

	public boolean isConditionGreaterThan() {
		// TODO Auto-generated method stub
		return this.conditionGreaterThan;
	}

	public boolean isConditionLessThan() {
		// TODO Auto-generated method stub
		return this.conditionLessThan;
	}

	public boolean hasOperation() {
		// TODO Auto-generated method stub
		return this.setOperation;
	}

	public void setConditional(String conditionVariable, int conditionComparedValue) {
		this.conditionVariableName = conditionVariable;
		this.conditionComparedValue = conditionComparedValue;
		this.conditional = true; // Set as conditional
		this.conditionEqual = (this.conditionComparedValue == 0);
		this.conditionGreaterThan = (this.conditionComparedValue > 0);
		this.conditionLessThan = (this.conditionComparedValue < 0);
	}

}
