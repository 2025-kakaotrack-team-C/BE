package com.kakaotrack.pin.mypage.repository;

import com.kakaotrack.pin.mypage.entity.UserDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kakaotrack.pin.jwt.member.Member;
import java.util.List;

@Repository
public interface UserDepartmentRepository extends JpaRepository<UserDepartment, Integer> {
    List<UserDepartment> findByMember(Member member);
    void deleteByMember(Member member);
}



