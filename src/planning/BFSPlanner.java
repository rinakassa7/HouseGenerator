package planning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import representation.Variable;


public class BFSPlanner implements Planner {

	private Map<Variable, Object> etatInitial;
	private Set<Action> ensAction;
	private Goal goal;
	
	/**
	 * 
	 * @param etatInitial
	 * @param ensAction
	 * @param goal
	 */
	public BFSPlanner(Map<Variable, Object> etatInitial, Set<Action> ensAction, Goal goal) {
		this.etatInitial = etatInitial;
		this.ensAction = ensAction;
		this.goal = goal;
	}

	@Override
	public List<Action> plan() {
		Map<Map<Variable,Object>,Map<Variable,Object>> father = new HashMap<Map<Variable,Object>,Map<Variable,Object>>();
		Map<Map<Variable,Object>,Action> plan = new HashMap<Map<Variable,Object>,Action>();
		HashSet<Map<Variable, Object>> closed = new HashSet<Map<Variable, Object>>();
		LinkedList<Map<Variable,Object>> open = new LinkedList<Map<Variable,Object>>();
		Map<Variable,Object> instanciation = new HashMap<Variable,Object>();
		Map<Variable, Object> next = new HashMap<Variable, Object>();

		closed.add(this.etatInitial);
		open.add(this.etatInitial);
		
		father.put(this.etatInitial, null);
		
		if(this.goal.isSatisfiedBy(etatInitial))
			/* Plan vide si l'état initial satisfait le but */
			return new LinkedList<Action>();
		
		while(!open.isEmpty()) {
			instanciation = open.poll();
			closed.add(instanciation);
			
			for (Action action : this.ensAction ) {
				if(action.isApplicable(instanciation)) {
					 next = action.successor(instanciation);					
					  if (!closed.contains(next) && !open.contains(next)) { 
							 father.put(next, instanciation);
							 plan.put(next, action);
							 if (this.goal.isSatisfiedBy(next)) {
								 	/* solution trouvé */
								 	return get_bfs_plan(father,plan,next);
							 }else {
								 open.offer(next);
							 }
						 }
					}
				}
			}
		/* solution non trouvé */
		return null;
	}
	/**
	 * permet de retourner le plan trouvé 
	 * @param father
	 * @param plan
	 * @param goal
	 * @return
	 */
	public List<Action> get_bfs_plan(Map<Map<Variable,Object>,Map<Variable,Object>> father, Map<Map<Variable,Object>,Action> plan,Map<Variable,Object> goal) {
		List<Action> bfs_plan = new LinkedList<Action>();
		while (goal != this.etatInitial){
				bfs_plan.add(plan.get(goal));
				goal = father.get(goal);
			
		}
		Collections.reverse(bfs_plan);
		return bfs_plan;
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
