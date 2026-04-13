# 📅 일정 관리 API
 
Spring Boot 기반의 일정 관리 REST API입니다.
 
---
 
## 🛠 기술 스택
 
- **Language:** Java
- **Framework:** Spring Boot
- **Build Tool:** Gradle
- **Base URL:** `http://localhost:8080`
 
---
 
## 📁 프로젝트 구조
 
```
src/main/java/com/schedule/
├── controller/       # HTTP 요청 처리 (REST Controller)
├── dto/              # 요청/응답 데이터 전송 객체
├── entity/           # DB 매핑 엔티티 클래스
├── repository/       # 데이터 접근 계층
├── service/          # 비즈니스 로직 처리
└── ScheduleApplication.java
```
 
---

## ERD
<img width="580" height="306" alt="image" src="https://github.com/user-attachments/assets/d48560f0-ea8d-4f1d-8541-89571d807785" />

---
 
## 📌 API 엔드포인트 목록
 
| 기능 | Method | URL |
|------|--------|-----|
| 일정 추가 | `POST` | `/schedules` |
| 일정 단건 조회 | `GET` | `/schedules/{id}` |
| 일정 전체 조회 | `GET` | `/schedules` |
| 작성자별 일정 조회 | `GET` | `/schedules?writer={writer}` |
| 일정 수정 | `PUT` | `/schedules/{id}` |
| 일정 삭제 | `DELETE` | `/schedules/{id}` |
| 댓글 추가 | `POST` | `/schedules/{id}/comments` |
 
---

# Schedule API 명세서

**Base URL:** `http://localhost:8080`

---

### 1. 일정 추가

- **Method:** `POST`
- **URL:** `/schedules`
- **응답 코드:** `201 Created`

**Request Body**

| 필드 | 타입 | 설명 |
|------|------|------|
| title | String | 일정 제목 |
| content | String | 일정 내용 |
| writer | String | 작성자명 |
| password | String | 비밀번호 |

**Response Body**

| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | 생성된 일정 ID |
| title | String | 일정 제목 |
| content | String | 일정 내용 |
| writer | String | 작성자명 |
| createdAt | LocalDateTime | 생성일시 |
| modifiedAt | LocalDateTime | 수정일시 |

**Response 예시**
```json
{
    "id": 1,
    "title": "제목1",
    "content": "내용1",
    "writer": "작성자1",
    "createdAt": "2026-04-13T15:45:31.6632955",
    "modifiedAt": "2026-04-13T15:45:31.6632955"
}
```

---

### 2. 일정 단건 조회

- **Method:** `GET`
- **URL:** `/schedules/{id}`
- **응답 코드:** `200 OK`

**Path Parameter**

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| id | Long | 조회할 일정 ID |

**Response Body**

| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | 일정 ID |
| title | String | 일정 제목 |
| content | String | 일정 내용 |
| writer | String | 작성자명 |
| createdAt | LocalDateTime | 생성일시 |
| modifiedAt | LocalDateTime | 수정일시 |
| comments | List | 댓글 목록 |

**Response 예시**
```json
{
    "id": 1,
    "title": "제목1",
    "content": "내용1",
    "writer": "작성자1",
    "createdAt": "2026-04-13T16:37:09.985122",
    "modifiedAt": "2026-04-13T16:37:09.985122",
    "comments": []
}
```

---

### 3. 일정 전체 조회

- **Method:** `GET`
- **URL:** `/schedules`
- **응답 코드:** `200 OK`

**Response Body**

| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | 일정 ID |
| title | String | 일정 제목 |
| content | String | 일정 내용 |
| writer | String | 작성자명 |
| createdAt | LocalDateTime | 생성일시 |
| modifiedAt | LocalDateTime | 수정일시 |

**Response 예시**
```json
[
    {
        "id": 1,
        "title": "제목1",
        "content": "내용1",
        "writer": "작성자1",
        "createdAt": "2026-04-13T16:37:09.985122",
        "modifiedAt": "2026-04-13T16:37:09.985122"
    }
]
```

