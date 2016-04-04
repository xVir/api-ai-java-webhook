package hello;

public class WebhookResponse {
    private final String speech;
    private final String displayText;

    private final String source = "java-webhook";

    public WebhookResponse(String speech, String displayText) {
        this.speech = speech;
        this.displayText = displayText;
    }

    public String getSpeech() {
        return speech;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getSource() {
        return source;
    }
}
