<template>
  <div class="detail-user">
    <h2>Détail utilisateur</h2>
    <form @submit.prevent="validerForm">
      <div class="form-group">
        <label for="nom">Nom</label>
        <input id="nom" type="text" class="form-control" v-model="user.nom" readonly>
      </div>
      <div class="form-group">
        <label for="prenom">Prénom</label>
        <input id="prenom" type="text" class="form-control" v-model="user.prenom" readonly>
      </div>
      <div class="form-group">
        <br>
        <strong>Rôles de l'utilisateur</strong>
        <br><br>
        <div class="form-check">
          <input class="form-check-input" type="checkbox"
                 id="redacteur" value="redacteurs" v-model="user.roles" :disabled="accessCheck">
          <label class="form-check-label" for="redacteur"> Rédacteur</label>
          <br>
          <input class="form-check-input" type="checkbox"
                 id="viseur" value="viseurs" v-model="user.roles" :disabled="accessCheck">
          <label class="form-check-label" for="viseur"> Viseur</label>
          <br>
          <input class="form-check-input" type="checkbox"
                 id="signataire" value="signataires" v-model="user.roles" :disabled="accessCheck">
          <label class="form-check-label" for="signataire"> Signataire</label>
          <br>
          <input class="form-check-input" type="checkbox"
                 id="receptionneur" value="receptionneurs" v-model="user.roles" :disabled="accessCheck">
          <label class="form-check-label" for="receptionneur"> Réceptionneur</label>
          <br>
          <input class="form-check-input" type="checkbox"
                 id="gestionnaire" value="gestionnaires" v-model="user.roles" :disabled="accessCheck">
          <label class="form-check-label" for="gestionnaire"> Gestionnaire</label>
          <br>
          <input class="form-check-input" type="checkbox"
                 id="admin" value="admins" v-model="user.roles" :disabled="accessCheck">
          <label class="form-check-label" for="admin"> Administrateur</label>
          <br>
        </div>
      </div>
      <button type="submit" class="btn btn-success">Enregistrer</button>
    </form>
  </div>
</template>

<script>
  export default {
    name: "DetailUser",
    props: ['user', 'accessCheck'],
    data() {
      return {
      }
    },
    watch: {
      $route: 'getUsers'
    },
    methods: {
      validerForm() {
        this.enregistrer();
        this.$snotify.success('Les droits de l\'utilisateur ont bien été changés !');
      },
      async enregistrer() {
        try {
          const url = `users/${this.user.id}`;
          const reponse = await this.$http['put'](url, this.user);
          if (reponse.status === 200) {
            // la fonction emit, permet d'utiliser une fonction du composant parent (ici Fournisseurs.vue)
            this.$emit('rechargerUsers');
          }
        } catch (e) {
          console.error(e);
        }
      }
    }
  }
</script>

<style scoped>
  .detail-user {
    padding: 5px;
    border-radius: 3px;
    background-color: aliceblue;
  }
</style>
