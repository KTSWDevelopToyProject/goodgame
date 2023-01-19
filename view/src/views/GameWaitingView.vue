<template>
  <div class="common-layout">
    <el-container class="layout-container-demo">
      <el-aside class="aside" width="200px">

          <el-menu class="aside-root-menu" :default-openeds="['1', '2']">
            <el-sub-menu class="aside-sub-menu-1" index="1">
              <template #title>
              <div class="aside-sub-menu-1-title">
                <b>개설된 방 목록</b>
              </div>
              </template>
              <div v-for="room in rooms" :key="room.gameId" class="room-element">
                <div class="room-name">{{room.roomName}}</div>
                <el-button class="button">게임 참가</el-button>
              </div>
            </el-sub-menu>
            <el-sub-menu class="aside-sub-menu-2" index="2">
              <template #title>
                <div class="aside-sub-menu-2-title">
                  <b>접속자 목록</b>
                </div>
              </template>
            </el-sub-menu>
          </el-menu>

        <div class="aside-sub-menu-3">
            <div class="aside-sub-menu-3-title">
              <div v-if="drawer == false">
                <b>방 만들기</b>
              </div>
              <div v-if="drawer == true">
                <b>방 닫기</b>
              </div>
            </div>
          <el-button type="primary" style="margin-left: 16px" @click="drawer = true">
            PUSH
          </el-button>
          <el-drawer v-model="drawer" title="I am the title" :with-header="false" size="84%">
            <GameView></GameView>
          </el-drawer>
        </div>
      </el-aside>
      <el-container>
        <el-header class="header">
          <div class="header-title">배스킨라빈스 31</div>
        </el-header>
        <el-main class="main">
            <el-card class="box-card-1">
            </el-card>
          <el-card class="box-card-2">
          </el-card>
          <div class="game-play-view" :style="{display: isGamePlayView}">
            <GameViewTest>
              <!-- 여기에 게임 플레이 화면 들어옴 -->
            </GameViewTest>
          </div>
        </el-main>
        <el-footer class="footer">
          <div class="footer-title">Copyright</div>
        </el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import GameViewTest from "@/components/GameViewTest.vue";
import GameView from "@/views/GameView.vue";
import {ref} from 'vue';

export default {
  name: "GameWaitingView",

  components: {
    GameViewTest,
    GameView,
  },

  data() {
    return {
      baseUrl: `http://localhost:8080/waiting-rooms`,
      rooms : [],

      isGamePlayView : false,
      drawer : false,

    };
  },

  created() {
    const eventSource = new EventSource(this.baseUrl);
    eventSource.onopen = evnet => {
      console.log(evnet);
      console.log("응답 connection 열림!!!");
    };
    eventSource.onmessage = event => {
      const roomData = JSON.parse(event.data);
      this.rooms.push(roomData);
      console.log(this.rooms);
    };
    eventSource.onerror = error => {
      eventSource.close();
      console.log(error);
      console.log("에러 발생으로 응답 connection 닫힘!!!");
    };
  },

  methods: {
    gamePlayView() {
      this.isGamePlayView = !this.isGamePlayView;
      console.log(this.isGamePlayView);
    },

    drawerTest() {
     this.drawer = ref(false);
    }
  },
}
</script>

<style scoped>
.common-layout .layout-container-demo {
  height: 900px;
}
.layout-container-demo .header {
  position: relative;
  background-color: cornsilk;
  height: 80px;
  display: flex;
  align-items: center;
  flex-direction: row;
  justify-content: center;
}
.layout-container-demo .main {
  position: relative;
  background-color: blanchedalmond;
  height: 740px;
  padding: 0;
}
.layout-container-demo .footer {
  position: relative;
  background-color: antiquewhite;
  height: 80px;
  display: flex;
  align-items: center;
  flex-direction: row;
  justify-content: center;
}
.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
.layout-container-demo .aside {
  width: 200px;
  height: 900px;
}
.aside-root-menu .room-element {
  background-color: darkkhaki;
  opacity: 90%;
}
.aside-sub-menu-1 {
  height: 350px;
  width: 200px;
}
.aside-sub-menu-2 {
  height: 350px;
  width: 200px;
}
.aside-sub-menu-3 {
  height: 100px;
  width: 200px;
}
.box-card-1 {
  height: 370px;
}
.box-card-2 {
  height: 370px;
}
.room-element {
  scroll-behavior: auto;
}
.room-name {
  border: cadetblue;
  font-weight: bolder;
  margin: 5px 0 5px 0;
}
.button {
  border-color: darkolivegreen;
  width: 70px;
  height: 30px;
  margin-bottom: 10px;
}
.header-title {
  font-weight: bolder;
  font-size: larger;
}
.footer-title {
  font-weight: bolder;
}
</style>
