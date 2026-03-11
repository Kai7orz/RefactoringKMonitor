package org.example.infrastructure;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.images.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GptImage {
    public GptImage(String presignedUrl){
        OpenAIClient openAIClient  = OpenAIOkHttpClient.fromEnv();
        // シークレットアクセスキー .env に登録
        // ユーザーID などの認証情報
        // S3 のリージョン名
        // AWS SDK 導入
        // presignedURL から画像をとって一旦 DL しないといけないっぽい
        // square 変換処理
        // DL したファイルをバイナリデータとして GPT API に投げる処理
        // 返ってきたイメージを S3 にアップロードする処理 user と イラストを紐づける処理がここで必要かも

        ImageEditParams params = ImageEditParams.builder().image()
        ImagesResponse imagesResponse = openAIClient.images().createVariation(params);

        List<Image> image = imagesResponse.data().orElseThrow();
        String encodedData = image.getFirst().b64Json().orElseThrow();
        System.out.println("imagesResponse encoded: "+encodedData);
        String regex = "data:image/png;base64";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(encodedData);
        byte[] bytes = Base64.getDecoder().decode(encodedData);
        try{
            FileOutputStream stream = new FileOutputStream("./decodedImage.png");
            stream.write(bytes);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
