<template>
  <q-dialog
    class="bg-grey-2"
    transition-show="slide-up"
    transition-hide="slide-down"
    v-model="completed"
    @click="change"
    style="width: 0px"
  >
    <q-layout
      view="lhr Lpr lff"
      container
      style="width: 270px; height: 540px; border-radius: 20px"
      class="bg-white text-dark"
    >
      <!-- header -->
      <q-item class="q-pt-md text-orange">
        {{historys[0].today}}
      </q-item>
      <q-separator class="q-my-xs" color="orange" style="size: 16px" />

      <!-- HistoryList -->
      <div class="q-ma-md disabled-touch">
        <HistoryList :historys="historys" />
      </div>

      <!-- 피드백 창 -->
      <q-item style="font-size: 8px" class="q-ma-md feedback-box">
        <q-item-section>{{ savedFeedback }}</q-item-section>
      </q-item>

      <!-- 피드백 textarea -->
      <q-form @submit="updateFeedbackText" class="q-mx-md">
        <q-input
          :input-style="{ fontSize: '8px', height: '80px' }"
          type="textarea"
          outlined
          clearable
          label="이날의 피드백을 작성해주세요!"
          v-model="feedbackText"
        ></q-input>

        <!-- 확인버튼 -->
        <div class="q-mt-sm row justify-end">
          <q-btn
            type="submit"
            size="sm"
            push
            dense
            style="width: 80px"
            color="orange"
          >
            <div>확인</div>
          </q-btn>
        </div>
      </q-form>

      <!-- 취소버튼 -->
      <q-item class="row justify-end" style="width: 100%">
        <q-btn
          size="sm"
          push
          dense
          color="red"
          style="width: 80px; height: 10px"
          @click="completed = false"
          class="flex-center"
        >
          <div>close</div>
        </q-btn>
      </q-item>
    </q-layout>
  </q-dialog>
</template>

<script>
import { userTaskStore } from "src/stores/storage";
import HistoryList from "./HistoryList.vue";

export default {
  components: { HistoryList },
  props: {
    dialogVisible: Boolean,
    historys: Object,
  },
  data() {
    return {
      completed: this.dialogVisible,
      tasks: Array.from({ length: 5 }, (_, i) => {
        return {
          id: i + 1,
          name: `Task ${i + 1}`,
          completed: false,
        };
      }),
      feedbackText: "",
      savedFeedback: "",
    };
  },
  watch: {
    dialogVisible() {
      this.completed = true;
    },
  },
  watch: {
    dialogVisible(newVal) {
      // dialogVisible prop의 변화를 감지
      this.completed = newVal; // 변화가 감지되면 completed에 새로운 값을 할당
    },
  },
  methods: {
    change() {
      this.completed = true;
    },
    updateFeedbackText() {
      this.savedFeedback = this.feedbackText;
    },
  },
  setup() {
    const store = userTaskStore();
    return {
      store,
    };
  },
};
</script>

<style scoped>
/* 피드백 박스 */
.feedback-box {
  height: 50px;
  border: 1px solid orange;
  border-radius: 20px;
}
/* 터치 금지 */
.disabled-touch {
  pointer-events: none;
}
</style>
