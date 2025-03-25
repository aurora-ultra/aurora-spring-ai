package com.rakuten.ross.aurora.application.command;

import com.rakuten.ross.aurora.core.layer.Dto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@Builder
@RequiredArgsConstructor
public class ChatOption implements Dto {

	private final boolean search;

}
