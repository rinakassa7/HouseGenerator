package datamining;

import java.util.Set;

public interface ItemsetMiner {
	
	/**
	 * la methode getDatabase permet de récupérer la base booléenne 
	 * @return
	 */
	public BooleanDatabase getDatabase();
	/**
	 * la méthode extract permet de retournant l'ensemble des itemsets (non vides)
	 * ayant au moins cette fréquence dans la base 
	 * @param frequence
	 * @return
	 */
	public Set<Itemset> extract(float frequence);
}
