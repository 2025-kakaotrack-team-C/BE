# 📌 PIN  
**예비 개발자들을 위한 사이드 프로젝트 팀빌딩 & 관리 커뮤니티 플랫폼**  

<p align="center">
<img width="276" height="195" alt="Image" src="https://github.com/user-attachments/assets/01c4d676-5e82-49e6-91ba-12d31e7727d4" />
</p>


## 프로젝트 개요  
사이드 프로젝트를 하고 싶지만,
기존 팀 매칭 커뮤니티의 높은 난이도와 진입 장벽 때문에 망설이는 예비 개발자들을 위한 플랫폼입니다.

> **PIN은 난이도 표시 시스템을 통해**  
> 자신의 개발 숙련도에 맞는 프로젝트를 찾고,  
> 비슷한 수준과 성향의 사람들과 부담 없이 팀을 구성할 수 있도록 돕습니다.

이를 통해 경험이 적은 사람도 쉽게 참여할 수 있으며,
함께 성장하며 프로젝트를 완수할 수 있는 환경을 제공합니다.

- **개발 기간**: 2025.01 (약 4주)  
- **팀 구성**: FE 2명, BE 2명  


## 기술 스택  
| 분야       | 기술 |
|------------|----------------------------------|
| Language   | Java |
| Framework  | Spring Boot |
| Database   | MySQL |
| Infra      | AWS EC2, Docker |
| DevOps     | GitHub Actions (CI/CD) |

<img width="715" height="495" alt="Image" src="https://github.com/user-attachments/assets/bd5f672e-aa6f-450a-b968-ba2805f7cbb9" />


## 주요 기능  
프로젝트 관리
- 프로젝트 생성, 조회, 수정, 삭제
  
팀원 모집
- 프로젝트에 필요한 포지션(분야)별로 팀원 신청 및 관리

리뷰 및 댓글
- 프로젝트 및 팀원에 대한 리뷰와 댓글 기능

마이페이지
- 내 정보, 내가 참여한 프로젝트, 내가 지원한 프로젝트 목록 관리
  
Spring Security 로그인 

EC2 자동 배포 (GitHub Actions 사용)

##  CI/CD 파이프라인  
- GitHub Actions 설정 → `main` 브랜치 merge 시 **EC2로 자동 배포**  
- Dockerfile 및 **NGINX Reverse Proxy** 구성  

## 폴더 구조

    1 src
    2 └── main
    3     ├── java
    4     │   └── com
    5     │       └── kakaotrack
    6     │           └── pin
    7     │               ├── PinApplication.java     # 메인 애플리케이션
    8     │               ├── config                  # Spring 설정
      (Web, Security 등)
    9     │               ├── domain                  # JPA Entity
    10    │               ├── jwt                     # JWT 관련 로직
    11    │               ├── project                 # 프로젝트 기능 패키지
    12    │               │   ├── controller
    13    │               │   ├── service
    14    │               │   ├── repository
    15    │               │   └── dto
    16    │               ├── (application, comment, review, mypage...) # 기능별 패키지 (기능 별 패키지)
    17    │               └── HealthCheckController.java # 서버 상태 체크용 컨트롤러
    18    └── resources
    19         ├── application.yml             # 공통 설정
    20         ├── application-develop.yml     # 개발 서버용 설정
    21         └── application-prod.yml        # 운영 서버용 설정


