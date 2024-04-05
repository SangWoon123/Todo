import axios from 'axios';
import { defineStore } from 'pinia'

export const useTokenStore = defineStore('token', {
  state: () => ({
    accessToken: null,
    refreshToken:null,
  }),

  getters: {
   
  },

  actions: {
    isTokenExpired(accessToken) {
      if (!accessToken) return true; // 토큰이 없으면 만료됨

      const tokenData = JSON.parse(atob(accessToken.split('.')[1])); // 토큰을 디코딩하여 정보 추출
      const expiration = tokenData.exp * 1000; // 만료 시간 (밀리초 단위)

      return Date.now() >= expiration; // 현재 시간과 비교하여 만료 여부 확인
    },

    refreshAccessToken(url,accessToken,refreshToken){
      axios
      .get(url, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          refreshToken: `Bearer ${refreshToken}`,
        },
      })
      .then((response) => {
        console.log("refreshToken설동")
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
    }
  },
})
