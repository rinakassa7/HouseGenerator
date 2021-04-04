package planning;

import java.util.Map;

import representation.Variable;

/**
 * La classe qui représente un but 
 *
 */
public class BasicGoal implements Goal{
	private Map<Variable, Object> etat;
	
	/**
	 * 
	 * @param etat
	 */
	public BasicGoal(Map<Variable, Object> etat) {
		this.etat = etat;
	}
	
	@Override
	public boolean isSatisfiedBy(Map<Variable, Object> map) {
		return map.entrySet().containsAll(etat.entrySet());
	}

	@Override
	public String toString() {
		return "Goal [etat=" + etat + "]";
	}
	
	
}
