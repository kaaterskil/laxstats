package laxstats.api.seasons;

public class SeasonDeletedEvent {
    private final SeasonId seasonId;

    public SeasonDeletedEvent(SeasonId seasonId) {
        this.seasonId = seasonId;
    }

    public SeasonId getSeasonId() {
        return seasonId;
    }
}
