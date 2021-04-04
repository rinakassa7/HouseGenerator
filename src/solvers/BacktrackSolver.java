package solvers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import representation.Constraint;
import representation.Variable;

public class BacktrackSolver extends AbstractSolver {

	/**
	 * 
	 * @param variables
	 * @param contraintes
	 */
	public BacktrackSolver(Set<Variable> variables, Set<Constraint> contraintes) {
		super(variables, contraintes);
	}
	
	/**
	 * retourne une solution qui satisfait toutes les contraintes
	 * @param instance
	 * @param listVar
	 * @return
	 */
	public Map<Variable, Object> sra(Map<Variable, Object> instance, LinkedList<Variable> listVar) {

		if(listVar.isEmpty()) {
			/* solution trouvé*/
			return instance;
			
		}else {
			Variable variable =  listVar.poll();
				for (Object domaine : variable.getDomain()) {
					instance.put(variable, domaine);
					if (super.isConsistent(instance)) {
						if(this.sra(instance, listVar) != null) return this.sra(instance, listVar);
					}
				instance.remove(variable, domaine);		
			}
				listVar.add(variable);
		}
		/* solution non trouvé */
		return null;
}

	@Override
	public Map<Variable, Object> solve() {
		Map<Variable, Object> instanceP = new HashMap<Variable, Object>();
		LinkedList<Variable> listVar = new LinkedList<Variable>();
		
		for (Variable variable : this.variables) {
			listVar.add(variable);
		}
		return sra(instanceP, listVar);
	}
	
	
}
