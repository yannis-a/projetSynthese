<template>
  <div>
    <h2 id="nouvelleOuDetailTitre">{{titrePage}}</h2>
    <div class="container col-12">
      <br>
      <ul class="progressbar">
        <li id="creee" v-bind:class="isCreee()? 'active' : ''">Créée</li>
        <li id="redigee" v-bind:class="isRedigee()? 'active' : ''">Rédigée</li>
        <li id="visee" v-bind:class="isVisee()? 'active' : ''">Visée</li>
        <li id="signee" v-bind:class="isSignee()? 'active' : ''">Signée</li>
        <li id="envoyee" v-bind:class="isEnvoyee()? 'active' : ''">Envoyée</li>
        <li id="receptionnee" v-bind:class="isReceptionnee()? 'active' : ''">Réceptionnée</li>
        <li id="archivee" v-bind:class="isArchivee()? 'active' : ''">Archivée</li>
      </ul>
      <br>
    </div>

    <form @submit.prevent="validerFormEnregistrement">
      <!-- On a un @NotEmpty sur "numero" dans le serveur : on aurait pu mettre "v-if="!creer" sinon -->
      <div class="form-group row">
        <label for="numero" class="col-2">Numéro</label>
        <div class="col-10">
          <input v-model="commande.numero" id="numero" name="numero" type="text" class="form-control"
                 v-validate="'required|digits:3'">
          <span v-show="errors.has('numero')" class="text-danger">{{errors.first('numero')}}</span>
        </div>
      </div>

      <div class="form-group row">
        <label for="fournisseur" class="col-2">Fournisseur</label>
        <div class="col-10">
          <select v-model="commande.fournisseur" id="fournisseur" name="fournisseur" type="text" class="form-control"
                  v-validate="'required'">
            <option v-for="fournisseur in fournisseurs" :key="fournisseur.id" :value="fournisseur">
              {{fournisseur.nom}}
            </option>
          </select>
          <span v-show="errors.has('fournisseur')" class="text-danger">{{errors.first('fournisseur')}}</span>
        </div>
      </div>

      <div>
        <label class="col-form-label">Articles</label>

        <table class="table">
          <tr>
            <th class="col-1">Référence</th>
            <th class="col-1">Prix</th>
            <th class="col-1">Quantité</th>
            <th class="col-xs-1">TVA</th>
            <th class="col-1">Total HT</th>
            <th class="col-1">Total TTC</th>
            <th class="col-1"></th>
          </tr>

          <tr v-for="(ligne, i) in commande.lignesCommandes" :key="ligne.id">
            <td>
              <input type="text" :id="'reference'+i" :name="'reference'+i" v-model="ligne.reference">
            </td>
            <td>
              <input type="number" step="0.01" :id="'prix'+i" :name="'prix'+i" v-model="ligne.prix" min="1">
            </td>
            <td>
              <input type="number" step="1" :id="'quantite'+i" :name="'quantite'+i" v-model="ligne.quantite" min="0">
            </td>
            <td class="col-xs-1">
              <input type="number" step="0.01" :id="'tva'+i" :name="'tva'+i" v-model="ligne.tva" min="1">
            </td>
            <td>
              {{getMontantHT(ligne)}}€
            </td>
            <td>
              {{getMontantTTC(ligne)}}€
            </td>
            <td>
              <button type="button" class="btn btn-sm btn-danger" @click="supprimerLigne(i)">X</button>
            </td>
          </tr>

          <tr>
            <td colspan="7">
              <button type="button" class="btn btn-light col-12" @click="ajouterUneLigne"><b>+</b></button>
            </td>
          </tr>
          <tr>
            <td colspan="5"></td>
            <th>Total TTC</th>
            <th>{{getTotalTTC()}} €</th>
          </tr>
        </table>

      </div>
      <br>
      <div class="d-flex justify-content-between">
        <button id="btn-saved" type="submit" class="btn btn-success">Enregistrer</button>
      </div>
    </form>
    <form @submit.prevent="validerFormEtat">
      <br>
      <div class="d-flex justify-content">
        <button id="btn-etat" type="submit" class="btn btn-outline-success"
                @click="changementEtat">Rédiger
        </button>
      </div>
    </form>
  </div>
</template>

