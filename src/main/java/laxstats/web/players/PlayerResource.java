package laxstats.web.players;

import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Position;
import laxstats.api.players.Role;

import org.joda.time.LocalDate;

public interface PlayerResource {

   /**
    * Returns this player's unique identifier, or null if the player has not been persisted.
    *
    * @return
    */
   public String getId();

   /**
    * Sets this player's unique identifier. Use null if the player has not been persisted.
    *
    * @param id
    */
   public void setId(String id);

   /**
    * Returns the identifier of the associated person. Never null.
    *
    * @return
    */
   public String getPerson();

   /**
    * Sets the identifier of the associated person. Must not be null.
    *
    * @param person
    */
   public void setPerson(String person);

   /**
    * Returns the identifier of the associated team season. Never null.
    *
    * @return
    */
   public String getTeamSeason();

   /**
    * Sets the identifier of the associated team season. Must not be null.
    *
    * @param teamSeason
    */
   public void setTeamSeason(String teamSeason);

   /**
    * Returns the player's role. Never null.
    *
    * @return
    */
   public Role getRole();

   /**
    * Sets the player's role. Must not be null.
    *
    * @param role
    */
   public void setRole(Role role);

   /**
    * Returns the player's playing status. Never null.
    *
    * @return
    */
   public PlayerStatus getStatus();

   /**
    * Sets the player's playing status. Must not be null.
    *
    * @param status
    */
   public void setStatus(PlayerStatus status);

   /**
    * Returns the player's jersey number or null.
    *
    * @return
    */
   public String getJerseyNumber();

   /**
    * Sets the player's jersey number. Use null if unknown.
    *
    * @param jerseyNumber
    */
   public void setJerseyNumber(String jerseyNumber);

   /**
    * Returns the player's position, or null.
    *
    * @return
    */
   public Position getPosition();

   /**
    * Sets the player's position. Use null if unknown.
    *
    * @param position
    */
   public void setPosition(Position position);

   /**
    * Returns true if the player is a team captain, false otherwise.
    *
    * @return
    */
   public boolean isCaptain();

   /**
    * Sets a flag to determine if the player is a team captain. Defaults to false.
    *
    * @param captain
    */
   public void setCaptain(boolean captain);

   /**
    * Returns the player's depth on the roster, or zero if not known.
    *
    * @return
    */
   public int getDepth();

   /**
    * Sets the player's depth on the roster. Defaults to zero.
    *
    * @param depth
    */
   public void setDepth(int depth);

   /**
    * Returns the player's height, in inches, or zero if not known.
    *
    * @return
    */
   public int getHeight();

   /**
    * Sets the player's height, in inches. Defaults to zero.
    *
    * @param height
    */
   public void setHeight(int height);

   /**
    * Returns the player's weight, in pounds, or zero if not known.
    *
    * @return
    */
   public int getWeight();

   /**
    * Sets the player's weight, in pounds. Defaults to zero.
    *
    * @param weight
    */
   public void setWeight(int weight);

   /**
    * Returns true if this person has a parental release, false otherwise.
    *
    * @return
    */
   public boolean isReleased();

   /**
    * Sets a flag to determine if the person has a parental release.
    *
    * @param released
    */
   public void setReleased(boolean released);

   /**
    * Returns the date this person's parental release request was sent, or null.
    *
    * @return
    */
   public String getParentReleaseSentOn();

   /**
    * Sets the date this person's parental release request was sent. Use null if not used or known.
    *
    * @param parentReleaseSentOn
    */
   public void setParentReleaseSentOn(String parentReleaseSentOn);

   /**
    * Returns the date this person's parental release request was sent, or null.
    *
    * @return
    */
   public LocalDate getParentReleaseSentOnAsLocalDate();

   /**
    * Sets the date this person's parental release request was sent. Use null if not used or known.
    *
    * @param parentReleaseSentOn
    */
   public void setParentReleaseSentOnFromLocalDate(LocalDate parentReleaseSentOn);

   /**
    * Returns the date this person's parental release was received, or null.
    *
    * @return
    */
   public String getParentReleaseReceivedOn();

   /**
    * Sets the date this person's parental release was received. Use null if not used or known.
    *
    * @param parentReleaseReceivedOn
    */
   public void setParentReleaseReceivedOn(String parentReleaseReceivedOn);

   /**
    * Returns the date this person's parental release was received, or null.
    *
    * @return
    */
   public LocalDate getParentReleaseReceivedOnAsLocalDate();

   /**
    * Sets the date this person's parental release was received. Use null if not used or known.
    *
    * @param parentReleaseReceivedOn
    */
   public void setParentReleaseReceivedOnFromLocalDate(LocalDate parentReleaseReceivedOn);


}
