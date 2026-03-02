package com.quizbattle.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.UUID;



@Controller
public class MatchWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;



    public MatchWebSocketController(
            SimpMessagingTemplate messagingTemplate
    ) {
        this.messagingTemplate = messagingTemplate;
    }



    @MessageMapping("/match.join")
    public void joinMatch(
            @Payload Map<String, String> payload
    ) {

        String matchId = payload.get("matchId");

        messagingTemplate.convertAndSend(
                "/topic/match/" + matchId,
                Map.of(
                        "type", "PLAYER_JOINED",
                        "matchId", matchId
                )
        );

    }



    public void sendMatchFound(
            UUID matchId,
            UUID player1,
            UUID player2
    ) {

        messagingTemplate.convertAndSend(
                "/topic/match/" + matchId,
                Map.of(
                        "type", "MATCH_FOUND",
                        "matchId", matchId.toString(),
                        "player1", player1.toString(),
                        "player2", player2.toString()
                )
        );

    }



    public void sendQuestion(
            UUID matchId,
            Object question
    ) {

        messagingTemplate.convertAndSend(
                "/topic/match/" + matchId,
                Map.of(
                        "type", "QUESTION",
                        "data", question
                )
        );

    }



    public void sendResult(
            UUID matchId,
            UUID winnerId
    ) {

        messagingTemplate.convertAndSend(
                "/topic/match/" + matchId,
                Map.of(
                        "type", "RESULT",
                        "winnerId", winnerId.toString()
                )
        );

    }

}