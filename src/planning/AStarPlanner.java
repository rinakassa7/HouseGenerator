package planning;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import representation.Variable;

/**
 * la Classe Astar permet de trouver le chemin le plus court dans un arbre 
 * @author
 *
 */
public class AStarPlanner implements Planner {
	
	private Map<Variable, Object> etatInitial;
	private Set<Action> ensAction;
	private Goal goal;
	private Heuristic heuristic;
	
	/**
	 * 
	 * @param etatInitial
	 * @param ensAction
	 * @param goal
	 * @param heuristic
	 */
	public AStarPlanner(Map<Variable, Object> etatInitial, Set<Action> ensAction,Goal goal, Heuristic heuristic) {
		this.etatInitial = etatInitial;
		this.ensAction = ensAction;
		this.goal = goal;
		this.heuristic = heuristic;
	}

	@Override
	public List<Action> plan() {
		/* Initialisation */
		Map<Map<Variable, Object>, Action> plan = new HashMap<Map<Variable, Object>, Action>();
		Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<Map<Variable, Object>, Map<Variable, Object>>();
		Map<Map<Variable, Object>, Integer> distance = new HashMap<Map<Variable, Object>, Integer>();
		Map<Map<Variable, Object>, Integer> value = new HashMap<Map<Variable, Object>, Integer>();
		List<Map<Variable, Object>> open = new LinkedList<Map<Variable, Object>>();
		Map<Variable, Object> instanciation = new HashMap<Variable, Object>();
		Comparator<Map<Variable, Object>> comparator = (state1, state2) -> value.get(state1) - value.get(state2);
		Map<Variable, Object> next = new HashMap<Variable, Object>();
		HeuristicNull heurist = new HeuristicNull();
		
		open.add(etatInitial);
		father.put(etatInitial, null);
		distance.put(etatInitial, 0);
		value.put(this.etatInitial, heurist.estimate(this.etatInitial));
		
		while (!open.isEmpty()) {
			instanciation = Collections.min(open, comparator);
			if (this.goal.isSatisfiedBy(instanciation)) {
				/* solution trouvé */
				return get_bfs_plan(father,plan,instanciation);
			}else {
				open.remove(instanciation);
				for (Action action : this.ensAction) {
					if (action.isApplicable(instanciation)) {
						next = action.successor(instanciation);
						if (!distance.containsKey(next)) {
							distance.put(next, Integer.MAX_VALUE);
						}
						if (distance.get(next) > distance.get(instanciation) + action.getCost()) {
							distance.put(next, distance.get(instanciation) + action.getCost());
							father.put(next, instanciation);
							plan.put(next, action);
							value.put(next, distance.get(next) + heurist.estimate(next));
							open.add(next);
						}
					}
				}
			}
		}
		/* solution non trouvé */
		return null;
	}
	/**
	 * permet de retourner le plan trouver par la méthode plan()
	 * @param father
	 * @param plan
	 * @param etat
	 * @return
	 */
	public List<Action> get_bfs_plan(Map<Map<Variable,Object>,Map<Variable,Object>> father, Map<Map<Variable,Object>,Action> plan,Map<Variable, Object> etat) {
		List<Action> bfs_plan = new LinkedList<Action>();
		while (etat != this.etatInitial){
				bfs_plan.add(plan.get(etat));
				etat = father.get(etat);
			}
		Collections.reverse(bfs_plan);
		/* retourner le plan */
		return bfs_plan;
	}
	
	@Override
	public Map<Variable, Object> getInitialState() {
		return this.etatInitial;
	}

	@Override
	public Set<Action> getActions() {
		return this.ensAction;
	}

	@Override
	public Goal getGoal() {
		return this.goal;
	}

	

}
