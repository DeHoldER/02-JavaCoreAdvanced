package HomeWork02;

public class Main {

    static final int LINE = 4;
    static final int COLUMN = 4;

    public static void main(String[] args) {

        String[][] numbers = new String[LINE][COLUMN];

        for (int i = 0; i < LINE; i++) {
            for (int j = 0; j < COLUMN; j++) {
                numbers[i][j] = "1";
            }
        }
        

        try {
            System.out.println("Сумма чисел в массиве: " + stringToIntArrayConverter(numbers));
        } catch (MyArraySizeException e) {
            System.out.println("Ошибка размера массива!");
        } catch (MyArrayDataException e) {
            e.printErrorMessage();
        }
    }


    public static int stringToIntArrayConverter(String[][] arr) throws MyArrayDataException, MyArraySizeException {
        int result = 0;

        try {
            for (int i = 0; i < 4; i++) {
                if (arr.length != 4 || arr[i].length != 4) {
                    throw new MyArraySizeException();
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MyArraySizeException();
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                try {
                    result += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException("Ячейку " + i + " x " + j + " невозможно преобразовать в число. Содержимое: " + arr[i][j]);
                }
            }
        }

        return result;
    }

    public static class MyArraySizeException extends ArrayIndexOutOfBoundsException {
    }

    public static class MyArrayDataException extends NumberFormatException {
        static String MSG;

        public MyArrayDataException(String errorMessage) {
            MSG = errorMessage;
        }

        public void printErrorMessage() {
            System.out.println(MSG);
        }
    }


}
