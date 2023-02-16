# Spring-JPA-study
D-Coding 백엔드 스터디 

## 1주차 과제 제출(23-01-21)
스프링 입문 강의 정리

## 2~4주차 과제
- 간단한 ‘당근마켓’ 벡엔드 구현해보기
### 요구 사항

- 회원가입/로그인 기능
- 상품등록 기능
- 상품페이지 기능
- 마이 페이지(나의 당근 페이지) 기능

### 제한 사항

- Spring 또는 Spring Boot Framework를 사용해야합니다.
- JPA를 사용해야합니다. (Spring data jpa 활용 가능)
- 인증/인가 방식은 JWT & Spring Security 를 활용해야합니다.

### 2주차

- 요구사항대로 당근마켓 ERD 그려보기
- 요구사항 API 구현 (가능한 만큼)
- jwt, security 이용하여 로그인, 회원가입 기능 구현


![dcoding-erd](https://user-images.githubusercontent.com/85729858/215239770-a2ac0b3a-cd5b-443d-b7a3-d026ce9ae5c5.png)


### 3주차

- 피드백 반영하여 카테고리를 enum으로 처리하도록 하고, createdDate와 updatedDate추가
- 로그인 기능 리펙토링
- 회원가입 기능 추가
- 상품 관련 엔티티, 레포지토리, 서비스 추가
- 상태, 카테고리 enum추가

![image](https://user-images.githubusercontent.com/85729858/216071539-42dc3af6-452e-464f-9a68-c52364c5fc3f.png)


### 4주차

- 상품등록 기능 구현
- 상품조회 기능 구현
- 좋아요 등록, 삭제 기능 구현
