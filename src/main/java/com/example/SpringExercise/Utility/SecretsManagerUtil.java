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
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;

@Component
public class SecretsManagerUtil {

    @Value("${encoded.aws.accessKey}")
    private String encodedAwsAccessKey;

    @Value("${encoded.aws.secretKey}")
    private String encodedAwsSecretKey;
    private static final String SECRET_NAME = "trueProxyApiKey";
    private static final String REGION = "us-east-1"; // Change to your region

    public String getSecret() throws JsonProcessingException {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(new String(Base64.getDecoder().decode(encodedAwsAccessKey)), new String(Base64.getDecoder().decode(encodedAwsSecretKey)).toString());
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().withRegion(REGION).withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(SECRET_NAME);
        GetSecretValueResult getSecretValueResult;
        getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        String secretString = getSecretValueResult.getSecretString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(secretString);
        return jsonNode.get("trueProxyApiKey").asText();
    }

}
