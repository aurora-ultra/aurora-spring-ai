package com.rakuten.ross.aurora.application;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Getter
@Builder
public class ChatReply {

	private final Flux<String> contents;

	public Flux<Content> getContents() {
		return contents.map(ChatReply.Content::of);
	}

	@Getter
	@RequiredArgsConstructor
	public static class Content {
		private final String text;

		public static Content of(String text) {
			return new Content(text);
		}
	}
}
