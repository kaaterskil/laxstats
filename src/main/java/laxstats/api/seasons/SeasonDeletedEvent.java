package laxstats.api.seasons;

public class SeasonDeletedEvent {
    private SeasonId seasonId;

    public SeasonDeletedEvent(SeasonId seasonId) {
        this.seasonId = seasonId;
    }

    public SeasonId getSeasonId() {
        return seasonId;
    }
}
