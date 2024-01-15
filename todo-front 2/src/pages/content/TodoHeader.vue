<template>
  <q-container>
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
        >
          <template #append>
            <q-icon name="border_color" size="15px" color="grey-4" />
          </template>
        </q-input>
      </q-form>
    </div>
    <!-- 랜덤 명언 구문 -->
    <div class="q-mt-md text-center">
      <div class="flex flex-center">
        <span class="phrase">
          단순하게 살아라. 현대인은 쓸데없는 절차와 일 때문에 얼마나 복잡한 삶을
          살아가는가?
        </span>
        <span class="phrase">- 이드리스 샤흐 -</span>
      </div>
    </div>
  </q-container>
</template>

<script>
export default {
  data() {
    return {
      newTask: "",
      currentDate: "",
    };
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
    addTask() {
      if (this.newTask.trim() !== "") {
        this.$emit("add-task", this.newTask.trim());
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
</style>
