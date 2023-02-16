package correcter;

import java.io.IOException;

public class Decode {


    public static void run() throws IOException {
        System.out.println(Support.RECIEVED_FILE);
        byte[] fileContent = Support.readLineFromFile(Support.RECIEVED_FILE);
        System.out.printf(Support.HEX_VIEW,Support.getHexViewOfString(fileContent));
        String binaryMessage = Support.getBinViewOfString(fileContent);
        System.out.println();
        String correctBinaryMessage = getCorrectMessage(binaryMessage);
        System.out.printf(Support.CORRECT,correctBinaryMessage);
        String decodeMessage = getDecodeMessage(correctBinaryMessage);
        System.out.printf(Support.DECODE,decodeMessage);
        System.out.printf(Support.HEX_VIEW,Support.getHexViewWithParityAndWriteFile(decodeMessage, null));
        String decodedMessage = getTextFromBinary(decodeMessage);
        Support.writeLineToFile(decodedMessage, Support.DECODED_FILE);
        System.out.printf(Support.TEXT_VIEW,decodedMessage);
    }

    private static String getTextFromBinary(String removeMessage) {
        String[] removeMessageArray = removeMessage.split(" ");
        StringBuilder output = new StringBuilder();
        for (String currentBlock : removeMessageArray) {
            char currentChar = (char) Integer.parseInt(currentBlock,2);
            output.append(currentChar);
        }
        return output.toString().trim();
    }

    private static String getDecodeMessage(String correctBinaryMessage) {
        char[] binaryMessageArray = correctBinaryMessage.replace(" ","").toCharArray();
        StringBuilder output = new StringBuilder();
        for (int x = 0; x < binaryMessageArray.length / 16; x++) {
            output.append(binaryMessageArray[16 * x + 2]);
            output.append(binaryMessageArray[16 * x + 4]);
            output.append(binaryMessageArray[16 * x + 5]);
            output.append(binaryMessageArray[16 * x + 6]);
            output.append(binaryMessageArray[16 * x + 10]);
            output.append(binaryMessageArray[16 * x + 12]);
            output.append(binaryMessageArray[16 * x + 13]);
            output.append(binaryMessageArray[16 * x + 14]);
            output.append(" ");
            }
        return output.toString().trim();
    }

    private static String getCorrectMessage(String binaryMessage) {
        char[] binaryMessageArray = binaryMessage.toCharArray();
        StringBuilder output = new StringBuilder();
        for (int x = 0; x < binaryMessageArray.length / 8; x++) {
            int errorBite = -1;
            int bite0 = Integer.parseInt(String.valueOf(binaryMessageArray[8 * x]));
            int bite1 = Integer.parseInt(String.valueOf(binaryMessageArray[8 * x + 1]));
            int bite2 = Integer.parseInt(String.valueOf(binaryMessageArray[8 * x + 2]));
            int bite3 = Integer.parseInt(String.valueOf(binaryMessageArray[8 * x + 3]));
            int bite4 = Integer.parseInt(String.valueOf(binaryMessageArray[8 * x + 4]));
            int bite5 = Integer.parseInt(String.valueOf(binaryMessageArray[8 * x + 5]));
            int bite6 = Integer.parseInt(String.valueOf(binaryMessageArray[8 * x + 6]));
            int parity1 = bite2 ^ bite4 ^ bite6;
            int parity2 = bite2 ^ bite5 ^ bite6;
            int parity4 = bite4 ^ bite5 ^ bite6;

            if (parity1 != bite0) {
                errorBite++;
            }

            if (parity2 != bite1) {
                errorBite += 2;
            }

            if (parity4 != bite3) {
                errorBite += 4;
            }

            if (errorBite > 0) {
                if (binaryMessageArray[8 * x + errorBite] == '0') {
                    binaryMessageArray[8 * x + errorBite] = '1';
                } else {
                    binaryMessageArray[8 * x + errorBite] = '0';
                }
            }
        }

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
}
