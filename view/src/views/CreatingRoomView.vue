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

      url: `http://localhost:8080/waiting-rooms`,
      room : {
        roomName: "",
        leftParticipant: "",
        rightParticipant: "",
        countOfParticipants: "",
        roomStatus: "",
      },
    };
  },

  created() {
  },

  methods: {
    async createRoom() {
      try {
        this.room.rightParticipant = "";
        this.room.countOfParticipants = 1;
        this.room.roomStatus = "A";
        let response = await this.$axios.post(this.url, this.room);
        this.$emit("enterGameRoom", response.data);
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
