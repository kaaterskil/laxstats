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
public class AddressResourceImpl implements AddressResource {

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
   public AddressResourceImpl(String id, String personId, AddressType type, String address1,
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
    * Creates an empty {@code AddressResource}.
    */
   public AddressResourceImpl() {
   }

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
      assert region != null;
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

}
