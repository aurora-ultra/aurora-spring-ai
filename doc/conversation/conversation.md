###  request

```Json
{
  "localMessageId": "uuid",
  "conversationId": "uuid",
  "chatOption": {},
  "messages": [
    {
      "content": "你好，大模型应用"
    }
  ]
}
```

### Reply

```json
{
  "replyId": "uuid",
  "messageId": "uuid",
  "conversationId": "uuid",
  "localMessageId": "uuid",
  "message": {
    "content": "..."
  }
}
```