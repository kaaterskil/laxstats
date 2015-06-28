package laxstats.api.seasons;

import java.io.Serializable;

import laxstats.query.users.UserEntry;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * {@code SeasonDTO} transfers information about a season between the presentation and domain
 * layers.
 */
public class SeasonDTO implements Serializable {
   private static final long serialVersionUID = 523697205059312241L;

   private final SeasonId seasonId;
   private final String description;
   private final LocalDate startsOn;
   private final LocalDate endsOn;
   private final LocalDateTime createdAt;
   private final UserEntry createdBy;
   private final LocalDateTime modifiedAt;
   private final UserEntry modifiedBy;

   /**
    * Creates a {@code SeasonDTO} with the given information.
    *
    * @param seasonId
    * @param description
    * @param startsOn
    * @param endsOn
    * @param createdAt
    * @param createdBy
    * @param modifiedAt
    * @param modifiedBy
    */
   public SeasonDTO(SeasonId seasonId, String description, LocalDate startsOn, LocalDate endsOn,
      LocalDateTime createdAt, UserEntry createdBy, LocalDateTime modifiedAt, UserEntry modifiedBy) {
      super();
      this.seasonId = seasonId;
      this.description = description;
      this.startsOn = startsOn;
      this.endsOn = endsOn;
      this.createdAt = createdAt;
      this.createdBy = createdBy;
      this.modifiedAt = modifiedAt;
      this.modifiedBy = modifiedBy;
   }

   /**
    * Creates a {@code SeasonDTO} with the given information.
    *
    * @param seasonId
    * @param description
    * @param startsOn
    * @param endsOn
    * @param modifiedAt
    * @param modifiedBy
    */
   public SeasonDTO(SeasonId seasonId, String description, LocalDate startsOn, LocalDate endsOn,
      LocalDateTime modifiedAt, UserEntry modifiedBy) {
      this(seasonId, description, startsOn, endsOn, null, null, modifiedAt, modifiedBy);
   }

   /**
    * Returns this season's indentifier.
    *
    * @return
    */
   public SeasonId getSeasonId() {
      return seasonId;
   }

   /**
    * Returns a description for this season.
    *
    * @return
    */
   public String getDescription() {
      return description;
   }

   /**
    * Returns this season's start date.
    *
    * @return
    */
   public LocalDate getStartsOn() {
      return startsOn;
   }

   /**
    * Returns this season's ending date.
    *
    * @return
    */
   public LocalDate getEndsOn() {
      return endsOn;
   }

   /**
    * Returns the date and time this season was first persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
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
    * Returns the date and time this season was last persisted.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Returns the user who last modified this season.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }
}
