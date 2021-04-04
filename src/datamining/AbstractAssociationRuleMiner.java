package datamining;

import java.util.*;

import representation.BooleanVariable;

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {
	
	public static Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());

	public BooleanDatabase base;

	/**
	 * Constructeur de la classe AbstractAssociationRuleMiner
	 * @param base
	 */
	public AbstractAssociationRuleMiner(BooleanDatabase base) {
		this.base = base;
	}

	/**
	 * la méthode frequency permet de récuperer la fréquence de l'item trouvé dans
	 * l'ensemble des itemset 
	 * @param item
	 * @param ensItemset
	 * @return
	 */
	public static float frequency(Set<BooleanVariable> item, Set<Itemset> ensItemset) {
		float frequence = 0;
		if (item == null) {
			throw new IllegalArgumentException();
		} else {
			for (Itemset itemset : ensItemset) {
				if (itemset.getItems().equals(item)) {
					frequence = itemset.getFrequency();
				}
			}
			return frequence;

		}

	}
	/**
	 * la méthode confidence permet de retourner la confiance de la règle d'association de la premiser et de la conclusion
	 * @param premisse
	 * @param conclusion
	 * @param itemset
	 * @return
	 */
	public static float confidence(Set<BooleanVariable> premisse, Set<BooleanVariable> conclusion,
			Set<Itemset> itemset) {
		float freqPremise = 0;
		float freqCombin = 0;

		Set<BooleanVariable> combinaison = new HashSet<>();
		//combiner la conclusion et la permise XY.
		combinaison.addAll(conclusion);
		combinaison.addAll(premisse);
		
		freqPremise = frequency(premisse, itemset);
		freqCombin = frequency(combinaison, itemset);

		return freqCombin / freqPremise;
	}

}