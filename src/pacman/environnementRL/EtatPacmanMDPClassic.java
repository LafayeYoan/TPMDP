package pacman.environnementRL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pacman.elements.ActionPacman;
import pacman.elements.StateAgentPacman;
import pacman.elements.StateGamePacman;
import environnement.Etat;
/**
 * Classe pour définir un etat du MDP pour l'environnement pacman avec QLearning tabulaire

 */
public class EtatPacmanMDPClassic implements Etat , Cloneable{

	private String stringToHash;



	public EtatPacmanMDPClassic(StateGamePacman _stategamepacman){

		stringToHash += super.hashCode();

		for (int i  =0 ; i < _stategamepacman.getNumberOfPacmans();i++){
			stringToHash += "_"+ _stategamepacman.getPacmanState(i).getX();
			stringToHash += "_"+_stategamepacman.getPacmanState(i).getY();
			stringToHash += "_"+_stategamepacman.getClosestDot(_stategamepacman.getPacmanState(i));
		}

		for (int i  =0 ; i < _stategamepacman.getNumberOfGhosts();i++){
			stringToHash += "_"+_stategamepacman.getGhostState(i).getX();
			stringToHash += "_"+_stategamepacman.getGhostState(i).getY();
		}

		stringToHash += "_"
				+ _stategamepacman.getFoodEaten()
				+ _stategamepacman.getCapsulesEaten();
	}
	
	@Override
	public String toString() {
		
		return "";
	}

	@Override
	public int hashCode(){
		return stringToHash.hashCode();
	}

	public Object clone() {
		EtatPacmanMDPClassic clone = null;
		try {
			// On recupere l'instance a renvoyer par l'appel de la 
			// methode super.clone()
			clone = (EtatPacmanMDPClassic)super.clone();
		} catch(CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implementons 
			// l'interface Cloneable
			cnse.printStackTrace(System.err);
		}
		


		// on renvoie le clone
		return clone;
	}



	

}