<script>
  import {LigneCommande} from "../modeles/LigneCommande";

  export default {
    name: "DetailCommande",
    props: ['commande', 'creer', 'titrePage'],
    data() {
      return {
        fournisseurs: [],
        articles: []
      }
    },
    async mounted() {
      await this.getFournisseurs();
    },
    methods: {
      async getFournisseurs() {
        try {
          const reponse = await this.$http.get('fournisseurs');
          this.fournisseurs = reponse.data;
        } catch (e) {
          console.error(e);
        }
      },
      validerFormEnregistrement() {
        this.$validator.validateAll().then(resultat => {
          if (resultat) {
            this.enregistrer();
            this.$snotify.success('La commande a bien été enregistrée !');
          } else {
            this.$snotify.error('Erreurs dans le formulaire !');
          }
        });
      },
      validerFormEtat() {
        this.changementEtat();
        this.$snotify.success('La commande a bien changé d\'état !');
      },
      async enregistrer() {
        try {
          const httpMethod = this.creer ? 'post' : 'put';
          const url = this.creer ? 'commandes' : `commandes/${this.commande.id}`;
          const reponse = await this.$http[httpMethod](url, this.commande);
          if (reponse.status === 200) {
            this.changementEtatBouton();
            this.$emit('rechargerCommandes');
          }
        } catch (e) {
          console.error(e);
        }
      },
      getMontantHT(ligne) {
        let montant = 0;
        montant = ligne.prix * ligne.quantite;
        return montant.toFixed(2);
      },
      getMontantTTC(ligne) {
        let montant = 0;
        montant = this.calculerMontantLigneTTC(ligne);
        return montant.toFixed(2);
      },
      getTotalTTC() {
        let total = 0;
        this.commande.lignesCommandes.forEach(ligne => {
          total += this.calculerMontantLigneTTC(ligne);
        });
        return total.toFixed(2);
      },
      calculerMontantLigneTTC(ligne) {
        return ligne.prix * ligne.quantite * ligne.tva;
      },
      supprimerLigne(index) {
        this.commande.lignesCommandes.splice(index, 1);
      },
      ajouterUneLigne() {
        this.commande.lignesCommandes.push(new LigneCommande());
      },
      isCreee() {
        if (this.commande.etat === 10) {
          return true;
        }
      },
      isRedigee() {
        if (this.commande.etat === 20) {
          return true;
        }
      },
      isVisee() {
        if (this.commande.etat === 30) {
          return true;
        }
      },
      isSignee() {
        if (this.commande.etat === 40) {
          return true;
        }
      },
      isEnvoyee() {
        if (this.commande.etat === 50) {
          return true;
        }
      },
      isReceptionnee() {
        if (this.commande.etat === 60) {
          return true;
        }
      },
      isArchivee() {
        if (this.commande.etat === 70) {
          return true;
        }
      },
      incrementerEtatCommande() {
        if (this.isCreee()) {
          this.commande.etat = 20;
        } else if (this.isRedigee()) {
          this.commande.etat = 30;
        } else if (this.isVisee()) {
          this.commande.etat = 40;
        } else if (this.isSignee()) {
          this.commande.etat = 50;
        } else if (this.isEnvoyee()) {
          this.commande.etat = 60;
        } else if (this.isReceptionnee()) {
          this.commande.etat = 70;
        }
      },
      changementEtatBouton() {
        if (this.commande.etat === 10) {
          document.getElementById('btn-etat').innerHTML = 'Rédiger';
          document.getElementById('btn-saved').disabled = false;
        } else if (this.commande.etat === 20) {
          document.getElementById('btn-etat').innerHTML = 'Viser';
          document.getElementById('btn-saved').disabled = false;
        } else if (this.commande.etat === 30) {
          document.getElementById('btn-etat').innerHTML = 'Signer';
          document.getElementById('btn-saved').disabled = false;
        } else if (this.commande.etat === 40) {
          document.getElementById('btn-etat').innerHTML = 'Envoyer';
          document.getElementById('btn-saved').disabled = false;
        } else if (this.commande.etat === 50) {
          document.getElementById('btn-etat').innerHTML = 'Réceptionner';
          document.getElementById('btn-saved').disabled = true;
        } else if (this.commande.etat === 60) {
          document.getElementById('btn-etat').innerHTML = 'Archiver';
          document.getElementById('btn-saved').disabled = true;
        }
      },
      etatSuivant() {
        if (this.isCreee()) {
          return 'rediger';
        } else if (this.isRedigee()) {
          return 'viser'
        } else if (this.isVisee()) {
          return 'signer'
        } else if (this.isSignee()) {
          return 'envoyer'
        } else if (this.isEnvoyee()) {
          return 'receptionner'
        } else if (this.isReceptionnee()) {
          return 'archiver'
        }
      },
      async changementEtat() {
        try {
          const url = `commandes/${this.commande.id}/${this.etatSuivant()}`;
          const reponse = await this.$http['put'](url);
          console.log('URL état : ' + url);
          if (reponse.status === 200) {
            this.incrementerEtatCommande();
            this.changementEtatBouton();
            this.$emit('chargerUneCommande', this.commande.id);
          }
        } catch (e) {
          console.error(e);
        }
      }
    }
  }
</script>

<style scoped>
  .btn-danger {
    border-radius: 5px;
  }

  h2 {
    margin: 5px;
  }

  .container {
    width: 100%;
    margin-bottom: 100px;
    margin-top: 10px;
  }

  .progressbar {
    counter-reset: step;
  }

  .progressbar li {
    list-style-type: none;
    width: 14%;
    float: left;
    font-size: 12px;
    position: relative;
    text-align: center;
    text-transform: uppercase;
    color: #ffffff;
    z-index: -1;
    background-color: #47641f;
    clip-path: polygon(75% 0%, 100% 50%, 75% 100%, 0% 100%, 25% 50%, 0% 0%);
  }

  .progressbar li:before {
    width: 30px;
    height: 30px;
    content: counter(step);
    counter-increment: step;
    line-height: 30px;
    display: block;
    text-align: center;
    margin: 0 auto 5px auto;
    background-color: transparent;
  }

  .progressbar li.active:before {
    color: #ffffff;
    font-weight: bold;
  }

  .progressbar .active {
    background-color: #8EC63F;
    color: #ffffff;
    font-weight: bold;
  }

  .progressbar .active ~ li {
    background-color: #b0b0b0;
  }
</style>
