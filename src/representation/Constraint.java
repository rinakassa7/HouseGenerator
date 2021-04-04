package representation;

import java.util.*;

public interface Constraint {
	/**
	 * la méthode getScope retourne l'ensemble des variables.
	 * @return
	 */
	public Set<Variable> getScope();
	/**
	 * permet de savoir si la contrainte est satisfaite par l'instanciation donnée ou non
	 * @param instance
	 * @return
	 */
	public boolean isSatisfiedBy(Map<Variable, Object> instance);
	
}
