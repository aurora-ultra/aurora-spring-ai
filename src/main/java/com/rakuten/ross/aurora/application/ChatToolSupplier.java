package com.rakuten.ross.aurora.application;


public interface ChatToolSupplier<T> {

	String getName();

	String getDescription();

	boolean support(ChatContext context);

	T getTool(ChatContext context);

}
