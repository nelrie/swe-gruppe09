package feedback.domain.valueobjects;

import feedback.exceptions.validation.InputValidator;

public class FullName {

    private final String firstName;
    private final String lastName;

    public FullName(String firstName, String lastName) {
        // Null-Prüfung auf Vorname und Nachname
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("Ungültiger Vorname: darf nicht null oder leer sein");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Ungültiger Nachname: darf nicht null oder leer sein");
        }

        // Überprüfung von invaliden Zeichen mit InputValidator
        if (!InputValidator.isValidName(firstName)) {
            throw new IllegalArgumentException("Ungültiger Vorname: enthält unzulässige Zeichen");
        }
        if (!InputValidator.isValidName(lastName)) {
            throw new IllegalArgumentException("Ungültiger Nachname: enthält unzulässige Zeichen");
        }

        // Initialisierung der Felder
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName = (FullName) o;
        return firstName.equals(fullName.firstName) && lastName.equals(fullName.lastName);
    }

    @Override
    public int hashCode() {
        return firstName.hashCode() + lastName.hashCode();
    }
}