<template>
  <div class="detail-fournisseur">
    <h2>Détail fournisseur</h2>

    <form @submit.prevent="validerForm">
      <div class="form-elem" v-if="!creer">
        <label for="id">ID</label>
        <input id="id" name="id" type="text" class="form-control" v-model="fournisseur.id" readonly>
      </div>
      <div class="form-group">
        <label for="nom">Nom</label>
        <input id="nom" name="nom" type="text" class="form-control" v-model="fournisseur.nom"
               v-validate="'required|alpha_spaces'">
        <span v-show="errors.has('nom')" class="text-danger">{{errors.first('nom')}}</span>
      </div>
      <div class="form-group">
        <label for="siret">Siret</label>
        <input id="siret" name="siret" type="text" class="form-control"
               v-model="fournisseur.siret" v-validate="'required|digits:4'">
        <span v-show="errors.has('siret')" class="text-danger">{{errors.first('siret')}}</span>
      </div>
      <div class="form-group" v-if="!creer">
        <label class="form-check-label">{{fournisseur.actif ? 'Actif' : 'Inactif'}}</label>
      </div>

      <button type="submit" class="btn btn-success">Enregistrer</button>
    </form>
  </div>
</template>

<script>
  /* eslint-disable */
  export default {
    name: "DetailFournisseur",
    props: ['fournisseur', 'creer'],
    data() {
      return {
      }
    },
    watch: {
      $route: 'getFournisseur'
    },
    methods: {
      validerForm() {
        this.$validator.validateAll().then(resultat => {
          if (resultat) {
            this.enregistrer();
            this.$snotify.success('Fournisseur enregistré !');
            return;
          }

          this.$snotify.error('Erreurs dans le formulaire !');
        });
      },
      async enregistrer() {
        try {
          const httpMethod = this.creer ? 'post' : 'put';
          const url = this.creer ? 'fournisseurs' : `fournisseurs/${this.fournisseur.id}`;
          const reponse = await this.$http[httpMethod](url, this.fournisseur);

          if (reponse.status === 200) {
            // la fonction emit, permet d'utiliser une fonction du composant parent (ici Fournisseurs.vue)
            this.$emit('rechargerFournisseurs');
          }
        } catch (e) {
          console.error(e);
        }
      }
    }
  }
</script>

<style scoped>
  .detail-fournisseur {
    padding: 5px;
    border-radius: 3px;
    background-color: aliceblue;
  }
</style>
