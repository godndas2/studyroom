# StudyRoom v1
## CI
- jenkins

## Rule
GET /v1/user/{userId} – 회원 userId에 해당하는 정보를 조회한다.  
GET /v1/users – 회원 리스트를 조회한다.  
POST /v1/user – 신규 회원정보를 입력한다.  
PUT /v1/user – 기존회원의 정보를 수정한다.  
DELETE /v1/user/{userId} – userId로 기존회원의 정보를 삭제한다.  

<i>Help</i>
```
https://localhost:443/swagger-ui.html
https://localhost:443/social/login
http://localhost:8080/ <- jenkins port
https://localhost:443/signin/google
( API Manager - Credential - GCP Oauth Client Seceret Pw )  
Https Keystore generate : keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 4000
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
## Model
- Board  
게시글 작성
![boardwriteapi](https://user-images.githubusercontent.com/34512538/64876015-3768f600-d689-11e9-9e46-9121ca0bcf3a.PNG)
## Oauth
- Https Keystore  
![https](https://user-images.githubusercontent.com/34512538/64970223-56ad9080-d8e0-11e9-8b71-f0a984b84f38.PNG)  
- Google  
![oauthgoogle](https://user-images.githubusercontent.com/34512538/65257015-7c82a180-db3b-11e9-9be0-e735f0940853.PNG)



## ISSUE
- social login -1004 error [kakao]
## ToDo


