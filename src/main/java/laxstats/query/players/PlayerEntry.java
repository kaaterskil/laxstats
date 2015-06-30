package laxstats.query.players;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Position;
import laxstats.api.players.Role;
import laxstats.query.people.PersonEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * {@code PlayerEntry} represents a query object model of a person assigned to a particular team
 * season and roster. A player may have a role other than athlete, such a coach, manager, etc. A
 * person may be associated with several seasons on a given team, and may be associated with several
 * teams, although not simultaneously.
 */
@Entity
@Table(name = "players",
         indexes = { @Index(name = "player_idx1", columnList = "id, team_season_id, person_id"),
            @Index(name = "player_idx2", columnList = "team_season_id"),
            @Index(name = "player_idx3", columnList = "person_id"),
            @Index(name = "player_idx4", columnList = "role"),
            @Index(name = "player_idx5", columnList = "status"),
            @Index(name = "player_idx6", columnList = "isCaptain"),
            @Index(name = "player_idx7", columnList = "depth") },
         uniqueConstraints = { @UniqueConstraint(name = "player_uk1", columnNames = { "id",
            "team_season_id", "person_id" }) })
public class PlayerEntry implements Serializable {
   private static final long serialVersionUID = -297221422212373720L;

   @Id
   @Column(length = 36)
   private String id;

   @ManyToOne
   @JoinColumn(name = "person_id", nullable = false)
   private PersonEntry person;

   @ManyToOne
   @JoinColumn(name = "team_season_id", nullable = false)
   private TeamSeasonEntry teamSeason;

   @Column(length = 100)
   private String fullName;

   @Enumerated(EnumType.STRING)
   @Column(length = 20, nullable = false)
   private Role role;

   @Enumerated(EnumType.STRING)
   @Column(length = 20, nullable = false)
   private PlayerStatus status;

   @Column(length = 4)
   private String jerseyNumber;

   @Enumerated(EnumType.STRING)
   @Column(length = 20)
   private Position position;

   private boolean isCaptain;
   private int depth;
   private int height;
   private int weight;

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   /**
    * Returns the player primary key.
    * 
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the player primary key.
    * 
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the plater's association person. Never null.
    * 
    * @return
    */
   public PersonEntry getPerson() {
      return person;
   }

   /**
    * Sets the player's associated person.
    * 
    * @param person
    */
   public void setPerson(PersonEntry person) {
      assert person != null;
      this.person = person;
   }

   /**
    * Returns the player's associated team season. Never null.
    * 
    * @return
    */
   public TeamSeasonEntry getTeamSeason() {
      return teamSeason;
   }

   /**
    * Sets the associated team season for this player.
    * 
    * @param teamSeason
    */
   public void setTeamSeason(TeamSeasonEntry teamSeason) {
      assert teamSeason != null;
      this.teamSeason = teamSeason;
   }

   /**
    * Returns a concatenation of the player's full name, or null if not known.
    * 
    * @return
    */
   public String getFullName() {
      return fullName;
   }

   /**
    * Sets a concatenation of the player's full name.
    * 
    * @param fullName
    */
   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   /**
    * Returns the player's role on the team. Never null.
    * 
    * @return
    */
   public Role getRole() {
      return role;
   }

   /**
    * Sets the player's role on the team.
    * 
    * @param role
    */
   public void setRole(Role role) {
      assert role != null;
      this.role = role;
   }

   /**
    * Returns the player's playing status. Never null.
    * 
    * @return
    */
   public PlayerStatus getStatus() {
      return status;
   }

   /**
    * Sets the player's playing status.
    * 
    * @param status
    */
   public void setStatus(PlayerStatus status) {
      assert status != null;
      this.status = status;
   }

   /**
    * Returns the player's jersey number, or null if not known or assigned.
    * 
    * @return
    */
   public String getJerseyNumber() {
      return jerseyNumber;
   }

   /**
    * Sets the player's jersey number.
    * 
    * @param jerseyNumber
    */
   public void setJerseyNumber(String jerseyNumber) {
      this.jerseyNumber = jerseyNumber;
   }

   /**
    * Returns the player's position, or null if not known.
    * 
    * @return
    */
   public Position getPosition() {
      return position;
   }

   /**
    * Sets the player's position.
    * 
    * @param position
    */
   public void setPosition(Position position) {
      this.position = position;
   }

   /**
    * Returns true if the player is a team captain, false otherwise.
    * 
    * @return
    */
   public boolean isCaptain() {
      return isCaptain;
   }

   /**
    * Sets a flag to determine if the player is a team captain.
    * 
    * @param isCaptain
    */
   public void setCaptain(boolean isCaptain) {
      this.isCaptain = isCaptain;
   }

   /**
    * Returns the player's depth on the roster, or zero if not known.
    *
    * @return
    */
   public int getDepth() {
      return depth;
   }

   /**
    * Sets the player's depth on the roster.
    *
    * @param depth
    */
   public void setDepth(int depth) {
      this.depth = depth;
   }

   /**
    * Returns the player's height, in inches, or zero if not known.
    *
    * @return
    */
   public int getHeight() {
      return height;
   }

   /**
    * Sets the player's height, in inches.
    *
    * @param height
    */
   public void setHeight(int height) {
      this.height = height;
   }

   /**
    * Returns the player's weight, in pounds, or zero if not known.
    *
    * @return
    */
   public int getWeight() {
      return weight;
   }

   /**
    * Sets the player's weight, in pounds.
    *
    * @param weight
    */
   public void setWeight(int weight) {
      this.weight = weight;
   }

   /**
    * Returns the date and time this player was first persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Sets the date and time that this player was first persisted.
    *
    * @param createdAt
    */
   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   /**
    * Returns the user who first persisted this player.
    *
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Sets the user who first persisted this player.
    *
    * @param createdBy
    */
   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
   }

   /**
    * Returns the date and time this player was last modifed.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Sets the date and time this player was last modified.
    *
    * @param modifiedAt
    */
   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   /**
    * Returns the user who last modified this player.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   /**
    * Sets the user who last modified this player.
    *
    * @param modifiedBy
    */
   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }
}
