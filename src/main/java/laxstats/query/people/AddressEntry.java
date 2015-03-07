package laxstats.query.people;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.api.Region;
import laxstats.api.people.AddressType;
import laxstats.api.people.Contactable;
import laxstats.query.sites.SiteEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "addresses", indexes = {
		@Index(name = "address_idx1", columnList = "city"),
		@Index(name = "address_idx2", columnList = "isPrimary"),
		@Index(name = "address_idx3", columnList = "doNotUse"),
		@Index(name = "address_idx4", columnList = "addressType") }, uniqueConstraints = { @UniqueConstraint(name = "address_uk1", columnNames = {
		"person_id", "isPrimary" }) })
public class AddressEntry implements Contactable, Serializable {
	private static final long serialVersionUID = 2873674846553569444L;

	@Id
	@Column(length = 36)
	private String id;

	@ManyToOne
	@JoinColumn(name = "person_id")
	private PersonEntry person;

	@OneToOne
	@JoinColumn(name = "site_id")
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

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
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

	/*---------- Methods ----------*/

	public String fullAddress() {
		final StringBuilder sb = new StringBuilder();
		boolean addConcat = false;
		if (address1 != null) {
			sb.append(address1);
			addConcat = true;
		}
		if (address2 != null) {
			if (addConcat) {
				sb.append(", ");
			}
			sb.append(address2);
			addConcat = true;
		}
		if (addConcat) {
			sb.append(" ");
		}
		sb.append(city);
		if (region != null) {
			sb.append(", ").append(region.getAbbreviation());
		}
		if (postalCode != null) {
			sb.append(" ").append(postalCode);
		}
		return sb.toString();
	}

	public String toHTML() {
		final StringBuilder sb = new StringBuilder();
		boolean addBreak = false;
		if (address1 != null) {
			sb.append(address1);
			addBreak = true;
		}
		if (address2 != null) {
			if (addBreak) {
				sb.append("<br>");
			}
			sb.append(address2);
			addBreak = true;
		}
		if (addBreak) {
			sb.append("<br>");
		}
		sb.append(city);
		if (region != null) {
			sb.append(", ").append(region.getAbbreviation());
		}
		if (postalCode != null) {
			sb.append(" ").append(postalCode);
		}
		return sb.toString();
	}

	/*---------- Getter/Setters ----------*/

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

	@Override
	public boolean isDoNotUse() {
		return doNotUse;
	}

	@Override
	public void setDoNotUse(boolean doNotUse) {
		this.doNotUse = doNotUse;
	}

	@Override
	public boolean isPrimary() {
		return isPrimary;
	}

	@Override
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
}
