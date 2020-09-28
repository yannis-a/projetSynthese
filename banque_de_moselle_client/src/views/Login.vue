<template>
  <div>
    <h2>Login</h2>

    <form @submit.prevent="envoyerFormulaire">
      <div class="form-group">
        <label for="username">Username</label>
        <input type="text" v-model="username" id="username" name="username" class="form-control"
               :class="{ 'is-invalid': submitted && !username }"/>
        <div v-show="submitted && !username" class="invalid-feedback">Username is required</div>
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" v-model="password" id="password" name="password" class="form-control"
               :class="{ 'is-invalid': submitted && !password }"/>
        <div v-show="submitted && !password" class="invalid-feedback">Password is required</div>
      </div>
      <div class="form-group">
        <button class="btn btn-primary" :disabled="loading">Login</button>
        <img v-show="loading" alt="une image" src="../assets/loading.gif">
      </div>
      <div v-if="error" class="alert alert-danger">{{error}}</div>
    </form>
  </div>
</template>

<script>
  /* eslint-disable no-console */
  import {userService} from '../services/user.service';

  export default {
    name: "Login",
    data() {
      return {
        username: '',
        password: '',
        submitted: false,
        loading: false,
        returnUrl: '',
        error: ''
      }
    },
    created() {
      // reset de l'utilisateur connecté
      userService.logout();

      // recupère l'URL de retour depuis le parametre de route ou par defaut vers '/'
      this.returnUrl = this.$route.query.returnUrl || '/';
    },
    methods: {
      envoyerFormulaire() {
        this.submitted = true;
        const {username, password} = this;

        if (!(username && password)) {
          return;
        }

        this.loading = true;

        userService.login(username, password)
          .then(
            () => location.assign(this.returnUrl),
            error => {
              this.error = error;
              this.loading = false;
            }
          );
      }
    }
  }
</script>

<style scoped>
  img {
    width: 100px;
  }
</style>
