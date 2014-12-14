package laxstats.query.people;

import laxstats.api.Region;
import laxstats.api.people.AddressType;
import laxstats.query.sites.SiteEntry;
import laxstats.query.users.UserEntry;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(indexes = {@Index(name = "address_idx1", columnList = "city"),
    @Index(name = "address_idx2", columnList = "isPrimary"),
    @Index(name = "address_idx3", columnList = "doNotUse"),
    @Index(name = "address_idx4", columnList = "addressType")})
public class AddressEntry {

    @Id
    @Column(length = 36)
    private String id;

    @ManyToOne
    private PersonEntry person;

    @ManyToOne
    private SiteEntry site;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private AddressType addressType;

    @Column(length = 34)
    private String address1;

    @Column(length = 34)
    private String address2;

    @Column(length = 30, nullable = false)
    private String city;

    @ManyToOne
    private Region region;

    @Column(length = 10)
    private String postalCode;

    private boolean isPrimary = false;

    private boolean doNotUse = false;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime createdAt;

    @ManyToOne
    private UserEntry createdBy;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime modifiedAt;

    @ManyToOne
    private UserEntry modifiedBy;

    // ---------- Getter/Setters ----------//

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserEntry getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntry createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public UserEntry getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(UserEntry modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public PersonEntry getPerson() {
        return person;
    }

    public void setPerson(PersonEntry person) {
        this.person = person;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public SiteEntry getSite() {
        return site;
    }

    public void setSite(SiteEntry site) {
        this.site = site;
    }

    public boolean isDoNotUse() {
        return doNotUse;
    }

    public void setDoNotUse(boolean doNotUse) {
        this.doNotUse = doNotUse;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    // ---------- Other methods ----------//

    public String toHTML() {
        String result = "";
        boolean addBreak = false;
        if (address1 != null) {
            result += address1;
            addBreak = true;
        }
        if (address2 != null) {
            if (addBreak) {
                result += "<br>";
            }
            result += address2;
            addBreak = true;
        }
        if (city != null) {
            if (addBreak) {
                result += "<br>";
            }
            result += city;
        }
        if (region != null) {
            result += ", " + region.getAbbreviation();
        }
        if (postalCode != null) {
            result += " " + postalCode;
        }
        return result;
    }
}
