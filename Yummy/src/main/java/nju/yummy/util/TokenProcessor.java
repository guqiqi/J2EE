package nju.yummy.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public class TokenProcessor {
    public static String getToken(String id){
        BASE64Encoder encoder = new BASE64Encoder();
        String cipherText = encoder.encode(id.getBytes());
        return cipherText;
    }

    public static String getId(String token){
        BASE64Decoder decoder = new BASE64Decoder();
        String plainText = null;
        try {
            plainText = new String(decoder.decodeBuffer(token));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return plainText;
    }
}
