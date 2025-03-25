package com.rakuten.ross.aurora.core.support;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class TimeProvider {

	public LocalDateTime now() {
		return LocalDateTime.now();
	}
}
