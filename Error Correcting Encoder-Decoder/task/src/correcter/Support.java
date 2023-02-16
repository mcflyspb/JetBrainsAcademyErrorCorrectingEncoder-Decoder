package correcter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Support {

    public static final String TEXT_VIEW = "text view: %s\n";
    public static final String HEX_VIEW = "hex view: %s\n";
    public static final String BIN_VIEW = "bin view: %s\n";
    public static final String EXPAND = "expand: %s\n";
    public static final String PARITY = "parity: %s\n";
    public static final String READ_FILE = "send.txt";
    public static final String ENCODED_FILE = "C:\\Development\\eclipse-workspace\\Error Correcting Encoder-Decoder\\Error Correcting Encoder-Decoder\\task\\encoded.txt";
    public static final String RECIEVED_FILE = "C:\\Development\\eclipse-workspace\\Error Correcting Encoder-Decoder\\Error Correcting Encoder-Decoder\\task\\received.txt";
    public static final String DECODED_FILE = "C:\\Development\\eclipse-workspace\\Error Correcting Encoder-Decoder\\Error Correcting Encoder-Decoder\\task\\decoded.txt";
    public static final String CORRECT = "correct: %s\n";
    public static final String DECODE = "decode: %s\n";
    public static final String REMOVE = "remove: %s\n";

    public static byte[] readLineFromFile(String file) throws IOException {
        byte[] fileContent;
        fileContent = Files.readAllBytes(Paths.get(file));
        return fileContent;
    }

    static String getHexViewOfString(byte[] fileContent) {
        StringBuilder output = new StringBuilder();
        for (byte x : fileContent) {
            output.append(String.format("%02x", x));
            output.append(" ");
        }
        return output.toString().trim().toUpperCase();
    }

    static String getBinViewOfString(byte[] fileContent) {
        StringBuilder binaryOutputToConsole = new StringBuilder();
        StringBuilder binaryWoSpace = new StringBuilder();
        for (byte x : fileContent) {
            String currentBinary =   Integer.toBinaryString(x & 0xFF);
            if (currentBinary.length() < 8) {
                StringBuilder prefix = new StringBuilder();
                for (int y = currentBinary.length(); y < 8; y++) {
                    prefix.append("0");
                }
                currentBinary = prefix + currentBinary;
            }
            binaryWoSpace.append(currentBinary);
            binaryOutputToConsole.append(currentBinary);
            binaryOutputToConsole.append(" ");
        }
        return binaryWoSpace.toString().trim();
    }

    static String getBinView(byte[] fileContent) {
        StringBuilder binaryOutput = new StringBuilder();
        for (byte x : fileContent) {
            String currentBinary =   Integer.toBinaryString(x & 0xFF);
            if (currentBinary.length() < 8) {
                StringBuilder prefix = new StringBuilder();
                for (int y = currentBinary.length(); y < 8; y++) {
                    prefix.append("0");
                }
                currentBinary = prefix + currentBinary;
            }
            binaryOutput.append(currentBinary);
            binaryOutput.append(" ");
        }
        return binaryOutput.toString().trim();
    }

    static String getHexViewWithParityAndWriteFile(String binaryExpandWithParity, String file) {
        String[] binaryArray = binaryExpandWithParity.split(" ");
        StringBuilder lineToFile = new StringBuilder();
        StringBuilder hex = new StringBuilder();
        for (String binaryDigit : binaryArray) {
            int decimailDigit = Integer.parseInt(binaryDigit,2);
            hex.append(String.format("%02x", decimailDigit));
            lineToFile.append((char) decimailDigit);
            hex.append(" ");
        }
        if (file != null) {
            writeLineToFile(lineToFile.toString(), file);
        }
        return hex.toString().trim().toUpperCase();
    }

    public static void writeLineToFile(String lineToFile, String file) {
        byte[] byteLine = lineToFile.getBytes(StandardCharsets.ISO_8859_1);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(byteLine);
            fos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
