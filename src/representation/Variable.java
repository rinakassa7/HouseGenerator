package representation;
import java.util.Set;

public class Variable {
	
/**
 * nom : représente le nom de la variable
 * domaine : représente le domaine de la variable
 */
    public String name;
    private Set<Object> domaine;

    /**
     * représenter la variable par son nom et son domaine
     * @param nom
     * @param domaine
     */
    public Variable(String nom, Set<Object> domaine){
        this.name= nom;
        this.domaine= domaine;
    }
    
    
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Variable other = (Variable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	/**
	 * le getteur qui retourne le nom de la variable
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * le setters qui modifie le nom de la variable
	 * @param nom
	 */
	public void setNom(String nom) {
		this.name = nom;
	}
	/**
	 * le getters qui retourne le domaine de la variable
	 * @return
	 */
	public Set<Object> getDomain() {
		return domaine;
	}

	/**
	 * le setters qui modifie le domaine de la variable
	 * @param domaine
	 */
	public void setDomain(Set<Object> domaine) {
		this.domaine = domaine;
	}

	@Override
	public String toString() {
		return "Variable [name=" + name + ", domaine=" + domaine + "]";
	}    


}
