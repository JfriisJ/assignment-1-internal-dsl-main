package main.metamodel;

public class Transition {

	private String event;
	private State target;
	private boolean setOperation = false;
	private boolean incrementOperation = false;
	private boolean decrementOperation = false;
	private String operationVariableName;
	private int setValue;
	private boolean conditional = false;
	private String conditionVariableName;
	private Integer conditionComparedValue;
	private boolean conditionEqual = false;
	private boolean conditionGreaterThan = false;
	private boolean conditionLessThan = false;

	public Transition(String event, State targetState) {
		this.event = event;
		this.target = targetState;
	}

	public String getEvent() {
		return this.event;
	}

	public State getTarget() {
		return this.target;
	}

	public boolean hasSetOperation() {
		return this.setOperation;
	}

	public boolean hasIncrementOperation() {
		return this.incrementOperation;
	}

	public boolean hasDecrementOperation() {
		return this.decrementOperation;
	}

	public String getOperationVariableName() {
		return this.operationVariableName;
	}

	public int getSetValue() {
		return this.setValue;
	}

	public boolean isConditional() {
		return this.conditional;
	}

	public String getConditionVariableName() {
		return this.conditionVariableName;
	}

	public Integer getConditionComparedValue() {
		return this.conditionComparedValue;
	}

	public boolean isConditionEqual() {
		return this.conditionEqual;
	}

	public boolean isConditionGreaterThan() {
		return this.conditionGreaterThan;
	}

	public boolean isConditionLessThan() {
		return this.conditionLessThan;
	}

	public boolean hasOperation() {
		return this.setOperation || this.incrementOperation || this.decrementOperation;
	}

	// Helper method to mark this transition as a set operation and store the value.
	public void setSetOperation(String variableName, int value) {
		this.operationVariableName = variableName;
		this.setValue = value;
		this.setOperation = true;
	}

	// Added helper method to mark increment operation.
	public void setIncrementOperation(String variableName) {
		this.operationVariableName = variableName;
		this.incrementOperation = true;
	}

	// Added helper method to mark decrement operation.
	public void setDecrementOperation(String variableName) {
		this.operationVariableName = variableName;
		this.decrementOperation = true;
	}

	// Added helper method to define condition equal.
	public void setConditionEquals(String variable, int value) {
		this.conditionVariableName = variable;
		this.conditionComparedValue = value;
		this.conditional = true;
		this.conditionEqual = true;
		this.conditionGreaterThan = false;
		this.conditionLessThan = false;
	}

	// Added helper method to define condition less than.
	public void setConditionLessThan(String variable, int value) {
		this.conditionVariableName = variable;
		this.conditionComparedValue = value;
		this.conditional = true;
		this.conditionEqual = false;
		this.conditionGreaterThan = false;
		this.conditionLessThan = true;
	}

	// Added helper method to define condition greater than.
	public void setConditionGreaterThan(String variable, int value) {
		this.conditionVariableName = variable;
		this.conditionComparedValue = value;
		this.conditional = true;
		this.conditionEqual = false;
		this.conditionGreaterThan = true;
		this.conditionLessThan = false;
	}
}