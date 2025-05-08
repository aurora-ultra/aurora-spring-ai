package com.rakuten.ross.aurora.application.support;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.rakuten.ross.aurora.application.ChatTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(1)
@Component
public class ExampleTool implements ChatTool {

	@Override
	public String getName() {
		return "天气信息搜索";
	}

	@Override
	public String getDescription() {
		return """
				获取天气预报
				""";
	}



	@Tool(description = "get the forecast weather of the specified city and date")
	public String getForecast(@ToolParam(description = "日期") LocalDate date,
							  @ToolParam(description = "城市") String city) {
		return """
				- 当前温度：12°C \n
				- 天气状况：雾霾 \n
				- 体感温度：12°C \n
				- 今天天气：大部分地区多云，最低气温9°C \n
				- 空气质量：轻度污染 (51-100)，主要污染物 PM2.5 75 μg/m³ \n
				- 风速：轻风 (2 - 5 公里/小时)，西南风 1级 \n
				- 湿度：78% \n
				- 能见度：能见度差 (1 - 2 公里)，2 公里 \n
				- 气压：1018 hPa \n
				- 露点：8°C \n
				""";
	}


}