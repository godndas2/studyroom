<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>kakao login</title>
</head>
<body>
<button onclick="popupKakaoLogin()">KakaoLogin</button>
<script>
    function popupKakaoLogin() {
        window.open('${loginUrl}', 'popupKakaoLogin', 'width=700,height=500,scrollbars=0,toolbar=0,menubar=no')
    }
</script>
</body>
</html>