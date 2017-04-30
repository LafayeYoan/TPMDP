package pacman.environnementRL;

import pacman.elements.StateGamePacman;
import environnement.Etat;

/**
 * Classe pour définir un etat du MDP pour l'environnement pacman avec QLearning tabulaire

 */
public class EtatPacmanMDPClassic implements Etat , Cloneable{
    
    private int[] pacmansY;
    private int[] pacmansX;
    private int[] ghostsY;
    private int[] ghostsX;


	public EtatPacmanMDPClassic(StateGamePacman _stategamepacman){

	    pacmansY = new int[_stategamepacman.getNumberOfPacmans()];
	    pacmansX = new int[_stategamepacman.getNumberOfPacmans()];
	    ghostsY = new int[_stategamepacman.getNumberOfGhosts()];
	    ghostsX = new int[_stategamepacman.getNumberOfGhosts()];

		for(int i = 0; i < pacmansY.length; i++) {

            if(! _stategamepacman.getPacmanState(i).isDead()) {
                pacmansX[i] = _stategamepacman.getPacmanState(i).getX();
                pacmansY[i] = _stategamepacman.getPacmanState(i).getY();
            } else {
                pacmansX[i] = -1;
                pacmansY[i] = -1;
            }
        }

        for(int j = 0; j < ghostsY.length; j++) {

            if(! _stategamepacman.getGhostState(j).isDead()) {
                ghostsX[j] = _stategamepacman.getGhostState(j).getX();
                ghostsY[j] = _stategamepacman.getGhostState(j).getY();
            } else {
                ghostsX[j] = -1;
                ghostsY[j] = -1;
            }
        }

	}
	
	@Override
	public String toString() {

		return "";
	}

	@Override
	public int hashCode() {
	    int result = 31;

        for(int l = 0; l < ghostsY.length; l++) {
            result *= (ghostsY[l] + ghostsX[l]);
        }

	    for(int k = 0; k < pacmansY.length; k++) {
	        result *= (pacmansY[k] + pacmansX[k]);
        }
        return result;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		EtatPacmanMDPClassic other = (EtatPacmanMDPClassic) obj;
        for(int l = 0; l < ghostsY.length; l++) {
            if(other.ghostsY[l] != this.ghostsY[l]
                    || other.ghostsX[l] != this.ghostsX[l]) {
                return false;
            }
        }

        for(int k = 0; k < pacmansY.length; k++) {
            if(other.pacmansY[k] != this.pacmansY[k]
                    || other.pacmansX[k] != this.pacmansX[k]) {
                return false;
            }
        }
		return true;
	}
}
