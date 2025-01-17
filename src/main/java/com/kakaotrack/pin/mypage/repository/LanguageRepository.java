package com.kakaotrack.pin.mypage.repository;

import com.kakaotrack.pin.mypage.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kakaotrack.pin.jwt.member.Member;
import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    List<Language> findByMember(Member member);
    void deleteByMember(Member member);
}
