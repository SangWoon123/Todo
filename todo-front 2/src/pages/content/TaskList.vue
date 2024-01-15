<template>
  <q-container class="q-mt-lg">
    <q-list class="q-gutter-y-sm">
      <TaskItem
        v-for="(task, index) in visibleTasks"
        :key="index"
        :task="task"
        @delete="deleteTask(id)"
        @updateDescription="updateDescription"
        @deleteDescription="deleteDescription"
      />
    </q-list>
  </q-container>
</template>

<script>
import TaskItem from "./TaskItem.vue";

export default {
  components: {
    TaskItem,
  },
  props: ["tasks"],
  data() {
    return {};
  },
  methods: {
    updateDescription({ id, description }) {
      const task = this.tasks.find((task) => task.id === id);
      if (task) {
        task.description = description;
      }
    },
    deleteDescription(id) {
      const task = this.tasks.find((task) => task.id === id);
      if (task) {
        task.description = "";
      }
    },
    deleteTask(id) {
      this.$emit("delete-task", id);
    },
  },
  computed: {
    visibleTasks() {
      // 화면에 최대 5개의 항목만 표시
      return this.tasks.slice(0, 5);
    },
  },
};
</script>

<style lang="scss" scoped></style>
