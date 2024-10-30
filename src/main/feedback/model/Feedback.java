package feedback.model;

public class Feedback {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String message;

    //Getter und Setter für ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    //Getter und Setter für firstName
    public String getFirstName() {
        return firstName;
    }
    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }


    //Getter und Setter für Nachname
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    //Getter und Setter für E-Mail
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;

    }


    //Getter und Setter für Message
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}