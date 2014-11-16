package laxstats.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(
	indexes = {
		@Index(name = "address_idx1", columnList = "city"), 
		@Index(name = "address_idx2", columnList = "isPrimary"), 
		@Index(name = "address_idx3", columnList = "doNotUse")
	}
)
public class Address {

	@Id
	@Column(length = 36)
	private String id;
	
	@ManyToOne
	private Person person;
	
	@ManyToOne(targetEntity = Site.class)
	private String siteId;
	
	@Column(length = 34)
	private String address1;
	
	@Column(length = 34)
	private String address2;
	
	@Column(length = 30)
	private String city;
	
	@ManyToOne
	private Region region;
	
	@Column(length = 10)
	private String postalCode;
	
	private boolean isPrimary = false;
	
	private boolean doNotUse = false;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne(targetEntity = User.class)
	private String createdBy;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;
	
	@ManyToOne(targetEntity = User.class)
	private String modifiedBy;
	
	//---------- Constructors ----------//
	
	protected Address(){}
	
	public Address(String id, Person person, String siteId, String address1,
			String address2, String city, Region region, String postalCode,
			boolean isPrimary, boolean doNotUse, LocalDateTime createdAt,
			String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
		this.id = id;
		this.person = person;
		this.siteId = siteId;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.region = region;
		this.postalCode = postalCode;
		this.isPrimary = isPrimary;
		this.doNotUse = doNotUse;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}

	//---------- Getter/Setters ----------//

	public String getId() {
		return id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
