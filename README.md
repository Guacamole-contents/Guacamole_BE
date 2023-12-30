#### 개발 환경
- JAVA 17
- Spring Boot 3.x.
- MySQL

#### Git
- 요구사항을 issue에 기록하고 pr 단위로 작업을 진행했습니다.

# 📌 애플리케이션 개발환경 실행 방법

## #1 Local
1. Git clone (https://github.com/Guacamole-contents/Guacamole_BE.git)
2. application-secret.yml.default -> application-secret.yml 파일명 변경
3. application-secret.yml 환경변수 직접 기입
4. 로컬 MySQL 접속 후 데이터베이스 생성
5. 실행

- 데이터베이스 생성 SQL
```sql
DROP
DATABASE IF EXISTS ${DATABASE_NAME};
CREATE
DATABASE ${DATABASE_NAME};
USE
${DATABASE_NAME};
```

#### 📌 애플리케이션 환경 변수 (application-secret.yml)

${DATABASE_HOST} : 데이터베이스 호스트 주소

${DATABASE_PORT} : 데이터베이스 포트 번호

${DATABASE_NAME} : 데이터베이스 이름

${DATABASE_USERNAME} : 데이터베이스 사용자 이름

${DATABASE_PASSWORD} : 데이트베이스 비밀번호

${AWS_S3_BUCKET} : AWS S3 버킷 이름

${AWS_S3_ACCESS-KEY} : AWS S3 엑세스 키

${AWS_S3_SECRET-KEY} : AWS S3 시크릿 
