<template>
  <q-item class="todo-box">
    <q-item-section class="col-1 q-mr-sm">
      <q-btn round outline color="orange" size="7px"> </q-btn>
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
      <q-icon size="20px" name="expand_more" @click="showPopup = !showPopup" />
    </div>
    <q-popup-edit
      v-if="showPopup"
      class="bg-orange-3"
      title="설명:"
      :cover="false"
      fit
    >
      <q-item-label class="popup-title ellipsis">
        {{ task.description }}
      </q-item-label>
      <q-input
        v-model="descriptionInput"
        filled
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
      showPopup: false,
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
      this.$emit("deleteDescription", this.task.id);
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
</style>
