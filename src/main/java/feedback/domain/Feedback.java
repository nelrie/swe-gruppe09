package feedback.domain;

public class Feedback {
    private String feedbackID;
    private String firstName;
    private String lastName;
    private String email;
    private String message;

    public Feedback(String feedbackID, String firstName, String lastName, String email, String message) {
        this.feedbackID = feedbackID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.message = message;
    }

@Override
public String toString() {
        return "Feedback von " + firstName + " " + lastName + " (" + email + "): " + message;
}

    //Getter und Setter für ID
    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }


    //Getter und Setter für firstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
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