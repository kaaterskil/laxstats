package laxstats.api.events;

/**
 * {@code PlayKey} enumerates the major statistical events in a game. Scoring events are defined as
 * a {@code Goal}; individual events are defined as a {@code Play}, and team events include a
 * {@code Clear}.
 */
public enum PlayKey {
   PLAY,
   GOAL,
   CLEAR
}
