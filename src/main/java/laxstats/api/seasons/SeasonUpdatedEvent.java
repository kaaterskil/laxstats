package laxstats.api.seasons;

public class SeasonUpdatedEvent {
    private SeasonId seasonId;
    private SeasonDTO seasonDTO;

    public SeasonUpdatedEvent(SeasonId seasonId, SeasonDTO seasonDTO) {
        this.seasonId = seasonId;
        this.seasonDTO = seasonDTO;
    }

    public SeasonId getSeasonId() {
        return seasonId;
    }

    public SeasonDTO getSeasonDTO() {
        return seasonDTO;
    }
}
