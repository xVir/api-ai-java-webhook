package hello;

import com.google.gson.annotations.SerializedName;

public class ParametersWithAddress {
    @SerializedName("date-time")
    private String dateTime;
    @SerializedName("date-time.original")
    private String dateTimeOriginal;
    private String activity;
    private String address;
    private String temperature;
    private String unit;

    public String getDateTime() {
        return dateTime;
    }

    public String getDateTimeOriginal() {
        return dateTimeOriginal;
    }

    public void setDateTimeOriginal(String dateTimeOriginal) {
        this.dateTimeOriginal = dateTimeOriginal;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
