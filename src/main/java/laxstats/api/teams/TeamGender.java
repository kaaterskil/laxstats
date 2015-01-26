package laxstats.api.teams;

public enum TeamGender {
	MEN("Men"), WOMEN("Women"), BOYS("Boys"), GIRLS("Girls");
	private String label;

	private TeamGender(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
