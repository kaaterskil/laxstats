package laxstats.query.seasons;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.api.utils.Common;
import laxstats.api.utils.Constants;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * {@code SeasonEntry} is a persistent query object model representing a playing season. Playing
 * seasons define a period of competition with a defined start and end date.
 */
@Entity
@Table(name = "seasons",
         indexes = { @Index(name = "season_idx1", columnList = "startsOn"),
            @Index(name = "season_idx2", columnList = "endsOn") },
         uniqueConstraints = { @UniqueConstraint(name = "season_uk1", columnNames = { "startsOn",
            "endsOn" }) })
public class SeasonEntry implements Serializable {
   private static final long serialVersionUID = -6462722991411227126L;

   @Id
   @Column(length = Constants.MAX_LENGTH_DATABASE_KEY)
   private String id;

   @Column(length = 100, nullable = false)
   private String description;

   @Column(nullable = false)
   @Type(type = Constants.LOCAL_DATE_DATABASE_TYPE)
   private LocalDate startsOn;

   @Type(type = Constants.LOCAL_DATE_DATABASE_TYPE)
   private LocalDate endsOn;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   /**
    * Returns the start and end dates of this season as an {@code Interval}. The dates are converted
    * to local timestamps at the start of the respective day. If the season's end date is not
    * defined, a timestamp corresponding to the end of time in the Java environment is substituted.
    *
    * @return
    */
   public Interval getInterval() {
      final DateTime start = getStartsOn().toDateTimeAtStartOfDay();
      DateTime end;
      try {
         end = getEndsOn().toDateTimeAtStartOfDay();
      }
      catch (final Exception e) {
         end = new DateTime(Long.MAX_VALUE);
      }
      return new Interval(start, end);
   }

   /**
    * Returns true if the given season overlaps with this season.
    *
    * @param target
    * @return
    */
   public boolean overlaps(SeasonEntry target) {
      if (target != null) {
         return overlaps(target.getStartsOn(), target.getEndsOn());
      }
      return false;
   }

   /**
    * Returns true if the given {@code Interval} overlaps with the start and ends dates of this
    * season, false otherwise. If the given interval's end date is not defined, a local date
    * corresponding to the end of time according to the Java environment is substituted.
    *
    * @param interval
    * @return
    */
   public boolean overlaps(Interval interval) {
      if (interval != null) {
         final LocalDate otherStart = interval.getStart().toLocalDate();
         LocalDate otherEnd;
         try {
            otherEnd = interval.getEnd().toLocalDate();
         }
         catch (final Exception e) {
            otherEnd = new LocalDate(Long.MAX_VALUE);
         }
         return overlaps(otherStart, otherEnd);
      }
      return false;
   }

   /**
    * Returns true if the given start and ends dates overlap with the the start and end dates of
    * this season, false otherwise.
    *
    * @param otherStart
    * @param otherEnd
    * @return
    */
   public boolean overlaps(LocalDate otherStart, LocalDate otherEnd) {
      final LocalDate thisStart = getStartsOn();
      final LocalDate thisEnd = Common.nvl(endsOn, Common.EOT.toLocalDate());
      otherEnd = Common.nvl(otherEnd, Common.EOT.toLocalDate());

      return (thisStart.isEqual(otherEnd) || thisStart.isBefore(otherEnd)) &&
         (otherStart.isEqual(thisEnd) || otherStart.isBefore(thisEnd));
   }

   /**
    * Returns this season's primary key.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets this season's primary key.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns a description for this season, or null if none.
    *
    * @return
    */
   public String getDescription() {
      return description;
   }

   /**
    * Sets a description for this season.
    *
    * @param description
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * Returns this season's start date. Never null.
    *
    * @return
    */
   public LocalDate getStartsOn() {
      return startsOn;
   }

   /**
    * Sets this season's start date.
    *
    * @param startsOn
    */
   public void setStartsOn(LocalDate startsOn) {
      this.startsOn = startsOn;
   }

   /**
    * Returns this season's end date.
    *
    * @return
    */
   public LocalDate getEndsOn() {
      return endsOn;
   }

   /**
    * Sets this season's end date. If the given end date is null, a local date corresponding to the
    * end of time in the Java environment is substituted.
    *
    * @param endsOn
    */
   public void setEndsOn(LocalDate endsOn) {
      if (endsOn == null) {
         endsOn = new LocalDate(Long.MAX_VALUE);
      }
      this.endsOn = endsOn;
   }

   /**
    * Returns the date an time this season was first persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Sets the date and time this season was first persisted.
    *
    * @param createdAt
    */
   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   /**
    * Returns the user who created this season.
    *
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Sets the user who created this season.
    *
    * @param createdBy
    */
   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
   }

   /**
    * Returns the date and time this season was last modified.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Sets the date and time that this season was last modified.
    *
    * @param modifiedAt
    */
   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   /**
    * Returns the user who last modified this season.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   /**
    * Sets the user who last modified this season.
    *
    * @param modifiedBy
    */
   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }
}
