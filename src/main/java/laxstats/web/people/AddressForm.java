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
public class AddressForm implements Serializable, AddressResource {
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
    * {@inheritDoc}
    */
   @Override
   public String getId() {
      return id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setId(String id) {
      this.id = id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getPersonId() {
      return personId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setPersonId(String personId) {
      assert personId != null;
      this.personId = personId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public AddressType getType() {
      return type;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setType(AddressType type) {
      assert type != null;
      this.type = type;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getAddress1() {
      return address1;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setAddress1(String address1) {
      this.address1 = address1;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getAddress2() {
      return address2;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setAddress2(String address2) {
      this.address2 = address2;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getCity() {
      return city;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setCity(String city) {
      assert city != null;
      this.city = city;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Region getRegion() {
      return region;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setRegion(Region region) {
      this.region = region;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getPostalCode() {
      return postalCode;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isPrimary() {
      return primary;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setPrimary(boolean primary) {
      this.primary = primary;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isDoNotUse() {
      return doNotUse;
   }

   /**
    * {@inheritDoc}
    */
   @Override
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
