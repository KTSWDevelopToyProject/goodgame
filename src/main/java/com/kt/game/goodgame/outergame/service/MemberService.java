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

    public Flux<Member> retrieveMembers() {
        return memberRepository.findAll();
    }

    public Mono<Member> retrieveMemberByUserId(String userId) {
        return memberRepository.retrieveMemberByUserId(userId);
    }

    public Flux<Member> retrieveMemberByUserName(String userName) {
        return memberRepository.retrieveMemberByUserName(userName);
    }

    public Mono<Member> createMember(Member member) {
        return memberRepository.save(member);
    }

    public Mono<Member> updateMember(Member member) {
        return memberRepository.updateMember(member.getUserId(), member.getUserName(), member.getAccountActivatedYn(), member.getLoginYn())
                .flatMap(result -> {
                    if (!result) {
                        return Mono.error(new Throwable("업데이트 실패"));
                    }
                    return retrieveMemberByUserId(member.getUserId());
                });
    }

    public Mono<Boolean> deleteMember(String userId) {
        return memberRepository.deleteMember(userId);
    }

}
