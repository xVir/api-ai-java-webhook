package hello;

import java.util.List;

public class ResultWithAdress {

    private String action;
    private ParametersWithAddress parameters;
    private List<Context> contexts;
    private String intentName;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ParametersWithAddress getParameters() {
        return parameters;
    }

    public void setParameters(ParametersWithAddress parameters) {
        this.parameters = parameters;
    }

    public List<Context> getContexts() {
        return contexts;
    }

    public void setContexts(List<Context> contexts) {
        this.contexts = contexts;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }
}
