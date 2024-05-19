package com.example.SpringExercise.Utility;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;

public class SecretsManagerUtil {

    @Value("${aws.accessKey}")
    public static String awsAccessKey;

    @Value("${aws.secretKey}")
    public static String awsSecretKey;
    private static final String SECRET_NAME = "trueProxyApiKey";
    private static final String REGION = "us-east-1"; // Change to your region

    public static String getSecret() throws JsonProcessingException {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAWAGLCRRYTHXZFDHS", "2c3yT3ap8tDRusC3YYu2mh+jO7XMQbrjdxLXwqHb");

        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                .withRegion(REGION)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();

        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(SECRET_NAME);
        GetSecretValueResult getSecretValueResult;

        getSecretValueResult = client.getSecretValue(getSecretValueRequest);

        String secretString = getSecretValueResult.getSecretString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(secretString);
        return jsonNode.get("trueProxyApiKey").asText();
    }

}
