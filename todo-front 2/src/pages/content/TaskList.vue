<template>
  <q-container class="q-mt-lg">
    <q-scroll-area
      style="height: calc(271px); overflow-y: auto; max-width: 100%"
      class="test"
    >
      <q-list class="q-gutter-y-sm">
        <TaskItem
          v-for="(task, index) in taskList"
          :key="index"
          :task="task"
          @delete="store.deleteTask(task.id)"
          @complete="store.updateComplete(task.id)"
          @updateDescription="store.updateDescription"
          @deleteDescription="store.deleteDescription"
        />
      </q-list>
    </q-scroll-area>
  </q-container>
</template>

<script>
import TaskItem from "./TaskItem.vue";
import { userTaskStore } from "src/stores/storage";
import { computed, ref, onMounted } from "vue";

export default {
  components: {
    TaskItem,
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
  },
  setup() {
    const store = userTaskStore();
    const taskList = computed(() => store.tasks);
    onMounted(async () => {
      // onMounted 훅에서 getAllTasks 호출
      await store.getAllTasks();
    });
    return { store, taskList };
  },
};
</script>

<style lang="scss" >
.test {
  width: 100%; /* 원하는 너비로 조절 */
  min-height: 38px;
  border: none;
  border-radius: 10px;
  box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
}
</style>
