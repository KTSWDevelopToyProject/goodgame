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

    public Flux<Member> retrieveMemberByUserName(String userName) {
        return memberRepository.findByUserName(userName);
    }

    public Mono<Member> createMember(Member member) {
        return memberRepository.save(member);
    }

}
