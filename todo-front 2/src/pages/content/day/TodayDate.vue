<template>
  <div class="flex justify-between">
    <span class="q-ml-sm text-orange">{{ currentDate }}</span>
    <span class="text-orange" style="font-size: 10px"
      ><b>{{ userName }}</b> 님 환영합니다!</span
    >
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      currentDate: "",
      userName: "관리자",
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
  created() {
    const url = "http://localhost:8080/member";
    const accessToken=localStorage.getItem('accessToken');
    axios
      .get(url,{
        headers: {
    'Authorization': `Bearer ${accessToken}`
  }
      })
      .then((response) => {
        const userName = response.data.loginId;
        this.userName=userName;
      })
      .catch((error) => {
        console.error(error);
      });
  },
};
</script>

<style lang="scss" scoped></style>
