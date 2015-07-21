package laxstats.web.people;

import laxstats.api.Region;
import laxstats.api.people.AddressType;

public interface AddressResource {

   /**
    * Returns the unique identifier for this address, or null if a new address.
    *
    * @return
    */
   public String getId();

   /**
    * Sets the unique identifier for this address. Use null for a new address.
    *
    * @param id
    */
   public void setId(String id);

   /**
    * Returns the identifier of the associated person. Never null.
    *
    * @return
    */
   public String getPersonId();

   /**
    * Sets the identifier of the associated person. Must not be null.
    *
    * @param personId
    */
   public void setPersonId(String personId);

   /**
    * Returns the address type. Never null.
    *
    * @return
    */
   public AddressType getType();

   /**
    * Sets the address type. Must not be null.
    *
    * @param type
    */
   public void setType(AddressType type);

   /**
    * Returns the first line of the street address, or null.
    *
    * @return
    */
   public String getAddress1();

   /**
    * Sets the first line of the street address. Use null for none.
    *
    * @param address1
    */
   public void setAddress1(String address1);

   /**
    * Returns the second line of the street address, or null.
    *
    * @return
    */
   public String getAddress2();

   /**
    * Sets the second line of the street address. Use null for none.
    *
    * @param address2
    */
   public void setAddress2(String address2);

   /**
    * Returns the name of the city. Never null.
    *
    * @return
    */
   public String getCity();

   /**
    * Sets the name of the city. Must not be null.
    *
    * @param city
    */
   public void setCity(String city);

   /**
    * Returns the state or region. Never null.
    *
    * @return
    */
   public Region getRegion();

   /**
    * Sets the state or region. Must not be null.
    *
    * @param region
    */
   public void setRegion(Region region);

   /**
    * Returns the postal code, or null.
    *
    * @return
    */
   public String getPostalCode();

   /**
    * Sets the postal code. Use null for none or unknown.
    *
    * @param postalCode
    */
   public void setPostalCode(String postalCode);

   /**
    * Returns true if this address is the person's primary address, false otherwise.
    *
    * @return
    */
   public boolean isPrimary();

   /**
    * Sets whether this address is the person's primary address. Defaults to false.
    *
    * @param primary
    */
   public void setPrimary(boolean primary);

   /**
    * Returns true if this address is obsolete, false otherwise.
    *
    * @return
    */
   public boolean isDoNotUse();

   /**
    * Sets whether this address is obsolete. Defaults to false.
    *
    * @param doNotUse
    */
   public void setDoNotUse(boolean doNotUse);

}
