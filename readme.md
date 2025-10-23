# RBAC (Role-Based Access Control) 서비스

Spring Boot 기반의 역할 기반 접근 제어 시스템입니다. 사용자 인증, 권한 관리, 프로젝트 관리 기능을 제공합니다.

### 구현 기능

#### 사용자 인증

- 회원가입
- 로그인
- 권한 변경

#### 프로젝트 관리

- 프로젝트 생성
- 프로젝트 조회
- 프로젝트 수정
- 프로젝트 삭제

#### 역할 기반 접근 제어

- A: 관리자 (모든 기능 접근 가능)
- B: 유저1 (생성,수정,삭제)
- C: 유저2 (생성,삭제)
- D: 유저3 (생성,조회)

# 설치 및 실행

## 1. 사전 요구사항

### Java 17 이상

```bash
java -version
```

### Maven

```bash
mvn --version
```

# 2. 애플리케이션 실행

## 프로젝트 클론

```bash
git clone https://github.com/yourusername/rbac-service.git
cd rbac-service
```

## 의존성 설치 및 실행

```bash
mvn clean install
mvn spring-boot:run
```

## 애플리케이션 접속

```
http://localhost:8081
```

# 3. 개발 환경 확인

## H2 데이터베이스 콘솔

```
http://localhost:8081/h2-console
```

## Swagger API 문서

```
http://localhost:8081/swagger-ui/index.html
```

# API 문서

## 🔐 사용자 인증 API

### 회원가입

```http
POST /auth/register
Content-Type: application/json

{
  "userName": "사용자명",
  "userId": "user123",
  "password": "Password123!@",
  "passwordConfirm": "Password123!@",
  "auth": "B"
}
```

### 로그인

```http
POST /auth/login
Content-Type: application/json

{
  "userId": "user123",
  "password": "Password123!@"
}
```

### 권한 변경

```http
PATCH /auth/permissions/{userId}
Content-Type: application/json

Parameters:
- userId (path): 사용자 ID

Body: "A" | "B" | "C" | "D"
```

## 📋 프로젝트 관리 API

### 프로젝트 생성

```http
POST /projects
Content-Type: application/json

{
  "projectName": "새 프로젝트",
  "pricingPlan": {
    "id": 1
  }
}
```

### 프로젝트 조회

```http
GET /projects/{id}

Parameters:
- id (path): 프로젝트 ID
```

### 프로젝트 수정

```http
PATCH /projects/{id}
Content-Type: application/json

Parameters:
- id (path): 프로젝트 ID

{
  "projectName": "수정 프로젝트",
  "pricingPlan": {
    "id": 1
  }
}
```

### 프로젝트 삭제

```http
DELETE /projects/{id}

Parameters:
- id (path): 프로젝트 ID
```

#### 응답 예시

```json
{
  "message": "프로젝트가 성공적으로 삭제되었습니다."
}
```

# 권한별 접근 제어

## 프로젝트 권한

| 기능 | A (관리자) | B   | C   | D   |
| ---- | ---------- | --- | --- | --- |
| 생성 | O          | O   | O   | O   |
| 수정 | O          | O   | X   | X   |
| 삭제 | O          | O   | O   | X   |
| 조회 | O          | X   | X   | O   |

# 에러 응답

## 인증 실패

```json
{
  "error": "잘못된 사용자 ID 또는 비밀번호입니다."
}
```

## 권한 부족

```json
{
  "error": "Access denied"
}
```

## 리소스 없음

```json
{
  "error": "Project not found"
}
```

## 유효성 검사 실패

```json
{
  "error": "비밀번호는 최소 2개의 대문자, 2개의 소문자, 1개의 숫자, 2개의 특수문자를 포함해야 하며 최소 8자 이상이어야 합니다."
}
```
