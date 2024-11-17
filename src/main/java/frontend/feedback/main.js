function submitFeedback() {
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const email = document.getElementById('email').value;
    const feedback = document.getElementById('feedback').value;
    const status = document.getElementById('status').value;

    const feedbackData = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        message: feedback,
        status: status
    };

    // ein neues XMLHTTPRequest-Objekt wird erstellt um eine HHTP Anfrage zu senden
    const xhr = new XMLHttpRequest();
    // öffnet eine POST_Anfrage an den angegebene URL-Endpunkt
    xhr.open("POST", "http://localhost:8080/feedbacks", true);
    // Header Content-Type wird auf application/json gesetzt, um JSON Daten zu senden
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
// Methode überwacht den Zustand der Anfrage
    xhr.onreadystatechange = function () {
        // wenn Anfrage erfolgreich, dann wird die Antwort verarbeitet und eine Erfoglsmeldung angezeigt
        if (xhr.readyState === 4 && xhr.status === 200) {
            const response = JSON.parse(xhr.responseText);
            console.log('Feedback gesendet:', response);
            alert('Feedback erfolgreich gesendet!');
        } else if (xhr.readyState === 4) {
            // wenn Anfrage nicht erfolreich, wird eine Fehlermeldung angezeigt
            console.error('Fehler beim Senden des Feedbacks:', xhr.responseText);
            alert('Fehler beim Senden des Feedbacks.');
        }
    };
// MEthode sendet die JSON Daten an den Server
    xhr.send(JSON.stringify(feedbackData));
}


function getAllFeedbacks() {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8080/feedbacks", true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const feedbacks = JSON.parse(xhr.responseText);
            console.log('Alle Feedbacks:', feedbacks);
            // Hier kannst du den Code hinzufügen, um die Feedbacks im Frontend anzuzeigen
        } else if (xhr.readyState === 4) {
            console.error('Fehler beim Abrufen der Feedbacks:', xhr.responseText);
        }
    };

    xhr.send();
}


function deleteFeedback(id) {
    const xhr = new XMLHttpRequest();
    xhr.open("DELETE", `http://localhost:8080/feedbacks/${id}`, true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log('Feedback gelöscht:', id);
            alert('Feedback erfolgreich gelöscht!');
            // Hier kannst du den Code hinzufügen, um das gelöschte Feedback aus der Anzeige zu entfernen
        } else if (xhr.readyState === 4) {
            console.error('Fehler beim Löschen des Feedbacks:', xhr.responseText);
            alert('Fehler beim Löschen des Feedbacks.');
        }
    };

    xhr.send();
}

