package datamining;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import representation.BooleanVariable;
import representation.Variable;

public class Database {

	private Set<Variable> variables;
	private List<Map<Variable, Object>> listInstance;

	/**
	 * constructeur de la classe Database 
	 * @param variables
	 */
	public Database(Set<Variable> variables) {
		this.variables = variables;
		this.listInstance = new LinkedList<Map<Variable, Object>>();
	}
	/**
	 * Methode add permet d'ajouter des instance a notre liste 
	 * @param instance
	 */
	public void add(Map<Variable, Object> instance) {
		this.listInstance.add(instance);
	}

	public Set<Variable> getVariables() {
		return this.variables;
	}
	public List<Map<Variable, Object>> getInstances() {
		return this.listInstance;
	}
	
	/**
	 * la méthode itemTable permet de retourner l'item codant les affectations x = v et null si x = false
	 * @return
	 */

	public Map<Variable, Map<Object, BooleanVariable>> itemTable() {
		Map<Variable, Map<Object, BooleanVariable>> itemCode = new HashMap<Variable, Map<Object, BooleanVariable>>();

		for (Variable variable : variables) {

			if (variable instanceof BooleanVariable) {
				Map<Object, BooleanVariable> mapBooleanVariable = new HashMap<Object, BooleanVariable>();
				/* affecter la valeur var à true et false à null */
				mapBooleanVariable.put(true, new BooleanVariable (variable.getName()));
				mapBooleanVariable.put(false, null);
				/* item codant les affectation*/
				itemCode.put(variable, mapBooleanVariable);
				
			} else {
				Map<Object, BooleanVariable> mapVariable = new HashMap<Object, BooleanVariable>();
				for (Object x : variable.getDomain()) {
					BooleanVariable variableBooleene = new BooleanVariable(variable.getName() + x.toString());
					mapVariable.put(x, variableBooleene);
				}
				itemCode.put(variable, mapVariable);
			}
		}
		return itemCode;
	}
	
	/**
	 * la méthode propositionalize permet de reformuler la database en une base transactionnelle  
	 * @return
	 */
	public BooleanDatabase propositionalize() {
		/* Initialisation */
		List<Set<BooleanVariable>> list = new LinkedList<>();
		Set<BooleanVariable> items = new HashSet<BooleanVariable>();
		/* stocker le resultat de itemTable dans une map */
		Map<Variable, Map<Object, BooleanVariable>> itemtable = itemTable();

		for (Map<Variable, Object> instance : this.getInstances()) {
			/* intancier un item pour chaque instance */
			Set<BooleanVariable> item = new HashSet<BooleanVariable>();

			for (Entry<Variable, Object> entryInstance : instance.entrySet()) {
				/* récupérer la variable qui correspond à la variable de l'instance dans la table des item */
				BooleanVariable variableBool = itemtable.get(entryInstance.getKey()).get(entryInstance.getValue());

				if (variableBool != null) {
					item.add(variableBool);/* l'insérer */
				}
			}
			list.add(item);
		}
		
		for (Entry<Variable, Map<Object, BooleanVariable>> entryItem : itemtable.entrySet()) {
			items.addAll(entryItem.getValue().values());
		}
		
		items.remove(null);
		BooleanDatabase booleanBase = new BooleanDatabase(items);
		
		if (list.isEmpty()) {
			return booleanBase;
		} else {
			for (Set<BooleanVariable> it : list) {
				booleanBase.add(it);
			}
		}
		return booleanBase;
	}
	
	
	

}