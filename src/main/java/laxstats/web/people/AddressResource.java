package laxstats.web.people;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import laxstats.api.Region;
import laxstats.api.people.AddressType;
import laxstats.api.utils.Constants;

/**
 * {@code AddressResource} represents a postal address resource for remote clients.
 */
public class AddressResource {

   private String id;

   @NotNull
   private String personId;

   @NotNull
   private AddressType type;

   @Size(max = Constants.MAX_LENGTH_ADDRESS)
   private String address1;

   @Size(max = Constants.MAX_LENGTH_ADDRESS)
   private String address2;

   @NotNull
   @Size(min = Constants.MIN_LENGTH_STRING, max = Constants.MAX_LENGTH_CITY)
   private String city;

   @NotNull
   private Region region;

   @Size(max = Constants.MAX_LENGTH_LONG_POSTAL_CODE)
   @Pattern(regexp = Constants.PATTERN_LONG_ZIP_CODE)
   private String postalCode;

   private boolean primary = false;

   private boolean doNotUse = false;

   /**
    * Creates an {@code AddressResource} with the given information.
    *
    * @param id
    * @param personId
    * @param type
    * @param address1
    * @param address2
    * @param city
    * @param region
    * @param postalCode
    * @param primary
    * @param doNotUse
    */
   public AddressResource(String id, String personId, AddressType type, String address1,
      String address2, String city, Region region, String postalCode, boolean primary,
      boolean doNotUse) {
      this.id = id;
      this.personId = personId;
      this.type = type;
      this.address1 = address1;
      this.address2 = address2;
      this.city = city;
      this.region = region;
      this.postalCode = postalCode;
      this.primary = primary;
      this.doNotUse = doNotUse;
   }

   /**
    * Creates an empty {@code AddressResource} for internal use.
    */
   public AddressResource() {
   }

   /**
    * Returns the unique identifier for this address, or null if a new address.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the unique identifier for this address. Use null for a new address.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the identifier of the associated person. Never null.
    *
    * @return
    */
   public String getPersonId() {
      return personId;
   }

   /**
    * Sets the identifier of the associated person. Must not be null.
    *
    * @param personId
    */
   public void setPersonId(String personId) {
      assert personId != null;
      this.personId = personId;
   }

   /**
    * Returns the address type. Never null.
    *
    * @return
    */
   public AddressType getType() {
      return type;
   }

   /**
    * Sets the address type. Must not be null.
    *
    * @param type
    */
   public void setType(AddressType type) {
      assert type != null;
      this.type = type;
   }

   /**
    * Returns the first line of the street address, or null.
    *
    * @return
    */
   public String getAddress1() {
      return address1;
   }

   /**
    * Sets the first line of the street address. Use null for none.
    *
    * @param address1
    */
   public void setAddress1(String address1) {
      this.address1 = address1;
   }

   /**
    * Returns the second line of the street address, or null.
    *
    * @return
    */
   public String getAddress2() {
      return address2;
   }

   /**
    * Sets the second line of the street address. Use null for none.
    *
    * @param address2
    */
   public void setAddress2(String address2) {
      this.address2 = address2;
   }

   /**
    * Returns the name of the city. Never null.
    *
    * @return
    */
   public String getCity() {
      return city;
   }

   /**
    * Sets the name of the city. Must not be null.
    *
    * @param city
    */
   public void setCity(String city) {
      assert city != null;
      this.city = city;
   }

   /**
    * Returns the state or region. Never null.
    *
    * @return
    */
   public Region getRegion() {
      return region;
   }

   /**
    * Sets the state or region. Must not be null.
    *
    * @param region
    */
   public void setRegion(Region region) {
      assert region != null;
      this.region = region;
   }

   /**
    * Returns the postal code, or null.
    *
    * @return
    */
   public String getPostalCode() {
      return postalCode;
   }

   /**
    * Sets the postal code. Use null for none or unknown.
    *
    * @param postalCode
    */
   public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
   }

   /**
    * Returns true if this address is the person's primary address, false otherwise.
    *
    * @return
    */
   public boolean isPrimary() {
      return primary;
   }

   /**
    * Sets whether this address is the person's primary address. Defaults to false.
    *
    * @param primary
    */
   public void setPrimary(boolean primary) {
      this.primary = primary;
   }

   /**
    * Returns true if this address is obsolete, false otherwise.
    *
    * @return
    */
   public boolean isDoNotUse() {
      return doNotUse;
   }

   /**
    * Sets whether this address is obsolete. Defaults to false.
    *
    * @param doNotUse
    */
   public void setDoNotUse(boolean doNotUse) {
      this.doNotUse = doNotUse;
   }

}
