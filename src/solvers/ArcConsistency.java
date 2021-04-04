package solvers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Iterator;

import representation.Constraint;
import representation.Variable;

public class ArcConsistency {

	private Set<Constraint> contraintes;

	/**
	 * 
	 * @param contraintes
	 */
	public ArcConsistency(Set<Constraint> contraintes) {
		this.contraintes = contraintes;
	}

	/**
	 * la méthode supprime, les valeurs du domaine de var1 qui ne sont pas arc-cohérentes avec la contrainte, 
	 * et retourne true si et seulement si le domaine a changé ;
	 * @param var1
	 * @param domaine1
	 * @param var2
	 * @param domaine2
	 * @param c
	 * @return
	 */
	public static boolean filter(Variable var1, Set<Object> domaine1, Variable var2, Set<Object> domaine2, Constraint c) {
		
		Set<Object> newSet = new HashSet<Object>();
		boolean test = false;
		for (Object o1 : domaine1) {
			Map<Variable, Object> newMap = new HashMap<Variable, Object>();
			newMap.put(var1, o1);
			boolean viable = false;
			for (Object o2 : domaine2) {
				newMap.put(var2, o2);
				//on teste si c'est arc-cohérent
				if (c.isSatisfiedBy(newMap)) {
					viable = true;
				}
			}
			if(!viable) { 
				newSet.add(o1);
				test = true;
			}
		}
		if(test) domaine1.removeAll(newSet);
		return !newSet.isEmpty();
		// true si un domaine a changé
	}
	
	/**
	 *  la méthode filtre les domaines des variables concernées par la contrainte 
	 *  et retourner true si et seulement si au moins l'un des domaines a changé
	 * @param c
	 * @param ensDomaine
	 * @return
	 */
	public static boolean enforce(Constraint c, Map<Variable, Set<Object>> ensDomaine) {
		Iterator<Variable> iterator = c.getScope().iterator();
		Variable var1 = null, var2 = null;
		boolean hasChanged = true;
		
		if (iterator.hasNext()) {
			var1 = iterator.next();
			if (iterator.hasNext()) {
				var2 = iterator.next();
			}
		}
		if (var1 == null || var2 == null) {
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		if (ensDomaine.containsKey(var1) && ensDomaine.containsKey(var2)) {
			boolean changedOnce = false;
			while(hasChanged) {
				hasChanged = false;
				if((filter(var1, ensDomaine.get(var1), var2, ensDomaine.get(var2), c)
					|| filter(var2, ensDomaine.get(var2), var1, ensDomaine.get(var1), c))) {
					hasChanged = true;
					changedOnce = true;
				}
			}
			return changedOnce;
		}
		return false;
	}
	
	/**
	 *  retourne true si et seulement si tous les domaines sont non vides
	 *   à la fin du traitement
	 * @param allArcDomaine
	 * @return
	 */
	public boolean enforceArcConsistency(Map<Variable,Set<Object>> allArcDomaine) {
		boolean asChange = true;
		while(asChange) {
			asChange = false;
			for(Constraint c : this.contraintes) {
				if(enforce(c, allArcDomaine)) {
					asChange = true;
				}
			}
		}
		for(Set<Object> domaines :allArcDomaine.values()) {
			if (domaines.isEmpty()) return false;
		}
		return true;
	}
	

}