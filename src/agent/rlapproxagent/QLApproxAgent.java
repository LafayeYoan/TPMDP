package agent.rlapproxagent;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.rlagent.QLearningAgent;
import agent.rlagent.RLAgent;
import environnement.Action;
import environnement.Environnement;
import environnement.Etat;
/**
 * Agent qui apprend avec QLearning en utilisant approximation de la Q-valeur : 
 * approximation lineaire de fonctions caracteristiques 
 * 
 * @author laetitiamatignon
 *
 */
public class QLApproxAgent extends QLearningAgent{

	private FeatureFunction featureFunction;
	private HashMap<Etat, HashMap<Action, Double>> poids;
	
	public QLApproxAgent(double alpha, double gamma, Environnement _env,FeatureFunction _featurefunction) {
		super(alpha, gamma, _env);
		//*** VOTRE CODE
		featureFunction = _featurefunction;
		poids = new HashMap<>();
	}

    public Double getPoidsFor(Etat key, Action action) {
        if(poids.containsKey(key) && poids.get(key).containsKey(action)) {
            return poids.get(key).get(action);
        }
        return null;
    }

    public void setPoidsFor(Etat key, Action action, Double value) {
        poids.get(key).put(action, value);
    }

    public FeatureFunction getFeatureFunction() {
        return featureFunction;
    }

    @Override
	public double getQValeur(Etat e, Action a) {
		//*** VOTRE CODE
        double[] vecteurs = getFeatureFunction().getFeatures(e, a);
        double somme = 0.0;

        for(int i = 0; i < vecteurs.length; i++) {

            double teta = getPoidsFor(e, a);

            if(teta != 0.0) {
                somme += (teta * vecteurs[i]);
            }
        }
		return somme;
	}

    /***
     * Retourne le Q max pour toutes les actions d'un etat e
     * @param e
     * @return
     */
	private double getMaxQForEtat(Etat e) {
	    double max = 0.0;
	    for(Action a : getActionsLegales(e)) {
	        double q = getQValeur(e, a);
	        max = (max < q) ? q : max;
        }
        return max;
    }
	
	
	
	
	@Override
	public void endStep(Etat e, Action a, Etat esuivant, double reward) {
		if (RLAgent.DISPRL){
			System.out.println("QL: mise a jour poids pour etat \n"+e+" action "+a+" etat' \n"+esuivant+ " r "+reward);
		}
       //inutile de verifier si e etat absorbant car dans runEpisode et threadepisode 
		//arrete episode lq etat courant absorbant	
		
		//*** VOTRE CODE

        double[] vecteurs = getFeatureFunction().getFeatures(e, a);
		double newValue = 0.0;

		for(int i = 0; i < vecteurs.length; i++) {
            newValue = getPoidsFor(e, a) + getAlpha()
                    * (reward + getGamma() * getMaxQForEtat(esuivant) - getQValeur(e, a))
                    * vecteurs[i];
        }

        setPoidsFor(e,a,newValue);
	}
	
	@Override
	public void reset() {
		super.reset();
		this.qvaleurs.clear();
	
		//*** VOTRE CODE
        this.poids = new HashMap<>();
		
		this.episodeNb =0;
		this.notifyObs();
	}
	
	
}
