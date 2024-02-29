<template>
  <q-dialog
    class="bg-grey-2"
    transition-show="slide-up"
    transition-hide="slide-down"
    v-model="completed"
    @click="handleClickOutside"
    style="width: 0px"
  >
    <q-layout
      view="lhr Lpr lff"
      container
      style="width: 270px; height: 550px; border-radius: 20px"
      class="bg-white text-dark"
    >
      <!-- header -->
      <q-item class="q-pt-md text-orange">
        {{ todos[0].today }}
      </q-item>
      <q-separator class="q-my-xs" color="orange" style="size: 16px" />

      <!-- HistoryList -->
      <div class="q-ma-md">
        <HistoryList :historys="todos" />
      </div>

      <q-form @submit="updateFeedbackText()" class="q-mx-md">
        <q-item class="flex-center q-column">
          <q-btn
            unelevated
            rounded
            @click="store.changeImage(historys.id, 'happy')"
          >
            <img
              v-show="
                this.store.unhappy[historys.id - 1] === true ||
                this.store.happy[historys.id - 1] === false
                  ? true
                  : false
              "
              src="~/assets/happy-notclick.png"
              class="emoji"
            />
            <img
              v-show="this.store.happy[historys.id - 1] === true ? true : false"
              src="~/assets/happy.png"
              class="emoji"
            />
          </q-btn>
          <q-btn
            unelevated
            rounded
            @click="store.changeImage(historys.id, 'unhappy')"
          >
            <img
              v-show="
                this.store.happy[historys.id - 1] === true ||
                this.store.unhappy[historys.id - 1] === false
                  ? true
                  : false
              "
              src="~/assets/unhappy-notclick.png"
              class="emoji"
            />

            <img
              v-show="
                this.store.unhappy[historys.id - 1] === true ? true : false
              "
              src="~/assets/unhappy.png"
              class="emoji"
            />
          </q-btn>
        </q-item>
        <!-- 피드백 textarea -->
        <!-- 텍스트 area -->
        <div style="width: 223px; height: 96px; position: relative">
          <input
            v-model="feedbackText"
            :style="{
              backgroundColor: historys.feedback ? '#416BFF' : '#ECECEC',
            }"
            type="textarea"
            style="
              width: 215px;
              height: 96px;
              left: 8px;
              top: 0px;
              position: absolute;
              border-radius: 10px;
              border: none;
              padding: 10px;
              color: orange;
            "
          />
          <div
            style="
              left: 17px;
              top: 10px;
              position: absolute;
              color: #afafaf;
              font-size: 10px;
              font-family: Inter;
              font-weight: 400;
              word-wrap: break-word;
            "
          >
            Write Your Comments here
          </div>
        </div>
        <!-- 선 -->
        <div
          class="q-my-sm"
          style="width: 237px; height: 0px; border: 0.5px #ececec solid"
        ></div>

        <!-- 확인버튼 -->
        <div style="width: 223px; height: 38px; position: relative">
          <button type="submit" class="submit-button"></button>
          <div class="submit-text">Submit feedback</div>
        </div>
      </q-form>
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
    todos: Object,
    historys: Object,
  },
  data() {
    return {
      completed: this.dialogVisible,
      feedbackText: "",
      savedFeedback: "",
      submitted: false, // 피드백 제출한 여부
    };
  },
  watch: {
    dialogVisible(newVal) {
      this.completed = newVal; // 변화가 감지되면 completed에 새로운 값을 할당
    },
  },
  methods: {
    handleClickOutside(event) {
      // Dialog 요소 밖을 클릭했을 때만 이벤트를 발생시킵니다.
      if (!this.$el.contains(event.target)) {
        this.completed = false;
        this.$emit("close");
      }
    },
    change() {
      this.completed = true;
    },
    updateFeedbackText() {
      this.store
        .updateFeedbackText(this.historys.id, this.feedbackText)
        .then(() => {
          this.savedFeedback = this.feedbackText;
          this.submitted = true;
        });
    },
  },
  created() {
    if (this.historys.feedback) {
      this.feedbackText = this.historys.feedback;
    }
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
  height: 80px;
  border: 1px solid orange;
  border-radius: 20px;
}
/* 터치 금지 */
.disabled-touch {
  pointer-events: none;
}

.submit-button {
  width: 223px;
  height: 38px;
  left: 8px;
  top: 0px;
  position: absolute;
  background: #416bff;
  border-radius: 5px;
  border: none;
  transition: transform 0.1s;
}

.submit-button:active {
  transform: scale(0.7);
}

.submit-text {
  left: 50px;
  top: 7px;
  position: absolute;
  color: white;
  font-size: 20px;
  font-family: Inter;
  font-weight: 400;
  word-wrap: break-word;
}

.emoji {
  width: 25px;
}
</style>
