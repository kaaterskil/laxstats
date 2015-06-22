package laxstats.query.siteTracking;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "visitors")
public class Visitor {

   @Id
   @Column(length = 36)
   private String id;

   @OneToMany
   private Set<Arrival> arrivals = new HashSet<>();

   public Visitor(String id) {
      this.id = id;
   }

   protected Visitor() {
   }

   /*---------- Methods ----------*/

   public void addArrival(Arrival arrival) {
      arrival.setVisitor(this);
      arrivals.add(arrival);
   }

   /*---------- Getter/Setters ----------*/

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public Set<Arrival> getArrivals() {
      return arrivals;
   }

   public void setArrivals(Set<Arrival> arrivals) {
      this.arrivals = arrivals;
   }
}
