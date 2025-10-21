# TARIFBERECHNUNG EINER UNFALLVERSICHERUNG
## Aufgabenstellung für eine Live Coding Session

### Auftrag
Unser Kunde, die **Pfefferminzia Versicherungs AG**, beauftragt uns mit der Implementierung eines Services zur Tarifierung einer Unfallversicherung. Ziel ist die Ermittlung der Prämie, also den Beitrag, den der Versicherungsnehmer zu zahlen hat.

### Eine Unfallversicherung - fachlicher Kontext
Eine Unfallversicherung ist eine Form der Versicherung, die Schadensersatz für Personen- und Sachschäden durch Unfälle oder andere Ereignisse bereitstellt. Die Versicherung übernimmt die finanzielle Verantwortung für Schäden, die durch Unfälle entstehen, um den Versicherungsnehmer von finanziellen Schwierigkeiten zu befreien. Die Prämie, die der Versicherungsnehmer zahlen muss, hängt von verschiedenen Faktoren ab. In einem Unfallvertrag ist eine Menge von Personen versicherbar. Jede versicherte Person ist gegen eine individuell zu erfassende Menge von Risiken geschützt. Diese sogenannten Leistungsarten wirken sich primär auf die jeweilige Prämie für die versicherte Person aus. Die Prämie für den Vertrag ergibt sich aus der Summe der Prämien aller versicherten Personen.

### Domänenmodell - Auszug
Das fachliche Modell ergibt sich dann folgendermaßen:

![Domain Model](assets/domain-model.png)

### Leistungsarten
Jede Person kostet grundsätzlich 5,-€ Prämie. Die Pfefferminzia versichert folgende Risiken:
* **Invalidität**
  * Versicherungssumme 200.000,-€
  * Prämie 5,50€ mtl.
* **Tod**
  * Versicherungssumme 1.000.000,-€
  * Prämie 12,-€ mtl.
* **Gipsgeld**
  * Versicherungssumme 1.000,-€
  * Prämie 2,25€ mtl.

### Lösungsansatz
Die Anwendung wird als **Multi-Module Maven-Projekt** mit zwei Hauptmodulen aufgebaut:

#### Backend-Modul (`unfall-backend`)
* REST-Service mit Spring Boot
* Layered Architecture:
  * **Domain**: JPA-Entities (Contract, Person, Coverage, CoverageType)
  * **Repository**: Spring Data JPA Repositories für Datenbankzugriff
  * **Service**: Business Logic (TariffService für Prämienkalkulation)
  * **Controller**: REST-Endpunkte (ContractController)
* H2 In-Memory-Datenbank zur Persistierung
* OpenAPI/Swagger UI zur API-Dokumentation
* CORS-Konfiguration für Frontend-Zugriff

#### Frontend-Modul (`unfall-frontend`)
* Single Page Application (SPA) mit Angular
* Component-basierte Architektur:
  * **ContractListComponent**: Übersicht aller Verträge mit Personen und Leistungsarten
  * **ContractDetailComponent**: Formular zum Anlegen/Bearbeiten von Verträgen
  * **ContractApiService**: REST-Client für Backend-Kommunikation
* Reactive Forms für Datenerfassung
* Angular Material für UI-Komponenten
* Routing für Navigation zwischen Listen- und Detailansicht

#### Kommunikation
* Das Frontend kommuniziert über **HTTP/REST** mit dem Backend
* JSON als Austauschformat
* API-Base-URL konfigurierbar via Injection Token

#### Build & Deployment
* Maven orchestriert den Build beider Module
* Frontend-Maven-Plugin integriert Angular-Build in Maven-Lifecycle
* Docker-Container für beide Module mit docker-compose orchestriert

### Technische Vorgaben
* Java 25
* REST Service
* Spring Boot 3.5.0
* Maven 3.9+
* JUnit 5
* JPA (Jakarta Persistence)
* H2 Database
* Swagger / OpenAPI (springdoc-openapi 2.8.13)
* Angular 20.3.0
* Angular Material 20.2.9
* Node.js 22.20.0
* npm 10.9.3
* TypeScript 5.9.2

### Mock up
Die Oberfläche kann sich am folgenden Mockup orientieren:

![Mockup](assets/mockup.png)