package solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import representation.Constraint;
import representation.Variable;

public class NbConstraintsVariableHeuristic implements VariableHeuristic {

	private Set<Variable> ensVariable;
	private Set<Constraint> ensContraintes;
	private boolean most;

	/**
	 * 
	 * @param ensVariable
	 * @param ensContraintes
	 * @param most
	 */
	public NbConstraintsVariableHeuristic(Set<Variable> ensVariable, Set<Constraint> ensContraintes, boolean most) {
		this.ensVariable = ensVariable;
		this.ensContraintes = ensContraintes;
		this.most = most;
		// most prend true si les variables apparaissant dans le plus de contraintes
		// false sinon
	}

	// best retourne la meilleure variable au sens de l'heuristic
	/**
	 * on parcours toute les variables au meme temps toutes les contraintes et on
	 * calcule le nombre d'apparition de chaque variable et on retourne la meilleure
	 */
	@Override
	public Variable best(Set<Variable> ensVariable, Map<Variable, Set<Object>> ensDomaine) {
		int compteur = 0;
		Map<Variable, Integer> map = new HashMap<Variable, Integer>();
		
		for (Variable variable : ensVariable) {
			map.put(variable, 0);
		}
		
		for (Constraint contrainte : this.ensContraintes) {	
			for (Variable variable : contrainte.getScope()) {
				if (ensVariable.contains(variable) ){
					map.put(variable, map.get(variable) + 1);
				}
			}
		}
		
		if (this.most == true) {
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
