# StudyRoom v1
## Rule
GET /v1/user/{userId} – 회원 userId에 해당하는 정보를 조회한다.  
GET /v1/users – 회원 리스트를 조회한다.  
POST /v1/user – 신규 회원정보를 입력한다.  
PUT /v1/user – 기존회원의 정보를 수정한다.  
DELETE /v1/user/{userId} – userId로 기존회원의 정보를 삭제한다.  

<i>Help</i>
```
http://localhost:8080/swagger-ui.html
```
![swagger_html](https://user-images.githubusercontent.com/34512538/64470974-6f060880-d186-11e9-9797-76e971781f01.PNG)
## Spring Security
- Sigin Up, Sign In
- 제한된 리소스에 접근가능한 ROLE_USER 권한은 "회원"에게 부여
- 권한을 가진 회원이 Sign In에 성공 -> 제한된 리소스에 접근가능한 Jwt Token 발급
