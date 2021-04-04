package solvers;

import java.util.*;
import java.util.Map.Entry;

import representation.*;

public class MACSolver extends AbstractSolver {

	/**
	 * 
	 * @param variables
	 * @param contraintes
	 */
	public MACSolver(Set<Variable> variables, Set<Constraint> contraintes) {
		super(variables, contraintes);

	}
	

	@Override
	public Map<Variable, Object> solve() {
		Map<Variable, Set<Object>> allArcDomaine = new HashMap<>();
		Map<Variable, Object> instanceP = new HashMap<>();
		LinkedList<Variable> listVar = new LinkedList<>();

		for (Variable variable : this.variables) {
			listVar.add(variable);
			allArcDomaine.put(variable, variable.getDomain());
		}
		return mac(instanceP, listVar, allArcDomaine);
	}

	/**
	 * 
	 * @param instance
	 * @param listVar
	 * @param allArcDomaine
	 * @return
	 */
	public Map<Variable, Object> mac(Map<Variable, Object> instance, LinkedList<Variable> listVar,
			Map<Variable, Set<Object>> allArcDomaine) {

		if (listVar.isEmpty()) {
			/* solution trouvé */
			return instance;

		} else {			
			ArcConsistency newArcConsistency = new ArcConsistency(this.contraintes);
			Map<Variable, Set<Object>> mapNouveauDomaine = new HashMap<Variable, Set<Object>>();// vide aucun domaine

			for (Variable variable : this.variables) {
				for (Entry<Variable, Set<Object>> object : allArcDomaine.entrySet()) {
					if (object.getKey().equals(variable)) {
						mapNouveauDomaine.put(variable, new HashSet<>(object.getValue()));
					}
				}
			} 

			if (!newArcConsistency.enforceArcConsistency(mapNouveauDomaine))
				return null;

			Variable variable = listVar.poll();

			for (Map.Entry<Variable, Set<Object>> entryDomaine : mapNouveauDomaine.entrySet()) {
				if (entryDomaine.getKey().equals(variable)) {
					for (Object domaine : entryDomaine.getValue()) {
						instance.put(variable, domaine);
						if (super.isConsistent(instance)) { /// si l'instance est consistante
							Set<Object> nouveauSet = new HashSet<Object>();
							nouveauSet.add(domaine);
							mapNouveauDomaine.put(variable, nouveauSet);

							if (this.mac(instance, listVar, mapNouveauDomaine) != null)
								return this.mac(instance, listVar, mapNouveauDomaine);
						}
						instance.remove(variable);
					}
				}
			}
			listVar.add(variable);

		}
		/* Solution non trouvé */
		return null;
	}

}