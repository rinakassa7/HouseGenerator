package solvers;

import java.util.Map;
import java.util.Set;
import representation.Constraint;
import representation.Variable;

public abstract class AbstractSolver implements Solver {

	public Set<Variable> variables;
	public Set<Constraint> contraintes;

	/**
	 * 
	 * @param variables
	 * @param contraintes
	 */
	 public AbstractSolver(Set<Variable> variables, Set<Constraint> contraintes) {
		this.variables = variables;
		this.contraintes = contraintes;
	}

	/**
	 * verifier pour chaque contrainte que le set de la map contient tout les element de scope
	 * @param instance
	 * @return
	 */
	 public boolean isConsistent(Map<Variable, Object> instance) {

	        for (Constraint c : this.contraintes) {
	            if (instance.keySet().containsAll(c.getScope())) {
	            	if(!c.isSatisfiedBy(instance)) return false;
	            }
	        }
	       return true;
	  }


	@Override
	public String toString() {
		return "AbstractSolver [variables=" + variables + ", contraintes=" + contraintes + "]";
	}
	 
	 
}
