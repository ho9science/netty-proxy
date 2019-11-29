package com.samdasu.netty;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.api.gax.core.FixedCredentialsProvider;

import com.google.auth.oauth2.GoogleCredentials;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.cloud.dialogflow.v2.TextInput.Builder;

import com.google.common.collect.Lists;


public class DialogFlowApiTest {
    String projectId = "mididice-exkgvv";
    List<String> texts = new ArrayList<>();
    String sessionId = "123456789";
    String languageCode = "ko";
    @Test
    public void detectIntentTextsTest() throws Exception {
        texts.add("미디다이스는 무엇인가요?");
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("service.json"))
                                                         .createScoped(
                                                                 Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        SessionsSettings sessionSettings = SessionsSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
        // Instantiates a client
        try (SessionsClient sessionsClient = SessionsClient.create(sessionSettings)) {
            // Set the session name using the sessionId (UUID) and projectID (my-project-id)
            SessionName session = SessionName.of(projectId, sessionId);
            System.out.println("Session Path: " + session.toString());

            // Detect intents for each text input
            for (String text : texts) {
                // Set the text (hello) and language code (en-US) for the query
                Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode(languageCode);

                // Build the query with the TextInput
                QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

                // Performs the detect intent request
                DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

                // Display the query result
                QueryResult queryResult = response.getQueryResult();

                System.out.println("====================");
                System.out.format("Query Text: '%s'\n", queryResult.getQueryText());
                System.out.format("Detected Intent: %s (confidence: %f)\n",
                                  queryResult.getIntent().getDisplayName(), queryResult.getIntentDetectionConfidence());
                System.out.format("Fulfillment Text: '%s'\n", queryResult.getFulfillmentText());
                assertThat(queryResult.getFulfillmentText(), is("미디다이스는 현지윤, 박주연, 민현기, 김창민 4명으로 구성된 미디어 아트 팀입니다."));
            }
        }
    }
}
