<template>
  <el-form :model="form" label-width="120px">

    <el-form-item label="방 이름">
      <el-input v-model="room.roomName" />
    </el-form-item>

    <el-form-item label="방장 ID">
      <el-input v-model="room.leftParticipant" />
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="createRoom">생성</el-button>
      <el-button @click="creatingRoomViewModalCancel">취소</el-button>
    </el-form-item>

  </el-form>
</template>

<script>

export default {

  name: "CreatingRoomView",

  data() {
    return {

      creatRoomUrl: `http://localhost:8080/waiting-rooms`,
      createGameUrl: `http://localhost:8080/game`,
      room : {
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
    async createRoom() {
      try {
        this.room.rightParticipant = "";
        this.room.countOfParticipants = 1;
        this.room.roomStatus = "W";
        let roomResponse = await this.$axios.post(this.creatRoomUrl, this.room);

        this.game.gameId = roomResponse.data.gameId;
        this.game.leftParticipant = roomResponse.data.leftParticipant;
        this.game.rightParticipant = "";
        this.game.currentUserId = roomResponse.data.leftParticipant;
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

    creatingRoomViewModalCancel() {
      this.$emit("creatingRoomViewModalCancel");
    },
  }

}
</script>
