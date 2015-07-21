package laxstats.web.players;

import javax.validation.constraints.NotNull;

import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Position;
import laxstats.api.players.Role;

import org.joda.time.LocalDate;

/**
 * {@code PlayerResource} represents a player resource for remote clients.
 */
public class PlayerResourceImpl implements PlayerResource {

   private String id;

   @NotNull
   private String person;

   @NotNull
   private String teamSeason;

   @NotNull
   private Role role = Role.ATHLETE;

   @NotNull
   private PlayerStatus status = PlayerStatus.ACTIVE;

   private String jerseyNumber;
   private Position position;
   private boolean captain = false;
   private int depth;
   private int height;
   private int weight;
   private boolean released;
   private String parentReleaseSentOn;
   private String parentReleaseReceivedOn;

   /**
    * Creates a {@code PlayerResource} with the given information.
    *
    * @param id
    * @param person
    * @param teamSeason
    * @param role
    * @param status
    * @param jerseyNumber
    * @param position
    * @param captain
    * @param depth
    * @param height
    * @param weight
    * @param released
    * @param parentReleaseSentOn
    * @param parentReleaseReceivedOn
    */
   public PlayerResourceImpl(String id, String person, String teamSeason, Role role,
      PlayerStatus status, String jerseyNumber, Position position, boolean captain, int depth,
      int height, int weight, boolean released, String parentReleaseSentOn,
      String parentReleaseReceivedOn) {
      this.id = id;
      this.person = person;
      this.teamSeason = teamSeason;
      this.role = role;
      this.status = status;
      this.jerseyNumber = jerseyNumber;
      this.position = position;
      this.captain = captain;
      this.depth = depth;
      this.height = height;
      this.weight = weight;
      this.released = released;
      this.parentReleaseSentOn = parentReleaseSentOn;
      this.parentReleaseReceivedOn = parentReleaseReceivedOn;
   }

   /**
    * Creates an empty {@code PlayerResource} for internal use.
    */
   public PlayerResourceImpl() {
   }

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
      assert person != null;
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
      assert teamSeason != null;
      this.teamSeason = teamSeason;
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
      assert role != null;
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
      assert status != null;
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
    * {@inheritDoc}
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
      return parentReleaseSentOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setParentReleaseSentOn(String parentReleaseSentOn) {
      this.parentReleaseSentOn = parentReleaseSentOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LocalDate getParentReleaseSentOnAsLocalDate() {
      return parentReleaseSentOn == null ? null : LocalDate.parse(parentReleaseSentOn);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setParentReleaseSentOnFromLocalDate(LocalDate parentReleaseSentOn) {
      this.parentReleaseSentOn = parentReleaseSentOn == null ? null : parentReleaseSentOn.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getParentReleaseReceivedOn() {
      return parentReleaseReceivedOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setParentReleaseReceivedOn(String parentReleaseReceivedOn) {
      this.parentReleaseReceivedOn = parentReleaseReceivedOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LocalDate getParentReleaseReceivedOnAsLocalDate() {
      return parentReleaseReceivedOn == null ? null : LocalDate.parse(parentReleaseReceivedOn);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setParentReleaseReceivedOnFromLocalDate(LocalDate parentReleaseReceivedOn) {
      this.parentReleaseReceivedOn =
         parentReleaseReceivedOn == null ? null : parentReleaseReceivedOn.toString();
   }
}
