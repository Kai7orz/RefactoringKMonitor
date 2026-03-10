package org.example.infrastructure;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.images.ImageGenerateParams;
import com.openai.models.images.ImagesResponse;
import org.springframework.beans.factory.annotation.Value;

public class GptImage {
    public GptImage(){
        OpenAIClient openAIClient  = OpenAIOkHttpClient.fromEnv();
        ImageGenerateParams params = ImageGenerateParams.builder().prompt("Create a illustration based on submitted image").build();
        ImagesResponse imagesResponse = openAIClient.images().generate(params);
    }
}
