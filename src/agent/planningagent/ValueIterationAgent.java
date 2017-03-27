package agent.planningagent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import util.HashMapUtil;

import java.util.HashMap;

import environnement.Action;
import environnement.Etat;
import environnement.IllegalActionException;
import environnement.MDP;
import environnement.Action2D;


/**
 * Cet agent met a jour sa fonction de valeur avec value iteration 
 * et choisit ses actions selon la politique calculee.
 * @author laetitiamatignon
 *
 */
public class ValueIterationAgent extends PlanningValueAgent{
	/**
	 * discount facteur
	 */
	protected double gamma;

	/**
	 * fonction de valeur des etats
	 */
	protected HashMap<Etat,Double> V;
	
	/**
	 * 
	 * @param gamma
	 * @param mdp
	 */
	public ValueIterationAgent(double gamma,  MDP mdp) {
		super(mdp);
		this.gamma = gamma;
		V = new HashMap<Etat,Double>();
		for (Etat etat:this.mdp.getEtatsAccessibles()){
			V.put(etat, 0.0);
		}
		this.notifyObs();
		
	}
	
	
	
	
	public ValueIterationAgent(MDP mdp) {
		this(0.9,mdp);

	}
	
	/**
	 * 
	 * Mise a jour de V: effectue UNE iteration de value iteration (calcule V_k(s) en fonction de V_{k-1}(s'))
	 * et notifie ses observateurs.
	 * Ce n'est pas la version inplace (qui utilise nouvelle valeur de V pour mettre a jour ...)
	 */
	@Override
	public void updateV(){
		//delta est utilise pour detecter la convergence de l'algorithme
		//lorsque l'on planifie jusqu'a convergence, on arrete les iterations lorsque
		//delta < epsilon 
		this.delta=0.0;
		//*** VOTRE CODE
		for (Map.Entry<Etat,Double> etat:this.V.entrySet()){
			List<Action> actionsPossibles = this.mdp.getActionsPossibles(etat.getKey());
			double max = 0;
			for(Action action:actionsPossibles){
				double somme = getSommeRecompenseTransition(etat.getKey(), action);

				max = somme>max?somme:max;
			}
			etat.setValue(max);
		}
		
		
		// mise a jour vmax et vmin pour affichage du gradient de couleur:
		//vmax est la valeur  max de V  pour tout s
		//vmin est la valeur min de V  pour tout s
		// ...
		
		//******************* laisser la notification a la fin de la methode	
		this.notifyObs();
	}

	private double getSommeRecompenseTransition(Etat etat, Action action) {
		double somme = 0;
		try{
            for(Map.Entry<Etat,Double> ateignable : this.mdp.getEtatTransitionProba(etat,action).entrySet()){
                somme += ateignable.getValue() * (this.mdp.getRecompense(etat,action,ateignable.getKey())+this.getGamma()*this.V.get(ateignable.getKey()));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
		return somme;
	}


	/**
	 * renvoi l'action executee par l'agent dans l'etat e 
	 * Si aucune actions possibles, renvoi Action2D.NONE
	 */
	@Override
	public Action getAction(Etat e) {
		List<Action> actions = this.getPolitique(e);
		if (actions.size() == 0)
			return Action2D.NONE;
		else{//choix aleatoire
			return actions.get(rand.nextInt(actions.size()));
		}

		
	}
	@Override
	public double getValeur(Etat _e) {
		return  V.get(_e);
	}
	/**
	 * renvoi la (les) action(s) de plus forte(s) valeur(s) dans etat 
	 * (plusieurs actions sont renvoyees si valeurs identiques, liste vide si aucune action n'est possible)
	 */
	@Override
	public List<Action> getPolitique(Etat _e) {
		//*** VOTRE CODE
		
		// retourne action de meilleure valeur dans _e selon V, 
		// retourne liste vide si aucune action legale (etat absorbant)
		List<Action> returnactions = new ArrayList<Action>();
		List<Action> actionsPossibles = this.mdp.getActionsPossibles(_e);

		double max = 0;
		for(Action action: actionsPossibles){
			double somme = getSommeRecompenseTransition(_e,action);
			if(max<somme){
				returnactions.clear();
				returnactions.add(action);
				max = somme;
			}
			if(max == somme){
				returnactions.add(action);
			}
		}
		return returnactions;
		
	}
	
	@Override
	public void reset() {
		super.reset();

		
		this.V.clear();
		for (Etat etat:this.mdp.getEtatsAccessibles()){
			V.put(etat, 0.0);
		}
		this.notifyObs();
	}

	

	

	public HashMap<Etat,Double> getV() {
		return V;
	}
	public double getGamma() {
		return gamma;
	}
	@Override
	public void setGamma(double _g){
		System.out.println("gamma= "+gamma);
		this.gamma = _g;
	}


	
	

	
}
