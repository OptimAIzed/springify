package com.code_assistant.project_service.services;

import com.code_assistant.project_service.helper.ExtractTextJson;
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
import java.util.HashMap;
import java.util.List;

@Service
public class AIService {

    private static final Logger logger = LoggerFactory.getLogger(AIService.class);

    private static final String GEMINI_MODEL = "gemini-1.5-flash";
    @Value("${gemini_api_key}")
    private String API_KEY ;
    private String conversationHistory = "";

    public HashMap<String, List<HashMap<String, String>>> sendImage(String artifactId, String packageName, String base64Image) {
        String payload = String.format(
                "{" + "\"contents\": [{" +
                           "\"parts\": [" +
                             "{\"text\": \"Provide the fully implemented services, repositories, entities, and controllers for the given class diagram in Java, ensuring that all required methods, including getters and setters, are present. For each file, include its name prefixed with # (e.g., #Person.java) followed immediately by the corresponding Java code. The artifactId is %s and the packageName is %s. Make sure that all four components—services, repositories, entities, and controllers—are fully implemented, with all necessary methods, dependencies, and logic based on the provided class diagram. Do not add any extra explanations or list file names separately; just provide the file name and its content.\"}," +
                           "{" +
                       "\"inline_data\": {" +
                        "\"mime_type\": \"image/jpeg\"," +
                        "\"data\": \"%s\"" +
                            "}" +
                            "}" +
                            "]" +
                   "}]" + "}", artifactId, packageName, base64Image);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=%s",
                API_KEY
        );
        JSONObject response = restTemplate.postForObject(url, request, JSONObject.class);
        String extracted = ExtractTextJson.extract(response);
        System.out.println(ExtractTextJson.extractCodeFromText(extracted));
        return ExtractTextJson.extractCodeFromText(extracted);
    }

    public String generateDependencies(String base64Image) {
        String payload = String.format(
                "{" + "\"contents\": [{" +
                        "\"parts\": [" +
                        "{\"text\": \"Generate a JSON array of dependencies based on the provided Java class diagram. Each dependency should include the following fields: id, name, description, category, and dependency. The dependency field should use the simplified dependency identifiers recognized by start.spring.io, such as 'web' instead of 'spring-boot-starter-web'. Ensure the dependency field uses the correct identifiers by referencing the list available from Spring Initializr. Provide only the JSON array as the output, without any additional explanations or text.\"}," +
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
        JSONObject response = restTemplate.postForObject(url, request, JSONObject.class);
        String extracted = ExtractTextJson.extract(response);
        ExtractTextJson.removeTicks(extracted);
        System.out.println(extracted);
        return ExtractTextJson.removeTicks(extracted);
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
