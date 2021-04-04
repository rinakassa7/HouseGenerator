package datamining;

import java.util.Set;

public interface AssociationRuleMiner {
	
	/**
	 * la méthode getDatabase retourne une base booléenne
	 * @return
	 */
	public BooleanDatabase getDatabase();
	/**
	 * la méthode extract permet de récupérer  l'ensemble des règles d'association
	 *  de fréquence et confiance supérieures aux seuils donnés
	 * @param frequence_min
	 * @param confiance_min
	 * @return
	 */
	public Set<AssociationRule> extract(float frequence_min,float confiance_min);
	

}