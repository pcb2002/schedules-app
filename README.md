# 📝 일정 관리 서비스 API 명세서

### Base URL
- `http://localhost:8080`

---

## 1. 일정(Schedule) API

### 1-1. 일정 생성 (Lv 1, Lv 7)
새로운 일정을 등록합니다. 입력값 검증을 수행하며, 응답 시 비밀번호는 제외됩니다.

- **URL:** `/schedules`
- **Method:** `POST`
- **Request Body (JSON)**

| 필드명 | 타입 | 필수여부 | 제약조건 (Validation) | 설명 |
| --- | --- | --- | --- | --- |
| `title` | String | O | 필수값, 최대 30자 이내 | 일정 제목 |
| `content` | String | O | 필수값, 최대 200자 이내 | 일정 내용 |
| `author` | String | O | 필수값 | 작성자명 |
| `password` | String | O | 필수값 | 비밀번호 |

```json
// 요청 예시
{
  "title": "스프링 과제하기",
  "content": "JPA와 3 Layer Architecture를 활용한 일정 관리 앱 개발",
  "author": "홍길동",
  "password": "password123!"
}
```

- **Response (201 Created)**

```json
// 응답 예시
{
  "id": 1,
  "title": "스프링 과제하기",
  "content": "JPA와 3 Layer Architecture를 활용한 일정 관리 앱 개발",
  "author": "홍길동",
  "createdAt": "2026-04-09T15:44:00",
  "updatedAt": "2026-04-09T15:44:00"
}
```

- **Response (400 Bad Request)**
```json
  {
  "status": 400,
  "error": "Bad Request",
  "message": "일정 제목은 필수 입력값입니다."
  }
```

```json
  {
  "status": 400,
  "error": "Bad Request",
  "message": "일정 제목은 최대 30자 이내여야 합니다."
}
```

```json
  {
  "status": 400,
  "error": "Bad Request",
  "message": "일정 내용은 필수 입력값입니다."
}
```

```json
  {
  "status": 400,
  "error": "Bad Request",
  "message": "일정 내용은 최대 200자 이내여야 합니다."
}
```

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "작성자명은 필수 입력값입니다."
}
```

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "비밀번호는 필수 입력값입니다."
}
```

---

### 1-2. 전체 일정 조회 (Lv 2)
등록된 모든 일정을 조회합니다. 작성자명을 조건으로 필터링할 수 있으며, 수정일 기준 내림차순으로 정렬됩니다.

- **URL:** `/schedules`
- **Method:** `GET`
- **Query Parameter**

| 파라미터명 | 타입 | 필수여부 | 설명 |
| --- | --- | --- | --- |
| `author` | String | X | 작성자명 필터링 (없을 시 전체 조회) |

- **Response (200 OK)**

```json
// 응답 예시 (배열 형태)
[
  {
    "id": 2,
    "title": "오후 회의",
    "content": "기획팀 주간 회의",
    "author": "홍길동",
    "createdAt": "2026-04-09T14:00:00",
    "updatedAt": "2026-04-09T14:30:00"
  },
  {
    "id": 1,
    "title": "스프링 과제하기",
    "content": "JPA와 3 Layer Architecture를 활용한 일정 관리 앱 개발",
    "author": "홍길동",
    "createdAt": "2026-04-09T12:00:00",
    "updatedAt": "2026-04-09T12:00:00"
  }
]
```

---

### 1-3. 선택 일정 단건 조회 (+댓글 포함) (Lv 2, Lv 6)
선택한 일정의 상세 정보와 해당 일정에 달린 댓글 목록을 함께 조회합니다.

- **URL:** `/schedules/{scheduleId}`
- **Method:** `GET`
- **Path Variable**
    - `scheduleId` (Long) : 조회할 일정의 고유 ID

- **Response (200 OK)**

```json
// 응답 예시 (일정 정보 + 연관된 댓글 목록 포함)
{
  "id": 1,
  "title": "스프링 과제하기",
  "content": "JPA와 3 Layer Architecture를 활용한 일정 관리 앱 개발",
  "author": "홍길동",
  "createdAt": "2026-04-09T12:00:00",
  "updatedAt": "2026-04-09T12:00:00",
  "comments": [
    {
      "id": 1,
      "scheduleId": 1,
      "content": "파이팅입니다!",
      "author": "김철수",
      "createdAt": "2026-04-09T13:10:00",
      "updatedAt": "2026-04-09T13:10:00"
    }
  ]
}
```

