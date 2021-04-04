package solvers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import representation.Constraint;
import representation.Variable;

public class DomainSizeVariableHeuristic implements VariableHeuristic {

	private Set<Variable> ensVariable;
	private Set<Constraint> ensContraintes;
	private boolean greatest;

	/**
	 * 
	 * @param ensVariable
	 * @param ensContraintes
	 * @param greatest
	 */
	public DomainSizeVariableHeuristic(Set<Variable> ensVariable, Set<Constraint> ensContraintes, boolean greatest) {
		this.ensVariable = ensVariable;
		this.ensContraintes = ensContraintes;
		this.greatest = greatest;
	}

	// best retourne la meilleure variable au niveau de l'heuristic
	@Override
	public Variable best(Set<Variable> ensVariable, Map<Variable, Set<Object>> ensDomaine) {
		Map<Variable, Integer> map = new HashMap<Variable, Integer>();
		for (Variable variable : ensVariable) {
			if (ensDomaine.containsKey(variable)) {
				map.put(variable, variable.getDomain().size());
			}
		}
		if (this.greatest == true) {
			int maxValueInMap = (Collections.max(map.values()));
			for (Entry entry : map.entrySet()) {
				if ((int) entry.getValue() == maxValueInMap) {
					return (Variable) entry.getKey();
				}
			}
		} else {
			int minValueInMap = (Collections.min(map.values()));
			for (Entry entry : map.entrySet()) {
				if ((int) entry.getValue() == minValueInMap) {
					return (Variable) entry.getKey();
				}
			}
		}

		return null;
	}
}
