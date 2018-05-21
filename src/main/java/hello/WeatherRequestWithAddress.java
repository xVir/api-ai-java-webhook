package hello;

public class WeatherRequestWithAddress {
    private String id;
    private String lang;
    private String sessionId;
    private ResultWithAdress result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ResultWithAdress getResult() {
        return result;
    }

    public void setResult(ResultWithAdress result) {
        this.result = result;
    }
}
