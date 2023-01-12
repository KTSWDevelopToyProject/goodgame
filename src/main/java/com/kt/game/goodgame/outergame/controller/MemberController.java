package com.kt.game.goodgame.outergame.controller;

import com.kt.game.goodgame.outergame.domain.Member;
import com.kt.game.goodgame.outergame.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("")
    Flux<Member> retrieveMembers() {
        return memberService.retrieveMembers();
    }

    @GetMapping("/{userName}")
    Flux<Member> retrieveMemberByUserName(@PathVariable String userName) {
        return memberService.retrieveMemberByUserName(userName);
    }

    @PostMapping("")
    Mono<Member> createMember(@RequestBody Member member) {
        return memberService.createMember(member);
    }

}
