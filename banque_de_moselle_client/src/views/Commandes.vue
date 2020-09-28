<template>
  <div class="row">
    <div id="listeCommandes" class="col-12">
      <h1 class="col-12">Liste des commandes</h1>

      <div class="col-12">
        <button class="btn btn-link" @click="nouvelleCommande">Nouvelle commande</button>
      </div>
      <br>
      <div>
        <table class="table" v-if="commandes.length">
          <tr>
            <th>Numéro</th>
            <th>Date création</th>
            <th>Etat</th>
            <th>Fournisseur</th>
            <th>Montant TTC</th>
          </tr>

          <tr id="commande" @click.prevent="chargerUneCommande(commande.id)+afficheDetail()"
              v-for="commande in commandes"
              :key="commande.id">
            <td><a href>{{commande.numero}}</a></td>
            <td>{{commande.dateCreation}}</td>
            <td>{{getEtatCommande(commande)}}</td>
            <td>{{commande.fournisseur.nom}}</td>
            <td>{{getMontantTTC(commande)}}</td>
          </tr>
        </table>
      </div>
    </div>

    <div id="detailCommande" class="col-12">
      <div>
        <button type="button" class="btn btn-outline-success" @click="retourCommandes()">&larr; Retour</button>
      </div>
      <br><br>
      <DetailCommande :creer="creer" :commande="commandeForm" :titrePage="titrePageDetail"
                      @rechargerCommandes="rechargerCommandes"
                      @effacerFormulaire="effacerFormulaire"
                      @getEtatCommande="getEtatCommande"
                      @getURLEtatCommande="getURLEtatCommande"
                      @chargerUneCommande="chargerUneCommande"/>
      <br><br>
    </div>
  </div>
</template>

<script>
  import DetailCommande from "../components/DetailCommande";
  import {Commande} from "../modeles/Commande";

  export default {
    name: "Commandes",
    components: {
      DetailCommande
    },
    data() {
      return {
        creer: true,
        commandes: [],
        commandeForm: new Commande(),
        titrePageDetail: null,
      }
    },
    async mounted() {
      await this.getCommandes();
      document.getElementById('detailCommande').hidden = true;
    },
    methods: {
      async getCommandes() {
        try {
          const reponse = await this.$http.get('commandes');
          this.commandes = reponse.data;
        } catch (e) {
          console.error(e);
        }
      },
      getMontantTTC(commande) {
        let montant = 0;
        commande.lignesCommandes.forEach(ligne => {
          montant += ligne.prix * ligne.quantite * ligne.tva;
        });
        return montant.toFixed(2);
      },
      async chargerUneCommande(id) {
        try {
          const reponse = await this.$http.get('commandes/' + id);
          this.commandeForm = reponse.data;
          this.creer = false;
          this.titrePageDetail = "Détail commande";
        } catch (e) {
          console.error(e);
        }
      },
      async rechargerCommandes() {
        await this.getCommandes();
      },
      effacerFormulaire() {
        this.creer = true;
        this.commandeForm = new Commande;
      },
      getEtatCommande(commande) {
        if (commande.etat === 10) {
          return "Créée";
        } else if (commande.etat === 20) {
          return "Rédigée";
        } else if (commande.etat === 30) {
          return "Visée";
        } else if (commande.etat === 40) {
          return "Signée";
        } else if (commande.etat === 50) {
          return "Envoyée";
        } else if (commande.etat === 60) {
          return "Réceptionnée";
        } else if (commande.etat === 70) {
          return "Archivée";
        }
      },
      getURLEtatCommande(commande) {
        if (commande.etat === 10) {
          return "rediger";
        } else if (commande.etat === 20) {
          return "viser";
        } else if (commande.etat === 30) {
          return "signer";
        } else if (commande.etat === 40) {
          return "envoyer";
        } else if (commande.etat === 50) {
          return "receptionner";
        } else if (commande.etat === 60) {
          return "archiver";
        }
      },
      afficheDetail() {
        document.getElementById('detailCommande').hidden = false;
        document.getElementById('listeCommandes').hidden = true;
      },
      nouvelleCommande() {
        this.effacerFormulaire();
        this.afficheDetail();
        this.titrePageDetail = "Nouvelle commande";
      },
      retourCommandes() {
        this.getCommandes();
        document.getElementById('detailCommande').hidden = true;
        document.getElementById('listeCommandes').hidden = false;
      },
    }
  }
</script>

<style scoped>
  .none {
    display: none
  }

  #commande:hover {
    background: rgba(31, 254, 16, 0.2);
  }

  #commande:active {
    background: linear-gradient(rgba(31, 254, 16, 0.2), rgba(31, 254, 16, 0.1));
  }
</style>
