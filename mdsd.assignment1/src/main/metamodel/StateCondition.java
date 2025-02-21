package main.metamodel;

public class StateCondition {
    private String variableName;
    private int value;
    private ConditionType type;

    public StateCondition(String variableName, int value, ConditionType type) {
        this.variableName = variableName;
        this.value = value;
        this.type = type;
    }

    public String getVariableName() {
        return variableName;
    }

    public int getValue() {
        return value;
    }

    public ConditionType getType() {
        return type;
    }
}
