<template>
  <q-container>
    <q-scroll-area style="height: 230px; max-width: 300px">
      <div class="q-pa-lg row no-wrap q-gutter-xl">
        <q-list v-for="(data, n) in store.historys" :key="n">
          <q-btn dense rounded unelevated @click="toggleLayout(n)">
            <!-- TodoPopup  -->
            <TodoPopup
              :dialogVisible="dialogVisible[n]"
              :todos="data.todos"
              :historys="data"
              @close="toggleLayout(n)"
            />

            <div class="column history">
              <!-- 날짜 -->
              <div class="col-4">
                <div class="q-ma-sm text-orange row flex-center">
                  {{ data.day }}
                  <img
                    v-show="data.emotion === 'UNHAPPY' ? true : false"
                    src="~/assets/unhappy.png"
                    style="width: 20px; margin-left: 15px"
                  />
                  <img
                    v-show="data.emotion === 'HAPPY' ? true : false"
                    src="~/assets/happy.png"
                    style="width: 20px; margin-left: 15px"
                  />
                </div>
                <q-separator inset color="orange" />
                <!-- 이모지 -->
              </div>
              <!-- 수행여부 -->
              <div class="col q-mt-sm">
                <div class="complete">Total: {{ data.total }}</div>
                <div class="complete">Complete: {{ data.complete }}</div>
              </div>
            </div>
          </q-btn>
        </q-list>
      </div>
    </q-scroll-area>
  </q-container>
</template>

<script>
import TodoPopup from "./TodoPopup.vue";
import { userTaskStore } from "src/stores/storage";
import { data } from "autoprefixer";

export default {
  components: { TodoPopup },
  data() {
    return {
      dialogVisible: {},
      datas: {},
    };
  },
  methods: {
    toggleLayout(n) {
      this.dialogVisible[n] = !this.dialogVisible[n];
    },
  },
  watch: {
    dialogVisible() {
      this.dialogVisible[n] = false;
    },
    emotion() {
      if (data.emotion === "HAPPY") {
        this.emoji = true;
      } else if (data.emotion === "UNHAPPY") {
        console.log("zzzz");
        console.log(data.emotion);
        this.emoji = false;
      }
    },
  },
  async created() {
    await this.store.getHistory();
  },
  setup() {
    const store = userTaskStore();
    return {
      store,
    };
  },
};
</script>

<style lang="scss" scoped>
/* 히스토리-수행여부 */
.complete {
  font-size: 10px;
}

/* 히스토리 */
.history {
  width: 176px;
  height: 165px;
  border-radius: 20px;
  box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
}

.tag {
  width: 60px;
  height: 20px;
  color: white;
  text-align: center;
  background-color: #64e196;
  font-size: 15px;
  border-radius: 15px;
  /* 텍스트 가운데 정렬을 위한 스타일 추가 */
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
