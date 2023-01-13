package com.kt.game.goodgame.outergame.repository;

import com.kt.game.goodgame.outergame.domain.Member;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, String> {

    @Query("""
                SELECT user_id
                     , user_name
                     , created_at
                     , deleted_at
                     , account_activated_yn
                     , login_yn
                FROM tb_ktgame_member
                WHERE user_id = :userId
            """)
    Mono<Member> retrieveMemberByUserId(String userId);

    @Query("""
                SELECT user_id
                     , user_name
                     , created_at
                     , deleted_at
                     , account_activated_yn
                     , login_yn
                FROM tb_ktgame_member
                WHERE user_name = :userName
            """)
    Flux<Member> retrieveMemberByUserName(String userName);

    @Modifying
    @Query("""
                UPDATE tb_ktgame_member
                SET (user_name, account_activated_yn, login_yn) = (:userName, :accountActivatedYn, :loginYn)
                WHERE user_id = :userId
            """)
    Mono<Boolean> updateMember(@Param("userId") String userId,
                               @Param("userName") String userName,
                               @Param("accountActivatedYn") String accountActivatedYn,
                               @Param("loginYn") String loginYn);

    @Modifying
    @Query("""
            DELETE FROM tb_ktgame_member
            WHERE user_id = :userId
        """)
    Mono<Boolean> deleteMember(@Param("userId") String userId);

}
