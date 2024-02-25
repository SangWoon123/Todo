<template>
  <q-page>
    <div class="column justify-center" style="height: 100vh">
      <!-- 로고 -->
      <span class="logo" style="margin-bottom: 100px">
        <div class="text-center">
          <div>
            <img
              src="../../assets/MyTodo.png"
              style="width: 41px; height: 41px"
            />
          </div>
        </div>
        <div class="text-center text-h2">
          <span>My</span>
          <span class="main-color">Todo</span>
        </div>
      </span>
      <!-- 버튼 -->
      <div>
        <!-- 카카오 Oauth-->
        <div class="column q-gutter-sm flex flex-center">
          <q-btn
            class="q-px-xl text-black bg-color login"
            color="yellow"
            style="
              color: white;
              width: 230px;
              height: 40px;
              border-radius: 12px;
            "
            unelevated
            size="10px"
            href="http://localhost:8080/oauth2/authorization/kakao"
            ><div class="kakao">
              <img class="absolute-left" src="src/assets/kakao.png" />
              <span> Kakao로 시작하기 </span>
            </div>
          </q-btn>

          <!-- 구분선 -->
          <div
            class="q-py-sm q-pb-lg"
            style="width: 230px; height: 12px; position: relative"
          >
            <div
              style="
                width: 230px;
                height: 0px;
                left: 0px;
                top: 8px;
                position: absolute;
                border: 0.5px #dadada solid;
              "
            ></div>
            <div
              style="
                width: 10px;
                height: 9px;
                left: 110px;
                top: 3px;
                position: absolute;
                background: white;
              "
            ></div>
            <div
              style="
                left: 110px;
                top: 0px;
                position: absolute;
                color: #a9a9a9;
                font-size: 10px;
                font-family: Inter;
                font-weight: 400;
                word-wrap: break-word;
              "
            >
              or
            </div>
          </div>
          <!-- 네이버 Oauth -->
          <q-btn
            class="q-px-xl text-black bg-color login"
            style="
              color: white;
              width: 230px;
              height: 40px;
              border-radius: 12px;
            "
            unelevated
            color="positive"
            size="10px"
          >
            <div class="naver">
              <img class="q-ma-sm absolute-left" src="src/assets/naver.png" />
              <span class="q-pl-sm"> Naver로 시작하기 </span>
            </div>
          </q-btn>
          <!-- 구글 Oauth -->
          <q-btn
            class="q-px-xl text-black login login-btn-google-github"
            unelevated
            color="white"
            size="10px"
          >
            <div class="google">
              <img class="q-ma-sm absolute-left" src="src/assets/google.png" />
              <span class="q-pl-sm"> Google로 시작하기 </span>
            </div>
          </q-btn>
          <!-- 깃헙 Oauth -->
          <q-btn
            class="q-px-xl text-black login login-btn-google-github"
            unelevated
            color="white"
            size="10px"
          >
            <div class="github">
              <img class="q-ma-sm absolute-left" src="src/assets/github.png" />
              <span class="q-pl-sm"> GitHub으로 시작하기 </span>
            </div>
          </q-btn>
        </div>
      </div>
      <!-- 이용약관 -->
      <div class="text-center q-mt-lg">
        <div style="opacity: 0.3">
          <span
            style="
              color: black;
              font-size: 12px;
              font-family: Inter;
              font-weight: 400;
              word-wrap: break-word;
            "
            >이용약관 </span
          ><span
            style="
              color: black;
              font-size: 12px;
              font-family: Inter;
              font-weight: 700;
              word-wrap: break-word;
            "
            >개인정보처리방침</span
          >
        </div>
      </div>
    </div>
  </q-page>
</template>

<script>
import { ref } from "vue";
import axios from "axios";

export default {
  setup() {
    return {
      dense: ref(true),
    };
  },
  methods: {
    loginWithKakao() {
    },
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
.logo {
  font-family: "Itim", cursive;
}
.main-color {
  color: $main;
}

.login-btn-google-github {
  border-radius: 12px;
  color: white;
  width: 230px;
  height: 40px;
  border: 1px solid black;
}

.github,
.google,
.naver,
.kakao {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.github > img,
.google > img {
  width: 25px;
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
