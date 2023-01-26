<template>
  <div>



    <div id="wrapper">


      <div id="content">

        <div id="gameId">
          <span id="game_id"></span>
        </div>
        <div id="user1_info">
          <span id="user1_id"></span>
          <span>/</span>
          <span id="user1_name"></span>
          <span>/</span>
          <span id="user1_win"></span>
          <span>/</span>
          <span id="user1_total"></span>
          <span>/</span>
          <span id="user1_winning_rate"></span>
        </div>
        <div id="user2_info">
          <span id="user2_id"></span>
          <span>/</span>
          <span id="user2_name"></span>
          <span>/</span>
          <span id="user2_win"></span>
          <span>/</span>
          <span id="user2_total"></span>
          <span>/</span>
          <span id="user2_winning_rate"></span>
        </div>




        <div id="game_score">

          <span id="gameScore"></span>

        </div>

        <el-button id="circle_plus"
                   :disabled="isCirclePlusDisabled"
                   @click="circlePlusClickEvent()"
        >
          <el-icon ><ArrowUp id="circlePlus"
                             :opacity="isCirclePlusOpacity"
                             style="width: 55px; height: 55px" /></el-icon>
        </el-button>

        <el-button id="select_btn"
                   :disabled="isSelectBtnDisabled"
                   @click="selectBtnClickEvent()"
        >
          <el-icon ><Check id="selectBtn"
                           :opacity="isSelectBtnOpacity"
                           style="width: 55px; height: 55px"/></el-icon>
        </el-button>


        <el-button id="exit_btn"
                   :disabled="isExitBtnDisabled"
                   @click="exitBtnClickEvent()"
        >
          <el-icon ><Check id="exitBtn"
                           :opacity="isExitBtnOpacity"
                           style="width: 55px; height: 55px"/></el-icon>
        </el-button>



      </div>
    </div>






  </div>



  <el-dialog
      v-model="dialogVisible"
      title="Tips"
      width="30%"
      :before-close="handleClose"
  >
    <span>게임이 종료되었습니다.</span>
    <template #footer>
      <span class="dialog-footer">
<!--        <el-button @click="clickDialogButton(false)">Cancel</el-button>-->
        <el-button type="primary" @click="clickDialogButton(true)">
          종료
        </el-button>
      </span>
    </template>
  </el-dialog>


</template>

<script>


