package com.rakuten.ross.aurora.application.command;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@Builder
@RequiredArgsConstructor
public class ChatOption implements Serializable {

	private final boolean search;

}
