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
import { useTokenStore } from "src/stores/token";

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
    var accessToken = localStorage.getItem("accessToken");
    var refreshToken = localStorage.getItem("refreshToken");
    if (this.token.isTokenExpired(accessToken)) {
      axios
        .get(url, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            refreshToken: `Bearer ${refreshToken}`,
          },
        })
        .then((response) => {
          const newAccessToken = response.headers.authorization.substring(7);
          const newRefreshToken = response.headers.refreshtoken.substring(7);
          // 새로운 액세스 토큰과 리프레시 토큰을 로컬 스토리지에 업데이트합니다.
          localStorage.setItem("accessToken", newAccessToken);
          localStorage.setItem("refreshToken", newRefreshToken);
          // 상태 업데이트
          this.token.accessToken = response.headers.authorization.substring(7);
          this.token.refreshToken = response.headers.refreshtoken.substring(7);
          // 페이지내 데이터는 그대로 + 리로드
          this.$router.go(this.$router.currentRoute);
        })
    } else {
      axios
        .get(url, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
        .then((response) => {
          const userName = response.data.loginId;
          this.userName = userName;
        })
    }
  },
  setup() {
    const token = useTokenStore();

    return {
      token,
    };
  },
};
</script>

<style lang="scss" scoped></style>
