package laxstats.query.siteTracking;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.axonframework.domain.IdentifierFactory;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "reportable_events")
public class ReportableEvent {

   /**
    * Factory to create a {@code ReportableEvent}
    *
    * @param arrival
    * @param eventType
    * @param value
    * @return
    */
   public static ReportableEvent recordEvent(Arrival arrival, String eventType, String value) {
      final String id = IdentifierFactory.getInstance().generateIdentifier();
      return new ReportableEvent(id, arrival, eventType, value);
   }

   /*---------- Instance properties ----------*/

   @Id
   @Column(length = 36)
   private String id;

   @ManyToOne
   private Arrival arrival;

   private String eventType;

   private String value;

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   private LocalDateTime createdAt;

   private long createdAtMillis;

   /*---------- Constructors ----------*/

   public ReportableEvent(String id, Arrival arrival, String eventType, String value) {
      this.id = id;
      this.arrival = arrival;
      this.eventType = eventType;
      this.value = value;
      createdAt = LocalDateTime.now();
      createdAtMillis = createdAt.toDateTime().getMillis();
   }

   public ReportableEvent() {
   }

   /*---------- Getter/Setters ----------*/

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public Arrival getArrival() {
      return arrival;
   }

   public void setArrival(Arrival arrival) {
      this.arrival = arrival;
   }

   public String getEventType() {
      return eventType;
   }

   public void setEventType(String eventType) {
      this.eventType = eventType;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
      createdAtMillis = createdAt.toDateTime().getMillis();
   }

   public long getCreatedAtMillis() {
      return createdAtMillis;
   }

   public void setCreatedAtMillis(long createdAtMillis) {
      this.createdAtMillis = createdAtMillis;
      createdAt = LocalDateTime.fromDateFields(new Date(createdAtMillis));
   }
}
