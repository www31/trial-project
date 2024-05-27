## Secure Your Spring Boot Application with Email OTP Verification | Step-by-Step Guide part 1:
https://www.youtube.com/watch?v=MQX5CsFOmoY
## Secure Your Spring Boot Application with Email OTP Verification | Step-by-Step Guide part 2:
https://www.youtube.com/watch?v=Yoh18Mfobb0
## Secure Your Spring Boot Application with Email OTP Verification | Step-by-Step Guide part 3:
https://www.youtube.com/watch?v=4EWvvCFEoNc

localhost:8080/h2-console
POST: http://localhost:8080/api/v1/auth/register
{
"userName" : "test",
"email" : "anonymoususer00007421041@gmail.com",
"password" : "test123"
}
POST: http://localhost:8008/api/v1/auth/verify?email=anonymoususer00007421041@gmail.com&otp=732634
