<template>
    <div></div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        accessToken: this.$route.query.accessToken,  // 라우트 매개변수에 접근
        refreshToken: this.$route.query.refreshToken,  // 라우트 매개변수에 접근
      };
    },
    mounted() {
      localStorage.clear();
      localStorage.setItem("accessToken", this.accessToken);
      localStorage.setItem("refreshToken", this.refreshToken);
      this.$router.replace("/home");  // 홈으로 리다이렉트
    },
  };
  </script>
  