<template>
  <q-item class="todo-box">
    <!-- check 버튼 -->
    <q-item-section class="col-1 q-mr-sm">
      <q-checkbox
        dense
        v-model="completed"
        color="orange"
      />
    </q-item-section>

    <q-item-section class="col">
      <div>{{ task.name }}</div>
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
      class="bg-orange-3"
      title="설명:"
      :cover="false"
      fit
    >
      <q-item-label ellipsis class="popup-title">
        {{ task.description }}
      </q-item-label>
      <q-input
        v-model="descriptionInput"
        standout
        unelevated
        type="textarea"
        class="bg-white"
        dark
        :input-style="{ color: 'orange' }"
      />
      <div class="q-mt-sm q-gutter-sm flex justify-end">
        <q-btn unelevated dense color="primary" @click="updateDescription">
          수정
        </q-btn>
        <q-btn unelevated dense color="red" @click="deleteDescription">
          삭제
        </q-btn>
      </div>
    </q-popup-edit>
  </q-item>
</template>

<script>
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
    // taskClick(index) {
    //   this.$emit("taskClick", {
    //     id: index,
    //     completed: this.completed,
    //   });
    // },
    updateDescription() {
      this.$emit("updateDescription", {
        id: this.task.id,
        description: this.descriptionInput,
      });
    },
    deleteDescription() {
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
  },
};
</script>

<style>
/* 메인컨텐ㅊ  */
.todo-box {
  width: 100%;
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
</style>
