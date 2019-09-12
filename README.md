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
http://localhost:8080/social/login
```
![swagger_html](https://user-images.githubusercontent.com/34512538/64470974-6f060880-d186-11e9-9797-76e971781f01.PNG)
## Spring Security
- Sigin Up, Sign In
- 제한된 리소스에 접근가능한 ROLE_USER 권한은 "회원"에게 부여
- 권한을 가진 회원이 Sign In에 성공 -> 제한된 리소스에 접근가능한 Jwt Token 발급  

<b>SignIn Success</b>
![token](https://user-images.githubusercontent.com/34512538/64478039-58d76700-d1dd-11e9-96a8-cabebfe7e066.PNG)  
<b>회원 조회</b>
![jwt login](https://user-images.githubusercontent.com/34512538/64483342-1d6b8580-d23b-11e9-8112-01a7a3af5483.PNG)  

> http://localhost:8080/social/login/kakao?code=XXXXXXXXXXX
- SocialController.java의 /social/login/kakao에서 parameter로 전달된 code 정보로 kakao access_token을 얻는 api 호출  
- access_token으로 kakao의 profile api 호출
- kakao 정보로 가입되어있는지 database에서 확인
- 가입자라면 jwt token 발급  

![kakao restapi](https://user-images.githubusercontent.com/34512538/64781295-7caff980-d59d-11e9-884d-85400673d656.PNG)

## ISSUE
## ToDo


