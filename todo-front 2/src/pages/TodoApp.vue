<template>
  <q-page class="q-ma-xl column">
    <!-- 이동버튼 -->
    <nav class="q-mb-md">
      <router-link to="sign">
        <q-btn
          color="grey-6"
          padding="none"
          flat
          unelevated
          icon="arrow_back_ios"
        ></q-btn>
      </router-link>
    </nav>

     <!-- 헤더 -->
    <TodoHeader @add-task="addTask" />
    <!-- 메인컨텐츠 -->
    <TaskList :tasks="tasks" @delete-task="deleteTask" />
    <!-- Todo 히스토리 -->
    <TodoHistory />
  </q-page>
</template>

<script>
import TaskList from "./content/TaskList.vue";
import TodoHeader from "./content/TodoHeader.vue";
import TodoHistory from "./content/history/TodoHistory.vue";

export default {
  data() {
    return {
     
    };
  },
  methods: {
    // 추가
    addTask(task) {
      if (task.trim() !== "") {
        this.addNewTask(task.trim());
      }
    },
    addNewTask(taskName) {
      const newTask = {
        id: this.tasks.length + 1,
        name: taskName,
        completed: false,
      };

      this.tasks.unshift(newTask);
    },
    deleteTask(id) {
      this.tasks.splice(id, 1);
    },
  },
  components: { TodoHeader, TodoHistory, TaskList },
};
</script>

<style></style>
