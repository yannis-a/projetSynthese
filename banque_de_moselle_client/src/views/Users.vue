<template>
  <div class="row">
    <h1 class="col-12">Liste des utilisateurs</h1>
    <div class="col-6">
      <br>
      <input id="user-search" class="col-xs-12" list="liste-users" autocomplete="off"
             value="" placeholder="Rechercher un utilisateur">
      <datalist id="liste-users">
        <option v-for="user in users" :key="user.id" :value="user.nomComplet"></option>
      </datalist>
      <button type="submit" class="btn btn-link" v-on:click="optionChoisie">Rechercher</button>
      <br>
      <br>
      <ul class="liste">
        <li v-for="user in usersPagination" :key="user.id"
            class="item" @click.prevent="chargerUnUser(user.id)">
          {{user.nomComplet|capitalize}}
        </li>
      </ul>
      <Pagination :elements="users" :nbPages="nbPages" :changerTableau="setUsersPagination"/>
    </div>
    <div class="col-6">
      <DetailUser :user="userForm" :access-check="accessCheck" @rechargerUsers="rechargerUsers"/>
    </div>
  </div>
</template>

<script>
  import DetailUser from "../components/DetailUser";
  import Pagination from "../components/Pagination";


  export default {
    name: "Users",
    components: {
      DetailUser,
      Pagination,
    },
    data() {
      return {
        users: [],
        userForm: {},
        usersPagination: [],
        nbPages: 0,
        accessCheck: true,
      }
    },
    async mounted() {
      await this.getUsers();
      this.usersPagination = this.users.slice(0, Pagination.PAR_PAGE);
      this.calculerNombrePage();
    },
    methods: {
      async getUsers() {
        try {
          const reponse = await this.$http.get('users');
          this.users = reponse.data;
        } catch (e) {
          console.error(e);
        }
      },
      async rechargerUsers() {
        await this.getUsers();
        this.calculerNombrePage();
      },
      async chargerUnUser(id) {
        try {
          const reponse = await this.$http.get('users/' + id);
          this.userForm = reponse.data;
          this.accessCheck = false;
        } catch (e) {
          console.error(e);
        }
      },
      optionChoisie() {
        console.log('Pas encore implémenté...');
      },
      setUsersPagination: function (nouveauTableau) {
        this.usersPagination = nouveauTableau;
      },
      calculerNombrePage() {
        const nbUsers = this.users.length;
        this.nbPages = Math.floor(nbUsers / Pagination.PAR_PAGE) + (nbUsers % Pagination.PAR_PAGE === 0 ? 0 : 1);
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
