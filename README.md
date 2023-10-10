# ChatGPT를 활용한 웨어러블 기기 데이터 기반 음악추천 플랫폼
# 🎵 Blueme 
![블루미소개](https://github.com/2023-SMHRD-SW-Fullstack-1/Blueme/assets/114223221/68bd864d-1470-4f4f-9b3a-d2be24a1ae96)
## 📆 프로젝트 기간
### 2023-08-10~2023-10.06
## ⭐ 주요 기능
  1. 회원관리 기능
  2. 웨어러블데이터 분석기능
  3. 음악재생기능
  4. 음악추천기능
  5. OPENAI ChatGPT3.5-turbo API 를 활용한 추천기능
## ⚒️ 기술스택
![블루미개발환경협력도구](https://github.com/2023-SMHRD-SW-Fullstack-1/Blueme/assets/114223221/8fe7123e-64cd-4a30-994a-1a5227cd0ff4)


## ⚙️ 시스템 아키텍처
![블루미시스템아키텍처](https://github.com/2023-SMHRD-SW-Fullstack-1/Blueme/assets/114223221/6b55619e-86c4-487c-b89d-fbcf919326b7)
## 📌 서비스 흐름도
![블루미서비스흐름도](https://github.com/29074I/Javapratice/assets/114223221/8e0f71ad-a493-422b-ab1d-81d4a58de658)
## 📌 ER다이어그램
![블루미er다이어그램](https://github.com/29074I/Javapratice/assets/114223221/e7931f04-f9a6-4529-abca-0b9936f89d87)
## 🖥️ 시연 영상
https://github.com/2023-SMHRD-SW-Fullstack-1/Blueme/assets/114223221/5c9d9cda-8c1a-49dd-bafd-bfdaaef97a42

## 👥 팀원 역할

### * 김혁(팀장)

#### 프론트, 백, 프로젝트 총괄
#### git, javadoc, postman 관리

#### D.A/M
- 데이터베이스 모델링, 설계 및 구축
- JPA 엔터티 설계 및 구축
  
#### Front-End

- 스마트워치 어플리케이션(Android Studio)
  - 로그인화면 구현 
  - 실시간 측정 데이터 가져오는 화면 구현
  - HealthServiceAPI 를 활용한 실시간 심박수, 속도, 칼로리소모량, 분당발걸음수, 위/경도 수집기능 구현
- 검색화면
  - 최근검색 화면 구현
  - 음악검색 화면 구현(Real-Time Search)
  - tailwindcss를 활용한 반응형 구현
- 관리자페이지
  - 오늘 가입한회원수, GPT추천음악수, 스마트워치에서 보낸 데이터수 통계화면 구현
  - 조회수 TOP 뮤직 통계 화면구현
  - 음악 조회기능 (Paging)
  - 회원조회, 음악등록, 테마등록 화면 구현
  - tailwindcss를 활용한 반응형 구현
    
#### Back-end
- jpa 엔터티 구현
  - 건강정보, 좋아요음악, 음악, 재생된음악, 추천음악상세, 저장된음악, 상세검색, 테마, 테마목록, 테마태그
- javadoc주석 작성
- 음악추천 알고리즘(감정 장소 시간 속도 계절 심장박동수 등) 설계 및 구현 (OpenWeatherMapAPI, 스마트워치 데이터)
    - 감정,장소,시간, 속도, 계절, 심장박동수 등 알고리즘 설계 및 구현
    - 스마트워치 데이터 를 활용한 알고리즘 고도화
    - OpenWeatherAPI를 활용한 알고리즘 고도화
- 알고리즘 순서도 작성
- OPENAI ChatGPT3.5-turbo-16K 모델을 활용한 음악추천 기능 구현
  - ChatGPTAPI 연결 및 구현
  - ChatGpt 질의문 다양화 설계 및 구현
  - 단위 테스트
- 건강정보
  - 조회, 등록
- 좋아요한 음악
  - 조회, 등록, 삭제
  - 저장한 음악리스트 조회
- GPT추천음악
  - 추천음악리스트등록
  - 사용자기반 모든/최근 목록 조회
  - 상세조회, 최근 10개 제한 조회
- 음악
  - 음원등록, 음원데이터 전송(전체/HttpRange)
  - 목록 조회(Paging)
  - 음원 정보 조회
  - 조회수 증가 
- 재생된 음악
  - 조회, 등록
- 저장된 음악
  - 조회, 상세조회, 등록
- 검색
  - 최근검색 등록, 조회
  - 음악검색(Real-Time Search)
- 테마
  - 등록, 모든/특정/상세테마조회
  - 태그 조회
- 관리자페이지
  - 회원,음악,테마관리
  
#### 배포
- AWS (git, scp활용)
  - EC2 - react, springboot(gradle) 배포
  - EBS - 음원, 재킷사진, 앨범사진 등 저장볼륨

---

### * 이유영

#### Front-End

- Tailwind CSS를 활용한 전체 디자인 및 모바일 웹 구현

- 회원 정보 및 인증 관리를 위한 Redux 스토어 구현
  - 통합적인 상태 관리를 위한 combineReducers 사용
  - JWT Token 및 OAuth2 정보 Redux 스토어에 보관 관리

- 회원 정보 관리 기능
  - 회원가입, 로그인, 로그아웃, 회원 정보 수정, 회원 탈퇴 등
  - 프로필 이미지 업로드 및 통신 처리 구현

- 사용자 선호도 페이지 개발
  - 장르 페이지
    - 전체 장르 조회, 등록, 수정 기능 구현
  - 아티스트 페이지
    - 전체 아티스트 조회, 등록, 수정, 검색 기능 구현

- AI 추천 기능
  - 웨어러블 데이터 불러오기 기능 구현
  - 개인화된 AI 추천 플레이 리스트 기능 구현
  - 블루미 추천 플레이 리스트 구현
    - 로그인 전 전체 추천 리스트 최신 10개 
    - 로그인 후 로그인한 사용자 제외 최신 10개
  - 추천 리스트의 전체 저장/전체 재생 기능 구현
  - 상세한 추천 리스트 조회 서비스와 제목 수정 구현
  - 스마트워치 등록 및 설명 화면 구현
  - 플레이리스트별 재생할 음악목록 Redux 처리

- 테마
  - 해시태그 필터링 구현

- Slick-Slider 라이브러리 활용
- 로그인 전 접근 제한 토스트 창 구현
- 시간 초과 로딩을 방지하기 위한 timeout 함수 구현

---

### * 이지희

#### Front-End
- 
- 
- 
- 
-
- 
- 


---

### * 손지연

#### Back-end
- JPA 엔터티 설계
  
  - 장르, 선호장르, 선호가수, 회원, 회원체크리스트
- Spring Security + JWT 를 이용한 자체 로그인 구현
- Spring Security OAuth2 구글 / 카카오 소셜로그인 구현
- 회원가입 기능, 비밀번호 암호화 / 검증 로직
- 회원정보 수정, 회원 탈퇴 기능
- 사용자 프로필 이미지 등록, 조회
- 마이페이지
- 사용자 선호도 선택
  - 선호 장르 선택 : 모든 장르 조회, 선택한 장르 등록, 수정 기능
  - 선호 가수 선택 : 모든 가수 조회, 선택한 가수 등록, 수정, 가수 검색 기능 

---
### * 신지훈

#### Front-End
● UI/UX 디자인 및 화면 구현
- 웹사이트 전체적인 UI/UX 초기 디자인 구현 
- Taiwind CSS를 활용한 전체적인 반응형 웹 구현
- Footer / Header 구현
- Three.js를 활용한 음악추천 부분 SmartWatch & ChatGPT로고 구현
- lottie-react를 활용한 animation 구현
- Swiper Slider를 활용한 화면 구현

● 메인 페이지
- 사용자별 최근 재생 음악목록 조회 구현

● 테마 페이지 구현
- 플레이리스트 저장 기능 구현
- 저장된 테마 조회 및 상세 페이지구현
- 테마별 음악목록 불러오기 구현
- 테마별 재생할 음악목록 Redux 처리

● 라이브러리 페이지 구현
- 좋아요 누른 음악 목록 조회
- 저장한 플레이리스트 조회 구현
- 저장한 플레이리스트 별 음악 목록 조회 구현
- AI 추천 플레이리스트 조회 및 상세 페이지 구현
- 플레이리스트별 재생할 음악목록 Redux 처리

● 음악 개별정보(장르, 총재생시간) 조회 구현


## 📌 알고리즘 순서도
#### 감정 알고리즘순서도
![감정알고리즘 drawio](https://github.com/2023-SMHRD-SW-Fullstack-1/Blueme/assets/107793363/c35ed6d0-9f8a-41ba-b6c9-901da25a5f1d)
#### 속도 알고리즘순서도 
![속도알고리즘 drawio](https://github.com/2023-SMHRD-SW-Fullstack-1/Blueme/assets/107793363/09fe229b-0141-47ce-8605-4fc8b7119d87)
#### 장소 알고리즘순서도
![장소순서도 drawio](https://github.com/2023-SMHRD-SW-Fullstack-1/Blueme/assets/107793363/0c59614a-a0ef-442f-9b86-c25bc88906b7)

## 🔍 화면 구성
![블루미화면구성](https://github.com/29074I/Javapratice/assets/114223221/847bb8f5-779a-4390-9866-bf35ac46a771)

<!-- 흰배경
![블루미화면구성](https://github.com/29074I/Javapratice/assets/114223221/2e8d8b17-c2e0-459a-9105-dfed0a690b2a) 
!-->

## ⚠️ 트러블 슈팅
![트러블슈팅chatgptapi](https://github.com/29074I/Javapratice/assets/114223221/18989c6e-8989-4587-9970-8e031c4567bd)
![트러블슈팅security](https://github.com/29074I/Javapratice/assets/114223221/ce395545-a0da-49aa-8ec5-38b71df75695)
![트러블슈팅player](https://github.com/29074I/Javapratice/assets/114223221/69dd5c94-f63c-4a9a-9b1b-36590a088fd3)
![aws](https://github.com/2023-SMHRD-SW-Fullstack-1/Blueme/assets/107793363/ddd11211-4cd3-46e9-9a21-9300c87b105c)


