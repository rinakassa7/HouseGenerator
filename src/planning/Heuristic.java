package planning;

import java.util.Map;
import representation.Variable;

public interface Heuristic {

	/**
	 * 
	 * @param etat
	 * @return
	 */
	public int estimate(Map<Variable, Object> etat);
}
