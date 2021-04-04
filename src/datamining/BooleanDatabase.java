 package datamining;

import java.util.*;
import representation.BooleanVariable;

public class BooleanDatabase {

	private Set<BooleanVariable> items;
	private List<Set<BooleanVariable>> transaction;

	/**
	 * Constructeur de la classe BooleanDatabase
	 * qui représente des bases de données transactionnelles
	 * @param items
	 */
	public BooleanDatabase(Set<BooleanVariable> items) {
		this.items = items;
		this.transaction = new ArrayList<>();
	}

	/**
	 * 
	 * @param items
	 */
	public void add(Set<BooleanVariable> items) {
		this.transaction.add(items);
	}

	public Set<BooleanVariable> getItems() {
		return this.items;
	}

	public List<Set<BooleanVariable>> getTransactions() {
		return this.transaction;
	}

	@Override
	public String toString() {
		return "BooleanDatabase [items=" + items + ", transaction=" + transaction + "]";
	}
	

}