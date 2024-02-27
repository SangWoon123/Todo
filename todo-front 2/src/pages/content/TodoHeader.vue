<template>
  <q-container>
    <!-- 헤더 -->
    <div>
      <TodayDate />
      <q-form class="q-mt-sm" @submit.prevent="addTask">
        <q-input
          standout="text-black"
          placeholder="Add Todo+"
          v-model="newTask"
          @keyup.enter="addTask"
        >
          <template #append>
            <q-icon name="border_color" size="15px" color="grey-4" />
          </template>
        </q-input>
      </q-form>
    </div>
    <!-- 랜덤 명언 구문 -->
    <div class="q-my-lg text-center">
      <div class="flex flex-center">
        <span name="fade" class="phrase">
          {{ quote }}
        </span>
        <!-- <span class="phrase">- 이드리스 샤흐 -</span> -->
      </div>
    </div>
  </q-container>
</template>

<script>
import axios from "axios";
import TodayDate from "./day/TodayDate.vue";
import { userTaskStore } from "src/stores/storage";
import { computed, ref } from "vue";

export default {
  components: { TodayDate },
  data() {
    return {
      quote: "",
    };
  },

  methods: {
    fetchQuote() {
      const quoteUrl = `http://localhost:8080/quote`;
      axios.get(quoteUrl).then((res) => {
        this.quote = res.data.slip.advice;
      });
    },
  },
  created() {
    this.fetchQuote();
    // setInterval(this.fetchQuote, 10000);
  },
  setup() {
    const store = userTaskStore();
    const newTask = ref("");

    const tasks = computed(() => store.tasks);

    const addTask = () => {
      store.addTask(newTask.value);
      newTask.value = "";
    };

    return { newTask, addTask, tasks };
  },
};
</script>

<style>
.q-field--standout .q-field__control {
  background-color: white;
  height: 50px;
  border-radius: 15px;
  box-shadow: 0px 0px 20px 1px rgb(230, 230, 230);
}

.q-placeholder {
  color: orange;
}

/* 명언 */
.phrase {
  font-size: 10px;
}

/* 넘김 효과를 위한 CSS를 추가합니다. */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
