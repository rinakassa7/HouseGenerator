package solvers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import representation.Variable;

public class RandomValueHeuristic implements ValueHeuristic {

	private Random rand;
	
	public RandomValueHeuristic(Random rand) {
		this.rand = rand;
	}

	/**
	 * ordering retourne une liste mélangée uniformément 
	 * retourne une liste contenant les valeurs du domaine, 
	 * ordonnées selon l'heuristique (la meilleure en premier)
	 * 
	 */
	
	@Override
	public List<Object> ordering(Variable variable, Set<Object> domaine) {
		List<Object> newObjects = new LinkedList<Object>();
		for (Object object : domaine) {
			newObjects.add(object);
		}
		Collections.shuffle(newObjects, rand);
		return newObjects;
	}

}
