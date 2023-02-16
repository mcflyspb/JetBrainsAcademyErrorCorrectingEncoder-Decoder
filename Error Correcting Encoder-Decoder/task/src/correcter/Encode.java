package correcter;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Encode {


    public static void run() throws IOException {
        System.out.println(Support.READ_FILE);
        byte[] fileContent = Support.readLineFromFile(Support.READ_FILE);
        System.out.printf(Support.TEXT_VIEW, new String(fileContent, StandardCharsets.UTF_8));
        System.out.printf(Support.HEX_VIEW,Support.getHexViewOfString(fileContent));
        String binaryWithSpace = Support.getBinView(fileContent);
        System.out.printf(Support.BIN_VIEW,binaryWithSpace);
        System.out.println();
        System.out.println(Support.ENCODED_FILE);
        String binaryExpand = getBinExpand(binaryWithSpace);
        System.out.printf(Support.EXPAND, binaryExpand);
        String binaryExpandWithParity = getParity(binaryExpand);
        System.out.printf(Support.PARITY, binaryExpandWithParity);
        System.out.printf(Support.HEX_VIEW,Support.getHexViewWithParityAndWriteFile(binaryExpandWithParity, Support.ENCODED_FILE));

    }


    private static String getParity(String binaryExpand) {
        StringBuilder binaryWithParity = new StringBuilder();
        String[] binaryExpandArray = binaryExpand.replace(".","0").split(" ");
        for (String binaryDigit : binaryExpandArray) {
            String[] charDigitArray = binaryDigit.split("");
            int bite2 = Integer.parseInt(charDigitArray[2]);
            int bite4 = Integer.parseInt(charDigitArray[4]);
            int bite5 = Integer.parseInt(charDigitArray[5]);
            int bite6 = Integer.parseInt(charDigitArray[6]);
            int parity1 = bite2 ^ bite4 ^ bite6;
            int parity2 = bite2 ^ bite5 ^ bite6;
            int parity4 = bite4 ^ bite5 ^ bite6;
            int parity8 = 0;
            charDigitArray[0] = String.valueOf(parity1);
            charDigitArray[1] = String.valueOf(parity2);
            charDigitArray[3] = String.valueOf(parity4);
            charDigitArray[7] = String.valueOf(parity8);
            for (String x : charDigitArray) {
                binaryWithParity.append(x);
            }
            binaryWithParity.append(" ");
        }
        return binaryWithParity.toString().trim();
    }

    private static String getBinExpand(String binaryWithSpace) {
        String[] binaryWithSpaceArray = binaryWithSpace.split(" ");
        StringBuilder binaryExpand = new StringBuilder();
        for (String currentBlock : binaryWithSpaceArray) {
            char[] currentBlockChar = currentBlock.toCharArray();
            //expand: ..0.101. ..0.100.
            binaryExpand.append("..");
            binaryExpand.append(currentBlockChar[0]);
            binaryExpand.append(".");
            binaryExpand.append(currentBlockChar[1]);
            binaryExpand.append(currentBlockChar[2]);
            binaryExpand.append(currentBlockChar[3]);
            binaryExpand.append(". ");
            binaryExpand.append("..");
            binaryExpand.append(currentBlockChar[4]);
            binaryExpand.append(".");
            binaryExpand.append(currentBlockChar[5]);
            binaryExpand.append(currentBlockChar[6]);
            binaryExpand.append(currentBlockChar[7]);
            binaryExpand.append(". ");
        }
        return binaryExpand.toString().trim();
    }
}
