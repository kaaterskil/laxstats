package laxstats.web.people;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import laxstats.api.Region;
import laxstats.api.people.AddressType;
import laxstats.api.utils.Constants;

/**
 * {@code AddressForm} contains user-defined information with which to create and update a postal
 * address.
 */
public class AddressForm implements Serializable {
   private static final long serialVersionUID = -3473572483235250762L;

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
   @Size(min = 3, max = Constants.MAX_LENGTH_CITY)
   private String city;

   private Region region;

   @Size(max = Constants.MAX_LENGTH_LONG_POSTAL_CODE)
   @Pattern(regexp = Constants.PATTERN_LONG_ZIP_CODE)
   private String postalCode;

   private boolean primary = false;

   private boolean doNotUse = false;

   private List<AddressType> addressTypes;
   private List<Region> regions;

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
    * Returns the identifier for the associated person. Never null.
    *
    * @return
    */
   public String getPersonId() {
      return personId;
   }

   /**
    * Sets the identifier for the associated person. Must not be null.
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
    * Returns the state or region, nor null.
    *
    * @return
    */
   public Region getRegion() {
      return region;
   }

   /**
    * Sets the state or region. Use null for none or unknown.
    *
    * @param region
    */
   public void setRegion(Region region) {
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

   public List<AddressType> getAddressTypes() {
      if (addressTypes == null) {
         addressTypes = Arrays.asList(AddressType.values());
      }
      return addressTypes;
   }

   public List<Region> getRegions() {
      if (regions == null) {
         regions = Arrays.asList(Region.values());
      }
      return regions;
   }

}
