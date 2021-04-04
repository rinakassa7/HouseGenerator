package planning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import representation.Variable;

public class DFSPlanner implements Planner {

	private Map<Variable, Object> etatInitial;
	private Set<Action> ensAction;
	private Goal goal;

	/**
	 * 
	 * @param etatInitial
	 * @param ensAction
	 * @param goal
	 */
	public DFSPlanner(Map<Variable, Object> etatInitial, Set<Action> ensAction, Goal goal) {
		this.etatInitial = etatInitial;
		this.ensAction = ensAction;
		this.goal = goal;
	}

	@Override
	public List<Action> plan() {
		List<Action> plan = new ArrayList<Action>();
		HashSet<Map<Variable, Object>> closed = new HashSet<Map<Variable, Object>>();
		
		return dfs(this.etatInitial, plan, closed);
	}

	/**
	 * @param etat
	 * @param plan
	 * @param closed
	 * @return
	 */
	public List<Action> dfs(Map<Variable, Object> etat, List<Action> plan, Set<Map<Variable, Object>> closed) {
		List<Action> subPlan = new ArrayList<Action>();
		Map<Variable, Object> next = new HashMap<Variable, Object>();
		
		if (this.getGoal().isSatisfiedBy(etat)) {
			/* plan trouvé */
			return plan;
		}else {
			for (Action action : this.ensAction) {
				if (action.isApplicable(etat)) {
					 next = action.successor(etat);
					if (!contient(closed,next)) {
						plan.add(action);
						closed.add(next);

						subPlan = dfs(next, plan, closed);
						if (subPlan != null) {
							return subPlan;
						} else {
							plan.remove(action);
						}
					}
				}
			}
		}
		/* plan non trouvé */
		return null;
	}

	/**
	 * contient permet de chercher si un état est dans une liste des fermées
	 * @param closed
	 * @param etat2
	 * @return
	 */
	public boolean contient(Set<Map<Variable, Object>> closed, Map<Variable, Object> etat2) {
		for (Map<Variable, Object> closedEtat : closed) {
			if (identique(closedEtat, etat2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * la methode identique vérifie si deux états sont identiques
	 * @param etat1
	 * @param etat2
	 * @return
	 */
	public boolean identique(Map<Variable, Object> etat1, Map<Variable, Object> etat2) {
		boolean identique = false;

		if (etat1.size() != etat2.size())
			return false;
		
		for (Entry<Variable, Object> entry : etat1.entrySet()) {
			for (Entry<Variable, Object> en : etat2.entrySet()) {
				
				if (entry.getKey().getName().equals(en.getKey().getName())) {
					
					if (entry.getValue() == null && en.getValue() == null) {
						identique = true;
					} else if (entry.getValue() == null && en.getValue() != null) {
						continue;
					} else if (en.getValue() == null && entry.getValue() != null) {
						continue;
					} else if (entry.getValue().equals(en.getValue()))
						identique = true;
				}
			}
			
			if (!identique) {
				return false;
			} else {
				identique = false;
			}
		}
		return true;
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
