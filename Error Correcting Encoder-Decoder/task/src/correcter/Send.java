package correcter;

import com.sun.security.jgss.GSSUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Send {

    public static void run() throws IOException {
        System.out.println(Support.ENCODED_FILE);
        byte[] fileContent = Support.readLineFromFile(Support.ENCODED_FILE);
        System.out.printf(Support.HEX_VIEW, getHexViewFromRawData(fileContent));
        String binaryMessage = Support.getBinViewOfString(fileContent);
        System.out.println();
        System.out.println(Support.RECIEVED_FILE);
        String binaryMessageWithErrors = makeErrors(binaryMessage);
        System.out.printf(Support.BIN_VIEW, binaryMessageWithErrors);
        System.out.printf(Support.HEX_VIEW,Support.getHexViewWithParityAndWriteFile(binaryMessageWithErrors, Support.RECIEVED_FILE));
    }

    private static String makeErrors(String binaryMessage) {
        char[] binaryMessageArray = binaryMessage.toCharArray();
        Random random = new Random();
        for (int x = 0; x < binaryMessageArray.length / 8; x++) {
            int rnd = random.nextInt(8);
            char oneChar = binaryMessageArray[8 * x + rnd];
            if (oneChar == '1') {
                binaryMessageArray[8 * x + rnd] = '0';
            } else {
                binaryMessageArray[8 * x + rnd] = '1';
            }
        }
        StringBuilder output = new StringBuilder();
        int insertSpaceCount = 0;
        for (char currentChar : binaryMessageArray) {
            insertSpaceCount++;
            output.append(currentChar);
            if (insertSpaceCount == 8) {
                output.append(" ");
                insertSpaceCount = 0;
            }
        }
        return output.toString().trim();
    }

    private static String getHexViewFromRawData(byte[] fileContent) {
        StringBuilder hex = new StringBuilder();
        for (byte currentChar : fileContent) {
            hex.append(String.format("%02x", currentChar));
            hex.append(" ");
        }
        return hex.toString().trim().toUpperCase();
    }
}
