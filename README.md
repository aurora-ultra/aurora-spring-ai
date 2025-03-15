# Getting Started

- run redis with docker locally

    ``docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 -e REDIS_ARGS="--requirepass 111111" redis/redis-stack:latest`` 

- run [AuroraJApplication.java](src/main/java/com/rakuten/ross/auroraj/AuroraJApplication.java)

- call learn endpoint to prepare the data in redis

    ``curl -XPOST http://localhost:8080/learn``

- call chat endpoint

    ```shell
    curl --location --request POST 'http://localhost:8080/chat' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "input": "我想要订单系统详细设计总结"
    }'
    ```