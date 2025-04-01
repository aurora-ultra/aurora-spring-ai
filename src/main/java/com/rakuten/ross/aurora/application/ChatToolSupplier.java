package com.rakuten.ross.aurora.application;


public interface ChatToolSupplier {

	boolean support(ChatContext context);

	ChatTool getTool(ChatContext context);

}
