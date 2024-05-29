package com.example.springbootdeveloper.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Sql("/insert-members.sql")
    @Test
    void getAllMembers() {
        // when
        List<Member> members = memberRepository.findAll();

        // then
        assertThat(members.size()).isEqualTo(3);
    }

    @Sql("/insert-members.sql")
    @Test
    void getMemberById() {
        // given
        final Long id = 2L;

        // when
        Member member = memberRepository.findById(id).get();

        // then
        assertThat(member).isNotNull();
        assertThat(member.getId()).isEqualTo(id);
        assertThat(member.getName()).isEqualTo("user2");
    }

    @Sql("/insert-members.sql")
    @Test
    void findByName() {
        // given
        final String name = "user1";

        // when
        Member members = memberRepository.findByName(name).get();

        // then
        assertThat(members.getId()).isEqualTo(1);
    }

    @Test
    void saveMember() {
        // given
        Member member = new Member(1L, "user1");

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertThat(savedMember.getId()).isNotNull();
        assertThat(memberRepository.findById(1L).get().getName()).isEqualTo("user1");
    }

    @Test
    void saveMembers() {
        // given
        Member member2 = new Member(2L, "user2");
        Member member3 = new Member(3L, "user3");

        // when
        memberRepository.saveAll(List.of(member2, member3));

        // then
        assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

    @Sql("/insert-members.sql")
    @Test
    void deleteMember() {
        // given
        final Long id = 1L;

        // when
        memberRepository.deleteById(id);

        // then
        assertThat(memberRepository.findById(id).isEmpty()).isTrue();
    }

    @Sql("/insert-members.sql")
    @Test
    void deleteMembers() {
        // when
        memberRepository.deleteAll();

        // then
        assertThat(memberRepository.findAll().size()).isEqualTo(0);
    }

    @Sql("/insert-members.sql")
    @Test
    void updateMember() {
        // given
        final Long id = 1L;
        final String updatedName = "user1 updated";

        // when
        Member member = memberRepository.findById(id).get();

        member.changeName(updatedName);

        // then
        assertThat(memberRepository.findById(id).get().getName()).isEqualTo(updatedName);
    }
}