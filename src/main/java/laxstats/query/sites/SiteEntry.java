package laxstats.query.sites;

import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.Surface;
import laxstats.query.people.AddressEntry;
import laxstats.query.users.UserEntry;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(indexes = {
    @Index(name = "site_idx1", columnList = "style"),
    @Index(name = "site_idx2", columnList = "surface")
})
public class SiteEntry {

    @Id
    @Column(length = 36)
    private String id;

    @Column(length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SiteStyle style;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Surface surface;

    @OneToOne(mappedBy = "site")
    private AddressEntry address;

    @Column(columnDefinition = "text")
    private String directions;

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

    public AddressEntry getAddress() {
        return address;
    }

    public void setAddress(AddressEntry address) {
        this.address = address;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
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
}
