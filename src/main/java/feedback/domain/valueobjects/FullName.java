package feedback.domain.valueobjects;

import feedback.exceptions.validation.InputValidator;

import java.util.Objects;

public class FullName {
    private final String firstName;
    private final String lastName;

    public FullName(String firstName, String lastName) {
        if (!InputValidator.isValidName(firstName)) {
            throw new IllegalArgumentException("Ungültiger Vorname");
        }
        if (!InputValidator.isValidName(lastName)) {
            throw new IllegalArgumentException("Ungültiger Nachname");
        }
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName = (FullName) o;
        return firstName.equals(fullName.firstName) && lastName.equals(fullName.lastName);
    }


    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
