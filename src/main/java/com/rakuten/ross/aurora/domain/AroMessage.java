package com.rakuten.ross.aurora.domain;

import lombok.*;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AroMessage {

    public enum Type {
        User,
        Assistant
    }

    private String messageId;

    private String replyMessageId;

    private LocalDateTime sendTime;

    private Type messageType;

    private String conversationId;

    private List<AroMessageContent> content;


    public boolean isType(Type type) {
        return type == this.messageType;
    }


    @SuppressWarnings("unused")
    @Builder(builderClassName = "AssistantTypeBuilder", builderMethodName = "assistant")
    static AroMessage fromAssistant(String messageId, String replyMessageId, LocalDateTime sendTime, String conversationId, List<AroMessageContent> content) {
        var aroMessage = new AroMessage();
        aroMessage.setMessageType(Type.Assistant);
        aroMessage.setMessageId(messageId);
        aroMessage.setReplyMessageId(replyMessageId);
        aroMessage.setSendTime(sendTime);
        aroMessage.setConversationId(conversationId);
        aroMessage.setContent(content);
        return aroMessage;
    }


    @SuppressWarnings("unused")
    @Builder(builderClassName = "UserTypeBuilder", builderMethodName = "user")
    static AroMessage userMessage(String messageId, String replyMessageId, LocalDateTime sendTime, String conversationId, List<AroMessageContent> content) {
        var aroMessage = new AroMessage();
        aroMessage.setMessageType(Type.User);
        aroMessage.setMessageId(messageId);
        aroMessage.setReplyMessageId(replyMessageId);
        aroMessage.setSendTime(sendTime);
        aroMessage.setConversationId(conversationId);
        aroMessage.setContent(content);
        return aroMessage;
    }


    public List<Message> toMessages() {
        var messages = new ArrayList<Message>();

        for (AroMessageContent aroMessageContent : this.getContent()) {

            if (this.getMessageType() == Type.User) {
                messages.add(new UserMessage(aroMessageContent.getText()));
            }

            if (this.getMessageType() == Type.Assistant) {
                messages.add(new AssistantMessage(aroMessageContent.getText()));
            }
        }
        return messages;
    }


}
