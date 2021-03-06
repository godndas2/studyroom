# StudyRoom v1
## CI/CD
- jenkins.

## Rule
GET /v1/user/{userId} – 회원 userId에 해당하는 정보를 조회한다.  
GET /v1/users – 회원 리스트를 조회한다.  
POST /v1/user – 신규 회원정보를 입력한다.  
PUT /v1/user – 기존회원의 정보를 수정한다.  
DELETE /v1/user/{userId} – userId로 기존회원의 정보를 삭제한다.  

<i>Help</i>
```
https://github.com/settings/developers (add to oauth2.0)
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
- github  
![githuboauth](https://user-images.githubusercontent.com/34512538/65892204-da877280-e3e0-11e9-9263-331b55c1a858.PNG)  
- microsoft  
![aad](https://user-images.githubusercontent.com/34512538/66216590-b9849180-e700-11e9-9ee6-f2330602d7ee.PNG)  
- Spring Security Oauth(Important)  
![attemptAuthentication](https://user-images.githubusercontent.com/34512538/65437485-d5fe0f80-de5e-11e9-8254-edae5e261066.PNG)  
## Jwt Token
- SignIn  
> {"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ....",
"token_type":"bearer",
"refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9......",
"expires_in":35998,
"scope":"read"
}  
- Refresh Token check  
> // create token
{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzA5MTAyMzQsInVzZXJfbmFtZSI6Imh1Lmh5dW5Ac29mdHdhcmVpbmxpZmUuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImNkNzYyYjQ1LTI0ZGYtNGNmNC1iMjk5LWZjY2RjM2JmNGRjOSIsImNsaWVudF9pZCI6InRlc3RDbGllbnRJZCIsInNjb3BlIjpbInJlYWQiXX0.OKpZGkZKlWatdVKZuNxtEPA4i5Yei7CJIV3ZileDhfY",
"token_type":"bearer",
"refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJodS5oeXVuQHNvZnR3YXJlaW5saWZlLmNvbSIsInNjb3BlIjpbInJlYWQiXSwiYXRpIjoiY2Q3NjJiNDUtMjRkZi00Y2Y0LWIyOTktZmNjZGMzYmY0ZGM5IiwiZXhwIjoxNTcwOTI0MjM0LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiNzRhOTI0YzMtMzU2NC00ZmIxLTk5MTctNjFhMDk4MmZmNjA0IiwiY2xpZW50X2lkIjoidGVzdENsaWVudElkIn0.ncIN_1uTEQtVhgDOg2VQ8tXo-VSsau9JbPqE_ThyNEY",
"expires_in":35999,
"scope":"read"
}   
> // change - > refresh token -> access token modifiy
{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzA5MTAzMTUsInVzZXJfbmFtZSI6Imh1Lmh5dW5Ac29mdHdhcmVpbmxpZmUuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6IjViNWViZmQzLTE3YmUtNDg4MC04ZTczLTAwYmEzYjc5ZDYwYSIsImNsaWVudF9pZCI6InRlc3RDbGllbnRJZCIsInNjb3BlIjpbInJlYWQiXX0.eqB37caMfiT4iYRwlPOxdv_1ZZtAdaLqtyENjn2MWQk",
"token_type":"bearer",
"refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJodS5oeXVuQHNvZnR3YXJlaW5saWZlLmNvbSIsInNjb3BlIjpbInJlYWQiXSwiYXRpIjoiNWI1ZWJmZDMtMTdiZS00ODgwLThlNzMtMDBiYTNiNzlkNjBhIiwiZXhwIjoxNTcwOTI0MjM0LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiNzRhOTI0YzMtMzU2NC00ZmIxLTk5MTctNjFhMDk4MmZmNjA0IiwiY2xpZW50X2lkIjoidGVzdENsaWVudElkIn0.qRnoR1e-YaKVfxFt5chxHiX76FZIcBy-buXnV413OPY",
"expires_in":35999,
"scope":"read"
}  

## ISSUE
- mail 
- Module : cafe -> Jwt Token Create 안되는 현상
## ToDo
- azure https://docs.microsoft.com/ko-kr/java/azure/spring-framework/configure-spring-boot-starter-java-app-with-azure-active-directory?view=azure-java-stable  
- naver oauth
- front 어떻게 


