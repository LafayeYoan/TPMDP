package pacman.environnementRL;

import pacman.elements.StateGamePacman;
import environnement.Etat;

/**
 * Classe pour définir un etat du MDP pour l'environnement pacman avec QLearning tabulaire

 */
public class EtatPacmanMDPClassic implements Etat , Cloneable{

	private int pacmanX;
	private int pacmanY;
	private int ghostX;
	private int ghostY;


	public EtatPacmanMDPClassic(StateGamePacman _stategamepacman){

		if(! _stategamepacman.getPacmanState(0).isDead()) {
            pacmanX = _stategamepacman.getPacmanState(0).getX();
            pacmanY = _stategamepacman.getPacmanState(0).getY();
        } else {
            pacmanX = -1;
            pacmanY = -1;
        }

		ghostX = _stategamepacman.getGhostState(0).getX();
		ghostY = _stategamepacman.getGhostState(0).getY();

	}
	
	@Override
	public String toString() {

		return "";
	}

	@Override
	public int hashCode() {
        int result = 31 * ghostX + ghostY;
        result = 31 * result + pacmanX + pacmanY;
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
		if(this.pacmanX != other.pacmanX)
			return false;
		if(this.pacmanY != other.pacmanY)
			return false;
		if(this.ghostX != other.ghostX)
			return false;
		if(this.ghostY != other.ghostY)
			return false;
		return true;
	}
}