- **Response (404 Not Found)**

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "해당 일정을 찾을 수 없습니다."
}
```

---

### 1-4. 선택 일정 수정 (Lv 3, Lv 7)
일정의 제목과 작성자명만 수정할 수 있습니다. 비밀번호가 일치해야만 수정이 가능합니다.

- **URL:** `/schedules/{scheduleId}`
- **Method:** `PATCH` (부분 수정이므로 PATCH 또는 PUT 사용)
- **Request Body (JSON)**

| 필드명 | 타입 | 필수여부 | 제약조건 | 설명 |
| --- | --- | --- | --- | --- |
| `title` | String | O | 필수값, 최대 30자 이내 | 수정할 일정 제목 |
| `author` | String | O | 필수값 | 수정할 작성자명 |
| `password` | String | O | 필수값 | 본인 확인용 비밀번호 |
*(요구사항에 맞춰 내용(`content`)은 수정 요청 필드에서 제외)*

```json
{
  "title": "스프링 과제 제출(완료)",
  "author": "홍길동(수정)",
  "password": "password123!"
}
```

- **Response (200 OK)**

```json
// 수정 후 updatedAt이 변경됨
{
  "id": 1,
  "title": "스프링 과제 제출(완료)",
  "content": "JPA와 3 Layer Architecture를 활용한 일정 관리 앱 개발",
  "author": "홍길동(수정)",
  "createdAt": "2026-04-09T12:00:00",
  "updatedAt": "2026-04-09T16:00:00"
}
```

- **Response (400 Bad Request)**
- 
```json
  {
  "status": 400,
  "error": "Bad Request",
  "message": "일정 제목은 필수 입력값입니다."
  }
```

```json
  {
  "status": 400,
  "error": "Bad Request",
  "message": "일정 제목은 최대 30자 이내여야 합니다."
}
```

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "작성자명은 필수 입력값입니다."
}
```

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "비밀번호는 필수 입력값입니다."
}
```

- **Response (401 Unauthorized)**

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "비밀번호가 일치하지 않습니다."
}
```

- **Response (404 Not Found)**

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "해당 일정을 찾을 수 없습니다."
}
```

---

### 1-5. 선택 일정 삭제 (Lv 4)
선택한 일정을 삭제합니다. 비밀번호가 일치해야만 삭제할 수 있습니다.

- **URL:** `/schedules/{scheduleId}`
- **Method:** `DELETE`
- **Request Body (JSON)**

| 필드명 | 타입 | 필수여부 | 설명 |
| --- | --- | --- | --- |
| `password` | String | O | 본인 확인용 비밀번호 |

```json
{
  "password": "password123!"
}
```

- **Response (204 No Content)**
    - 본문 없이 상태 코드 `204`만 반환 (성공적으로 삭제됨)

- **Response (401 Unauthorized)**

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "비밀번호가 일치하지 않습니다."
}
```

---

## 2. 댓글(Comment) API

### 2-1. 댓글 생성 (Lv 5, Lv 7)
특정 일정에 댓글을 작성합니다. 한 일정당 최대 10개까지만 작성 가능합니다.

- **URL:** `/schedules/{scheduleId}`
- **Method:** `POST`
- **Path Variable**
    - `scheduleId` (Long) : 댓글을 달 일정의 고유 ID
- **Request Body (JSON)**

| 필드명 | 타입 | 필수여부 | 제약조건 (Validation) | 설명 |
| --- | --- | --- | --- | --- |
| `content` | String | O | 필수값, 최대 100자 이내 | 댓글 내용 |
| `author` | String | O | 필수값 | 댓글 작성자명 |
| `password` | String | O | 필수값 | 비밀번호 |

```json
// 요청 예시
{
  "content": "파이팅입니다!",
  "author": "김철수",
  "password": "comment_password!"
}
```

- **Response (201 Created)**

```json
// 응답 예시 (비밀번호 제외)
{
  "id": 1,
  "scheduleId": 1,
  "content": "파이팅입니다!",
  "author": "김철수",
  "createdAt": "2026-04-09T13:10:00",
  "updatedAt": "2026-04-09T13:10:00"
}
```

- **Response (400 Bad Request)**

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "댓글은 최대 10개 입니다."
}
```

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "댓글 내용은 필수 입력값입니다."
}
```

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "댓글 내용은 최대 100자 이내여야 합니다."
}
```

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "작성자명은 필수 입력값입니다."
}
```

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "비밀번호는 필수 입력값입니다."
}
```