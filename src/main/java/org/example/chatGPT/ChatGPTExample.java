package org.example.chatGPT;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ChatGPTExample {

    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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"; // Replace with your OpenAI API key

    public static void main(String[] args) {
        HttpClient httpClient = HttpClients.createDefault();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Prepare the request payload
            String requestBody = "{\n" +
                    "  \"model\": \"gpt-3.5-turbo\",\n" +
                    "  \"prompt\": \"1+1은 몇이야?\",\n" +
                    "  \"temperature\": 1,\n" +
                    "  \"max_tokens\": 10\n" +
                    "}";

            // Create the HTTP request
            HttpPost httpPost = new HttpPost(API_ENDPOINT);
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + API_KEY);
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));

            // Execute the request
            HttpResponse response = httpClient.execute(httpPost);

            // Process the response
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
                String line;
                StringBuilder responseContent = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }

                // Parse JSON response
                JsonNode jsonResponse = objectMapper.readTree(responseContent.toString());
                System.out.println(jsonResponse);
                String generatedText = jsonResponse.get("choices").get(0).get("text").asText();

                // Print the generated text
                System.out.println("Generated Text: " + generatedText);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}


