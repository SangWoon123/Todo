<template>
  <q-container class="q-mt-lg">
    <q-list class="q-gutter-y-sm">
      <HistoryItem
        v-for="(task, index) in historys"
        :key="index"
        :todos="task"
      />
    </q-list>
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
    visibleTasks() {
      // 화면에 최대 5개의 항목만 표시
      return this.tasks.slice(0, 5);
    },
    // todosArray() {
    //   return this.store.historys;
    // },
  },
  created(){
    console.log(this.store.historys);
  },
  setup() {
    const store = userTaskStore();
    return { store };
  },
};
</script>

<style lang="scss" scoped></style>
