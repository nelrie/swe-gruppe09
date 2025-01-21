package feedback.application.commands;

import feedback.domain.valueobjects.Email;
import feedback.domain.valueobjects.FullName;
import feedback.domain.valueobjects.Message;

// Kapselt Daten für die Erstellung des Feedbacks;
// Record Klasse enthält automatisch Konstruktor und getter Methoden
public record CreateFeedbackCommand(FullName fullName, Email email, Message message) {


}
