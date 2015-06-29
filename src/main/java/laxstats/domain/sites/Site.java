package laxstats.domain.sites;

import laxstats.api.people.AddressDTO;
import laxstats.api.sites.DeleteSite;
import laxstats.api.sites.SiteCreated;
import laxstats.api.sites.SiteDTO;
import laxstats.api.sites.SiteDeleted;
import laxstats.api.sites.SiteId;
import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.SiteUpdated;
import laxstats.api.sites.Surface;
import laxstats.api.sites.UpdateSite;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

/**
 * {@code Site} represents a domain object model of a playing field.
 */
public class Site extends AbstractAnnotatedAggregateRoot<SiteId> {
   private static final long serialVersionUID = 6097187976724565990L;

   @AggregateIdentifier
   private SiteId siteId;

   private String name;
   private SiteStyle style;
   private Surface surface;
   private AddressDTO address;
   private String directions;

   /**
    * Applies a creation event to the site aggregate.
    * 
    * @param siteId
    * @param siteDTO
    */
   public Site(SiteId siteId, SiteDTO siteDTO) {
      apply(new SiteCreated(siteId, siteDTO));
   }

   /**
    * Creates a {@code Site}. Internal use only.
    */
   protected Site() {
   }

   /**
    * Instructs the framework to update the site aggregate.
    * 
    * @param command
    */
   public void update(UpdateSite command) {
      apply(new SiteUpdated(command.getSiteId(), command.getSiteDTO()));
   }

   /**
    * Instructs the framework to mark the site aggregate for deletion.
    * 
    * @param command
    */
   public void delete(DeleteSite command) {
      apply(new SiteDeleted(command.getSiteId()));
   }

   /**
    * Stores and persists a new site with information contained in the payload of the given event.
    * 
    * @param event
    */
   @EventSourcingHandler
   protected void handle(SiteCreated event) {
      final SiteDTO dto = event.getSiteDTO();

      siteId = event.getSiteId();
      name = dto.getName();
      style = dto.getStyle();
      surface = dto.getSurface();
      address = dto.getAddress();
      directions = dto.getDirections();
   }

   /**
    * Updates and persists the site with information contained in the payload of the given event.
    * 
    * @param event
    */
   @EventSourcingHandler
   protected void handle(SiteUpdated event) {
      final SiteDTO dto = event.getSiteDTO();

      name = dto.getName();
      style = dto.getStyle();
      surface = dto.getSurface();
      address = dto.getAddress();
      directions = dto.getDirections();
   }

   /**
    * Marks the site for deletion.
    * 
    * @param event
    */
   @EventSourcingHandler
   protected void handle(SiteDeleted event) {
      markDeleted();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public SiteId getIdentifier() {
      return siteId;
   }

   /**
    * Returns the site name.
    *
    * @return
    */
   public String getName() {
      return name;
   }

   /**
    * Returns the site quality.
    *
    * @return
    */
   public SiteStyle getStyle() {
      return style;
   }

   /**
    * Returns the site surface.
    *
    * @return
    */
   public Surface getSurface() {
      return surface;
   }

   /**
    * Returns the site address.
    *
    * @return
    */
   public AddressDTO getAddress() {
      return address;
   }

   /**
    * Returns driving directions.
    *
    * @return
    */
   public String getDirections() {
      return directions;
   }
}
