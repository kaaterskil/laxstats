package laxstats.query.people;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * {@code ZipCode} is a persistent object containing information about a US ZIP postal code.
 */
@Entity
@Table(name = "zip_codes")
public class ZipCode implements Serializable {
   private static final long serialVersionUID = -5831369912809042953L;

   /**
    * The 5-digit US ZIP code. Never null.
    */
   @Id
   @Column(length = 5, nullable = false)
   private String zipCode;


   private String primaryRecord;

   /**
    * The name of the city that contains this ZIP code.
    */
   @Column(length = 30)
   private String city;

   /**
    * A comma-delimited list of city names that contain this ZIP code.
    */
   @Column(columnDefinition = "text")
   private String acceptableCities;

   /**
    * A comma-delimited list of unacceptable city names.
    */
   @Column(columnDefinition = "text")
   private String unacceptableCities;

   /**
    * The 2-letter abbreviation for the state that contains this ZIP code.
    */
   @Column(length = 2)
   private String state;

   private String county;

   private String timezone;

   /**
    * A comma-delimited string of telephone area codes associated with this ZIP code.
    */
   private String areaCodes;

   /**
    * The latitude of the centroid of the geographic area covered by this ZIP code.
    */
   private BigDecimal latitude;

   /**
    * The longitude of the centroid of the geographic area covered by this ZIP code.
    */
   private BigDecimal longitude;

   /**
    * The 2-letter abbreviation of the continent that contains this ZIP code.
    */
   @Column(length = 2)
   private String continent;

   /**
    * The s-letter abbreviation of the country that contains this ZIP code.
    */
   @Column(length = 2)
   private String country;

   /**
    * A boolean flag to indicate if this ZIP code is active or decommissioned.
    */
   private boolean decommissioned;

   /**
    * The estimated population of the geographic area represented by this ZIP code, if known.
    */
   private int estimatedPopulation;

   @Column(columnDefinition = "text")
   private String notes;

   /*---------- Getter/Setters ----------*/

   public String getZipCode() {
      return zipCode;
   }

   public void setZipCode(String zipCode) {
      this.zipCode = zipCode;
   }

   public String getPrimaryRecord() {
      return primaryRecord;
   }

   public void setPrimaryRecord(String primaryRecord) {
      this.primaryRecord = primaryRecord;
   }

   public String getCity() {
      return city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getAcceptableCities() {
      return acceptableCities;
   }

   public void setAcceptableCities(String acceptableCities) {
      this.acceptableCities = acceptableCities;
   }

   public String getUnacceptableCities() {
      return unacceptableCities;
   }

   public void setUnacceptableCities(String unacceptableCities) {
      this.unacceptableCities = unacceptableCities;
   }

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public String getCounty() {
      return county;
   }

   public void setCounty(String county) {
      this.county = county;
   }

   public String getTimezone() {
      return timezone;
   }

   public void setTimezone(String timezone) {
      this.timezone = timezone;
   }

   public String getAreaCodes() {
      return areaCodes;
   }

   public void setAreaCodes(String areaCodes) {
      this.areaCodes = areaCodes;
   }

   public BigDecimal getLatitude() {
      return latitude;
   }

   public void setLatitude(BigDecimal latitude) {
      this.latitude = latitude;
   }

   public BigDecimal getLongitude() {
      return longitude;
   }

   public void setLongitude(BigDecimal longitude) {
      this.longitude = longitude;
   }

   public String getContinent() {
      return continent;
   }

   public void setContinent(String continent) {
      this.continent = continent;
   }

   public String getCountry() {
      return country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   public boolean isDecommissioned() {
      return decommissioned;
   }

   public void setDecommissioned(boolean decommissioned) {
      this.decommissioned = decommissioned;
   }

   public int getEstimatedPopulation() {
      return estimatedPopulation;
   }

   public void setEstimatedPopulation(int estimatedPopulation) {
      this.estimatedPopulation = estimatedPopulation;
   }

   public String getNotes() {
      return notes;
   }

   public void setNotes(String notes) {
      this.notes = notes;
   }

   @Override
   public int hashCode() {
      return zipCode.hashCode();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (!(o instanceof ZipCode)) {
         return false;
      }
      final ZipCode that = (ZipCode)o;
      return zipCode.equals(that.zipCode);
   }
}
