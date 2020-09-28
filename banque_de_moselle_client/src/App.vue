<template>
  <div id="app" class="container">
    <nav id="menu">
      <ul class="navbar-collapse justify-content-center" v-if="userActuel">
        <li class="nav-item">
          <router-link to="/">Accueil</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/commandes">Commandes</router-link>
        </li>
        <li class="nav-item" v-if="isAdmin">
          <router-link to="/users">Utilisateurs</router-link>
        </li>
        <li class="nav-item" v-if="isAdmin">
          <router-link to="/articles/importer">Importer articles</router-link>
        </li>
        <li class="nav-item" v-if="isAdmin">
          <router-link to="/fournisseurs">Fournisseurs</router-link>
        </li>
        <li class="nav-item">
          <a href @click="logout">Déconnexion [<u>{{userActuel.username|capitalize}}</u>]</a>
        </li>
      </ul>


      <div class="col-12" v-if="!userActuel">
        <span>Identifiez-vous</span>
      </div>

    </nav>

    <router-view/>
    <vue-snotify/>
  </div>
</template>

<script>
  /* eslint-disable no-console */
  import {router} from './router';
  import {userService} from "./services/user.service";

  export default {
    name: 'App',
    data() {
      return {
        userActuel: ''
      }
    },
    created() {
      this.userActuel = userService.userActuel();
    },
    computed: {
      isAdmin: function () {
        return this.userActuel && this.possedeLeRoleAdmin(this.userActuel);
      }
    },
    methods: {
      logout() {
        userService.logout();
        router.navigate(['/login']);
      },
      possedeLeRoleAdmin(user) {
        for (const element of user.authorities) {
          if (element['authority'] === 'ROLE_ADMINS') {
            return true;
          }
        }
        return false;
      },
    }
  }
</script>

<style>
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
  }

  #menu div {
    height: 50px;
    margin-top: 2em;
    margin-bottom: 2em;
    padding: 0;
    text-align: center;
    background: rgb(0, 130, 255);
    border-radius: 10px;
    box-shadow: 0 1px 3px rgba(0, 130, 255, .3),
    0 3px 5px rgba(0, 130, 255, .2),
    0 5px 10px rgba(0, 130, 255, .2),
    0 20px 20px rgba(0, 130, 255, .15);
  }

  #menu div span {
    color: white;
    font-size: 2em;
  }

  #menu ul {
    margin-top: 2em;
    margin-bottom: 2em;
    width: 100%;
    display: table;
    padding: 0;
    background: rgb(142, 198, 63);
    border-radius: 10px;
    box-shadow: 0 1px 3px rgba(142, 198, 63, .3),
    0 3px 5px rgba(142, 198, 63, .2),
    0 5px 10px rgba(142, 198, 63, .2),
    0 20px 20px rgba(142, 198, 63, .15);
  }

  #menu ul li {
    display: table-cell;
  }

  #menu ul li a {
    display: block;
    text-align: center;
    color: white;
    text-decoration: none;
    padding: 1em 8px 1em 8px;
    transition: padding .3s .1s, background .3s .1s;
  }

  #menu ul li:first-child a {
    border-radius: 3px 0 0 3px;
  }

  #menu ul li:last-child a {
    border-radius: 0 3px 3px 0;
  }

  #menu ul li a:hover, #menu ul li a:focus {
    background: rgba(255, 255, 255, .2);
    padding: 1em 25px 1em 25px;
    transition: padding .3s 0s, background .3s 0s;
  }

  #menu ul li a:active {
    background: linear-gradient(rgba(0, 0, 0, .2), rgba(0, 0, 0, .1));
    box-shadow: 0 0 2px rgba(0, 0, 0, .3) inset;
  }
</style>
