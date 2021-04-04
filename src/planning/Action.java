package planning;

import java.util.Map;
import representation.Variable;


public interface Action {

	/**
	 * vérifie si une action est applicable sur un etat
	 * @param etat
	 * @return
	 */
	public boolean isApplicable(Map<Variable, Object> etat);
	/**
	 * permet de récupérer l'état suivant de l'état courant 
	 * @param map
	 * @return
	 */
	public Map<Variable, Object> successor(Map<Variable, Object> map);
	/**
	 * récupère le cout d'une action
	 * @return
	 */
	public int getCost();
}
