package laxstats.api.people;

import laxstats.api.Region;
import laxstats.query.people.PersonEntry;
import laxstats.query.sites.SiteEntry;
import laxstats.query.users.UserEntry;
import org.joda.time.LocalDateTime;

public class AddressDTO {
    private String id;
    private PersonEntry person;
    private SiteEntry site;
    private AddressType addressType;
    private String address1;
    private String address2;
    private String city;
    private Region region;
    private String postalCode;
    private boolean isPrimary;
    private boolean doNotUse;
    private UserEntry createdBy;
    private LocalDateTime createdAt;
    private UserEntry modifiedBy;
    private LocalDateTime modifiedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PersonEntry getPerson() {
        return person;
    }

    public void setPerson(PersonEntry person) {
        this.person = person;
    }

    public SiteEntry getSite() {
        return site;
    }

    public void setSite(SiteEntry site) {
        this.site = site;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
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

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public boolean isDoNotUse() {
        return doNotUse;
    }

    public void setDoNotUse(boolean doNotUse) {
        this.doNotUse = doNotUse;
    }

    public UserEntry getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntry createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserEntry getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(UserEntry modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
