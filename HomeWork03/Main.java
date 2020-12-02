package HomeWork03;

import java.util.HashMap;
import java.util.Map;


public class Main {

    public static void main(String[] args) {

        String[] wordsList = {"яблоко", "ананас", "дыня", "банан", "виноград", "арбуз", "киви", "картофель", "яблоко", "ананас", "банан", "яблоко", "апельсин", "сыр", "томат", "арбуз", "вишня"};
        HashMap<String, String> wordsMap = new HashMap<>();

        // да, вижу подсказку, но то что предлагает IDEA мне пока сложно читать
        for (int i = 0; i < wordsList.length; i++) {
            int wordRepeats = 0;
            for (String currentWord : wordsList) {
                if (currentWord.equals(wordsList[i])) wordRepeats++;
            }
            wordsMap.put(wordsList[i], String.valueOf(wordRepeats));
        }
        // а тут IDEA вообще предлагает непонятную конструкцию :)
        for (Map.Entry entry : wordsMap.entrySet()) {
            System.out.println(entry);
        }

        System.out.println("\n-------ЗАДАНИЕ №2----------");

        PhoneBook bk = new PhoneBook();

        bk.add("Сидоров", "+7(921)222-33-22, раб. +7(812)-222-22-32");
        bk.add("Петров", "+7(911)345-35-35, 555-55-55");
        bk.add("Иванов", "+7(904)228-28-28");
        bk.add("Иванов", "+7(931)925-25-25");
        bk.add("Путин", "+7(777)777-77-77");
        bk.add("Мишустин", "+7(777)555-55-55");
        bk.add("Петров", "570-60-70");
        bk.add("Петров", "230-20-30");

        bk.get("иванов");
        bk.get("петров");
        bk.get("сидоров");

    }
}