export default {
  name: 'GameView',
  components: {

  },
  props: {
    createdRoom: {
      type: Object,
    },
  },
  data() {
    return {
      gameMaxScore: 3,
      eventSource: '',
      gameId: '',
      userId: '',
      leftParticipant: '',
      rightParticipant: '',
      isCirclePlusDisabled: true,
      isCirclePlusOpacity: 0.5,
      isSelectBtnDisabled: true,
      isSelectBtnOpacity: 0.5,
      isExitBtnDisabled: true,
      isExitBtnOpacity:0.5,
      currentJoinedMember: 0,
      dialogVisible: false,
      user1_info: {},
      user1_rating: {},
      user2_info: {},
      user2_rating: {},
    };
  },
  created() {
    // this.gameId = prompt('gameId?');
    // this.userId = prompt('userId?');
    this.gameId = this.createdRoom.gameId;
    this.userId = this.createdRoom.currentUserId;
// @ is an alias to /src
    const eventSource = new EventSource(`http://localhost:8080/game/${this.gameId}`);

    eventSource.onmessage = (event) => {

      const data = JSON.parse(event.data);

      this.changeGameView(data);

    };
  },
  methods: {
    isEmpty(value, setValue) {
      if (value == null || value.length === 0 || Number.isNaN(value)) {
        if (setValue == null || setValue.length === 0) {
          return "";
        } else {
          return setValue;
        }
      } else {
        return value;
      }
    },
    async setUserInfo(data) {
      let response;
      let response2;

      await fetch(`http://localhost:8080/member/user-id/${data.currentUserId}`, {
        method: "get",
        headers: {
          "Content-Type": "application/json; charset=utf-8"
        }
      }).then(res => res.json()).then(data => response = data).catch(err => {
        response = {};
        console.log(err);
      });

      await fetch(`http://localhost:8080/rating/${data.currentUserId}`, {
        method: "get",
        headers: {
          "Content-Type": "application/json; charset=utf-8"
        }
      }).then(res => res.json()).then(data => response2 = data).catch(err => {
        response2 = {};
        console.log(err);
      });




      switch (data.currentUserId) {
        case data.leftParticipant:
          this.user1_info = response;
          this.user1_rating = response2;
          document.querySelector("#user1_id").textContent = this.isEmpty(this.user1_info.userId);
          document.querySelector("#user1_name").textContent = this.isEmpty(this.user1_info.userName);
          document.querySelector("#user1_win").textContent = this.isEmpty(this.user1_rating.win, 0);
          document.querySelector("#user1_total").textContent = this.isEmpty(this.user1_rating.total, 0);
          document.querySelector("#user1_winning_rate").textContent = String(this.isEmpty((this.user1_rating.win / this.user1_rating.total) * 100, 0));
          console.log("user1 userId : " + this.user1_info.userId + ", user1 userName : " + this.user1_info.userName);
          break;
        case data.rightParticipant:
          this.user2_info = response;
          this.user2_rating = response2;

          document.querySelector("#user2_id").textContent = this.isEmpty(this.user2_info.userId);
          document.querySelector("#user2_name").textContent = this.isEmpty(this.user2_info.userName);
          document.querySelector("#user2_win").textContent = this.isEmpty(this.user2_rating.win, 0);
          document.querySelector("#user2_total").textContent = this.isEmpty(this.user2_rating.total, 0);
          document.querySelector("#user2_winning_rate").textContent = String(this.isEmpty((this.user2_rating.win / this.user2_rating.total) * 100, 0));
          console.log("user2 userId : " + this.user2_info.userId + ", user2 userName : " + this.user2_info.userName);
          break;
        default:
          console.log("ERROR");
          break;
      }
    },
    changeGameView(data) {
      console.log(data);
      this.leftParticipant = data.leftParticipant;
      this.rightParticipant = data.rightParticipant;
      document.querySelector("#gameScore").textContent = '';
      document.querySelector("#gameScore").textContent = data.gameScore;

      // if (data.gameScore >= this.gameMaxScore) {
      //   this.buttonAction(true);
      //
      //   this.dialogVisible = true;
      //   this.sendMessage(true);
      //   return;
      // }

      switch (data.status) {
        case "E":
          this.buttonAction(true);
          console.log("Game Is End!");
          this.dialogVisible = true;
          break;
        case "G":
          if (this.userId === data.currentUserId) {
            this.buttonAction(false);
          } else {
            this.buttonAction(true);
          }
          break;
        case "S":
          if (this.userId === data.currentUserId) {
            this.buttonAction(true);
          } else {
            this.buttonAction(false);
          }
          break;
        case "A":
          this.currentJoinedMember++;
          this.setUserInfo(data);
          if (this.currentJoinedMember === 2) {
            if (this.userId === data.leftParticipant) {
              this.buttonAction(false);
            } else {
              this.buttonAction(true);
            }

          }
          break;
      }


    },
    buttonAction(flag) {
      if (flag) {
        this.isCirclePlusDisabled = true;
        this.isCirclePlusOpacity = 0.5;
        this.isSelectBtnDisabled = true;
        this.isSelectBtnOpacity = 0.5;
      } else {
        this.isCirclePlusDisabled = false;
        this.isCirclePlusOpacity = 1.0;
        this.isSelectBtnDisabled = false;
        this.isSelectBtnOpacity = 1.0;
      }
    },
    circlePlusClickEvent() {
      console.log('circlePlusClickEvent');
      // let game = {
      //   "gameId" : this.gameId,
      //   "leftParticipant" : this.leftParticipant,
      //   "rightParticipant" : this.rightParticipant,
      //   "currentUserId" : this.userId,
      //   "gameScore" : 1 + Number(currentScore),
      //   "status" : "G"
      // };
      let currentGameScore = document.querySelector("#gameScore").textContent;
      if (Number(currentGameScore) + 1 >= this.gameMaxScore) {
        this.buttonAction(true);

        this.dialogVisible = true;
        this.sendMessage(true);
        this.sendResult();
        return;
      }

      this.sendMessage(false, false);
    },
    selectBtnClickEvent() {
      console.log('selectBtnClickEvent');
      // let game = {
      //   "gameId" : this.gameId,
      //   "leftParticipant" : this.leftParticipant,
      //   "rightParticipant" : this.rightParticipant,
      //   "currentUserId" : this.userId,
      //   "gameScore" : Number(currentScore),
      //   "status" : "S"
      // };
      this.sendMessage(false, true);
    },
    async sendMessage(isEnd, isSelectBtn) {
      let currentScore = document.querySelector("#gameScore").textContent;
      let game;
      if (isEnd) {
        game = {
          "gameId" : this.gameId,
          "leftParticipant" : this.leftParticipant,
          "rightParticipant" : this.rightParticipant,
          "currentUserId" : this.userId,
          "gameScore" : Number(currentScore) + 1,
          "status" : "E"
        };
      } else {
        game = {
          "gameId" : this.gameId,
          "leftParticipant" : this.leftParticipant,
          "rightParticipant" : this.rightParticipant,
          "currentUserId" : this.userId,
          "gameScore" : isSelectBtn ? Number(currentScore): 1 + Number(currentScore),
          "status" : isSelectBtn ? "S" : "G"
        };
      }

      let response = await fetch(`http://localhost:8080/game`, {
        method: "post",
        body: JSON.stringify(game),
        headers: {
          "Content-Type": "application/json; charset=utf-8"
        }
      })

      console.log(response);
    },
    clickDialogButton(isConfirm) {
      if (isConfirm) {
        console.log("click OK Button");
        this.dialogVisible = false;
        this.isExitBtnDisabled = false;
      } else {
        console.log("click CANCEL Button");
        this.dialogVisible = false;
        this.isExitBtnDisabled = false;
      }
    },
    handleClose() {
      console.log("handleClose");
      this.dialogVisible = false;
      this.isExitBtnDisabled = false;
    },
    exitBtnClickEvent() {
      console.log("exitBtnClickEvent");
    },

    // private String gameId;
    // private String leftUserId;
    // private String rightUserId;
    // private String winnerFlag;
    // @JsonSerialize(using = LocalDateTimeSerializer.class)
    //     @JsonFormat(pattern = GoodGameConstant.MILLISECOND_DATE_TIME_FORMAT)
    //     @CreatedDate
    // private LocalDateTime createdAt;
    // private String gameStatusCode;

    async sendResult() {
      console.log("sendResult()");
      let gameHistory = {
        "gameId" : this.gameId,
        "leftUserId" : this.leftParticipant,
        "rightUserId" : this.rightParticipant,
        "winnerFlag" : this.userId === this.leftParticipant ? "L" : "R",
        "gameStatusCode" : "E"
      };
      let response = await fetch(`http://localhost:8080/game-history`, {
        method: "post",
        body: JSON.stringify(gameHistory),
        headers: {
          "Content-Type": "application/json; charset=utf-8"
        }
      })

      console.log(response);
    }
  }
}
</script>

<style scoped>
#wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color:beige;
}
#content {
  font-family: system-ui, serif;
  font-size: 2rem;
  padding: 3rem;
  width: 16rem;
  height: 20rem;
  border-radius: 1rem;
  background: cornflowerblue;
}
#container {
  padding: 30px;
  background-color:red;
}
#container2 {
  box-size: border-box;
  width: 100vw;
  margin: 3vw;
  background-color:blue;
}
.test {

  position: relative;


  background-color:red;
}
.test2 {
  background-color:blue;
  top: 50%;
  width: 100%;
}
</style>