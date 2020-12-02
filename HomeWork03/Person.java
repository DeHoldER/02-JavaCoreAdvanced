package HomeWork03;

public class Person {

    private String lastName;
    private String phones;

    public Person(String lastName, String phones) {
        this.lastName = lastName.toLowerCase();
        this.phones = phones;
    }

    public String getNumbers() {
        return phones;
    }

    public void setNumbers(String numbers) {
        phones = numbers;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
