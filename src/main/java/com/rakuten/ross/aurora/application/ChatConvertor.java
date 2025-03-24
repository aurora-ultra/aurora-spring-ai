package com.rakuten.ross.aurora.application;

import com.rakuten.ross.aurora.application.dto.AroMessageContentVo;
import com.rakuten.ross.aurora.application.dto.AroMessageDto;
import com.rakuten.ross.aurora.domain.AroMessage;
import com.rakuten.ross.aurora.domain.AroMessageContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatConvertor {

    public AroMessageContent toDomain(AroMessageContentVo aroMessageContentVo) {
        return new AroMessageContent()
            .setText(aroMessageContentVo.getText())
            ;
    }

    public List<AroMessageContent> toDomain(Collection<AroMessageContentVo> aroMessageContentVos) {
        return aroMessageContentVos.stream()
            .map(this::toDomain)
            .toList()
            ;
    }

    public AroMessageDto toDto(AroMessage message) {
        return  new AroMessageDto()
            .setId(message.getMessageId())
            .setConversationId(message.getConversationId())
            .setSendTime(message.getSendTime())
            .setReplyMessageId(message.getReplyMessageId())
            .setConversationId(message.getConversationId())
            .setMessageType(message.getMessageType())
            .setContent(this.toVo(message.getContent()))
            ;
    }

    private AroMessageContent toVo(AroMessageContent content) {
        return new AroMessageContent()
            .setText(content.getText())
            ;
    }

    private List<AroMessageContent> toVo(List<AroMessageContent> contents) {
        return contents.stream()
            .map(this::toVo)
            .toList()
            ;
    }
}
