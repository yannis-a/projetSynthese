<template>
  <div class="row">
    <div class="col-10" style="margin: auto;">
      <h2>Importer des articles</h2>
      <label>Fichier CSV
        <input type="file" id="file" ref="file" class="btn btn-outline-info" v-on:change="chargerFichier()"/>
      </label>
      <button class="btn btn-outline-primary" style="margin: 5%" v-on:click="importer()">Importer</button>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Import",
    data() {
      return {
        fichier: ''
      }
    },
    methods: {
      chargerFichier() {
        this.fichier = this.$refs.file.files[0];
      },
      async importer() {
        let formData = new FormData();
        formData.append('file', this.fichier);

        try {
          // eslint-disable-next-line no-unused-vars
          const reponse = await this.$http.post('artciles/importer', formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })
        } catch (e) {
          console.error(e);
        }
      }
    }
  }
</script>

<style scoped>
</style>
