# swe-gruppe09
# Was ist Git und warum sollte es verwendet werden? Samantha

Git ist ein verteiltes Versionenkontrollsystem, dass vor allem in der Softwareentwicklung für das Verwalten von Dateien genutzt wird. Dabei speichert Git jede Änderung im Quellcode, sodass man jederzeit zu einer früheren Version zurückkehren kann. Außerdem vereinfacht es die Zusammenarbeit im Team, da mehrere Entwickler gleichzeitig am selben Projekt arbeiten können. Dateiänderungen können lokal vorgenommen werden und dann an das zentrale Repository übermittelt werden, womit die Änderungen für alle Entwickler sichtbar werden.

# Grundlegende Git-Befehle (z. B. git init, git add, git commit, git push): Mirjam

- **git init** – Neues Repository erstellen
- **git clone** <repository-url> – Bestimmtes Repository clonen

- **git log** – Historie der Commits
- **git status** - Überblick über das Repository, Änderungen, Branch-Informationen etc.
- **git diff** - Zeigt Unterschiede zwischen Branches, Commits etc. 

- **git add <Dateiname>** - Dateien zum Staging-Bereich hinzugügen
- **git commit -m „kurze Beschreibung der Änderungen“** - Änderungen speichern/committen
- **git pull** – Änderungen synchronisieren/vom Remote-Repository abrufen
- **git push** -  Änderungen an das Remote Repository senden

- **git branch <branch-name>** - Neuen Branch erstellen
- **git checkout <branch-name>** - Zu einem anderen Branch wechseln
- **git checkout -b <branch-name>** - Neuen Branch erstellen und direkt wechseln
- **git merge <branch-name>** - Branch mit den aktuellen Branch zusammenführen

- **git reset --<commit-hash>** -  Branch wird auf bestimmten Commit zurückgesetzt, behält die Änderungen in der Arbeitskopie und Staging-  Bereich bei. 
- **git reset --hard <commit-hash>** - Alle uncommitteten Änderungen im Branch (in der Arbeitskopie und im Staging-Bereich)  werden gelöscht und auf einen bestimmten Commit zurückgesetzt
- **git checkout <commit-hash>** -- <dateiname> - Wiederherstellung eines früheren Commit

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

- GitHub: Ist eine webbasierte Plattform für die Verwaltung von Git-Repositories. Entwickler können ihre Projekte online speichern, verwalten und mit anderen teilen.
- GitLab: Ist eine DevOPs-Plattform, die den gesamten Softwareentwicklungsprozess abdeckt. Sie bietet alles von Projektplanung und Quellcodeverwaltung bis hin zu CI/CD, Überwachung und Sicherheit. Es gibt eine Cloud-basierte Version sowie die Möglichkeit, die Plattform selbst zu hosten.
- GitKraken: Ist ein Git-Client mit einer grafischen Benutzeroberfläche, der Git-Funktionen wie Versionskontrolle, Branching und Merging, ohne Nutzung der Befehlszeile ermöglicht.
- Git Extensions: Ist ein Tool mit grafischer Benutzeroberfläche, für die Verwaltung von Git-Repositoires unter Windows, Linux und macOS. Es hat verschiedene Funktionen zur Verwaltung von Repositories, Branches und Merges.
- Git Bash: Ist eine Befehlszeilenoberfläche, die es Entwicklern ermöglicht unter Windows gängige Unix-Befehle zu verwenden.
- TortoiseGit: Ist eine grafische Benutzeroberfläche für Git, speziell für Windows. Git-Funktionalitäten werden in den Windows Explorer integriert, Entwickler können ihr Repository direkt über das Kontextmenü verwalten.

