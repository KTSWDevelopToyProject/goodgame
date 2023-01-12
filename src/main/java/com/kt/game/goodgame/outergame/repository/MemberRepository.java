package com.kt.game.goodgame.outergame.repository;

import com.kt.game.goodgame.outergame.domain.Member;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, String> {

    @Query("""
                SELECT *
                FROM tb_ktgame_member
                WHERE user_name = :userName
            """)
    Flux<Member> findByUserName(String userName);

}