---

### 4. 작성자별 일정 전체 조회

- **Method:** `GET`
- **URL:** `/schedules?writer={writer}`
- **응답 코드:** `200 OK`

**Query Parameter**

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| writer | String | 조회할 작성자명 |

**Response Body**

| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | 일정 ID |
| title | String | 일정 제목 |
| content | String | 일정 내용 |
| writer | String | 작성자명 |
| createdAt | LocalDateTime | 생성일시 |
| modifiedAt | LocalDateTime | 수정일시 |

**Response 예시**
```json
[
    {
        "id": 1,
        "title": "제목1",
        "content": "내용1",
        "writer": "작성자1",
        "createdAt": "2026-04-13T14:51:54.241479",
        "modifiedAt": "2026-04-13T14:51:54.241479"
    }
]
```

---

### 5. 일정 수정

- **Method:** `PUT`
- **URL:** `/schedules/{id}`
- **응답 코드:** `200 OK`

**Path Parameter**

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| id | Long | 수정할 일정 ID |

**Request Body**

| 필드 | 타입 | 설명 |
|------|------|------|
| title | String | 수정할 제목 |
| writer | String | 수정할 작성자명 |
| password | String | 확인용 비밀번호 |

**Response Body**

| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | 일정 ID |
| title | String | 수정된 제목 |
| content | String | 일정 내용 |
| writer | String | 수정된 작성자명 |
| createdAt | LocalDateTime | 생성일시 |
| modifiedAt | LocalDateTime | 수정일시 |

**Response 예시**
```json
{
    "id": 1,
    "title": "변경제목",
    "content": "내용1",
    "writer": "변경작성자",
    "createdAt": "2026-04-13T16:37:09.985122",
    "modifiedAt": "2026-04-13T16:43:04.444339"
}
```

---

### 6. 일정 삭제

- **Method:** `DELETE`
- **URL:** `/schedules/{id}`
- **응답 코드:** `204 No Content`

**Path Parameter**

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| id | Long | 삭제할 일정 ID |

**Request Body**

| 필드 | 타입 | 설명 |
|------|------|------|
| password | String | 확인용 비밀번호 |

**Response Body**

없음

---

## 댓글 (Comments)

---

### 7. 일정 댓글 추가

- **Method:** `POST`
- **URL:** `/schedules/{id}/comments`
- **응답 코드:** `201 Created`

**Path Parameter**

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| id | Long | 댓글을 달 일정 ID |

**Request Body**

| 필드 | 타입 | 설명 |
|------|------|------|
| commentContent | String | 댓글 내용 |
| commentWriter | String | 댓글 작성자명 |
| commentPassword | String | 댓글 비밀번호 |

**Response Body**

| 필드 | 타입 | 설명 |
|------|------|------|
| commentId | Long | 생성된 댓글 ID |
| commentContent | String | 댓글 내용 |
| commentWriter | String | 댓글 작성자명 |
| createdAt | LocalDateTime | 생성일시 |
| modifiedAt | LocalDateTime | 수정일시 |

**Response 예시**
```json
{
    "commentId": 1,
    "commentContent": "댓글1",
    "commentWriter": "작성자1",
    "createdAt": "2026-04-13T16:40:49.6554589",
    "modifiedAt": "2026-04-13T16:40:49.6554589"
}
```

---

#### Q1. 3 Layer Architecture(Controller, Service, Repository)를 적절히 적용했는지 확인해 보고, 왜 이러한 구조가 필요한지 작성해 주세요.

a. 각 레이어의 책임 분리, 유지보수/확장에 유리 

#### Q2. @RequestParam, @PathVariable, @RequestBody가 각각 어떤 어노테이션인지, 어떤 특징을 갖고 있는지 작성해 주세요.

a. requsetparam = url의 쿼리스트링에서 값을 가져옴, pathvariable = url자체에서 값을 가져옴, requestbody = http요청의 body에서 json데이터를 꺼내 객체로 가져옴
