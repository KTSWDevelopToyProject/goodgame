package com.kt.game.goodgame.outergame.service;

import com.kt.game.goodgame.exception.handler.BadRequestException;
import com.kt.game.goodgame.outergame.domain.Member;
import com.kt.game.goodgame.outergame.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * Member 전체 목록 조회
     */
    public Flux<Member> retrieveMembers() {
        try {
            return memberRepository.findAll();
        } catch (Exception e) {
            return Flux.error(new BadRequestException("멤버 목록 조회 작업을 실패하였습니다.", e.getCause()));
        }
    }

    /**
     * userId 로 Member 단 건 조회
     */
    public Mono<Member> retrieveMemberByUserId(String userId) {
        try {
            return memberRepository.retrieveMemberByUserId(userId);
        } catch (Exception e) {
            return Mono.error(new BadRequestException("멤버 ID로 멤버 조회하는 작업을 실패하였습니다.", e.getCause()));
        }
    }

    /**
     * userName 으로 Member 목록 조회
     */
    public Flux<Member> retrieveMemberByUserName(String userName) {
        try {
            return memberRepository.retrieveMemberByUserName(userName);
        }catch (Exception e) {
            return Flux.error(new BadRequestException("멤버 이름으로 멤버 조회하는 작업을 실패하였습니다.", e.getCause()));
        }
    }

    /**
     * Member 생성
     */
    public Mono<Member> createMember(Member member) {
        try {
            return memberRepository.save(member);
        } catch (Exception e) {
            return Mono.error(new BadRequestException("멤버 생성 작업을 실패하였습니다.", e.getCause()));
        }
    }

    /**
     * Member 수정
     */
    public Mono<Integer> updateMember(Member member) {
        try {
            return memberRepository.updateMember(member.getUserId(), member.getUserName(), member.getAccountActivatedYn(), member.getLoginYn());
        } catch (Exception e) {
            return Mono.error(new BadRequestException("멤버 업데이트 작업을 실패하였습니다.", e.getCause()));
        }
    }

    /**
     * Member 삭제
     */
    public Mono<Integer> deleteMember(String userId) {
        try {
            return memberRepository.deleteMember(userId);
        } catch (Exception e) {
            return Mono.error(new BadRequestException("멤버 삭제 작업을 실패하였습니다.", e.getCause()));
        }
    }

}
