package planning;

import java.util.List;
import java.util.Map;
import java.util.Set;

import representation.Variable;

public interface Planner {
	
	/**
	 * récupère la liste des actions qui représente le plan 
	 * @return
	 */
	public List<Action> plan();
	/**
	 * 
	 * @return
	 */
	public Map<Variable, Object> getInitialState();
	/**
	 * 
	 * @return
	 */
	public Set<Action> getActions();
	/**
	 * 
	 * @return
	 */
	public Goal getGoal();
}
