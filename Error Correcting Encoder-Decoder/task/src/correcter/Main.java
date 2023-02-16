package correcter;



import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static final String START_MESSAGE = "Write a mode:";
    public static void main(String[] args) throws IOException {
        System.out.println(START_MESSAGE);
        Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "encode" -> {
                    Encode.run();
                }
                case "decode" -> {
                    Decode.run();
                }
                case "send" -> {
                    Send.run();
                }
            }
    }
}


