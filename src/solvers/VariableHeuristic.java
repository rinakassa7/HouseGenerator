package solvers;

import java.util.Map;
import java.util.Set;

import representation.Variable;

public interface VariableHeuristic {

	/**
	 * best retourne la meilleure variable au niveau de l'heuristic
	 * @param ensVariable
	 * @param ensDomaine
	 * @return
	 */
	public Variable best(Set<Variable> ensVariable,Map<Variable, Set<Object>> ensDomaine);
}
