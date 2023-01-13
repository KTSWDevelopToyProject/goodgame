package com.kt.game.goodgame.outergame.service;

import com.kt.game.goodgame.outergame.domain.Member;
import com.kt.game.goodgame.outergame.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * Member 전체 목록 조회
     */
    public Flux<Member> retrieveMembers() {
        return memberRepository.findAll();
    }

    /**
     * userId 로 Member 단 건 조회
     */
    public Mono<Member> retrieveMemberByUserId(String userId) {
        return memberRepository.retrieveMemberByUserId(userId);
    }

    /**
     * userName 으로 Member 목록 조회
     */
    public Flux<Member> retrieveMemberByUserName(String userName) {
        return memberRepository.retrieveMemberByUserName(userName);
    }

    /**
     * Member 생성
     */
    public Mono<Member> createMember(Member member) {
        return memberRepository.save(member);
    }

    /**
     * Member 수정
     */
    public Mono<Member> updateMember(Member member) {
        return memberRepository.updateMember(member.getUserId(), member.getUserName(), member.getAccountActivatedYn(), member.getLoginYn())
                .flatMap(result -> {
                    if (!result) {
                        return Mono.error(new Throwable("업데이트 실패"));
                    }
                    return retrieveMemberByUserId(member.getUserId());
                });
    }

    /**
     * Member 삭제
     */
    public Mono<Boolean> deleteMember(String userId) {
        return memberRepository.deleteMember(userId);
    }

}
