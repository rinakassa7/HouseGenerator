package planning;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import representation.Variable;

public class DijkstraPlanner implements Planner {

	private Map<Variable, Object> etatInitial;
	private Set<Action> ensAction;
	private Goal goal;

	/**
	 * 
	 * @param etatInitial
	 * @param ensAction
	 * @param goal
	 */
	public DijkstraPlanner(Map<Variable, Object> etatInitial, Set<Action> ensAction, Goal goal) {
		this.etatInitial = etatInitial;
		this.ensAction = ensAction;
		this.goal = goal;
	}

	@Override
	public List<Action> plan() {
		List<Map<Variable, Object>> goals = new LinkedList<>();
		Map<Map<Variable, Object>, Action> plan = new HashMap<Map<Variable, Object>, Action>();
		Map<Map<Variable, Object>, Integer> distance = new HashMap<Map<Variable, Object>, Integer>();
		Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<Map<Variable, Object>, Map<Variable, Object>>();
		List<Map<Variable, Object>> open = new LinkedList<Map<Variable, Object>>();
		Map<Variable, Object> next = new HashMap<Variable, Object>();

			
		father.put(etatInitial, null);
		distance.put(etatInitial, 0);
		open.add(etatInitial);

		while (!open.isEmpty()) {
			Comparator<Map<Variable, Object>> comparator = (state1, state2) -> distance.get(state1) - distance.get(state2);
			Map<Variable, Object> instanciation = Collections.min(open, comparator);
			open.remove(instanciation);

			if (this.goal.isSatisfiedBy(instanciation)) {
				goals.add(instanciation);
			}		
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
						open.add(next);
					}
				}
			}
		}
		if (goals.isEmpty()) {
			/* solution non trouvé */
			return null;
		} else {
			/* solution trouvé */
			return get_djiskra_plan(father, plan, goals, distance);
		}
	}

	/**
	 * retourne le plan trouvé
	 * @param father
	 * @param plan
	 * @param goals
	 * @param distance
	 * @return
	 */
	public List<Action> get_djiskra_plan(Map<Map<Variable, Object>, Map<Variable, Object>> father,Map<Map<Variable, Object>, Action> plan, List<Map<Variable, Object>> goals,	Map<Map<Variable, Object>, Integer> distance) {
		Map<Variable, Object> goal = new HashMap<Variable, Object>();
		List<Action> dIJ_Plan = new LinkedList<Action>();
		Comparator<Map<Variable, Object>> comparator = (state1, state2) -> distance.get(state1) - distance.get(state2);
		goal = Collections.min(goals, comparator);
		while (goal != this.etatInitial) {
			dIJ_Plan.add(plan.get(goal));
			goal = father.get(goal);
		}
		Collections.reverse(dIJ_Plan);
		return dIJ_Plan;
	}

	@Override
	public Map<Variable, Object> getInitialState() {
		// TODO Auto-generated method stub
		return this.etatInitial;
	}

	@Override
	public Set<Action> getActions() {
		// TODO Auto-generated method stub
		return this.ensAction;
	}

	@Override
	public Goal getGoal() {
		// TODO Auto-generated method stub
		return this.goal;
	}

}
