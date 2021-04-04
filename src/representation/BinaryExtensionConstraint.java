package representation;

import java.util.*;

/**
 * la classe BinaryExtensionConstraint représente les contraintes binaires en extension 
 *
 */
public class BinaryExtensionConstraint implements Constraint {
	private Variable var1;
	private Variable var2;
	private Set<Variable> scope = new HashSet<>();
	private Set<BinaryTuple> extention = new HashSet<>();

	/**
	 * constructeur de BinaryExtensionConstraint
	 * @param var1
	 * @param var2
	 */
	public BinaryExtensionConstraint(Variable var1, Variable var2) {
		this.var1 = var1;
		this.var2 = var2;
		scope.add(var1);
		scope.add(var2);
	}
	/**
	 * permet d'ajouter un couple de valeurs autorisé 
	 * @param o1
	 * @param o2
	 */
	public void addTuple(Object o1, Object o2) {
		this.extention.add(new BinaryTuple(o1,o2));
		
	}

	@Override
	public Set<Variable> getScope() {
		return this.scope;
	}
	
	@Override
	public boolean isSatisfiedBy(Map<Variable, Object> extension) {
		//recuperer les valeurs de var1 et var2, transformer les deux valeurs en binaryTuple et 
		//verifier que c'est dans l'extnsion.

		if(!extension.containsKey(var1) || !extension.containsKey(var2)) 
			throw new IllegalArgumentException();
		return this.extention.contains(new BinaryTuple(extension.get(var1), extension.get(var2)));
		}
	
	@Override
	public String toString() {
		return "BinaryExtensionConstraint on (var1=" + var1.getName()+ ", var2=" + var2.getName() + ") with allowed tuples " + extention;
	}

	
	

}
