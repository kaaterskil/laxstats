package laxstats.api.seasons;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class DeleteSeasonCommand {

    @TargetAggregateIdentifier
    private SeasonId seasonId;

    public DeleteSeasonCommand(SeasonId seasonId) {
        this.seasonId = seasonId;
    }

    public SeasonId getSeasonId() {
        return seasonId;
    }
}
