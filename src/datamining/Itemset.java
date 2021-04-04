package datamining;

import java.util.Comparator;
import java.util.Set;

import representation.BooleanVariable;

public class Itemset {
	
	private Set<BooleanVariable> items;
	private float frequence;
	
	/**
	 * constructeur de la classe Itemset qui représente un 
	 * ensemble d'item et sa fréquence 
	 * @param items
	 * @param frequence
	 */
	public Itemset(Set<BooleanVariable> items, float frequence) {
		this.items = items;
		this.frequence = frequence;
	}

	public Set<BooleanVariable> getItems() {
		return items;
	}

	public float getFrequency() {
		return frequence;
	}

	public void setItems(Set<BooleanVariable> items) {
		this.items = items;
	}

	public void setFrequency(float frequence) {
		this.frequence = frequence;
	}

	
	
}
