package com.example.auctionarena.repository;

import com.example.auctionarena.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Member findByNickname(String nickname);

  Optional<Member> findByEmail(String email);

  @Query("select m from Member m where m.email = :email")
  Member findByEmail2(String email);

  Optional<Member> findOptionalByNickname(String nickname);

  // 회원 찾기 (email, social 회원여부)
  // @EntityGraph(attributePaths = { "roleSet" }, type = EntityGraphType.LOAD)
  @Query(
    "select m from Member m where m.email = :email and m.fromSocial = :social"
  )
  Optional<Member> findByEmailAndFromSocial(String email, boolean social);
}
