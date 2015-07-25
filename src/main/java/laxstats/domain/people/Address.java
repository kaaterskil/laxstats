package laxstats.domain.people;

import laxstats.api.Region;
import laxstats.api.people.AddressChanged;
import laxstats.api.people.AddressDTO;
import laxstats.api.people.AddressType;
import laxstats.api.people.Contactable;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;

/**
 * {@code Address} represents a domain object model of a postal address.
 */
public class Address extends AbstractAnnotatedEntity implements Contactable {
   private String id;
   private String personId;
   private String siteId;
   private AddressType addressType;
   private String address1;
   private String address2;
   private String city;
   private Region region;
   private String postalCode;
   private boolean isPrimary;
   private boolean doNotUse;

   /**
    * Returns a concatenated string.
    *
    * @return
    */
   public String getAddress() {
      final StringBuilder sb = new StringBuilder();
      boolean concat = false;
      if (address1 != null) {
         sb.append(address1);
         concat = true;
      }
      if (address2 != null) {
         if (concat) {
            sb.append(", ");
         }
         sb.append(address2);
         concat = true;
      }
      if (city != null) {
         if (concat) {
            sb.append(", ");
         }
         sb.append(city);
         concat = true;
      }
      if (region != null) {
         if (concat) {
            sb.append(" ");
         }
         sb.append(region.getAbbreviation());
      }
      if (postalCode != null) {
         sb.append(" ")
            .append(postalCode);
      }
      return sb.toString();
   }

   /**
    * Updates and persists changes to this address with information contained in the given event.
    * This is a delegated event handler from the parent association.
    *
    * @param event
    */
   @EventHandler
   protected void handle(AddressChanged event) {
      if (id != null && !id.equals(event.getAddressDTO()
         .getId())) {
         return;
      }

      final AddressDTO dto = event.getAddressDTO();
      addressType = dto.getAddressType();
      address1 = dto.getAddress1();
      address2 = dto.getAddress2();
      city = dto.getCity();
      region = dto.getRegion();
      postalCode = dto.getPostalCode();
      isPrimary = dto.isPrimary();
      doNotUse = dto.isDoNotUse();
   }

   /**
    * Returns the unique identifier of this {@code Address}
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the unique identifier of this {@code Address.}
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the identifier of the associated person.
    *
    * @return
    */
   public String getPersonId() {
      return personId;
   }

   /**
    * Sets the identifier of the associated person.
    *
    * @param personId
    */
   public void setPersonId(String personId) {
      this.personId = personId;
   }

   /**
    * Returns the identifier of the associated playing field.
    *
    * @return
    */
   public String getSiteId() {
      return siteId;
   }

   /**
    * Sets the identifier of the associated playing field.
    *
    * @param siteId
    */
   public void setSiteId(String siteId) {
      this.siteId = siteId;
   }

   /**
    * Returns the type of {@code Address}.
    *
    * @return
    */
   public AddressType getAddressType() {
      return addressType;
   }

   /**
    * Sets the type of {@code Address}.
    *
    * @param addressType
    */
   public void setAddressType(AddressType addressType) {
      this.addressType = addressType;
   }

   /**
    * Returns the first line of the street address.
    *
    * @return
    */
   public String getAddress1() {
      return address1;
   }

   /**
    * Sets the first line of the street address.
    *
    * @param address1
    */
   public void setAddress1(String address1) {
      this.address1 = address1;
   }

   /**
    * Returns the second line of the street address.
    *
    * @return
    */
   public String getAddress2() {
      return address2;
   }

   /**
    * Sets the second line of the street address.
    *
    * @param address2
    */
   public void setAddress2(String address2) {
      this.address2 = address2;
   }

   /**
    * Returns the city.
    *
    * @return
    */
   public String getCity() {
      return city;
   }

   /**
    * Sets the city.
    *
    * @param city
    */
   public void setCity(String city) {
      this.city = city;
   }

   /**
    * Returns the region.
    *
    * @return
    */
   public Region getRegion() {
      return region;
   }

   /**
    * Sets the region.
    *
    * @param region
    */
   public void setRegion(Region region) {
      this.region = region;
   }

   /**
    * Returns the postal code.
    *
    * @return
    */
   public String getPostalCode() {
      return postalCode;
   }

   /**
    * Sets the postal c0de.
    *
    * @param postalCode
    */
   public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isPrimary() {
      return isPrimary;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setPrimary(boolean isPrimary) {
      this.isPrimary = isPrimary;
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
