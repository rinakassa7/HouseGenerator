package solvers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import representation.Constraint;
import representation.Variable;

public class HeuristicMACSolver extends AbstractSolver {

	private VariableHeuristic variableHeuristic;
	private ValueHeuristic valueHeuristic;
	private ArcConsistency newArcConsistency;
	
	/**
	 * 
	 * @param variables
	 * @param contraintes
	 * @param variableHeuristic
	 * @param valueHeuristic
	 */
	public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> contraintes, VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic) {
		super(variables, contraintes);
		this.valueHeuristic = valueHeuristic;
		this.variableHeuristic = variableHeuristic;
		this.newArcConsistency = new ArcConsistency(this.contraintes);
	}

	@Override
	public Map<Variable, Object> solve() {
		Map<Variable, Set<Object>> allArcDomaine = new HashMap<Variable, Set<Object>>();
		Map<Variable, Object> instanceP = new HashMap<Variable, Object>();
		Set<Variable> listVar = new HashSet<Variable>();

		for (Variable variable : this.variables) {
			listVar.add(variable);
			allArcDomaine.put(variable, variable.getDomain());
		}
		return macHeuristic(instanceP, listVar ,allArcDomaine);	
	}
	/**
	 * 
	 * @param instance
	 * @param listVar
	 * @param allArcDomaine
	 * @return
	 */
	public  Map<Variable, Object> macHeuristic(Map<Variable, Object> instance,Set<Variable> listVar,Map<Variable, Set<Object>> allArcDomaine){
		if (listVar.isEmpty()) {
			return instance;
		} else {
			Map<Variable, Set<Object>> mapNouveauDomaine = new HashMap<Variable, Set<Object>>();
			for(Variable var : listVar) {
				for(Map.Entry<Variable, Set<Object>> entry : allArcDomaine.entrySet()) {
					if(entry.getKey().equals(var)) {
						mapNouveauDomaine.put(var,(new HashSet<>(entry.getValue())));
					}
				}
			}
			if (!newArcConsistency.enforceArcConsistency(mapNouveauDomaine))
				return null;
			//recuperer la variable avec la methode best 
			Variable variable = this.variableHeuristic.best(listVar, mapNouveauDomaine);

			List<Object> listOrdonner = this.valueHeuristic.ordering(variable,mapNouveauDomaine.get(variable));
			//créer une liste ordonner
				
			listVar.remove(variable);

					for (Object domaine : listOrdonner){
						instance.put(variable, domaine);

						if(super.isConsistent(instance)) {
								Set<Object> nouveauDomaine = new HashSet<Object>();
								nouveauDomaine.add(domaine);
								mapNouveauDomaine.put(variable, nouveauDomaine);
								
								Map<Variable, Object> newInstance = this.macHeuristic(instance, listVar, mapNouveauDomaine);
								if (newInstance!= null) return newInstance;
							}
						instance.remove(variable ,domaine);
						}
					listVar.add(variable);

					}
			return null;
			}	
	
	
}


