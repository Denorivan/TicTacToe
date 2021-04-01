package academy.devonline.java.basic.TicTacToe;

import java.util.Random;
import java.util.Scanner;

public class TTT {
    final char SignX = 'x';
    final char SignO = 'o';
    final char SignEmpty = '.';
    char[][] table;
    Random random;
    Scanner Sc;

    int countX = 0;
    int countO = 0;
    int free = 0;


    public static void main(String[] args) {
        System.out.println("Номера столбцов и строк для игры: ");
        System.out.println("""
                  1 2 3\s
                1 . . .
                2 . . .
                3 . . .
                """);
        new TTT().game();

    }

    TTT() {
        // конструктор: инициализация полей
        random = new Random();
        Sc = new Scanner(System.in);
        table = new char[3][3];

        this.initTable();  // инициализация таблицы

    }

    public void game() {

        while (true) {
            // ход человека
            this.manWalk();

            if (checkWin(SignX)) {
                System.out.println("Победил игрок! Поздравляю!");
                break;
            }

            if (this.isFullTable()) {
                System.out.println("Ничья!");
                break;
            }
            // ход компьютера
            this.programWalk();
            this.printTable();

            if (checkWin(SignO)) {
                System.out.println("Победил ИИ! Повезет в следуйщем разе.");
                break;
            }

            if (this.isFullTable()) {
                System.out.println("Ничья!");
                break;
            }

        }

        System.out.println("Игра закончена.");
        this.printTable();
    }


    // начальное заполнение таблицы
    void initTable() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.table[row][col] = SignEmpty;
            }
        }
    }

    // вывод таблицы в консоль
    void printTable() {

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                System.out.print(this.table[row][col] + " ");
            }
            System.out.print("\n");
        }
    }

    // получение числа из консоли
    int getNumber() {
        int trying;
        String buffer;

        while (true) {
            if (Sc.hasNextInt()) {
                trying = Sc.nextInt();
                break;
            } else {
                buffer = Sc.next();
                System.out.println("Введены неверные символы: " + buffer);
            }
        }

        return trying;
    }

    // ходит человек
    void manWalk() {
        int x, y;

        do {

            System.out.println("Введите номер столбца и номер строки (1..3):");
            x = getNumber() - 1;
            y = getNumber() - 1;
        } while (!this.isCellValidMan(x, y));

        table[y][x] = SignX;
    }

    // правильные ли координаты?
    //  человек
    boolean isCellValidMan(int x, int y) {

        if (x < 0 || x > 2 || y < 0 || y > 2) {
            System.out.println("Число не из диапазона (1..3)");
            return false;
        }

        if (this.table[y][x] != SignEmpty) {
            System.out.println("Ячейка занята.");
            return false;
        }

        return true;
    }

    //  программа
    boolean isCellValidProg(int x, int y) {

        if (x < 0 || x > 2 || y < 0 || y > 2) {
            return false;
        }

        return this.table[y][x] == SignEmpty;
    }

    // заполнена ли таблица полностью?
    boolean isFullTable() {

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {

                if (this.table[row][col] == SignEmpty)
                    return false;

            }
        }

        return true;
    }

    // ходит программа
    void programWalk() {

        if (this.check1Diagonal()) return;

        if (this.check2Diagonal()) return;

        if (this.checkRows()) return;

        if (this.checkColumns()) return;

        this.randomWalk();

    }

    // подсчет 'х', 'о' и пустых клеток
    void count(int row, int col) {
        if (this.table[row][col] == SignX) {
            this.countX++;
        }

        if (this.table[row][col] == SignO) {
            this.countO++;
        }

        if (this.table[row][col] == SignEmpty) {
            this.free++;
        }
    }

    void initCount() {
        this.countX = 0;
        this.countO = 0;
        this.free = 0;
    }

    // близко проигрышная ситуация для программы
    boolean isLosingNear() {
        return (this.free == 1 && this.countX == 2);
    }

    // близко выигрыш
    boolean isWinNear() {
        return (this.free == 1 && this.countO == 2);
    }

    // проверка выигрыша
    boolean checkWin(char dot) {
        for (int i = 0; i < 3; i++) {
            if ((table[i][0] == dot && table[i][1] == dot && table[i][2] == dot) ||
                    (table[0][i] == dot && table[1][i] == dot && table[2][i] == dot))
                return true;
        }

        return (table[0][0] == dot && table[1][1] == dot && table[2][2] == dot) ||
                (table[2][0] == dot && table[1][1] == dot && table[0][2] == dot);
    }

    // делим на части programWalk()
    // выбор варианта случаен
    void randomWalk() {
        int x, y;
        do {
            x = random.nextInt(3);
            y = random.nextInt(3);
        } while (!this.isCellValidProg(x, y));

        this.table[y][x] = SignO;
    }

    // постановка нолика
    boolean putO(int row, int col) {
        if (this.table[row][col] == SignEmpty) {
            this.table[row][col] = SignO;
            return true;
        }
        return false;
    }

    // контроль первой диагонали
    boolean check1Diagonal() {
        this.initCount();
        for (int row = 0; row < 3; row++) {
            this.count(row, row);
        }

        if (this.isWinNear() || this.isLosingNear()) {
            for (int row = 0; row < 3; row++) {
                if (this.putO(row, row))
                    return true;
            }
        }

        return false;
    }

    // контроль второй диагонали
    boolean check2Diagonal() {
        this.initCount();
        int col = 2;
        for (int row = 0; row < 3; row++) {
            this.count(row, col);
            col--;
        }

        if (this.isWinNear() || this.isLosingNear()) {
            col = 2;
            for (int row = 0; row < 3; row++) {
                if (this.putO(row, col))
                    return true;
                col--;
            }
        }

        return false;
    }

    // контроль строк
    boolean checkRows() {
        for (int row = 0; row < 3; row++) {
            this.initCount();
            for (int col = 0; col < 3; col++) {
                this.count(row, col);
            }

            if (this.isWinNear() || this.isLosingNear()) {
                for (int col = 0; col < 3; col++) {
                    if (this.putO(row, col))
                        return true;
                }
            }

        }

        return false;
    }

    // контроль столбцов
    boolean checkColumns() {
        for (int col = 0; col < 3; col++) {
            this.initCount();
            for (int row = 0; row < 3; row++) {
                this.count(row, col);
            }

            if (this.isWinNear() || this.isLosingNear()) {
                for (int row = 0; row < 3; row++) {
                    if (this.putO(row, col))
                        return true;
                }
            }

        }

        return false;
    }
}
