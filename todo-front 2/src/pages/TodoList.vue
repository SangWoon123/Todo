<template>
  <q-page class="q-ma-xl column">
    <q-nav class="q-mb-md">
      <router-link to="sign">
        <q-btn
          color="grey-6"
          padding="none"
          flat
          unelevated
          icon="arrow_back_ios"
        ></q-btn>
      </router-link>
    </q-nav>

    <q-container class="col">
      <!-- 헤더 -->
      <div>
        <span class="q-ml-sm text-orange">{{ currentDate }}</span>
        <q-form class="q-mt-sm" @submit.prevent="addTask">
          <q-input
            standout="text-black"
            placeholder="Add Todo+"
            v-model="newTask"
            @keyup.enter="addTask"
            class="rounded-input"
            ><template #append
              ><q-icon
                name="border_color"
                size="15px"
                color="grey-4"
              /> </template
          ></q-input>
        </q-form>
      </div>
      <!-- 랜덤 명언 구문 -->
      <div class="q-mt-md text-center">
        <div class="flex flex-center">
          <span class="phrase">
            단순하게 살아라. 현대인은 쓸데없는 절차와 일 때문에 얼마나 복잡한
            삶을 살아가는가?
          </span>
          <span class="phrase">- 이드리스 샤흐 -</span>
        </div>
      </div>

      <!-- 메인컨텐츠 -->
      <div class="q-mt-lg">
        <q-list v-for="(task, index) in visibleTasks" :key="index">
          <div class="row flex items-center bottom-line q-py-sm">
            <!-- Task버튼 -->
            <div class="col-3">
              <q-btn size="10px" flat padding class="tag"
                >Task{{ index + 1 }}</q-btn
              >
            </div>
            <!-- 텍스트 -->
            <div class="col-6">{{ task }}</div>
            <!-- 아이콘 -->
            <div class="col">
              <q-btn
                unelevated
                size="8px"
                icon="edit"
                dense
                class="q-ml-md"
              ></q-btn>
              <q-btn
                unelevated
                size="8px"
                color="red"
                icon="close"
                dense
                class="q-ml-sm"
                @click="deleteTask"
              ></q-btn>
            </div>
          </div>
        </q-list>
      </div>
    </q-container>

    <!-- Todo 히스토리 -->
    <q-container class="col">
      <q-scroll-area style="height: 230px; max-width: 300px">
        <div class="q-pa-lg row no-wrap q-gutter-xl">
          <q-list v-for="n in 16" :key="n">
            <div class="history">
              <!-- 날짜 -->
              <div class="q-ml-sm col column">
                <span class="q-my-sm q-ml-md text-orange">2023.12.1</span>
                <q-separator inset color="orange" />
              </div>
              <!-- 수행여부 -->
              <div class="col q-ml-sm q-mt-lg">
                <div class="q-my-sm q-ml-md flex column">
                  <div class="complete">Total: 5</div>
                  <div class="complete">Complete: 5</div>
                </div>
              </div>
            </div>
          </q-list>
        </div>
      </q-scroll-area>
    </q-container>
  </q-page>
</template>

<script>
export default {
  data() {
    return {
      newTask: "",
      tasks: Array.from({ length: 5 }, (_, i) => `Task ${i + 1}`),
      currentDate: "",
      offset: 5, // 스크롤 도달 시점
    };
  },
  computed: {
    visibleTasks() {
      // 화면에 최대 5개의 항목만 표시
      return this.tasks.slice(0, 5);
    },
  },
  mounted() {
    this.updateCurrentDate();
  },
  methods: {
    updateCurrentDate() {
      const now = new Date();
      const formattedDate = `${now.getFullYear()}.${(now.getMonth() + 1)
        .toString()
        .padStart(2, "0")}.${now.getDate().toString().padStart(2, "0")}`;
      this.currentDate = formattedDate;
    },
    deleteTask(index) {
      this.tasks.splice(index, 1);
    },
    addTask() {
      if (this.newTask.trim() !== "") {
        this.tasks.unshift(this.newTask.trim());
        this.newTask = "";
      }
    },
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
  font-size: 8px;
}

/* 메인컨텐ㅊ  */
.bottom-line {
  border-style: solid;
  border-width: 0px 0px 1px 0;
  border-color: orange;
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

/* 히스토리 */
.history {
  width: 176px;
  height: 165px;
  border-radius: 20px;
  box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
}

/* 히스토리-수행여부 */
.complete {
  font-size: 10px;
}
</style>
