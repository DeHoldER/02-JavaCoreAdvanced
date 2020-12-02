package HomeWork03;

import java.util.*;

public class PhoneBook {

    LinkedList<Person> phoneBook = new LinkedList<>();

    public void add(String lastName, String phones) {
        phoneBook.add(new Person(lastName, phones));
    }

    public void get(String lastName) {
        int matches = 0;
        ArrayList<Person> out = new ArrayList<>();

        for (Person person : phoneBook) {
            if (person.getLastName().equals(lastName)) {
                out.add(person);
                matches++;
            }
        }
        if (matches > 0) {
            System.out.println("С фамилией " + lastName.substring(0, 1).toUpperCase() + lastName.substring(1) + " найдено контактов [" + matches + "]:");
            for (int i = 0; i < matches; i++) {
                System.out.println("фамилия: " + out.get(i).getLastName().substring(0, 1).toUpperCase() + out.get(i).getLastName().substring(1) + "\nтел.: " + out.get(i).getNumbers() + "\n");
            }
        } else System.out.println("Совпадений не найдено");
    }
}
