package com.code_assistant.project_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.apache.logging.log4j.util.Strings;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;

import java.io.IOException;
import java.security.Key;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class AIService {

    private static final Logger logger = LoggerFactory.getLogger(AIService.class);

    private static final String GEMINI_MODEL = "gemini-1.5-flash";
    @Value("${gemini_api_key}")
    private String API_KEY ;
    private String conversationHistory = "";
    public String sendImage(String base64Image) {
        String payload = String.format(
                "{" + "\"contents\": [{" +
                           "\"parts\": [" +
                             "{\"text\": \"Give entities for this class diagram in java.\"}," +
                           "{" +
                       "\"inline_data\": {" +
                        "\"mime_type\": \"image/jpeg\"," +
                        "\"data\": \"%s\"" +
                            "}" +
                            "}" +
                            "]" +
                   "}]" + "}", base64Image);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=%s",
                API_KEY
        );
        try {
            JSONObject response = restTemplate.postForObject(url, request, JSONObject.class);

            if (response != null) {
                List<Object> candidates = (List<Object>) response.get("candidates");
                if (candidates != null && !candidates.isEmpty()) {
                    LinkedHashMap candidate = (LinkedHashMap) candidates.get(0);

                    LinkedHashMap content = (LinkedHashMap) candidate.get("content");
                    if (content != null) {
                        List<Object> parts = (List<Object>) content.get("parts");
                        if (parts != null && !parts.isEmpty()) {
                            LinkedHashMap firstPart = (LinkedHashMap) parts.get(0);

                            String text = (String) firstPart.get("text");
                            System.out.println("Extracted text: " + text);
                        }
                    }
                }
            }

            return restTemplate.postForObject(url, request, String.class);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }


        return "No Text";
    }
    public String chat(String prompt) {
        String fullPrompt = prompt;

        if (!Strings.isBlank(conversationHistory)) {
            fullPrompt = "[Context]" + conversationHistory + " [Content] " + prompt;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        fullPrompt = getPromptBody(fullPrompt);
        HttpEntity<String> requestEntity = new HttpEntity<>(fullPrompt, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://generativelanguage.googleapis.com/v1beta/models/" + GEMINI_MODEL + ":generateContent?key=" + API_KEY,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();

        if (statusCode == HttpStatus.OK) {
            String responseText = responseEntity.getBody();
            try {
                responseText = parseGeminiResponse(responseText);
                conversationHistory += prompt + "\n" + responseText + "\n";
            } catch (Exception e) {
                logger.error("Error parsing response", e);
            }
            return responseText;
        } else {
            throw new RuntimeException("API request failed with status code: " + statusCode + " and response: " + responseEntity.getBody());
        }
    }

    public String getPromptBody(String prompt) {
        JSONObject promptJson = new JSONObject();

        JSONArray contentsArray = new JSONArray();
        JSONObject contentsObject = new JSONObject();
        contentsObject.put("role", "user");

        JSONArray partsArray = new JSONArray();
        JSONObject partsObject = new JSONObject();
        partsObject.put("text", prompt);
        partsArray.add(partsObject);
        contentsObject.put("parts", partsArray);

        contentsArray.add(contentsObject);
        promptJson.put("contents", contentsArray);

        JSONArray safetySettingsArray = new JSONArray();

        JSONObject hateSpeechSetting = new JSONObject();
        hateSpeechSetting.put("category", "HARM_CATEGORY_HATE_SPEECH");
        hateSpeechSetting.put("threshold", "BLOCK_ONLY_HIGH");
        safetySettingsArray.add(hateSpeechSetting);

        JSONObject dangerousContentSetting = new JSONObject();
        dangerousContentSetting.put("category", "HARM_CATEGORY_DANGEROUS_CONTENT");
        dangerousContentSetting.put("threshold", "BLOCK_ONLY_HIGH");
        safetySettingsArray.add(dangerousContentSetting);

        JSONObject sexuallyExplicitSetting = new JSONObject();
        sexuallyExplicitSetting.put("category", "HARM_CATEGORY_SEXUALLY_EXPLICIT");
        sexuallyExplicitSetting.put("threshold", "BLOCK_ONLY_HIGH");
        safetySettingsArray.add(sexuallyExplicitSetting);

        JSONObject harassmentSetting = new JSONObject();
        harassmentSetting.put("category", "HARM_CATEGORY_HARASSMENT");
        harassmentSetting.put("threshold", "BLOCK_ONLY_HIGH");
        safetySettingsArray.add(harassmentSetting);

        promptJson.put("safetySettings", safetySettingsArray);

        JSONObject parametersJson = new JSONObject();
        parametersJson.put("temperature", 0.5);
        parametersJson.put("topP", 0.99);
        promptJson.put("generationConfig", parametersJson);

        return promptJson.toJSONString();
    }

    public String parseGeminiResponse(String jsonResponse) throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonResponse);

        JSONArray candidatesArray = (JSONArray) jsonObject.get("candidates");

        JSONObject candidateObject = (JSONObject) candidatesArray.get(0);
        JSONObject contentObject = (JSONObject) candidateObject.get("content");

        JSONArray partsArray = (JSONArray) contentObject.get("parts");

        JSONObject partObject = (JSONObject) partsArray.get(0);
        return (String) partObject.get("text");
    }

}
