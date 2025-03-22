package com.megaport.util;

import io.qameta.allure.Attachment;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Reporting {
    
    @Attachment(value = "{name}", type = "text/plain")
    public static String logList(String name, String message, List<?> list) {
        return message + ":\n" + list.toString();
    }
    
    @Attachment(value = "{title}", type = "application/json")
    public static byte[] logJson(String title, Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsBytes(data);
        } catch (Exception e) {
            return ("Error creating JSON: " + e.getMessage()).getBytes();
        }
    }

    public static void ReportFoundErrors(String title, String message, List<?> list) {
        Reporting.logJson(title, list);
        assertThat(false).as(message).isTrue();
        
    }
}
