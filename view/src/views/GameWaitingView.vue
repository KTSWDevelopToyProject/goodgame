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
                    <el-button class="button"
                               v-if="room.roomStatus === 'W'"
                               style="background-color: green; color: white"
                               @click="participateInGameRoom(room)"
                    >대기 중</el-button>
                    <el-button disabled="true"
                               class="button"
                               v-if="room.roomStatus === 'A'"
                               style="background-color: saddlebrown;
                               color: white"
                    >게임 중</el-button>
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
              <div v-if="waitingRoom == true">
                <div class="room-create-title">
                <b>방 만들기</b>
                </div>
                <div class="room-create-button">
                  <el-button type="primary" style="margin-left: 16px" @click="onCreatingRoomVisible">
                    PUSH
                  </el-button>
                </div>
              </div>
              <div v-if="creatingRoomVisible == true">
                <b>방 생성 중</b>
              </div>
              <div v-if="gameRoomVisible == true">
                <b>방 입장 완료</b>
              </div>
              <div v-if="participateInRoomVisible == true">
                <b>방 참여 시도 중</b>
              </div>
            </div>
          <!-- 방 생성 모달 by 좌측 유저 -->
          <el-dialog title="방 생성하기" v-model="creatingRoomVisible" width="800px">
            <creating-room-view @creatingRoomViewModalCancel="cancelCreatingRoomViewModal"
                                @enterGameRoom="enterGameRoom"
            ></creating-room-view>
          </el-dialog>

          <!-- 방 생성 모달 by 우측 유저 -->
          <el-dialog title="방 참여하기" v-model="participateInRoomVisible" width="800px">
            <participate-in-room-view @participateInRoomViewModalCancel="cancelParticipateInRoomViewModel"
                                      @enterGameRoom="enterGameRoom" ref="ParticipateInRoomView"
            ></participate-in-room-view>
          </el-dialog>

          <!-- 게임 방 화면 -->
          <el-drawer class="game-view-drawer" v-model="gameRoomVisible" title="game-play" :with-header="false" size=1580>
            <game-view
                :createdRoom="this.createdRoom"
            ></game-view>
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
import ParticipateInRoomView from "@/views/@element-plus/ParticipateInRoomView.vue";

export default {
  name: "GameWaitingView",

  components: {
    ParticipateInRoomView,
    GameView,
    CreatingRoomView,
  },

  data() {
    return {
      baseUrl: `http://localhost:8080/waiting-rooms`,

      rooms : [],

      createdRoom : {},

      waitingRoom : true,
      creatingRoomVisible : false,
      participateInRoomVisible : false,
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
      let gameIdOfRemovedRoom = "";
      const roomData = JSON.parse(event.data);

      if (roomData.roomStatus ==="R") {
        gameIdOfRemovedRoom = roomData.gameId;
        this.rooms.forEach((room, index) => {
          if (room.gameId === gameIdOfRemovedRoom) {
            this.rooms.splice(index, 1);
          }
        });
      } else {
        this.rooms.push(roomData);
      }
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
      this.createdRoom = data;
      this.gameRoomVisible = true;
      this.cancelCreatingRoomViewModal();
      this.offParticipateInRoomVisible();
      this.onGameRoomVisible();
    },

    participateInGameRoom(room) {
      this.onParticipateInRoomVisible();
      this.$refs.ParticipateInRoomView.createdRoom = room;
    },

    cancelCreatingRoomViewModal() {
      this.creatingRoomVisible = false;
      this.waitingRoom = true;
    },

    cancelParticipateInRoomViewModel() {
      this.participateInRoomVisible = false;
      this.waitingRoom = true;
    },

    onWaitingRoom() {
      this.waitingRoom = true;
      this.creatingRoomVisible = false;
      this.gameRoomVisible = false;
    },
    offWaitingRoom() {
      this.waitingRoom = false;
    },

    onCreatingRoomVisible() {
      this.waitingRoom = false;
      this.creatingRoomVisible = true;
      this.gameRoomVisible = false;
    },
    offCreatingRoomVisible() {
      this.creatingRoomVisible = false;
    },

    onParticipateInRoomVisible() {
      this.waitingRoom = false;
      this.participateInRoomVisible = true;
      this.gameRoomVisible = false;
    },

    offParticipateInRoomVisible() {
      this.participateInRoomVisible = false;
      this.waitingRoom = true;
    },

    onGameRoomVisible() {
      this.waitingRoom = false;
      this.creatingRoomVisible = false;
      this.gameRoomVisible = true;
    },
    offGameRoomVisible() {
      this.gameRoomVisible = false;
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
