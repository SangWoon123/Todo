<template>
  <q-item class="todo-box">
    <!-- check 버튼 -->
    <q-item-section class="col-1 q-mr-sm">
      <q-checkbox
        dense
        v-model="completed"
        color="orange"
        @click="handleComplete"
      />
    </q-item-section>

    <q-item-section class="col">
      <div
        :style="{
          color: completed ? 'green' : 'black',
          textDecoration: completed ? 'line-through' : 'none',
        }"
      >
        {{ task.content }}
      </div>
    </q-item-section>

    <q-item-section class="col-1 q-mr-sm">
      <q-btn
        @click="$emit('delete', task.id)"
        size="8px"
        flat
        dense
        icon="delete"
      />
    </q-item-section>

    <div>
      <q-icon size="20px" :name="arrow" @click="clickArrow" />
    </div>
    <q-popup-edit
      v-if="showPopup"
      style="background-color: #fef7ee"
      :cover="false"
      fit
      :content-style="{ height: '1000px' }"
    >
      <q-item-label ellipsis class="popup-title q-mt-md">
        {{ task.description }}
      </q-item-label>

      <div
        style="
          color: black;
          font-size: 8px;
          font-family: Inter;
          font-weight: 400;
          word-wrap: break-word;
        "
      >
        상세 내용을 작성해주세요
      </div>
      <input
        v-model="descriptionInput"
        type="textarea"
        style="
          width: 254px;
          height: 65px;
          background: rgba(255, 255, 255, 0);
          border-radius: 10px;
          border: 1px rgba(187.44, 187.44, 187.44, 0.8) solid;
          padding:10px;
        "
      />

      <!-- 버튼 -->
      <div class="q-mt-sm q-gutter-sm flex justify-end">
        <div style="width: 90px; height: 15px; position: relative">
          <button
            @click="deleteDescription"
            style="
              width: 42px;
              height: 15px;
              left: 48px;
              top: 0px;
              position: absolute;
              background: #ff4141;
              border-radius: 20px;
              border: none;
            "
          >
            <div
              style="
                color: white;
                font-size: 8px;
                font-family: Inter;
                font-weight: 400;
                word-wrap: break-word;
              "
            >
              삭제
            </div>
          </button>
          <button
            @click="updateDescription"
            style="
              width: 42px;
              height: 15px;
              left: 0px;
              top: 0px;
              position: absolute;
              background: #416bff;
              border-radius: 20px;
              border: none;
            "
          >
            <div
              style="
                color: white;
                font-size: 8px;
                font-family: Inter;
                font-weight: 400;
                word-wrap: break-word;
              "
            >
              수정
            </div>
          </button>
        </div>
      </div>
    </q-popup-edit>
  </q-item>
</template>

<script>
import axios from "axios";
import { userTaskStore } from "src/stores/storage";

export default {
  props: ["task"],
  data() {
    return {
      completed: false,
      showPopup: false,
      arrow: "expand_more",
      descriptionInput: this.task.description,
    };
  },
  methods: {
    updateDescription() {
      this.$emit("updateDescription", {
        id: this.task.id,
        description: this.descriptionInput,
      });
    },
    deleteDescription() {
      this.descriptionInput="";
      this.$emit("deleteDescription", this.task.id);
    },
    clickArrow() {
      this.showPopup = !this.showPopup;
      if (this.showPopup) {
        this.arrow = "expand_less";
      } else {
        this.arrow = "expand_more";
      }
    },
    handleComplete() {
      this.$emit("complete", this.task.id);
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

<style>
/* 메인컨텐ㅊ  */
.todo-box {
  min-height: 38px;
  border-radius: 10px;
  box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
}

.q-popup-edit__buttons {
  background-color: beige;
}

.popup-title {
  margin-bottom: 5px;
  height: 50px;
}

.q-field__native {
  height: 45px;
}

.q-dialog__title {
  font-size: 10px;
}

.popup-title {
  font-size: 10px;
  max-width: 262px;
}

/* 라디오 버튼 클릭 */
.q-checkbox__bg {
  border-radius: 10px;
}

.button {
}
</style>
