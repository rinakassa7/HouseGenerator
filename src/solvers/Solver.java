package solvers;

import java.util.Map;

import representation.Variable;

public interface Solver {
	/**
	 * permet de récupérer la solution trouvé 
	 * @return
	 */
	public Map<Variable,Object> solve();

}
