<template>
  <div class="row">
    <h1 class="col-12">Liste des fournisseurs</h1>

    <div class="col-12">
      <button class="btn btn-link" @click="effacerFormulaire">Ajouter un fournisseur</button>
    </div>

    <div class="col-6">
      <ul class="liste">
        <li v-for="fournisseur in fournisseursPagination" :key="fournisseur.id"
            class="item" @click.prevent="chargerUnFournisseur(fournisseur.id)">
          {{fournisseur.nom|capitalize}}
        </li>
      </ul>
      <Pagination :elements="fournisseurs" :nbPages="nbPages" :changerTableau="setFournisseursPagination"/>
    </div>

    <div class="col-6">
      <DetailFournisseur :creer="creer" :fournisseur="fournisseurForm" @rechargerFournisseurs="rechargerFournisseurs"/>
    </div>
  </div>
</template>

<script>
  import DetailFournisseur from "../components/DetailFournisseur";
  import Pagination from "../components/Pagination";

  export default {
    name: "Fournisseurs",
    components: {
      DetailFournisseur,
      Pagination
    },
    data() {
      return {
        creer: true,
        fournisseurs: [],
        fournisseurForm: {},
        fournisseursPagination: [],
        nbPages: 0
      }
    },
    async mounted() {
      await this.getFournisseurs();
      this.fournisseursPagination = this.fournisseurs.slice(0, Pagination.PAR_PAGE);
      this.calculerNombrePage();
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
      async rechargerFournisseurs() {
        await this.getFournisseurs();
        this.calculerNombrePage();
      },
      async chargerUnFournisseur(id) {
        try {
          const reponse = await this.$http.get('fournisseurs/' + id);
          this.fournisseurForm = reponse.data;
          this.creer = false;
        } catch (e) {
          console.error(e);
        }
      },
      effacerFormulaire() {
        this.creer = true;
        this.fournisseurForm = {};
      },
      setFournisseursPagination: function (nouveauTableau) {
        this.fournisseursPagination = nouveauTableau;
      },
      calculerNombrePage() {
        const nbFourniseurs = this.fournisseurs.length;
        this.nbPages = Math.floor(nbFourniseurs / Pagination.PAR_PAGE) + (nbFourniseurs % Pagination.PAR_PAGE === 0 ? 0 : 1);
      }
    }
  }
</script>

<style scoped>
  .liste {
    list-style: none;
    padding-left: 0;
    width: 100%;
    position: relative;
  }

  .item {
    margin: 5px auto;
    border-radius: 4px;
    display: flex;
    padding: 10px;
    justify-content: space-between;
    background-color: aliceblue;
  }
</style>
