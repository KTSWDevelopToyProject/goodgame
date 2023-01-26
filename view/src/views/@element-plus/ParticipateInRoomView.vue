<template>
  <el-form :model="form" label-width="120px">

    <el-form-item label="방 ID">
      <el-input disabled v-model="createdRoom.gameId" />
    </el-form-item>

    <el-form-item label="방 이름">
      <el-input disabled v-model="createdRoom.roomName" />
    </el-form-item>

    <el-form-item label="방장 ID">
      <el-input disabled v-model="createdRoom.leftParticipant" />
    </el-form-item>

    <el-form-item label="당신의 ID">
      <el-input v-model="rightParticipant" />
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="participateInRoom">참여</el-button>
      <el-button @click="participateInRoomViewModelCancel">취소</el-button>
    </el-form-item>

  </el-form>
</template>

<script>

export default {

  name: "ParticipateInRoomView",

  data() {
    return {

      creatRoomUrl: `http://localhost:8080/waiting-rooms`,
      createGameUrl: `http://localhost:8080/game`,
      rightParticipant: "",
      createdRoom : {
        gameId: "",
        roomName: "",
        leftParticipant: "",
        rightParticipant: "",
        countOfParticipants: "",
        roomStatus: "",
      },
      game : {},
    };
  },

  created() {
  },

  methods: {
    async participateInRoom() {
      try {
        this.createdRoom.rightParticipant = this.rightParticipant;
        this.createdRoom.countOfParticipants = 2;
        this.createdRoom.roomStatus = "A";
        let roomResponse = await this.$axios.post(this.creatRoomUrl, this.createdRoom);

        this.game.gameId = roomResponse.data.gameId;
        this.game.leftParticipant = roomResponse.data.leftParticipant;
        this.game.rightParticipant = roomResponse.data.rightParticipant;
        this.game.currentUserId = roomResponse.data.rightParticipant;
        this.game.gameScore = "0";
        this.game.status = "A";

        let response = await this.createGame();
        this.$emit("enterGameRoom", response.data);
      } catch (error) {
        console.log(error);
      }
    },

    async createGame() {
      try {
        return await this.$axios.post(this.createGameUrl, this.game);
      } catch (error) {
        console.log(error);
      }
    },

    participateInRoomViewModelCancel() {
      this.$emit("participateInRoomViewModalCancel");
    },
  }

}
</script>
