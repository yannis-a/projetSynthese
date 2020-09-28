<template>
  <paginate
    v-model="page"
    :page-count="nbPages"
    :page-range="3"
    :margin-pages="2"
    :click-handler="afficherPageDemandee"
    :prev-text="'<<'"
    :next-text="'>>'"
    :container-class="'pagination'"
    :prev-link-class="'page-link'"
    :next-link-class="'page-link'"
    :page-link-class="'page-link'"
    :page-class="'page-item'">
  </paginate>
</template>

<script>
  const PAR_PAGE = 5;

  export default {
    name: 'Pagination',
    props: ['elements', 'nbPages', 'changerTableau'],
    data: function () {
      return {
        page: 1
      }
    },
    methods: {
      afficherPageDemandee: function (pageNum) {
        const debut = (pageNum - 1) * PAR_PAGE;
        this.changerTableau(this.elements.slice(debut, debut + PAR_PAGE));
      }
    },
    PAR_PAGE: PAR_PAGE
  }
</script>

<style scoped>
</style>
