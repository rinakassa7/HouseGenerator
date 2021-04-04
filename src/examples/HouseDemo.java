package examples;

import java.util.*;

import datamining.AssociationRule;
import datamining.BooleanDatabase;
import datamining.BruteForceAssociationRuleMiner;
import datamining.Database;
import planning.AStarPlanner;
import planning.Action;
import planning.BFSPlanner;
import planning.BasicGoal;
import planning.DFSPlanner;
import planning.DijkstraPlanner;
import planning.Goal;
import planning.HeuristicNull;
import representation.BooleanVariable;
import representation.Constraint;
import representation.Variable;
import solvers.BacktrackSolver;
import solvers.HeuristicMACSolver;
import solvers.MACSolver;
import solvers.NbConstraintsVariableHeuristic;
import solvers.RandomValueHeuristic;

public class HouseDemo {

	public static void main(String[] args) {

		/************************************************************************************ Exemple de maison **************************************************************************/
		
		System.out.println("//_____________________________________________________Exemple de maison___________________________________________________________________//");
		
		/* Pièces d'eau */
		Set<String> wetRooms = new HashSet<String>();
		wetRooms.add("cuisine");
		wetRooms.add("salle de bains");
		wetRooms.add("WC");

		/* Pièces sèches*/
		Set<String> dryRooms = new HashSet<String>();
		dryRooms.add("séjour");
		dryRooms.add("chambre1");
		dryRooms.add("chambre2");
	

		HouseExample house = new HouseExample(2,2, wetRooms, dryRooms);
		
		System.out.println("\nListe des Variables...\n");
		Set<Variable> setVariable = new HashSet<>(house.getListVariable());
		for(Variable variable : setVariable) {
			System.out.println(variable);
		}
		System.out.println("\nListe des Contraintes...\n");
        Set<Constraint> setContrainte =  new HashSet<>(house.getListeContraintes());
        for(Constraint constraint : setContrainte) {
			System.out.println(constraint);
		}
        
        System.out.println("\nListe des Actions...\n");
        Set<Action> actions = new HashSet<>(house.getAction());
        for(Action action : house.getAction()) {
        	System.out.println(action);
        }
	/*********************************************************************************Solveurs***********************************************************************************************/

		System.out.println("\n//_____________________________________________________________Solveurs____________________________________________________________________//\n");
		
		System.out.println("Algorithme de BacktrackSolver...\n");
		BacktrackSolver backtrack = new BacktrackSolver(setVariable, setContrainte);
		Map<Variable, Object> resultBackTrack = backtrack.solve();			
		for(Variable variable : resultBackTrack.keySet()) {
			System.out.println(variable.getName() + " => " + resultBackTrack.get(variable));
		}
		
		System.out.println("\nALgorithme de MacSolver...\n");
		MACSolver macSolver = new MACSolver(setVariable, setContrainte);
		Map<Variable, Object> resultMac = macSolver.solve();
		for(Variable variable : resultMac.keySet()) {
			System.out.println(variable.getName() + " => " + resultMac.get(variable));
		}

		System.out.println("\nALgorithme de HeuristicMacSolver...\n");
		NbConstraintsVariableHeuristic variableHeuristic = new NbConstraintsVariableHeuristic(setVariable, setContrainte, true);
		RandomValueHeuristic valueHeuristic = new RandomValueHeuristic(new Random());
		HeuristicMACSolver heuristicMacsolver = new HeuristicMACSolver(setVariable,setContrainte, variableHeuristic,valueHeuristic);
		Map<Variable, Object> resultHeuristic = heuristicMacsolver.solve();
		for(Variable variable : resultHeuristic.keySet()) {
			System.out.println(variable.getName() + " => " + resultHeuristic.get(variable));
		}

	/********************************************************************************planification *******************************************************************************************/

		System.out.println("\n//__________________________________________________________________Planification_______________________________________________________________________________//\n");
		
		System.out.println("EtatInitial...\n");
		HashMap<Variable, Object> etatInitial = new HashMap<>();
		List<Variable> lvariable = house.getListVariable();
		etatInitial.put(lvariable.get(0), false);//dalleCoulee
		//etatInitial.put(lvariable.get(1), true);//dalleHumide
		//etatInitial.put(lvariable.get(2), false);//murElevee
		//etatInitial.put(lvariable.get(3), false);//toitureTerminee
		for (int i = 4; i < lvariable.size(); i++) {
			etatInitial.put(lvariable.get(i), null);
		}
		System.out.println(etatInitial);
	

		System.out.println("Goal... ");
		HashMap<Variable, Object> but = new HashMap<>(new HeuristicMACSolver(setVariable, setContrainte, variableHeuristic,valueHeuristic).solve());
		but.put(lvariable.get(0),true);
		but.put(lvariable.get(1),false);
		but.put(lvariable.get(2),true);
		but.put(lvariable.get(3),true);
	
		BasicGoal goal = new BasicGoal(but);
		System.out.println(goal + "\n");
	
		List<Action> plan;

		System.out.println("\nPlan avec BFS...\n");
		BFSPlanner bfsPlanner = new BFSPlanner(etatInitial,actions , goal);
		plan = bfsPlanner.plan(); 
		if(plan != null){
	            for (Action action : plan) {
	                System.out.println(action);
	            }
	        } else {
	            System.out.println("Pas de plan trouvé");
	       }
		
		System.out.println("\nPlan avec DFS...\n");
		DFSPlanner dfsPlanner = new DFSPlanner(etatInitial, actions,goal);
		plan = dfsPlanner.plan(); 
	
		if(plan != null){
	            for (Action action : plan) {
	                System.out.println(action);
	            }
	        } else {
	            System.out.println("Pas de plan trouvé");
	       }
		
		System.out.println("\nPlan avec Djikstra...\n");
		DijkstraPlanner djikstra = new DijkstraPlanner(etatInitial,actions,  goal);
		plan = djikstra.plan();
	        if(plan != null){
	            for (Action action : plan) {
	                System.out.println(action);
	            }
	        } else {
	            System.out.println("Pas de plan trouvé");
	       }
	        
		System.out.println("\nPlan avec AStar...\n");
		AStarPlanner astar = new AStarPlanner(etatInitial,actions , goal , new HeuristicNull());
		plan = astar.plan();
        if(plan != null){
            for (Action action : plan) {
                System.out.println(action);
            }
        } else {
            System.out.println("Pas de plan trouvé");
       }

	/************************************************************************************Datamining ********************************************************************************************/
		System.out.println("\n//_________________________________________________________________Extraction de connaissances_______________________________________________________________________//");
		System.out.println("\nCréation de la base de donnée...");
	
		Database base = new Database(setVariable);

		for (int i = 0; i < 7; i++) {
            base.add(new BacktrackSolver(setVariable, setContrainte).solve());
        }
        for (int i = 0; i <100 ; i++) {
            base.add(new HeuristicMACSolver(setVariable, setContrainte, variableHeuristic,valueHeuristic).solve());
        }

        for (int i = 0; i <5 ; i++) {
            base.add(new MACSolver(setVariable, setContrainte).solve());
        }
	
		System.out.println("\nPropositionnalisation...");
		BooleanDatabase booleanBase = base.propositionalize();
		System.out.println("\nExtraction de règles avec une fréquence 0.15 et une confiance de 0.5...");
		BruteForceAssociationRuleMiner brute = new BruteForceAssociationRuleMiner(booleanBase);
		Set<AssociationRule> rules = new HashSet<>(brute.extract( 0.15f,  0.5f));
		
		for(AssociationRule rule : rules) {
		System.out.println(rule);
		}

	}
	
}
