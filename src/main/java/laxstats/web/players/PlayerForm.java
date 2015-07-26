package laxstats.web.players;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Position;
import laxstats.api.players.Role;
import laxstats.api.utils.Constants;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * {@code PlayerForm} contains user-defined information to create or update a player.
 */
public class PlayerForm implements Serializable, PlayerResource {
   private static final long serialVersionUID = -1822868866195304483L;

   private String id;

   @NotNull
   private String person;

   @NotNull
   private String teamSeason;

   @NotNull
   private Role role = Role.ATHLETE;

   @NotNull
   private PlayerStatus status = PlayerStatus.ACTIVE;

   private String fullName;
   private String jerseyNumber;
   private Position position;
   private boolean captain;
   private int depth = 1;
   private int height;
   private int weight;
   private boolean released;

   @DateTimeFormat(pattern = Constants.PATTERN_DATE_FORMAT)
   private LocalDate parentReleaseSentOn;

   @DateTimeFormat(pattern = Constants.PATTERN_DATE_FORMAT)
   private LocalDate parentReleaseReceivedOn;

   private List<Role> roles;
   private List<PlayerStatus> statuses;
   private List<Position> positions;

   /**
    * {@inheritDoc}
    */
   @Override
   public String getId() {
      return id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setId(String id) {
      this.id = id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getPerson() {
      return person;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setPerson(String person) {
      this.person = person;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getTeamSeason() {
      return teamSeason;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setTeamSeason(String teamSeason) {
      this.teamSeason = teamSeason;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getFullName() {
      return fullName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Role getRole() {
      return role;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setRole(Role role) {
      this.role = role;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public PlayerStatus getStatus() {
      return status;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setStatus(PlayerStatus status) {
      this.status = status;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getJerseyNumber() {
      return jerseyNumber;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setJerseyNumber(String jerseyNumber) {
      this.jerseyNumber = jerseyNumber;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Position getPosition() {
      return position;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setPosition(Position position) {
      this.position = position;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isCaptain() {
      return captain;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setCaptain(boolean captain) {
      this.captain = captain;
   }

   /**
    * Returns the player's depth on the roster, or zero if not known.
    *
    * @return
    */
   @Override
   public int getDepth() {
      return depth;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setDepth(int depth) {
      this.depth = depth;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int getHeight() {
      return height;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setHeight(int height) {
      this.height = height;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int getWeight() {
      return weight;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setWeight(int weight) {
      this.weight = weight;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isReleased() {
      return released;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setReleased(boolean released) {
      this.released = released;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getParentReleaseSentOn() {
      return parentReleaseSentOn == null ? null : parentReleaseSentOn.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setParentReleaseSentOn(String parentReleaseSentOn) {
      this.parentReleaseSentOn =
         parentReleaseSentOn == null ? null : LocalDate.parse(parentReleaseSentOn);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getParentReleaseReceivedOn() {
      return parentReleaseReceivedOn == null ? null : parentReleaseReceivedOn.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setParentReleaseReceivedOn(String parentReleaseReceivedOn) {
      this.parentReleaseReceivedOn =
         parentReleaseReceivedOn == null ? null : LocalDate.parse(parentReleaseReceivedOn);
   }

   @Override
   public LocalDate getParentReleaseSentOnAsLocalDate() {
      return parentReleaseSentOn;
   }

   @Override
   public void setParentReleaseSentOnFromLocalDate(LocalDate parentReleaseSentOn) {
      this.parentReleaseSentOn = parentReleaseSentOn;

   }

   @Override
   public LocalDate getParentReleaseReceivedOnAsLocalDate() {
      return parentReleaseReceivedOn;
   }

   @Override
   public void setParentReleaseReceivedOnFromLocalDate(LocalDate parentReleaseReceivedOn) {
      this.parentReleaseReceivedOn = parentReleaseReceivedOn;
   }

   /*---------- Select element option values ----------*/

   public List<Role> getRoles() {
      if (roles == null) {
         roles = Arrays.asList(Role.values());
      }
      return roles;
   }

   public List<PlayerStatus> getStatuses() {
      if (statuses == null) {
         statuses = Arrays.asList(PlayerStatus.values());
      }
      return statuses;
   }

   public List<Position> getPositions() {
      if (positions == null) {
         positions = Arrays.asList(Position.values());
      }
      return positions;
   }
}
