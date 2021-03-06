package academy.devonline.java.basic.TicTacToe;

import java.util.Random;
import java.util.Scanner;

public class Version2 {
    public static final String USER_SIGN = "X";
    public static final String USER_SIGN_SECOND = "O";
    public static final String AI_SIGN = "O";
    public static final String NOT_SIGN = "*";
    public static int aiLevel = 0;
    public static final int DIMENSION = 3;
    public static String[][] field = new String[DIMENSION][DIMENSION];

    public static void main(String[] args) {
        mainMenu();
    }

    public static void modeTwoPlayers() {
        int count = 0;
        initField();
        while (true) {
            printField();
            userShot(USER_SIGN, 1);
            count++;
            if (checkWin(USER_SIGN)) {
                System.out.println("USER 1 WIN!!!");
                printField();
                break;
            }
            userShot(USER_SIGN_SECOND, 2);
            count++;
            if (checkWin(USER_SIGN_SECOND)) {
                System.out.println("USER 2 WIN!!!");
                printField();
                break;
            }
            if (count == Math.pow(DIMENSION, 2)) {

                printField();
                break;
            }
        }
    }

    public static void modeAgainstAI() {
        int count = 0;
        initField();
        while (true) {
            printField();
            userShot(USER_SIGN, 0);
            count++;
            if (checkWin(USER_SIGN)) {
                System.out.println("USER WIN!!!");
                printField();
                break;
            }
            if (count == Math.pow(DIMENSION, 2)) {
                printField();
                System.out.println("It's Draw");
                break;
            }
            aiShot();
            count++;
            if (checkWin(AI_SIGN)) {
                System.out.println("AI WIN!!!");
                printField();
                break;
            }
            if (count == Math.pow(DIMENSION, 2)) {
                printField();
                break;
            }
        }
    }

    public static void mainMenu() {
        System.out.println("???????????????? ?????????? ????????: ");
        System.out.println("1. ???????? ???????????? ????????????????????.");
        System.out.println("2. 2 ????????????.");
        System.out.println("3. ??????????.");
        int i;
        Scanner sc = new Scanner(System.in);
        i = sc.nextInt();
        switch (i) {
            case 1: {
                aiLevel();
                break;
            }
            case 2: {
                modeTwoPlayers();
                break;
            }
            case 3: {
                System.exit(0);
                break;
            }
            default: {
                System.out.println("???????? ?????????????? ???????????????? ????????????????!");
            }
        }
    }

    public static void aiLevel() {
        System.out.println("???????????????? ?????????????????? ????????????????????: ");
        System.out.println("1. ??????????????.");
        System.out.println("2. ??????????????????????.");
        System.out.println("3. ??????????????.");
        System.out.println("4. ?????????????????? ?? ???????????????????? ????????.");
        System.out.println("5. ??????????.");
        int i;
        Scanner sc = new Scanner(System.in);
        i = sc.nextInt();
        switch (i) {
            case 1: {
                aiLevel = 0;
                modeAgainstAI();
                break;
            }
            case 2: {
                aiLevel = 1;
                modeAgainstAI();
                break;
            }
            case 3: {
                aiLevel = 2;
                modeAgainstAI();
                break;
            }
            case 4: {
                mainMenu();
                break;
            }
            case 5: {
                System.exit(0);
                break;
            }
            default: {
                System.out.println("???????? ?????????????? ???????????????? ????????????????!");
            }
        }
    }

    public static void initField() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                field[i][j] = NOT_SIGN;
            }
        }
    }

    public static void printField() {
        for (int i = 0; i <= DIMENSION; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < DIMENSION; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < DIMENSION; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void userShot(String sign, int i) {
        int x;
        int y;
        do {
            if (i == 0) {
                System.out.println("?????????????? ???????????????????? x y (1 - " + DIMENSION + "): ");
            } else {
                System.out.println("?????????? " + i + ". ?????????????? ???????????????????? x y (1 - " + DIMENSION + "): ");
            }
            Scanner sc = new Scanner(System.in);
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        }
        while (isCellBusy(x, y));
        field[x][y] = sign;
    }

    public static void aiShot() {
        int x = 1;
        int y = 1;
        boolean ai_win = false;
        boolean user_win = false;
        // ?????????????? ???????????????????? ??????
        if (aiLevel == 2) {
            for (int i = 0; i < DIMENSION; i++) {
                for (int j = 0; j < DIMENSION; j++) {
                    if (!isCellBusy(i, j)) {
                        field[i][j] = AI_SIGN;
                        if (checkWin(AI_SIGN)) {
                            x = i;
                            y = j;
                            ai_win = true;
                        }
                        field[i][j] = NOT_SIGN;
                    }
                }
            }
        }

        if (aiLevel > 0) {
            if (!ai_win) {
                for (int i = 0; i < DIMENSION; i++) {
                    for (int j = 0; j < DIMENSION; j++) {
                        if (!isCellBusy(i, j)) {
                            field[i][j] = USER_SIGN;
                            if (checkWin(USER_SIGN)) {
                                x = i;
                                y = j;
                                user_win = true;
                            }
                            field[i][j] = NOT_SIGN;
                            System.out.println();
                        }
                    }
                }
            }
        }
        if (!ai_win && !user_win) {
            do {
                Random rnd = new Random();
                x = rnd.nextInt(DIMENSION);
                y = rnd.nextInt(DIMENSION);
            }
            while (isCellBusy(x, y));
        }
        field[x][y] = AI_SIGN;
    }


    public static boolean isCellBusy(int x, int y) {
        if (x < 0 || y < 0 || x > DIMENSION - 1 || y > DIMENSION - 1) {
            return false;
        }
        return !field[x][y].equals(NOT_SIGN);
    }

    public static boolean checkWin(String sign) {
        // ???????????????? ???? ??????????????
            for (int i = 0; i < DIMENSION; i++) {
                if (field[i][0].equals(sign) && field[i][1].equals(sign) && field[i][2].equals(sign)) {
                    return true;
                }
            }
        // ???????????????? ???? ????????????????
            for (int j = 0; j < DIMENSION; j++) {
                if (field[0][j].equals(sign) && field[1][j].equals(sign) && field[2][j].equals(sign)) {
                    return true;
                }
            }

        // ???????????????? ????????????????????
            if (field[0][0].equals(sign) && field[1][1].equals(sign) && field[2][2].equals(sign)) {
                return true;
            }
            if (field[0][2].equals(sign) && field[1][1].equals(sign) && field[2][0].equals(sign)) {
                return true;
            }
        return false;
    }
}

