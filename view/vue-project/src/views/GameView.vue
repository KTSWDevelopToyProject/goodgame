<template>
  <div>

    <div id="container">

      <div id="container2">

      </div>
    </div>






    <div id="game_score">

      <span id="gameScore"></span>

    </div>

    <el-button id="circle_plus"
               :disabled="isCirclePlusDisabled"
               @click="circlePlusClickEvent()"
    >
      <el-icon ><CirclePlusFilled id="circlePlus" style="width: 55px; height: 55px" /></el-icon>
    </el-button>

    <el-button id="select_btn"
               :disabled="isSelectBtnDisabled"
               @click="selectBtnClickEvent()"
    >
      <el-icon ><Select id="selectBtn" style="width: 55px; height: 55px"/></el-icon>
    </el-button>







  </div>



</template>

<script>


export default {
  name: 'GameView',
  components: {

  },
  data() {
    return {
      eventSource: '',
      gameId: '',
      userId: '',
      user1Id: '',
      user2Id: '',
      isCirclePlusDisabled: true,
      isSelectBtnDisabled: true,
      currentJoinedMember: 0,
    };
  },
  created() {
    this.gameId = prompt("gameId?");
    this.userId = prompt("userId?");
// @ is an alias to /src
    const eventSource = new EventSource(`http://localhost:8080/game/${this.gameId}`);

    eventSource.onmessage = (event) => {

      const data = JSON.parse(event.data);

      this.changeGameView(data);

    };

  },
  methods: {
    changeGameView(data) {
      console.log(data);
      this.user1Id = data.user1Id;
      this.user2Id = data.user2Id;
      document.querySelector("#gameScore").textContent = '';
      document.querySelector("#gameScore").textContent = data.gameScore;
      switch (data.status) {
        case "G":
          if (this.userId === data.currentUserId) {
            this.isCirclePlusDisabled = false;
            this.isSelectBtnDisabled = false;
          } else {
            this.isCirclePlusDisabled = true;
            this.isSelectBtnDisabled = true;
          }
          break;
        case "S":
          if (this.userId === data.currentUserId) {
            this.isCirclePlusDisabled = true;
            this.isSelectBtnDisabled = true;
          } else {
            this.isCirclePlusDisabled = false;
            this.isSelectBtnDisabled = false;
          }
          break;
        case "A":
          this.currentJoinedMember++;
          if (this.currentJoinedMember === 2) {
            if (this.userId === data.user1Id) {
              this.isCirclePlusDisabled = false;
              this.isSelectBtnDisabled = false;
            } else {
              this.isCirclePlusDisabled = true;
              this.isSelectBtnDisabled = true;
            }

          }
          break;
      }


    },
    circlePlusClickEvent() {
      console.log('circlePlusClickEvent');
      this.sendMessage(false);
    },
    selectBtnClickEvent() {
      console.log('selectBtnClickEvent');
      this.sendMessage(true);
    },
    async sendMessage(isSelectBtn) {
      let currentScore = document.querySelector("#gameScore").textContent;

      let game = {
        "gameId" : this.gameId,
        "user1Id" : this.user1Id,
        "user2Id" : this.user2Id,
        "currentUserId" : this.userId,
        "gameScore" : 1 + Number(currentScore),
        "status" : isSelectBtn ? "S" : "G"
      };

      let response = await fetch(`http://localhost:8080/game/${this.gameId}`, {
        method: "post",
        body: JSON.stringify(game),
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