package laxstats.api.people;

public enum DominantHand {
	RIGHT("Right"), LEFT("Left"), AMBIDEXTROUS("Ambidextrous");
	private String label;

	private DominantHand(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
