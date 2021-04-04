package examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import planning.Action;
import planning.BasicAction;
import planning.BasicGoal;
import representation.BinaryExtensionConstraint;
import representation.BooleanVariable;
import representation.Constraint;
import representation.DifferenceConstraint;
import representation.Rule;
import representation.Variable;

public class HouseExample {

	private int largeur;
	private int longueur;

	private Set<String> wetRooms;
	private Set<String> dryRooms;
	private List<Variable> listVariables;
	private List<Constraint> listeContraintes;
	private List<Action> actions;

	/**
	 * représentation de notre maison avec des variables, des contraines et des actions 
	 * @param largeur
	 * @param longueur
	 * @param wetRooms
	 * @param dryRooms
	 */
	public HouseExample(int largeur, int longueur, Set<String> wetRooms, Set<String> dryRooms) {

		this.largeur = largeur;
		this.longueur = longueur;
		this.wetRooms = wetRooms;
		this.dryRooms = dryRooms;
		this.listVariables = new ArrayList<Variable>();
		this.listeContraintes = new ArrayList<Constraint>();
		
		
/************************************************************************************* Variables ************************************************************************************************/

		/* instanciation des variables booléennes */
		BooleanVariable dalleCoulee = new BooleanVariable("dalle coulée");
		BooleanVariable dalleHumide = new BooleanVariable("dalle humide");
		BooleanVariable murElevee = new BooleanVariable("murs élevés");
		BooleanVariable toiture = new BooleanVariable("toiture terminée");

		/* ajout des variables booléennes à la liste des variables */
		this.listVariables.add(dalleCoulee);
		this.listVariables.add(dalleHumide);
		this.listVariables.add(murElevee);
		this.listVariables.add(toiture);
		
		/* création de l'ensemble des pièces */
		Set<Object> allPieces = new HashSet<>();
		allPieces.addAll(wetRooms); /* ajout des pièces d'eau */
		allPieces.addAll(dryRooms); /* ajout des pièces sèche */

		List<Variable> listPieces = new ArrayList<Variable>();
	

		/* création des variables */
		for (int i = 1; i <= this.largeur; i++) {
			for (int j = 1; j <= this.longueur; j++) {
				Variable piece = new Variable("piece(" + i + "," + j + ")", allPieces);
				this.listVariables.add(piece);
				listPieces.add(piece);
			}
		}
		
/************************************************************************************* Contraintes ************************************************************************************************/

		// contrainte (deux pièces d'eau (salles de bains, cuisines, toilettes) doivent
		// nécessairement être envisagées côte à côte)

		int x1, y1, x2, y2;
		int distance = 0;
		
		for (Variable piece1 : listPieces) {
			/* récupérer les coordonnée des variables */
			x1 = Integer.parseInt(piece1.getName().substring(6, piece1.getName().length() - 1).split(",", 0)[0]);
			y1 = Integer.parseInt(piece1.getName().substring(6, piece1.getName().length() - 1).split(",", 0)[1]);
		
			for (Variable piece2 : listPieces) {
				if (!piece2.equals(piece1)) {
					x2 = Integer.parseInt(piece2.getName().substring(6, piece2.getName().length() - 1).split(",", 0)[0]);
					y2 = Integer.parseInt(piece2.getName().substring(6, piece2.getName().length() - 1).split(",", 0)[1]);

					/* calculer la distance entre deux positions */
					distance = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
			
					if (distance > 1) {
						/* création de la contrainte binaire */
						BinaryExtensionConstraint binaryConstraint1 = new BinaryExtensionConstraint(piece1, piece2);
						for (Object objet1 : piece1.getDomain()) {
							for (Object objet2 : piece2.getDomain())
								if (!objet1.equals(objet2)) {
									/* deux pièces dont la distance entre elles est > 1 ne peuvent pas avoir une pièce d'eau chaqu'une  */
									if (!this.wetRooms.contains(objet1) || !this.wetRooms.contains(objet2)) {
										binaryConstraint1.addTuple(objet1, objet2);
									}
								}
						   }
					this.listeContraintes.add(binaryConstraint1);
				}else {
					/* une pièce occupe au plus une position */
					DifferenceConstraint differenteVariable = new DifferenceConstraint(piece1, piece2);
					this.listeContraintes.add(differenteVariable);
				}
			}
		}
	}
		
		// dalle humide => dalle coulée
		Rule booleanConstraint1 = new Rule(dalleHumide, false, dalleCoulee, true);
		// mur elevée => dalle sèche
		Rule booleanConstraint2 = new Rule(murElevee, true, dalleHumide, false);
		// toiture => mur élevée
		Rule booleanConstraint3 = new Rule(toiture, true, murElevee, true);

		this.listeContraintes.add(booleanConstraint1);
		this.listeContraintes.add(booleanConstraint2);
		this.listeContraintes.add(booleanConstraint3);


/************************************************************************************* Actions ************************************************************************************************/

		this.actions = new ArrayList<>();

		Map<Variable, Object> precond = new HashMap<>();
		Map<Variable, Object> effect = new HashMap<>();
	
		// Couller dalle
		//precond.put(dalleCoulee, false);
		effect.put(dalleCoulee, true);
		effect.put(dalleHumide, true);
		actions.add( new BasicAction("Couller Dalle",precond, effect, 2));
		
		// Attendre
		precond = new HashMap<>();
		effect = new HashMap<>();
		precond.put(dalleCoulee, true);
		precond.put(dalleHumide, true);
		effect.put(dalleHumide, false);
		actions.add( new BasicAction("Attendre",precond, effect, 5));
		
		// Mur Elevée
		precond = new HashMap<>();
		effect = new HashMap<>();
		precond.put(dalleCoulee, true);
		precond.put(dalleHumide, false);
		effect.put(murElevee, true);
		actions.add( new BasicAction("Mur Elevee",precond, effect, 7));


		// Toiture Terminée
		precond = new HashMap<>();
		effect = new HashMap<>();
		precond.put(dalleCoulee, true);
		precond.put(murElevee, true);
		effect.put(toiture, true);
		actions.add( new BasicAction("Toiture Terminee",precond, effect, 8));

		/*positionner la pièce p en position (i,j) (pour tous p et tous (i,j)) */
		precond = new HashMap<>();
		precond.put(dalleCoulee, true);
		precond.put(dalleHumide, false);
		
		for (Variable variable : listPieces) {
			for (Object piece : variable.getDomain()){
				 Map<Variable, Object> preconditionCopy = new HashMap<>(precond);
                 preconditionCopy.put(variable, null);
					effect = new HashMap<>();
					effect.put(variable, piece);
					actions.add( new BasicAction("Positionner la pièce " +variable.getName(),preconditionCopy, effect, 10));
				}
			}
		
/************************************************************************************* Fin ************************************************************************************************/

		
	} 	

	/**
	 * 
	 * @return
	 */
	public List<Variable> getListVariable() {
		return listVariables;
	}

	/**
	 * 
	 * @return
	 */
	public List<Constraint> getListeContraintes() {
		return listeContraintes;
	}

	/**
	 * 
	 * @return
	 */
	public List<Action> getAction() {
		return this.actions;
	}

}
