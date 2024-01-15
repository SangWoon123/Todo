<template>
  <q-container>
    <q-scroll-area style="height: 230px; max-width: 300px">
      <div class="q-pa-lg row no-wrap q-gutter-xl">
        <q-list v-for="n in 16" :key="n">
          <div class="history">
            <!-- 날짜 -->
            <div class="q-ml-sm col column">
              <span class="q-my-sm q-ml-md text-orange">2023.12.1</span>
              <q-separator inset color="orange" />
            </div>
            <!-- 수행여부 -->
            <div class="col q-ml-sm q-mt-lg">
              <div class="q-my-sm q-ml-md flex column">
                <div class="complete">Total: 5</div>
                <div class="complete">Complete: 5</div>
              </div>
            </div>
          </div>
        </q-list>
      </div>
    </q-scroll-area>
  </q-container>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
/* 히스토리-수행여부 */
.complete {
  font-size: 10px;
}

/* 히스토리 */
.history {
  width: 176px;
  height: 165px;
  border-radius: 20px;
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

.tag {
  width: 60px;
  height: 20px;
  color: white;
  text-align: center;
  background-color: #64e196;
  font-size: 15px;
  border-radius: 15px;
  /* 텍스트 가운데 정렬을 위한 스타일 추가 */
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
