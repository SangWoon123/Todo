<template>
  <q-container class="q-mt-lge">
    <q-scroll-area
      style="height: calc(218px); overflow-y: auto; max-width: 100%"
      class="test"
    >
    <q-list class="q-gutter-y-sm">
      <HistoryItem
        v-for="(task, index) in historys"
        :key="index"
        :todos="task"
      />
    </q-list>
    </q-scroll-area>
  </q-container>
</template>

<script>
import { userTaskStore } from "src/stores/storage";
import HistoryItem from "./HistoryItem.vue";

export default {
  props:["historys"],
  components: {
    HistoryItem,
  },
  data() {
    return {};
  },
  methods: {},
  computed: {
  },
  setup() {
    const store = userTaskStore();
    return { store };
  },
};
</script>

<style lang="scss" scoped>
</style>
