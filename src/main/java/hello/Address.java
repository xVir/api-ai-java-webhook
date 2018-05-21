package hello;

import com.google.gson.annotations.SerializedName;

import javax.xml.bind.annotation.XmlElement;

public class Address {

    private String city;//city
    @SerializedName( "business-name")
    private String businessName;//business-name
    @SerializedName("admin-area")
    private String adminArea;//admin-area
    @SerializedName( "subadmin-area")
    private String subadminArea;//subadmin-area

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAdminArea() {
        return adminArea;
    }

    public void setAdminArea(String adminArea) {
        this.adminArea = adminArea;
    }

    public String getSubadminArea() {
        return subadminArea;
    }

    public void setSubadminArea(String subadminArea) {
        this.subadminArea = subadminArea;
    }
}
