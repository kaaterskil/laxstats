package laxstats.query.siteTracking;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "arrivals")
public class Arrival {

   @Id
   @Column(length = 36)
   private String id;

   @ManyToOne
   private Visitor visitor;

   private String host;

   private String path;

   private String query;

   private String arrivalSourceId;

   private String referrer;

   private String userAgent;

   private String remoteIp;

   private boolean isTesting;

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   private LocalDateTime createdAt;

   @OneToMany
   private Set<ReportableEvent> reportableEvents = new HashSet<ReportableEvent>();

   /*---------- Constructors ----------*/

   public Arrival(String id, Visitor visitor, String host, String path, String query,
      String arrivalSourceId, String referrer, String userAgent, String remoteIp,
      boolean isTesting) {

      this.id = id;
      this.visitor = visitor;
      this.host = host;
      this.path = path;
      this.query = query;
      this.arrivalSourceId = arrivalSourceId;
      this.referrer = referrer;
      this.userAgent = userAgent;
      this.remoteIp = remoteIp;
      this.isTesting = isTesting;
   }

   protected Arrival() {
   }

   /*---------- Methods ----------*/

   public void addReportableEvent(ReportableEvent reportableEvent) {
      reportableEvent.setArrival(this);
      reportableEvents.add(reportableEvent);
   }

   /*---------- Getter/Setters ----------*/

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public Visitor getVisitor() {
      return visitor;
   }

   public void setVisitor(Visitor visitor) {
      this.visitor = visitor;
   }

   public String getHost() {
      return host;
   }

   public void setHost(String host) {
      this.host = host;
   }

   public String getPath() {
      return path;
   }

   public void setPath(String path) {
      this.path = path;
   }

   public String getQuery() {
      return query;
   }

   public void setQuery(String query) {
      this.query = query;
   }

   public String getArrivalSourceId() {
      return arrivalSourceId;
   }

   public void setArrivalSourceId(String arrivalSourceId) {
      this.arrivalSourceId = arrivalSourceId;
   }

   public String getReferrer() {
      return referrer;
   }

   public void setReferrer(String referrer) {
      this.referrer = referrer;
   }

   public String getUserAgent() {
      return userAgent;
   }

   public void setUserAgent(String userAgent) {
      this.userAgent = userAgent;
   }

   public String getRemoteIp() {
      return remoteIp;
   }

   public void setRemoteIp(String remoteIp) {
      this.remoteIp = remoteIp;
   }

   public boolean isTesting() {
      return isTesting;
   }

   public void setTesting(boolean isTesting) {
      this.isTesting = isTesting;
   }

   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   public Set<ReportableEvent> getReportableEvents() {
      return reportableEvents;
   }

   public void setReportableEvents(Set<ReportableEvent> reportableEvents) {
      this.reportableEvents = reportableEvents;
   }
}
