package com.rakuten.ross.aurora.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.rakuten.ross.aurora.core.layer.DomainModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage implements DomainModel, PromptMessageProvider {

	public enum Type {
		User,
		Assistant
	}

	private String messageId;

	private String replyMessageId;

	private LocalDateTime sendTime;

	private Type messageType;

	private String conversationId;

	private List<ChatMessageContent> content;

	public boolean isType(Type type) {
		return type == this.messageType;
	}

	@Builder(builderClassName = "AssistantTypeBuilder", builderMethodName = "assistant")
	public static ChatMessage fromAssistant(ChatMessage userMessage, LocalDateTime sendTime, List<ChatMessageContent> content) {
		var aroMessage = new ChatMessage();
		aroMessage.setMessageId(UUID.randomUUID().toString());
		aroMessage.setMessageType(Type.Assistant);
		aroMessage.setReplyMessageId(userMessage.getMessageId());
		aroMessage.setConversationId(userMessage.getConversationId());
		aroMessage.setSendTime(sendTime);
		aroMessage.setContent(content);
		return aroMessage;
	}

	@Builder(builderClassName = "UserTypeBuilder", builderMethodName = "user")
	public static ChatMessage fromUser(String messageId, String conversationId, LocalDateTime sendTime, List<ChatMessageContent> content) {
		var aroMessage = new ChatMessage();
		aroMessage.setMessageType(Type.User);
		aroMessage.setMessageId(messageId);
		aroMessage.setSendTime(sendTime);
		aroMessage.setConversationId(conversationId);
		aroMessage.setContent(content);
		return aroMessage;
	}

	public ChatMessage reply(List<ChatMessageContent> content) {
		return ChatMessage.assistant()
				.userMessage(this)
				.sendTime(this.getSendTime())
				.content(content)
				.build();
	}

	public ChatMessage reply(ChatMessageContent content) {
		return ChatMessage.assistant()
				.userMessage(this)
				.sendTime(this.getSendTime())
				.content(List.of(content))
				.build();
	}

	public ChatMessage reply(String content) {
		return ChatMessage.assistant()
				.userMessage(this)
				.sendTime(this.getSendTime())
				.content(List.of(ChatMessageContent.of(content)))
				.build();
	}

	public List<Message> createPromptMessages() {
		var messages = new ArrayList<Message>();

		for (ChatMessageContent chatMessageContent : this.getContent()) {

			if (this.getMessageType() == Type.User) {
				messages.add(new UserMessage(chatMessageContent.getText()));
			}

			if (this.getMessageType() == Type.Assistant) {
				messages.add(new AssistantMessage(chatMessageContent.getText()));
			}

		}
		return messages;
	}

	public String tractContent(){
		return getContent().stream()
				.map(ChatMessageContent::getText)
				.collect(Collectors.joining(";"));
	}

}
