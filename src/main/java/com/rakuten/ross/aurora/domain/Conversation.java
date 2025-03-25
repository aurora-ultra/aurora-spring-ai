package com.rakuten.ross.aurora.domain;

import java.time.LocalDateTime;
import com.rakuten.ross.aurora.core.layer.Model;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Conversation implements Model {

	private String id;

	private LocalDateTime startTime;

	public static Conversation start(String id, LocalDateTime startTime) {
		var conversation = new Conversation();
		conversation.setId(id);
		conversation.setStartTime(startTime);
		return conversation;
	}

}
