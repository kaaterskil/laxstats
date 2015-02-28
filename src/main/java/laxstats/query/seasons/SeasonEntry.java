package laxstats.query.seasons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.api.Common;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "seasons", indexes = {
		@Index(name = "season_idx1", columnList = "startsOn"),
		@Index(name = "season_idx2", columnList = "endsOn") }, uniqueConstraints = { @UniqueConstraint(name = "season_uk1", columnNames = {
		"startsOn", "endsOn" }) })
public class SeasonEntry {

	@Id
	@Column(length = 36)
	private String id;

	@Column(length = 100, nullable = false)
	private String description;

	@Column(nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate startsOn;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate endsOn;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne
	private UserEntry createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne
	private UserEntry modifiedBy;

	/*---------- Methods ----------*/

	public Interval getInterval() {
		final DateTime start = getStartsOn().toDateTimeAtStartOfDay();
		DateTime end;
		try {
			end = getEndsOn().toDateTimeAtStartOfDay();
		} catch (final Exception e) {
			end = new DateTime(Long.MAX_VALUE);
		}
		return new Interval(start, end);
	}

	public boolean overlaps(SeasonEntry target) {
		if (target != null) {
			return overlaps(target.getStartsOn(), target.getEndsOn());
		}
		return false;
	}

	public boolean overlaps(Interval interval) {
		if (interval != null) {
			final LocalDate otherStart = interval.getStart().toLocalDate();
			LocalDate otherEnd;
			try {
				otherEnd = interval.getEnd().toLocalDate();
			} catch (final Exception e) {
				otherEnd = new LocalDate(Long.MAX_VALUE);
			}
			return overlaps(otherStart, otherEnd);
		}
		return false;
	}

	public boolean overlaps(LocalDate otherStart, LocalDate otherEnd) {
		final LocalDate thisStart = getStartsOn();
		final LocalDate thisEnd = Common.nvl(endsOn, Common.EOT.toLocalDate());
		otherEnd = Common.nvl(otherEnd, Common.EOT.toLocalDate());

		return (thisStart.isEqual(otherEnd) || thisStart.isBefore(otherEnd))
				&& (otherStart.isEqual(thisEnd) || otherStart.isBefore(thisEnd));
	}

	/*---------- Getter/Setters ----------*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		if (endsOn == null) {
			endsOn = new LocalDate(Long.MAX_VALUE);
		}
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
