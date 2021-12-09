import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    private static final int SIZE = 3;

    private static final char EMPTY_DOT = '·';
    private static final char HUMAN_DOT = 'X';
    private static final char AI_DOT = '0';

    private static final char[][] MAP = new char[SIZE][SIZE];
    private static final Random random = new Random();
    private static final Scanner sc = new Scanner(System.in);
    private static final String HEAD_FIRST_SYMBOL = "♥";
    private static final String SPACE_SYMBOL = " ";


    private static int turnCount = 0;

    public static void turnGame() {
        System.out.println("Game begins");
        do {
            init();
            printMap();
            playGame();
        } while (isContinueGame());
        endGame();
    }

    private static void init() {
        turnCount = 0;
        initMap();
    }

    private static void initMap() {
        for (int i = 0; i < MAP.length; i++) {
            for (int j = 0; j < MAP.length; j++) {
                MAP[i][j] = EMPTY_DOT;

            }
        }
    }

    private static void printMap() {
        printHeaderMap();
        printBody();
    }

    private static void printHeaderMap() {
        System.out.print(HEAD_FIRST_SYMBOL + SPACE_SYMBOL);
        for (int i = 0; i < MAP.length; i++) {
            printMapNumber(i);
        }
        System.out.println();
    }

    private static void printBody() {
        for (int i = 0; i < MAP.length; i++) {
            printMapNumber(i);

            for (int j = 0; j < MAP.length; j++) {
                System.out.print(MAP[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void printMapNumber(int i) {
        System.out.print(i + 1 + SPACE_SYMBOL);
    }


    private static void playGame() {

        while (true) {
            turnHuman();
            printMap();
            if (checkEnd(HUMAN_DOT)) {
                break;
            }

            turnAI();
            printMap();
            if (checkEnd(AI_DOT)) {
                break;
            }
        }
    }

    private static void turnHuman() {
        System.out.println("MOVE MAN");
        int rowNumber, columnNumber;

        while (true) {
            rowNumber = getValidNumberFromUser() - 1;
            columnNumber = getValidNumberFromUser() - 1;
            if (isCellFree(rowNumber, columnNumber)) {
                break;
            }
            System.out.println("YOU CHOSEN NOT EMPTY CELL");

        }

        MAP[rowNumber][columnNumber] = HUMAN_DOT;
        turnCount++;

    }

    private static int getValidNumberFromUser() {
        while (true) {
            System.out.print("\nINPUT COORDINATE(1 - " + SIZE + "): ");
            if (sc.hasNextInt()) {
                int n = sc.nextInt();
                if (isNumberValid(n)) {
                    return n;
                }
                System.out.println("CHECK COORDINATE VALUE. SHOULD BE FROM 1 TO " + SIZE);
            } else {
                sc.next();
                System.out.println("INTEGERS ONLY");
            }
        }

    }

    private static boolean isNumberValid(int n) {
        return n >= 1 && n <= SIZE;
    }

    private static boolean isCellFree(int rowNumber, int columnNumber) {
        return MAP[rowNumber][columnNumber] == EMPTY_DOT;
    }

    private static boolean checkEnd(char symb) {
        if (checkWin(symb)) {
            if (symb == HUMAN_DOT) {
                System.out.println("YOU WIN");
            } else {
                System.out.println("COMPUTER WIN");
            }
            return true;
        }

        if (checkDraw()) {
            System.out.println("DRAW");
            return true;
        }
        return false;
    }

    private static void turnAI() {
        System.out.println("MOVE COMPUTER");
        int rowNumber;
        int columnNumber;
        do {
            rowNumber = random.nextInt(SIZE);
            columnNumber = random.nextInt(SIZE);

        } while (!isCellFree(rowNumber, columnNumber));

        MAP[rowNumber][columnNumber] = AI_DOT;
        turnCount++;

    }

    private static boolean checkWin(char symb) {
        boolean rowNumber;
        boolean columnNumber;

        for (int i = 0; i < MAP.length; i++) {
            rowNumber = true;
            columnNumber = true;
            for (int j = 0; j < MAP.length; j++) {
                columnNumber &=(MAP[i][j] == symb || MAP[j][i] == symb);
                rowNumber &=(MAP[i][j] == symb || MAP[j][i] == symb);

            }
            if (columnNumber || rowNumber){
                return true;
            }
        }

        return false;
    }

    private static boolean checkDraw() {

        return turnCount >= SIZE * SIZE;
    }

    private static boolean isContinueGame() {
        System.out.println("Let's play again? y\\n");
        return switch (sc.next()) {
            case "y", "yes", "+", "да", "конечно" -> true;
            default -> false;

        };
    }

    private static void endGame() {
        System.out.println("GAME OVER");
        sc.close();
    }
}