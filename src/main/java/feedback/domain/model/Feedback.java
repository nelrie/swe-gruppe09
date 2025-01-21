package feedback.domain.model;

import jakarta.persistence.*;
import status.domain.model.Status;
import feedback.domain.valueobjects.*;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String feedbackID;

    @Embedded
    private FullName fullName;
    @Embedded
    private Email Email;

    @Embedded
    private Message Message;

    private Status status;

    public Feedback() {
        //No-Args-Konstruktor für JPA
    }

    public Feedback(String feedbackID, FullName fullName, Email email, Message message) {
        this.feedbackID = feedbackID;
        this.fullName = fullName;
        this.Email = email;
        this.Message = message;
        this.status = Status.RECEIVED;
    }




    @Override
public String toString() {
        return "Feedback von " + fullName + "(" + Email + "): " + Message;
}

    //Getter und Setter für ID
    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }


    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

//    //Getter und Setter für firstName
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }


//    //Getter und Setter für Nachname
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }


    //Getter und Setter für E-Mail
    public Email getEmail() {
        return Email;
    }

    public void setEmail(Email email) {
        this.Email = email;

    }

    //Getter und Setter für Status
    public Status getStatus() {
    return status;
}

     public void setStatus(Status status) {
         this.status = status;
     }

    //Getter und Setter für Message
    public Message getMessage() {
        return Message;
    }

    public void setMessage(Message message) {
        this.Message = message;
    }



}