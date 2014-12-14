package laxstats.api.seasons;

import laxstats.query.users.UserEntry;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class SeasonDTO {
  private SeasonId seasonId;
  private String description;
  private LocalDate startsOn;
  private LocalDate endsOn;
  private LocalDateTime createdAt;
  private UserEntry createdBy;
  private LocalDateTime modifiedAt;
  private UserEntry modifiedBy;

  public SeasonId getSeasonId() {
    return seasonId;
  }

  public void setSeasonId(SeasonId seasonId) {
    this.seasonId = seasonId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getStartsOn() {
    return startsOn;
  }

  public void setStartsOn(LocalDate startsOn) {
    this.startsOn = startsOn;
  }

  public LocalDate getEndsOn() {
    return endsOn;
  }

  public void setEndsOn(LocalDate endsOn) {
    this.endsOn = endsOn;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public UserEntry getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(UserEntry createdBy) {
    this.createdBy = createdBy;
  }

  public LocalDateTime getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(LocalDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  public UserEntry getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(UserEntry modifiedBy) {
    this.modifiedBy = modifiedBy;
  }
}
