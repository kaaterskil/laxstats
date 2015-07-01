package laxstats.query.leagues;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.api.Region;
import laxstats.api.leagues.Level;
import laxstats.api.utils.Constants;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "leagues",
         indexes = { @Index(name = "league_idx1", columnList = "name, region, level"),
            @Index(name = "league_idx2", columnList = "name"),
            @Index(name = "league_idx3", columnList = "region"),
            @Index(name = "league_idx4", columnList = "region, level"),
            @Index(name = "league_idx5", columnList = "level") },
         uniqueConstraints = { @UniqueConstraint(name = "league_uk1", columnNames = { "name",
            "region", "level" }) })
public class LeagueEntry implements Serializable {
   private static final long serialVersionUID = -5663763326055057539L;

   @Id
   @Column(length = 36)
   private String id;

   @Column(length = 50, nullable = false)
   private String name;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING, nullable = false)
   private Region region;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING, nullable = false)
   private Level level;

   @ManyToOne
   @JoinColumn(name = "parent_id")
   private LeagueEntry parent;

   @Column(columnDefinition = "text")
   private String description;

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   @OneToMany(mappedBy = "parent")
   private Set<LeagueEntry> children = new HashSet<>();

   @OneToMany(mappedBy = "league")
   private final Set<TeamAffiliation> affiliatedTeams = new HashSet<>();

   /*----------- Methods ----------*/

   public String getQualifiedName() {
      final StringBuilder sb = new StringBuilder();
      boolean concat = false;

      while (parent != null) {
         if (concat) {
            sb.append(".");
         }
         sb.append(parent.getQualifiedName());
         if (sb.length() > 0) {
            concat = true;
         }
      }
      if (concat) {
         sb.append(".");
      }
      sb.append(name);
      return sb.toString();
   }

   /*----------- Getter/Setters ----------*/

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Region getRegion() {
      return region;
   }

   public void setRegion(Region region) {
      this.region = region;
   }

   public Level getLevel() {
      return level;
   }

   public void setLevel(Level level) {
      this.level = level;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public LeagueEntry getParent() {
      return parent;
   }

   public void setParent(LeagueEntry parent) {
      this.parent = parent;
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

   public Set<LeagueEntry> getChildren() {
      return children;
   }

   public void setChildren(Set<LeagueEntry> children) {
      this.children = children;
   }

   public boolean addChild(LeagueEntry child) {
      child.setParent(this);
      return children.add(child);
   }

   public Set<TeamAffiliation> getAffiliatedTeams() {
      return affiliatedTeams;
   }
}
