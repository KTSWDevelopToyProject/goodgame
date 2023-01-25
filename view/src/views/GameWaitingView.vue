<template>
  <div class="common-layout">
    <el-container class="layout-container">
      <el-aside class="aside">
          <el-menu class="aside-root-menu" :default-openeds="['1', '2']">
              <el-sub-menu class="aside-sub-menu-1" index="1">
                <template #title>
                <div class="aside-sub-menu-1-title">
                  <b>개설된 방 목록</b>
                </div>
                </template>
                <div class="room-element-wrapper">
                  <div v-for="room in rooms" :key="room.gameId" class="room-element">
                    <div class="room-name">{{room.roomName}}</div>
                    <el-button class="button">게임 참가</el-button>
                  </div>
                </div>
              </el-sub-menu>
            <el-sub-menu class="aside-sub-menu-2" index="2">
              <template #title>
                <div class="aside-sub-menu-2-title">
                  <b>접속자 목록</b>
                </div>
              </template>
              <div class="user-element-wrapper">
                <div v-for="user in ['김동현', '이상은']" :key="user" class="user-element">
                  <div class="user-name">{{user}}</div>
                  <el-button class="button">초대</el-button>
                </div>
              </div>
            </el-sub-menu>
          </el-menu>
        <div class="aside-sub-menu-3">
            <div class="aside-sub-menu-3-title">
              <div v-if="creatingRoomVisible == false">
                <div class="room-create-title">
                <b>방 만들기</b>
                </div>
                <div class="room-create-button">
                  <el-button type="primary" style="margin-left: 16px" @click="creatingRoomVisible = true">
                    PUSH
                  </el-button>
                </div>
              </div>
              <div v-if="creatingRoomVisible == true">
                <b>방 생성 중</b>
              </div>
            </div>
          <!-- 방 생성 모달 -->
          <el-dialog title="방 생성하기" v-model="creatingRoomVisible" width="800px">
            <creating-room-view @creatingRoomViewModalCancel="cancelCreatingRoomViewModal"
                                @enterGameRoom="enterGameRoom"
            ></creating-room-view>
          </el-dialog>

          <!-- 게임 방 화면 -->
          <el-drawer class="game-view-drawer" v-model="gameRoomVisible" title="game-play" :with-header="false" size=1580>
            <game-view
                :room="this.room"></game-view>
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
        </el-main>
        <el-footer class="footer">
          <div class="footer-title">Copyright</div>
        </el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import GameView from "@/views/GameView.vue";
import CreatingRoomView from "@/views/CreatingRoomView.vue";

export default {
  name: "GameWaitingView",

  components: {
    GameView,
    CreatingRoomView,
  },

  data() {
    return {
      baseUrl: `http://localhost:8080/waiting-rooms`,
      room : {
        roomName: "",
        leftParticipant: "",
        countOfParticipants: "",
        roomStatus: "",
      },
      rooms : [],

      creatingRoomVisible : false,
      gameRoomVisible : false,

    };
  },

  created() {
    const eventSource = new EventSource(this.baseUrl);
    eventSource.onopen = event => {
      console.log(event);
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

    enterGameRoom(data) {
      this.room = data;
      this.gameRoomVisible = true;
      this.cancelCreatingRoomViewModal();
    },

    cancelCreatingRoomViewModal() {
      this.creatingRoomVisible = false;
    },

  },
}
</script>

<style scoped>
.common-layout .layout-container {
  height: 960px;
}
.layout-container .header {
  position: relative;
  background-color: cornsilk;
  height: 80px;
  display: flex;
  align-items: center;
  flex-direction: row;
  justify-content: center;
  padding: 0px;
}
.layout-container .main {
  position: relative;
  background-color: blanchedalmond;
  height: 800px;
  padding: 0px;
}
.layout-container .footer {
  position: relative;
  background-color: antiquewhite;
  height: 80px;
  display: flex;
  align-items: center;
  flex-direction: row;
  justify-content: center;
  padding: 0px;
}
.layout-container .aside {
  width: 200px;
  height: 960px;
}
.aside-root-menu .room-element {
  background-color: darkkhaki;
  opacity: 90%;
}
.aside-root-menu .user-element {
  background-color: honeydew;
  opacity: 90%;
}
.aside-sub-menu-1 {
  height: 420px;
  width: 200px;
}
.aside-sub-menu-2 {
  height: 420px;
  width: 200px;
}
.aside-sub-menu-3 {
  height: 120px;
  width: 200px;
}
.box-card-1 {
  height: 400px;
  border: 0px;
}
.box-card-2 {
  height: 400px;
  border: 0px;
}
.room-element {
  scroll-behavior: auto;
}
.room-name {
  border: cadetblue;
  font-weight: bolder;
  margin: 5px 0 5px 0;
}
.user-name {
  border-color: cadetblue;
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
.room-element-wrapper {
  height: 350px;
  overflow: auto;
}
.user-element-wrapper {
  height: 350px;
  overflow: auto;
}
.room-create-title {
  margin-bottom: 10px;
}
</style>
