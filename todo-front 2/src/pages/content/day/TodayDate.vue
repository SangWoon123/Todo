<template>
  <span class="q-ml-sm text-orange">{{ currentDate }}</span>
</template>

<script>
export default {
  data() {
    return {
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
  },
};
</script>

<style lang="scss" scoped></style>
