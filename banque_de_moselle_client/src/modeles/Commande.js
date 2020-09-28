export class Commande {
  numero;
  agence;
  fournisseur;
  dateCreation;
  lignesCommandes;

  constructor() {
    this.numero = null;
    this.agence = null;
    this.fournisseur = null;
    this.dateCreation = null;
    this.lignesCommandes = []
  }
}
