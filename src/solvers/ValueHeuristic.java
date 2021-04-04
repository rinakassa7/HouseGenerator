package solvers;

import java.util.List;
import java.util.Set;

import representation.Variable;

public interface ValueHeuristic {

	/**
	 * 
	 * @param variable
	 * @param domaine
	 * @return
	 */
	public List<Object> ordering(Variable variable,Set<Object> domaine);
}
