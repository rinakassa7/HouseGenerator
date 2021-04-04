package representation;

import java.util.Set;
import java.util.*;

public class BooleanVariable extends Variable {

	
	private static Set<Object> domaine = new HashSet<>();

	static {
		domaine.add(true);
		domaine.add(false);
	} 
	
	/**
	 * constructeur de BooleanVariable représenté par le nom de la variable
	 * @param nom
	 */
	public BooleanVariable(String nom) {
		super(nom,domaine);
	}

	public Set<Object> getDomain() {
		return domaine;
	}

	public  void setDomain(Set<Object> domaine) {
		BooleanVariable.domaine = domaine;
	}
	
	@Override
	public String toString(){
	  return this.name;
	}
	
}
