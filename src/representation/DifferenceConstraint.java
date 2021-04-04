package representation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * la classe DifferenceConstraint représente les contraintes de la forme v1 != v2
 *
 */
public class DifferenceConstraint implements Constraint {

	private Variable var1;
	private Variable var2;
	private Set<Variable> scope = new HashSet<>();

	/**
	 * constructeur de DifferenceConstraint
	 * @param var1
	 * @param var2
	 */
	public DifferenceConstraint(Variable var1, Variable var2) {
		this.var1 = var1;
		this.var2 = var2;
		scope.add(var1);
		scope.add(var2);
	}

	@Override
	public Set<Variable> getScope() {
		return scope;
	}

	/**
	 * permet de savoir si l'instanciation donnée satisfait les contraintes 
	 * de la forme v1 != v2
	 */
	@Override
	public boolean isSatisfiedBy(Map<Variable, Object> instance) {
		if (!instance.containsKey(var1) || !instance.containsKey(var2)) 
			throw new IllegalArgumentException();
		return !instance.get(var1).equals(instance.get(var2));
		}

	@Override
	public String toString() {
		return var1.getName() + " !=  " + var2.getName() ;
	}
	
	

}
