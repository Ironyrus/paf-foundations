package workshop.day28marvelworkshop.Services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HexFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.type.ArrayType;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.stream.JsonParser;
import workshop.day28marvelworkshop.Models.marvelModel;

@Service
public class MarvelService {

    public static final String URL_CHARACTER = "https://gateway.marvel.com:443/v1/public/characters";

    // Inject into the service the private and public key
    // private String publicKey = System.getenv("MARVEL_PUBLIC_KEY");
    // private String privateKey = System.getenv("MARVEL_PRIVATE_KEY");


    @Value("${MARVEL_PUBLIC_KEY}")
    private String publicKey;

    @Value("${MARVEL_PRIVATE_KEY}")
    private String privateKey;

    public ArrayList<marvelModel> search(String name) {
        // https://gateway.marvel.com:443/v1/public/characters?
        // nameStartsWith=Captain%20america
        // &apikey=5b853bcc9e61bed907bb7af36946074b
        
        Long ts = System.currentTimeMillis();
        String signature = "%d%s%s".formatted(ts, privateKey, publicKey);
        String hash = "";

        //Message Digest = md5, sha1, sha512. It is a sum computed for a file. If anything changes in the file, the md5 changes.
        MessageDigest md5 = null;
        try {
            //Get an instance of MD5
            md5 = MessageDigest.getInstance("MD5");        
            md5.update(signature.getBytes());
            byte[] h = md5.digest();

            //Stringify the MD5 digest
            hash = HexFormat.of().formatHex(h);
            System.out.println("md5: " + md5);
        } catch (Exception e) {
            // TODO: handle exception
        }

        // Calculate our md5 hash
        // Update our message digest
        
        String url = UriComponentsBuilder.fromUriString(URL_CHARACTER)
        .queryParam("nameStartsWith", name)
        .queryParam("limit", 10)
        .queryParam("ts", ts)
        .queryParam("apikey", publicKey)
        .queryParam("hash", hash)
        .toUriString();

        System.out.println("url: "+ url);

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity = template.getForEntity(url, String.class);

        InputStream input;
        JsonObject data;
        JsonReader reader = null;

        try {
            input = new ByteArrayInputStream(responseEntity.getBody().getBytes());
            reader = Json.createReader(input);
        } catch (Exception e) {
            // TODO: handle exception
        }
        data = reader.readObject();
        
        return new marvelModel().create(data);
        // ArrayList<HashMap> results = (ArrayList<HashMap>)responseEntity.getBody().getData().get("results");
        // HashMap temp = (HashMap)results.get(0).get("thumbnail");
        // String image = (String)temp.get("path");

        // System.out.println("image url: " + image);
    }
}