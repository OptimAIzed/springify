package com.code_assistant.project_service.helper;

import org.json.simple.JSONObject;

import java.net.StandardSocketOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ExtractTextJson {
    public static String extract(JSONObject response) {
        try {
            List<Object> candidates = (List<Object>) response.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                LinkedHashMap candidate = (LinkedHashMap) candidates.get(0);

                LinkedHashMap content = (LinkedHashMap) candidate.get("content");
                if (content != null) {
                    List<Object> parts = (List<Object>) content.get("parts");
                    if (parts != null && !parts.isEmpty()) {
                        LinkedHashMap firstPart = (LinkedHashMap) parts.get(0);

                        String text = (String) firstPart.get("text");
                        return text;
                    }
                }
            }
        } catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return "";
    }

    public static HashMap<String, List<HashMap<String, String>>> extractCodeFromText(String text) {
        text = text.replaceAll("```java", "").replaceAll("```", "").trim();

        HashMap<String,List<HashMap<String,String>>> output = new HashMap<>();
        output.put("repository",new ArrayList<>());
        output.put("service",new ArrayList<>());
        output.put("controller",new ArrayList<>());
        output.put("model",new ArrayList<>());

        StringBuilder fileName = new StringBuilder();
        boolean flag = false;
        StringBuilder code = new StringBuilder();
        boolean code_activator = false;
        for(int i = 0; i < text.length(); i++) {
            if(text.charAt(i) == '#') {
                flag = true;
            } else if(text.charAt(i) == '\n' && flag) {
                flag = false;
                code_activator = true;
            } else if(flag) {
                fileName.append(text.charAt(i));
            }

            if(code_activator) {
                if(text.charAt(i) == '#' || i == text.length()-1) {
                    if(fileName.toString().toLowerCase().contains("repository")) {
                        HashMap<String,String> file_code = new HashMap<>();
                        file_code.put(fileName.toString(), code.toString());
                        output.get("repository").add(file_code);
                    } else if(fileName.toString().toLowerCase().contains("service")) {
                        HashMap<String,String> file_code = new HashMap<>();
                        file_code.put(fileName.toString(), code.toString());
                        output.get("service").add(file_code);
                    } else if(fileName.toString().toLowerCase().contains("controller")) {
                        HashMap<String,String> file_code = new HashMap<>();
                        file_code.put(fileName.toString(), code.toString());
                        output.get("controller").add(file_code);
                    } else {
                        HashMap<String,String> file_code = new HashMap<>();
                        file_code.put(fileName.toString(), code.toString());
                        output.get("model").add(file_code);
                    }
                    code_activator = false;
                    fileName = new StringBuilder();
                    code = new StringBuilder();
                } else {
                    code.append(text.charAt(i));
                }
            }
        }
        return output;
    }

    public static String removeTicks(String text) {
        text = text.replaceAll("```json", "").replaceAll("```", "").trim();
        return text;
    }
}
