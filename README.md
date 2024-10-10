# swe-gruppe09
# Was ist Git und warum sollte es verwendet werden? Samantha
# Grundlegende Git-Befehle (z. B. git init, git add, git commit, git push): Mirjam
# Branches und ihre Nutzung, Umgang mit Merge-Konflikten: Nele
## Branches und ihre Nutzung
**Branches** sind ein wichtiges Konzept in Git, welches es Entwickler*innen ermöglicht, parallel an verschiedenen Features und Aufgaben zu arbeiten ohne den Hauptcode durcheinanderzubringen. Ein Branch ist im Wesentlichen ein separater Entwicklungszweig, der von einem bestimmten Punkt im Projektverlauf abzweigt. Warum das möglich ist, erklärt sich durch die Art und Weise wie Git Daten speichert.
### Wie speichert Git seine Daten?
Die Funktionsweise von Branches hängt mit der Art und Weise, wie in Git gespeichert wird. Git speichert seine Daten nicht als eine Serie von Änderungen, wie es beispielsweise ein Textverarbeitungsprogram wie Word tut, sondern als eine Reihe von **Snapshots**. Das sind vollständige Momentaufnahmen des gesamten Projekts zu einem bestimmten Zeipunkt. Ein Branch in Git ist ein Zeiger auf einen neuen Zeiger, der auf den aktuellen Commit zeigt.
## Was passiert bei einem Commit?
Wenn ein Commit erfolgt, erstellt Git ein Commit-Objekt, welches einen Zeiger auf den Snapshot des aktuellen Projektzustandes enthält. Zudem enthält das Commit-Objekt folgende Informationen:
- Name und Adresse des Autoren des Commits
- eine Commit-nachricht, die beschreibt was geändert wurde
- ggf. Vorgänger-Commits


## Umgang mit Merge-Konflikten
# Git mit IntelliJ/PyCharm benutzen: Local Repository und Remote Repository: Maylin
# Nützliche Git-Tools und Plattformen (z. B. GitHub) Samantha
