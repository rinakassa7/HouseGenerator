package planning;

import java.util.Map;

import representation.Variable;

public class HeuristicNull implements Heuristic{

	
	@Override
	public int estimate(Map<Variable, Object> etat) {	
		return 0;
	}

}
