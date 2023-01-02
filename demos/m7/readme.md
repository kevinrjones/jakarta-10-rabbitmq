# Demo Notes

## Demo 1 - Poll Consumer
1. Run the API Client
    - Gradle..Api Client..Tasks...application..run
1. Send HTTP Message From Postman
    - Connect to http://localhost:7777/users
    - POST request
    - JSON format {"name": "Kevin", "password" : "foobar"}
        > In Postman choose, Body, raw, JSON
1. Run the Poll Consumer
    - Gradle..RegisterUserConsolePollConsumer..Tasks..application..run
        

## Demo 1 - Poll Consumer
1. Run the API Client
    - Gradle..Api Client..Tasks...application..run
1. Run the Pull Consumer
    - Gradle..RegisterUserConsolePullConsumer..Tasks..application..run
1. Send HTTP Message From Postman
    - Connect to http://localhost:7777/users
    - POST request
    - JSON format {"name": "Kevin", "password" : "foobar"}
        > In Postman choose, Body, raw, JSON
                