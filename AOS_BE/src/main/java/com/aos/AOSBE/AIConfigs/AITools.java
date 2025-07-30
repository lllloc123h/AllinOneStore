package com.aos.AOSBE.AIConfigs;

import com.aos.AOSBE.DTOS.ForeCastDTO;
import com.aos.AOSBE.DTOS.ProductItemsDTOS;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Service.ProductItemsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.ai.chat.client.DefaultChatClientBuilder;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Component
public class AITools {
    @Autowired
    private ProductItemsService productItemsService;
    @Tool(description = "Get the current date and time in the user's timezone")
//    tooparam thong tin bo sung
    String getCurrentDateTime(@ToolParam(description = "Time in ISO-8601 format") String time) {
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }

    @Tool(description = "Set a user alarm for the given time, provided in ISO-8601 format")
    void setAlarm(String time) {
        LocalDateTime alarmTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("Alarm set for " + alarmTime);
    }
    @Tool(description = "Phân tích xu hướng của sản phẩm có id và dự đoán nhu cầu trong 30 ngày tới")
    public ForeCastDTO analyzeProductTrend(Integer id) {
        return productItemsService.getForeCastDataLast30Days(id);
    }

}
