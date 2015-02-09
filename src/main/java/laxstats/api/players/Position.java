package laxstats.api.players;

public enum Position {
	ATTACK("Attack"), MIDFIELD("Midfield"), LSM("Long-Stick Midfield"), DEFENSE(
			"Defense"), GOALIE("Goalie"), REFEREE("Referree"), UMPIRE("Umpire"), FIELD_JUDGE(
			"Field Judge"), SCOREKEEPER("Scorekeeper"), TIMEKEEPER("Timekeeper"), COACH(
			"Coach"), ASST_COACH("Assistant Coach"), MANAGER("Manager");

	private String label;

	private Position(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
