package com.example.kafka.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class MessageHelper {

    /**
     * Gets random string.
     *
     * @return the random string
     */
    public static String getRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

//    /**
//     * Gets simple json object.
//     *
//     * @param message the message
//     * @return the simple json object
//     * @throws Exception the exception
//     */
//    public static JSONObject getJsonStringFromObject(String message) throws Exception {
//        JSONObject obj = new JSONObject();
//        String bootstrapServers = getProperties().getProperty("bootstrap.servers");
//        obj.put("message", message);
//        return obj;
//    }
}
