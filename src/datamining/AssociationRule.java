package datamining;

import java.util.Set;

public class AssociationRule<BooleanVariable> {
	private Set<BooleanVariable> permise;
	private Set<BooleanVariable> conclusion;
	private float frequence;
	private float confiance;

	/**
	 * Constructeur de la classe AssociationRule
	 * @param permise
	 * @param conclusion
	 * @param frequence
	 * @param confiance
	 */
	public AssociationRule(Set<BooleanVariable> permise, Set<BooleanVariable> conclusion, float frequence, float confiance) {
		this.permise = permise;
		this.conclusion = conclusion;
		this.frequence = frequence;
		this.confiance = confiance;
	}

	public Set<BooleanVariable> getPremise() {
		return this.permise;
	}

	public Set<BooleanVariable> getConclusion() {
		return this.conclusion;
	}

	public float getFrequency() {
		return this.frequence;
	}

	public float getConfidence() {
		return this.confiance;
	}


	@Override
	public String toString() {
		return "Rule [permise=" + permise + "    => conclusion=" + conclusion + ", frequence=" + frequence
				+ ", confiance=" + confiance + "]";
	}


}