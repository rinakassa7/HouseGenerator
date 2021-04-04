package datamining;

import java.util.*;

import representation.BooleanVariable;

public class Apriori extends AbstractItemsetMiner {

	/**
	 * Constructeur de la classe Apriori
	 * @param base
	 */
	public Apriori(BooleanDatabase base) {
		super(base);

	}

	/**
	 * la méthode frequentSingletons permet de retourner l'ensemble d'itemset singleton dont
	 *  la fréquence supérieur ou égale à la fréquence min donnée en parametre 
	 * @param frequenceMin
	 * @return
	 */
	public Set<Itemset> frequentSingletons(float frequenceMin) {
		/* Initialisation d'ensemble d'items */
		Set<Itemset> ensItems = new HashSet<>();
		for (BooleanVariable value : this.getDatabase().getItems()) { 
			SortedSet<BooleanVariable> ensTrie = new TreeSet<BooleanVariable>(super.COMPARATOR);
			ensTrie.add(value); 
			float frequece_items = frequency(ensTrie); 
			if (frequece_items >= frequenceMin) {
				ensItems.add(new Itemset(ensTrie, frequece_items)); 
			}
		}
		return ensItems;
	}

	/**
	 * la méthode combine permet de combiner (union) deux ensemble de meme taille y ont k-1 élément commun, dernier élément différent 
	 * @param itemsTrie1
	 * @param itemsTrie2
	 * @return
	 */
	public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> itemsTrie1,
			SortedSet<BooleanVariable> itemsTrie2) {
		SortedSet<BooleanVariable> combinaison = new TreeSet<>(COMPARATOR);

		if (itemsTrie1.size() > 0 && itemsTrie1.size() == itemsTrie2.size()
				&& !itemsTrie1.last().equals(itemsTrie2.last())
				&& itemsTrie1.subSet(itemsTrie1.iterator().next(), itemsTrie1.last())
						.equals(itemsTrie2.subSet(itemsTrie2.iterator().next(), itemsTrie2.last()))) {

			combinaison.addAll(itemsTrie2);
			combinaison.addAll(itemsTrie1);
			return combinaison;
		}

		return null;

	}

	/**
	 * la méthode allSubsetsFrequent permet de vérifier si en retirant un élément d'un item, l'item appartient toujours à la collection.
	 * @param ensItems
	 * @param collection
	 * @return
	 */
	public static boolean allSubsetsFrequent(Set<BooleanVariable> ensItems,
			Collection<SortedSet<BooleanVariable>> collection) {

		boolean contient = true;
		Set<BooleanVariable> item = new HashSet<>(ensItems);
		for (BooleanVariable variable : ensItems) {
			item.remove(variable);
			contient = contient && collection.contains(item);
			item.add(variable);
		}

		return contient;
	}

	@Override
	public Set<Itemset> extract(float frequenceMin) {
		/* Initialisation des */
		Set<Itemset> resultat = new HashSet<>();
		Set<Itemset> singleton = new HashSet<>();
		singleton = frequentSingletons(frequenceMin); // l'ensemble des frequent itemset singleton
		List<SortedSet<BooleanVariable>> listItemsFreq = new ArrayList<>();

		for (Itemset itemset : singleton) {
			SortedSet<BooleanVariable> items = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
			items.addAll(itemset.getItems());
			listItemsFreq.add(items);
			resultat.add(itemset); // ajouter les singleton au resultat
		}
		
		for (int k = 2; k <= super.getDatabase().getItems().size(); k++) {
			List<SortedSet<BooleanVariable>> listItemsFreq2 = new ArrayList<>();

			for (int i = 0; i < listItemsFreq.size(); i++) {
				SortedSet<BooleanVariable> itemsI = listItemsFreq.get(i);
				for (int j = i + 1; j < listItemsFreq.size(); j++) {
					SortedSet<BooleanVariable> itemsJ = listItemsFreq.get(j);
					SortedSet<BooleanVariable> itemsCombine = combine(itemsI, itemsJ);

					if (itemsCombine != null && allSubsetsFrequent(itemsCombine, listItemsFreq)) {
						float frequence = frequency(itemsCombine);
						if (frequence >= frequenceMin) {
							resultat.add(new Itemset(itemsCombine, frequence));
							listItemsFreq2.add(itemsCombine);
						}
					}
				}
			}
			listItemsFreq = listItemsFreq2;
		}

		return resultat;

	}
}