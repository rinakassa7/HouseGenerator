package representation;

import java.util.*;

/**
 * la classe Rule représente les contraintes de la forme l1 implique l2 ou l1 
 * est soit une variable booléenne, soit la négation d'une variable booléenne, 
 * et de même pour l2
 */
public class Rule implements Constraint {
	private boolean test1, test2;
	private BooleanVariable l1, l2;
	private Set<Variable> scope = new HashSet<>();

	/**
	 * constructeur de la classe Rule
	 * @param l1
	 * @param test1
	 * @param l2
	 * @param test2
	 */
	public Rule(BooleanVariable l1, boolean test1, BooleanVariable l2, boolean test2) {
		this.l1 = l1;
		this.l2 = l2;
		this.test1 = test1;
		this.test2 = test2;
		scope.add(l1);
		scope.add(l2);
	}
	
	/**
	 * getScope retourne l'ensemble des variables sur lesquelles porte la contrainte
	 */
	
	@Override
	public Set<Variable> getScope() {
		return this.scope;
	}

	
	/* permet de savoir si l'instanciation donnée satisfait les contraintes 
	 a implique b equivalent à !a ou b  */
	@Override
	public boolean isSatisfiedBy(Map<Variable, Object> map) {
		
		if (!map.containsKey(l1) || !map.containsKey(l2)) {
			throw new IllegalArgumentException();
		}else {
		return (!map.get(l1).equals(test1) || map.get(l2).equals(test2));
		}
	}

	@Override
	public String toString() {
		return "Rule [l1=" + l1 + ", test1=" + test1 + ", l2=" + l2 + ", test2=" + test2 + "]";
	}
	
	
}
