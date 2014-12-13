package laxstats.web.site;


import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.Surface;
import laxstats.api.Region;

public class SiteForm {

    private String name;
    private SiteStyle style;
    private Surface surface;
    private String directions;
    private String address1;
    private String address2;
    private String city;
    private Region region;
    private String postalCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SiteStyle getStyle() {
        return style;
    }

    public void setStyle(SiteStyle style) {
        this.style = style;
    }

    public Surface getSurface() {
        return surface;
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
