package com.rakuten.ross.aurora.application.ablity.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DateTimeTools {

	@Tool(description = "Get the current date and time in the user's timezone")
	public String getCurrentDateTime() {
		var now = LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
		log.info("Current time is {}", now);
		return now;
	}

	@Tool(description = "Set a user alarm for the given time, provided in ISO-8601 format")
	public void setAlarm(String time) {
		LocalDateTime alarmTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
		log.info("Alarm time is {}", alarmTime);
	}

}