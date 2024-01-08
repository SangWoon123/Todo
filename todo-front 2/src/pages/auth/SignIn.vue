<template>
  <q-page class="flex flex-center">
    <div class="column" style="height: 100vh">
      <!-- 로고 -->
      <div
        class="col-4 text-h2 flex flex-center q-mb-xl"
        style="font-family: 'Itim', cursive"
      >
        <span>My</span>
        <span class="main-color">Todo</span>
      </div>

      <div class="col">
        <q-form>
          <div class="q-gutter-y-sm">
            <q-input
              color="orange"
              label-color="orange"
              outlined
              v-model="ph"
              label="ID"
              :dense="dense"
              class="main-color input-size"
            />
            <q-input
              color="orange"
              label-color="orange"
              outlined
              v-model="ph"
              label="비밀번호"
              :dense="dense"
              class="input-size"
            />
          </div>

          <div class="column q-mt-sm flex flex-center">
            <!-- 회원가입 안내 메시지 -->
            <q-btn class="text-black q-btn2" flat size="10px" to="/auth/sign-up"
              ><span class="text-primary q-btn2"
                >회원가입이 필요하신가요?</span
              ></q-btn
            >
            <!-- 비밀번호 잊어버림 안내 메시지 -->
            <q-btn class="text-black" flat size="10px" to="/"
              ><span class="text-primary q-btn2"
                >비밀번호를 잊어버리셨나요?</span
              ></q-btn
            >
            <!-- 로그인 버튼 -->
            <router-link to="home">
              <q-btn
                class="q-px-xl text-black bg-color login"
                style="color: white; width: 230px; height: 30px"
                unelevated
                size="10px"
                ><span class="text-white">로그인</span></q-btn
              >
            </router-link>
          </div>
        </q-form>
        <!-- 구분선 -->
        <q-separator class="q-my-md" inset style="size: 16px" />

        <!-- 카카오, 네이버 Oauth-->
        <div class="column q-gutter-sm flex flex-center">
          <q-btn
            class="q-px-xl text-black bg-color login"
            color="yellow"
            style="color: white; width: 230px; height: 30px"
            unelevated
            size="10px"
            ><div class="kakao">
              <img class="absolute-left" src="src/assets/kakao.png" />
              <span> Kakao로 시작하기 </span>
            </div></q-btn
          >

          <q-btn
            class="q-px-xl text-black bg-color login"
            style="color: white; width: 230px; height: 30px"
            unelevated
            color="positive"
            size="10px"
          >
            <div class="naver">
              <img class="q-ma-xs absolute-left" src="src/assets/naver.png" />
              <span class="q-pl-sm"> Naver로 시작하기 </span>
            </div>
          </q-btn>
        </div>
      </div>
    </div>
  </q-page>
</template>

<script>
import { ref } from "vue";

export default {
  setup() {
    return {
      dense: ref(true),
    };
  },
};
</script>

<style>
.q-field--dense .q-field__control {
  border-radius: 10px;
  height: 33px;
}
.q-field__label {
  font-size: 10px;
}
</style>

<style lang="scss" scoped>
.main-color {
  color: $main;
}

.bg-color {
  background-color: $main;
}

.input-size {
  width: 250px;
}

.q-btn2 {
  border: none;
  text-decoration: underline;
  text-decoration-color: $primary;
  cursor: pointer;
}

.naver,
.kakao {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}
.naver > img {
  width: 25px;
}
.naver > span {
  color: white;
}

.kakao > img {
  width: 10px;
  height: 10px;
  top: 35%;
  left: 5%;
}

.kakao > span {
  color: black;
}
</style>
