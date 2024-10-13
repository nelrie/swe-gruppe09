# swe-gruppe09
# Was ist Git und warum sollte es verwendet werden? Samantha
# Grundlegende Git-Befehle (z. B. git init, git add, git commit, git push): Mirjam
# Branches und ihre Nutzung, Umgang mit Merge-Konflikten: Nele
## Branches und ihre Nutzung
**Branches** sind unabhängige Entwicklungsumgebungen in Git, die es Entwickler*innen ermöglicht, parallel an verschiedenen Features und Aufgaben zu arbeiten ohne den Hauptzweig durcheinanderzubringen. Ein Branch ist im Wesentlichen ein separater Entwicklungszweig, der von einem bestimmten Punkt im Projektverlauf abzweigt.
### Datenspeicherung in Git
Die Funktionsweise von Branches hängt mit der Art und Weise zusammen, wie in Git gespeichert wird. Git speichert seine Daten nicht als eine Serie von Änderungen, wie es beispielsweise ein Textverarbeitungsprogram tut, sondern als eine Reihe von **Snapshots**. Diese sind vollständige Momentaufnahmen des gesamten Projekts zu dem Zeitpunkt eines **Commits**.
Wenn ein Commit erfolgt, erstellt Git ein Commit-Objekt, welches einen Zeiger auf den Snapshot des aktuellen Projektzustandes enthält. Ein Branch ist somit ein beweglicher Zeiger auf einen bestimmten Commit. Wird ein neuer Branch erstellt, wird ein neuer Zeiger erstellt, der auf den aktuellen Commit zeigt.

### Erstellung und Verwaltung von Branches
- **Erstellung eines Branches**: Ein neuer Branch wird mit dem Befehl `git branch <branch-name>` erstellt. 
- **Wechseln zwischen Branches**: Mit `git checkout <branch-name>` oder `git switch <branch-name>` kann zwischen verschiedenen Branches gewechselt werden.
- **Erstellung und Wechseln in einem Schritt**: Wenn ein neuer Branch erstellt und direkt zu diesem gewechselt werden soll, wird dem checkout oder Switch Befehl ein-c (create) angehängt: `git switch -c <branch-name>`
- **Zusammenführen von Branches**: Änderungen aus einem Branch können mit `git merge <branch-name>` in einen anderen Bereich integriert werden. Dies ermöglicht die Integration in den Hauptzweig.

### Umgang mit Merge-Konflikten
Bei letztem, dem Zusammenführen von Branches, können Konflikte auftreten, wenn dieselben Dateien in beiden Branches geändert wurden. Git bietet Werkzeuge an, um diese Konflikte manuell zu beheben und die Änderungen zu integrieren:
1. **Erkennung von Konflikten**: Git erkennt automatisch Konflikte während des Merge-Vorgangs und markiert die betroffenen Stellen in den Dateien.
2. **Manuelle Behebung**: Diese Konfliktmarkierungen müssen dann manuell entfernt werden.
3. **Bestätigung der Änderungen**: Mit `git add <datei>` und `git commit` können die Änderungen bestätigt und der Merge abgeschlossen werden.

### Zusammenfassung nützlicher Git-Befehle
- **Branch erstellen**: `git branch <branch-name>`
- **Zu einem Branch wechseln**: `git checkout <branch-name>` oder `git switch <branch-name>`
- **Branch zusammenführen**: `git merge <branch-name>`
- **Konflikte anzeigen**: `git status`
- **Konflikte beheben**: erfolgt manuell: öffne die betroffenen Dateien in einem Texteditor und entferne die Konfliktmarkierungen.
- **Änderungen bestätigen**: `git add <datei>` und `git commit`

# Git mit IntelliJ/PyCharm benutzen: Local Repository und Remote Repository: Maylin
# Nützliche Git-Tools und Plattformen (z. B. GitHub) Samantha
