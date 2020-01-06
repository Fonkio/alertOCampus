
public class ControleurServeur {
	private VueServeur vue;
	private Serveur serveur;
	
	public ControleurServeur(VueServeur vue, Serveur serveur) {
		this.vue = vue;
		this.serveur = serveur;
	}
	
	public void afficherListeUtilisateurs() {
	
	}
	
	//TODO Changement d'onglet = changement de liste
	//TODO Sélection = changement des champs
	//TODO Liste de sauvegarde des modifications en cours
	//TODO Affichage message si l'utilisateur n'a pas sauvegardé ses modifications
}
