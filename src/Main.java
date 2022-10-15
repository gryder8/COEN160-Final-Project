import com.fasterxml.jackson.databind.*;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        WordDictionary dict = new WordDictionary();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readValue(new File("data/dictionary.json"), JsonNode.class);
            for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
                String letter = String.valueOf(alphabet);
                JsonNode array = node.get(letter);
                String[] arr = new String[array.size()];
                for (int i = 0; i < array.size(); i++) {
                    String valToAdd = array.get(i).asText();
                    arr[i] = valToAdd;
                }
                System.out.println("Found " + arr.length + " words for letter " + letter);
                dict.data.put(letter, arr);
            }
            System.out.println("Found " + dict.data.keySet().size() + " letters in JSON");
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
    }
}