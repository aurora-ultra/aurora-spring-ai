package com.rakuten.ross.aurora.application;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@Builder
@RequiredArgsConstructor
public class ChatOption implements Serializable {

	private final boolean search;
	private final int searchTopK;
	private final int historySize;

}
