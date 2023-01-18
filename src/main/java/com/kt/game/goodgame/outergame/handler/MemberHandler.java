package com.kt.game.goodgame.outergame.handler;

import com.kt.game.goodgame.exception.handler.BadRequestException;
import com.kt.game.goodgame.outergame.domain.Member;
import com.kt.game.goodgame.outergame.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MemberHandler {

    private final MemberService memberService;

    /**
     * Member 전체 목록 조회 핸들러
     */
    public Mono<ServerResponse> retrieveMembers(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(memberService.retrieveMembers(), Member.class);
    }

    /**
     * userId 로 Member 단 건 조회 핸들러
     */
    public Mono<ServerResponse> retrieveMemberByUserId(ServerRequest serverRequest) {
        String userId = serverRequest.pathVariable("userId");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(memberService.retrieveMemberByUserId(userId), Member.class);
    }

    /**
     * userName 으로 Member 목록 조회 핸들러
     */
    public Mono<ServerResponse> retrieveMemberByUserName(ServerRequest serverRequest) {
        String userName = serverRequest.pathVariable("userName");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(memberService.retrieveMemberByUserName(userName), Member.class);
    }

    /**
     * Member 생성 핸들러
     */
    public Mono<ServerResponse> createMember(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Member.class)
                .flatMap(member -> memberService.createMember(member))
                .flatMap(member -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(member), Member.class));
    }

    /**
     * Member 수정 핸들러
     */
    public Mono<ServerResponse> updateMember(ServerRequest serverRequest) {
        String userId = serverRequest.pathVariable("userId");
        return serverRequest.bodyToMono(Member.class)
                .flatMap(member -> {
                    member.setUserId(userId);
                    return memberService.updateMember(member);
                })
                .flatMap(result -> memberService.retrieveMemberByUserId(userId))
                .switchIfEmpty(Mono.error(new BadRequestException("수정된 멤버가 없습니다.")))
                .flatMap(member -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(member), Member.class));
    }

    /**
     * Member 삭제 핸들러
     */
    public Mono<ServerResponse> deleteMember(ServerRequest serverRequest) {
        String userId = serverRequest.pathVariable("userId");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(memberService.deleteMember(userId), Integer.class);
    }

}
