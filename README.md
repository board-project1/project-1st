# 📋 게시판 프로젝트
<br>


## 🧑‍💻 팀장 및 팀원 정보
  - 팀장 : 이민혁
  - 팀원 : 김가은, 백지영

## ⚙ 개발 환경
- 운영체제(OS): Window, Mac OS
- 통합 개발 환경(IDE) : Intellij Ultimate
- JDK 버전 : JAVA 11
- Spring Boot 버전 : 2.7.14
- 데이터베이스 : MySQL
- 빌드툴 : Gradle
- 버전 관리 툴 : GitHub

## 🗃 기술 스택
- Java
- Spring Boot
- MySQL

## 📌 DEPENDENCIES
- Spring Security
- Spring Web
- Spring JPA
- Lombok
- MySQL
- JWT

## 📢 명명규칙
- Package
  - 소문자로 작성한다.
  - ex) ```package```

- Java Class
  - 파스칼 표기법으로 한다.
  - ex) ```UserEntity```

- Database
  - Table 명 및 Column 명은 스네이크 표기법으로 한다.
  - ex) ```user_entity```

- Variable and Method
  - 카멜 표기법으로 한다.
  - ex) ```userId```

- Mapping-URL
  - 소문자로 사용한다.
  - 단어를 ```/``` 로 구분하여 사용한다.
  - ex) ```posts/{id}```

- Naming은 축약형을 사용하지 않고 명시적으로 작성한다.

## 🧱 프로젝트 구조
- 회원가입, 로그인/로그아웃, 게시글작성/조회(전체조회, 이메일조회)/수정/삭제, 댓글생성/조회/수정/삭제의 12가지 API를 제공합니다.
- API Request body와 Response body는 json 타입으로 표현하였습니다.

