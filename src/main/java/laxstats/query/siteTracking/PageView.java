package laxstats.query.siteTracking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.axonframework.domain.IdentifierFactory;

@Entity
@Table(name = "page_views")
public class PageView {

   /**
    * Factory to create a {@code ReportableEvent} with eventType of 'PageView' and value of the
    * current page count, and an associated {@code PageView}
    *
    * @param arrival
    * @param host
    * @param path
    * @param query
    * @param method
    * @param pageCount
    * @return
    */
   public static PageView recordReportableEvent(Arrival arrival, String host, String path,
      String query, String method, int pageCount)
   {
      // First call the ReportableEvent factory to create a new event.
      final ReportableEvent reportableEvent =
         ReportableEvent.recordEvent(arrival, "PageView", new Integer(pageCount).toString());

      // Then create the PageView.
      return recordPageView(reportableEvent, arrival, host, path, query, method);
   }

   public static PageView recordPageView(ReportableEvent reportableEvent, Arrival arrival,
      String host, String path, String query, String method)
   {
      final String id = IdentifierFactory.getInstance().generateIdentifier();
      return new PageView(id, reportableEvent, arrival, host, path, query, method);
   }

   /*---------- Instance properties ----------*/

   @Id
   @Column(length = 36)
   private String id;

   @ManyToOne
   private ReportableEvent reportableEvent;

   @ManyToOne
   private Arrival arrival;

   private String host;

   private String path;

   private String query;

   private String method;

   /*---------- Constructors ----------*/

   public PageView(String id, ReportableEvent reportableEvent, Arrival arrival, String host,
      String path, String query, String method) {
      this.id = id;
      this.reportableEvent = reportableEvent;
      this.arrival = arrival;
      this.host = host;
      this.path = path;
      this.query = query;
      this.method = method;
   }

   public PageView() {
   }

   /*---------- Getter/Setters ----------*/

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public ReportableEvent getReportableEvent() {
      return reportableEvent;
   }

   public void setReportableEvent(ReportableEvent reportableEvent) {
      this.reportableEvent = reportableEvent;
   }

   public Arrival getArrival() {
      return arrival;
   }

   public void setArrival(Arrival arrival) {
      this.arrival = arrival;
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

   public String getMethod() {
      return method;
   }

   public void setMethod(String method) {
      this.method = method;
   }
}
