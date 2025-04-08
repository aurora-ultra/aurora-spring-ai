package com.rakuten.ross.aurora.application;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@Builder
@RequiredArgsConstructor
public class ChatOption implements Serializable {

	private final boolean enableInternalSearch;
	private final boolean enableExternalSearch;
	private final boolean enableExampleTools;
	private final boolean enableMemory;
	private final boolean enableDebug;

	private final int retrieveTopK;

	private final String model;
}