## 🗄 ERD
![ERD](https://github.com/supercoding-project1/project-1st/assets/122381580/ab35e84e-3447-4fcb-8412-b109f39f94b9)

## 📍 API
### ```회원 API```

1. 회원가입 (POST)
    1. 파라미터 : 
    2. 결과
        
        i. 실패 : 이미 등록된 이메일이면 실패 응답
        
        ii. 성공 :
        
- 응답 :  `회원가입 완료` ,  `이미 등록된 이메일 입니다.`

1. 로그인(POST)
    
    a. 파라미터 :
    
    b. 결과
    
    i. 실패 : 토큰 발행 실패 시( 유저 없을 시, 비밀번호 불일치 시)
    
    ii. 성공 :
    
- 응답 :  `로그인 성공` , `로그인 실패`

1. 로그아웃(POST)
    
    a. 파라미터 :
    
    b. 결과
    
    i. 실패 : 
    
    ii. 성공 :
    
- 응답 :  `로그아웃 성공` , `로그아웃 실패`

### ```게시글 API```

1. 게시글 생성 (POST)
    
    a. 파라미터 : 제목, 내용
    
    b. 결과
    
    i. 실패 : 사용자 없는 경우 실패 응답
    
    ii. 성공
    
- 응답 : 게시글 ID, 사용자 ID, 제목, 내용, 작성자, 닉네임, 생성일 정보를 JSON 형식으로 반환

1. 게시글 조회 (GET)
    
    a. 파라미터 : -
    
    b. 결과
    
    i. 실패 : 사용자 없는 경우 실패 응답
    
    ii. 성공
    
- 응답 : 게시글 ID, 제목, 내용, 작성자, 생성일, 좋아요 수, 좋아요 여부 정보를 JSON LIST 형식으로 반환

1. 작성자 이메일로 게시물 조회 (GET)
    
    a. 파라미터 : 이메일
    
    b. 결과
    
    i. 실패 : 사용자 없는 경우 실패 응답
    
    ii. 성공
    
- 응답 : 게시글 ID, 제목, 내용, 작성자, 생성일, 좋아요 수, 좋아요 여부 정보를 JSON LIST 형식으로 반환

1. 게시글 수정 (PUT)
a. 파라미터 : 게시글 ID, 제목, 내용
    
    b. 결과
    
    i. 실패 : 사용자 없는 경우 실패 응답
    
    ii. 성공
    
- 응답 : `게시글이 수정되었습니다.`
1. 게시글 삭제 (DELETE)
    
    a. 파라미터 : 게시글 ID
    
    b. 결과
    
    i. 실패 : 사용자 없는 경우 실패 응답
    
    ii. 성공
    
- 응답 : -

### ```댓글 API```

1. 댓글 생성 (POST)
    
    a. 파라미터 : 사용자 토큰, 본문
    
    b. 결과
    
    i. 실패 : 사용자 없는 경우 실패 응답, 게시글 없는 경우 실패 응답
    
    ii. 성공
    
- 응답 : 댓글 ID, 게시글 ID, 사용자 이메일, 본문, 작성 시간 JSON LIST 형식으로 반환

1. 댓글 수정 (PUT)
    
    a. 파라미터 : 사용자 토큰, 게시글 ID, 본문
    
    b. 결과
    
    i. 실패 : 사용자가 없는 경우 실패 응답, 게시글이 없는 경우 실패 응답, 사용자가 다른 경우 실패 응답
    
    ii. 성공
    
- 응답 : 댓글 ID, 게시글 ID, 사용자 이메일, 본문, 작성 시간 JSON LIST 형식으로 반환

1. 댓글 삭제 (DELETE)
    
    a. 파라미터 : 사용자 토큰, 게시글 ID, 댓글 ID
    
    b. 결과
    
    i. 실패 : 사용자 없는 경우 실패 응답, 게시글 없는 경우 실패 응답, 댓글 없는 경우 실패 응답
    
    ii. 성공
    
- 응답 : `댓글 삭제 성공`

1. 댓글 조회 (GET)
    
    a. 파라미터 : -
    
    b. 결과
    
    i. 실패 : 댓글이 없는 경우 실패 응답
    
    ii. 성공
    
- 응답 : JSON LIST 형식으로 전체 댓글 반환

### ```좋아요 API``` 

1. 좋아요 생성과 취소 (POST)
    
    a. 파라미터 : 사용자 토큰, 게시글 ID
    
    b. 결과
    
    i. 실패 : 사용자 없는 경우 실패 응답, 게시글 없는 경우 실패 응답
    
    ii. 성공
    
    iii. 사용자가 좋아요가 있다면 좋아요 취소 없다면 좋아요 생성
    
- 응답 : `좋아요 생성 성공` , `좋아요 취소 성공`

## 🔗 게시판 기능
  
| 기능    | 상세    | 참고    |
|----|----|----|
|회원    |1. 이메일 비밀번호 입력하여 회원가입 API <br> 2. 이메일 비밀번호 입력하여 접속하는 API <br> 3. 접속된 유저 로그아웃<br>    |    |
|조회    |1. 게시물 전체 조회하는 API<br> 2. 작성자 이메일을 통해 특정 게시물들을 검색하는 API<br> 3. 댓글을 조회하는 API<br>    |    |
|생성    |1. 댓글을 새롭게 만들 수 있는 API<br> 2. 게시물을 새롭게 만들 수 있는 API<br> |    |
|수정    |1. 기존 댓글의 글을 수정하는 API <br> 2. 기존 게시물을 수정할 수 있는 API<br>     |    |
|삭제    |1. 게시물을 삭제하는 API <br> 2. 댓글을 삭제하는 API<br> |    | 
|심화    |1. 게시물 좋아요를 할 수 있는 기능 <br>    |    |

## ✔️ 프로젝트 진행 순서
1. 인원별로 각자 맡을 API를 정한다. (회원, 게시판, 댓글, 좋아요)
2. 프로젝트의 규칙을 정하여 공통된 구현을 할 수 있도록 진행한다. (pakeage명, DB Table명 등)
3. GitHub organization을 새로 만들어 Repository를 생성 후 각자 branch를 생성하여 개인이 맡은 API 구현을 시작한다.
4. API 구현을 완료하고 Postman으로 구현된 API를 테스트한다.
5. 모든 branch를 base branch에 merge하여 충돌 사항을 먼저 확인하고 수정한다.
6. merge된 API를 Postman으로 테스트 하고 제공된 깡통 프론트와 연결하여 테스트를 한다.
7. 프론트와 API가 맞지 않는 부분을 수정하고 프론트에 추가적인 기능을 추가한다.
8. 모든 확인이 끝난 후 base branch를 main branch에 merge 한다.

#### application.yaml 파일에 JWT secret key 가 포함되어 있어 따로 올리지 않았습니다. 
#### 파일 구동시 application.yaml 파일을 생성 및 수정 후 실행 해주시기 바랍니다.
