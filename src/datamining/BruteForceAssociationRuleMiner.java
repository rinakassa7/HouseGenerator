package datamining;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dataminingtests.BruteForceAssociationRuleMinerTests;
import representation.BooleanVariable;

public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

	/**
	 * constructeur de la classe BruteForceAssociationRuleMiner qui prend en
	 * argument une base booléenne
	 * 
	 * @param baseDeBruteForce
	 */
	public BruteForceAssociationRuleMiner(BooleanDatabase baseDeBruteForce) {
		super(baseDeBruteForce);

	}

	/**
	 * la méthode allCandidatePremises retourne l'ensemble de tous ses
	 * sous-ensembles, à l'exception de l'ensemble vide et de l'ensemble lui-même
	 * (ensemble de partition)
	 * 
	 * @param myset
	 * @return
	 */
	public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> myset) {
		Set<Set<BooleanVariable>> resultat = new HashSet(subSet(myset));
		resultat.remove(myset);
		resultat.remove(Collections.EMPTY_SET);

		return resultat;
	}

	/**
	 * la méthode subSet retourne l'ensemble de tous les sous ensembles de myset
	 * 
	 * @param myset
	 * @return
	 */
	public static Set<Set<BooleanVariable>> subSet(Set<BooleanVariable> myset) {
		Set<Set<BooleanVariable>> allPartition = new HashSet<>();

		if (myset.isEmpty()) {
			allPartition.add(new HashSet<BooleanVariable>());
			return allPartition;
		}
		List<BooleanVariable> list = new ArrayList<BooleanVariable>(myset);
		BooleanVariable first = list.get(0);
		Set<BooleanVariable> rest = new HashSet<BooleanVariable>(list.subList(1, list.size()));
		for (Set<BooleanVariable> set : subSet(rest)) {
			Set<BooleanVariable> newSet = new HashSet<BooleanVariable>();
			newSet.add(first);
			newSet.addAll(set);
			allPartition.add(newSet);
			allPartition.add(set);
		}

		return allPartition;
	}

	@Override
	public Set<AssociationRule> extract(float frequence_min, float confiance_min) {

		Set<AssociationRule> regles = new HashSet<>();
		ItemsetMiner apriori = new Apriori(this.getDatabase());
		Set<Itemset> items_freq = apriori.extract(frequence_min);

		for (Itemset value_items : items_freq) {
			Set<Set<BooleanVariable>> premises = BruteForceAssociationRuleMiner
					.allCandidatePremises(value_items.getItems());
			float frequenceItem = value_items.getFrequency();
			for (Set<BooleanVariable> value_premise : premises) {
				Set<BooleanVariable> conclusion = new HashSet<>(value_items.getItems());
				conclusion.removeAll(value_premise);

				float confiance = AbstractAssociationRuleMiner.confidence(value_premise, conclusion, items_freq);
				float frequence = AbstractAssociationRuleMiner.frequency(value_premise, items_freq);

				if (confiance >= confiance_min) {
					regles.add(new AssociationRule(value_premise, conclusion, frequenceItem, confiance));
				}
			}
		}
		return regles;

	}

	@Override
	public BooleanDatabase getDatabase() {
		return this.base;
	}

}