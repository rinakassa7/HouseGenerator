package datamining;

import java.util.*;
import representation.BooleanVariable;

public abstract class AbstractItemsetMiner implements ItemsetMiner {
	
	public static Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());
	public BooleanDatabase base;

	/**
	 * constructeur de la classe AbstractItemsetMiner
	 * @param base
	 */
	public AbstractItemsetMiner(BooleanDatabase base) {
		this.base = base;
	}

	@Override
	public BooleanDatabase getDatabase() {
		return this.base;
	}
	
	/**
	 * la méthode frequency permet de récuperer la fréquence d'un item donné dans la base de donnée
	 * @param items
	 * @return
	 */
	public float frequency(Set<BooleanVariable> items) {
		
		int nb_apparition = 0;
		for(Set<BooleanVariable> transaction : this.base.getTransactions()) {
			//tester si la transaction contient tout les item 
			if(transaction.containsAll(items)) {
				nb_apparition++;
			}
		}
		
		return (float)nb_apparition/(this.base.getTransactions().size());
	}

}