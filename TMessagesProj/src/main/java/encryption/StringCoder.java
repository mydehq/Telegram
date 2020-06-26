package encryption;

import java.util.Arrays;
import java.util.Base64;

public class StringCoder {
    private static final int BLOCK_SIZE = 8;
    private static final String KEY = "Xa4yhEjf41hoRMNI"; // Sample key
    private final Idea encoder = new Idea(KEY, true);
    private final Idea decoder = new Idea(KEY, false);

    private byte[] code(byte[] bytes, Idea coder) {
        if (bytes.length % BLOCK_SIZE != 0) {
            bytes = Arrays.copyOf(bytes, bytes.length + BLOCK_SIZE - bytes.length % BLOCK_SIZE);
        }
        for (int i = 0; (i + 1) * BLOCK_SIZE <= bytes.length; i++) {
            byte[] block = Arrays.copyOfRange(bytes, i * BLOCK_SIZE, (i + 1) * BLOCK_SIZE);
            coder.crypt(block);
            System.arraycopy(block, 0, bytes, i * BLOCK_SIZE, BLOCK_SIZE);
        }
        return bytes;
    }

    public String encode(String data) {
        byte[] bytes = code(data.getBytes(), encoder);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public String decode(String base64Data) {
        byte[] bytes = Base64.getDecoder().decode(base64Data);
        return new String(code(bytes, decoder)).trim();
    }
}
