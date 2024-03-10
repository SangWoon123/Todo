<template>
  <q-page class="q-ma-xl column">
    <!-- 이동버튼 -->
    <nav class="flex justify-between q-mb-md">
      <router-link to="sign">
        <q-btn
          color="grey-6"
          padding="none"
          flat
          unelevated
          icon="arrow_back_ios"
        ></q-btn>
      </router-link>

      <q-btn
        icon="logout"
        unelevated
        flat
        color="grey-6"
        size="10px"
        @click="confirm"
      ></q-btn>
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
import { useQuasar } from "quasar";
import TaskList from "./content/TaskList.vue";
import TodoHeader from "./content/TodoHeader.vue";
import TodoHistory from "./content/history/TodoHistory.vue";
import { useTokenStore } from "src/stores/token";

export default {
  data() {
    return {};
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
  setup() {
    const $q = useQuasar();
    const token = useTokenStore();

    function confirm() {
      $q.dialog({
        message: "정말로 로그아웃 하시겠습니까?",
        cancel: true,
        persistent: true,
      }).onOk(() => {
        token.accessToken = null;
        token.refreshToken = null;
        localStorage.clear();
        console.log("로그인 완료");
        console.log(token.accessToken);
        console.log(token.refreshToken);
        window.location.href = "/sign";
      });
    }
    return { confirm, token };
  },
};
</script>

<style></style>
