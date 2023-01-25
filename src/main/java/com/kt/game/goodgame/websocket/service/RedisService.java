package com.kt.game.goodgame.websocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.game.goodgame.websocket.domain.GameMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

//    public void setRedisStringValue(GameMessage gameMessage) {
//        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
//        stringValueOperations.set("sender", gameMessage.getSender());
//        stringValueOperations.set("context", gameMessage.getContext());
//    }
//
//    public void getRedisStringValue(String key) {
//
//        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
//        System.out.println(key +" : " + stringValueOperations.get(key));
//    }

    //직접 만든 redisTemplate 사용
    public void setRedisValue(GameMessage gameMessage) throws JsonProcessingException {
        String key = gameMessage.getGameId();
        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(gameMessage));
    }

    public <T> T  getRedisValue(String key, Class<T> classType) throws JsonProcessingException {
        String redisValue = (String)redisTemplate.opsForValue().get(key);

        return objectMapper.readValue(redisValue, classType);
    }

    @Transactional
    public void sendMessage(GameMessage gameMessage) {  // RedisPublisher
//        ChatRoom chatRoom = chatRoomRepository.findById(chatMessage.getRoomId()).orElseThrow(ChatRoomNotFoundException::new);

//        //채팅 생성 및 저장
//        ChatMessage chatMessage = ChatMessage.builder()
//                .chatRoom(chatRoom)
//                .user(user)
//                .message(chatMessageRequest.getMessage())
//                .build();

//        chatMessageRepository.save(chatMessage);
//        String topic = channelTopic.getTopic();
//
//        // ChatMessageRequest에 유저정보, 현재시간 저장
//        chatMessageRequest.setNickName(user.getNickname());
//        chatMessageRequest.setUserId(user.getId());
//
//        if (chatMessageRequest.getType() == ChatMessageRequest.MessageType.TALK) {
            // 그륩 채팅일 경우
            redisTemplate.convertAndSend(gameMessage.getGameId(), gameMessage);
//        }
    }
}
