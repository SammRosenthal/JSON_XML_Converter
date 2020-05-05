package converter;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.json.simple.JSONObject;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine().trim();
        scan.close();

        if (input != null && !input.isEmpty()) {
            findParser(input);
        }
    }

    private static void findParser(String input) throws IOException, SAXException, ParserConfigurationException {
        if (input.charAt(0) == '{') {
            jsonToXmlParser(input);
        } else if (input.charAt(0) == '<') {
            xmlToJsonParser(input);
        } else {
            System.out.println("Please input a valid form of XML or JSON.");
        }
    }

    private static void jsonToXmlParser(String str) {
        JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
        Set<String> test = jsonObject.keySet();

        for (String i : test) {
            StringBuilder sb = new StringBuilder();
            String value = jsonObject.get(i).toString().replace("\"", "");
            if ("null".equals(value.toLowerCase())) {
                sb.append("<" + i + "/>");
            } else {
                sb.append("<" + i + ">");
                sb.append(value);
                sb.append("</" + i + ">");
            }
            System.out.println(sb);
        }
    }

    private static void xmlToJsonParser(String str) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource input = new InputSource(new StringReader(str));
        Document document = builder.parse(input);
        Element ele = document.getDocumentElement();

        // EVERYTHING BELOW THIS WILL HAVE TO BE LOOPED IN FUTURE

        // get xml tag and content
        String eleName = ele.getTagName();
        String eleContent = ele.getTextContent();

        // build the json
        StringBuilder sb = new StringBuilder();
        sb.append("{\"");
        sb.append(eleName);
        sb.append("\":");
        if (eleContent.isEmpty()) {
            sb.append("null}");
        } else {
            sb.append("\"" + eleContent + "\"}");
        }
        System.out.println(sb);
    }
}