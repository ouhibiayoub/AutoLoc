# Notes API - Atelier 5

## A. Endpoints

| Controleur | Verbe et route | Service appele | Succes | Erreurs |
|---|---|---|---:|---|
| AgenceController | `GET /api/agences` | `IAgenceService.getAll()` | 200 | - |
| AgenceController | `GET /api/agences/{id}` | `IAgenceService.getRequiredById(id)` | 200 | 404 |
| AgenceController | `POST /api/agences` | `IAgenceService.create(entity)` | 201 | 400 |
| AgenceController | `PUT /api/agences/{id}` | `IAgenceService.update(id, entity)` | 200 | 400, 404 |
| AgenceController | `DELETE /api/agences/{id}` | `IAgenceService.delete(id)` | 204 | 404 |
| ClientController | `GET /api/clients` | `IClientService.getAll()` | 200 | - |
| ClientController | `GET /api/clients/{id}` | `IClientService.getRequiredById(id)` | 200 | 404 |
| ClientController | `POST /api/clients` | `IClientService.create(entity)` | 201 | 400 |
| ClientController | `PUT /api/clients/{id}` | `IClientService.update(id, entity)` | 200 | 400, 404 |
| ClientController | `DELETE /api/clients/{id}` | `IClientService.delete(id)` | 204 | 404 |
| ContratController | `GET /api/contrats` | `IContratService.getAll()` | 200 | - |
| ContratController | `GET /api/contrats/{id}` | `IContratService.getRequiredById(id)` | 200 | 404 |
| ContratController | `POST /api/contrats` | `IContratService.create(entity)` | 201 | 400 |
| ContratController | `PUT /api/contrats/{id}` | `IContratService.update(id, entity)` | 200 | 400, 404 |
| ContratController | `DELETE /api/contrats/{id}` | `IContratService.delete(id)` | 204 | 404 |
| EmployeController | `GET /api/employes` | `IEmployeService.getAll()` | 200 | - |
| EmployeController | `GET /api/employes/{id}` | `IEmployeService.getRequiredById(id)` | 200 | 404 |
| EmployeController | `POST /api/employes` | `IEmployeService.create(entity)` | 201 | 400 |
| EmployeController | `PUT /api/employes/{id}` | `IEmployeService.update(id, entity)` | 200 | 400, 404 |
| EmployeController | `DELETE /api/employes/{id}` | `IEmployeService.delete(id)` | 204 | 404 |
| EquipementController | `GET /api/equipements` | `IEquipementService.getAll()` | 200 | - |
| EquipementController | `GET /api/equipements/{id}` | `IEquipementService.getRequiredById(id)` | 200 | 404 |
| EquipementController | `POST /api/equipements` | `IEquipementService.create(entity)` | 201 | 400 |
| EquipementController | `PUT /api/equipements/{id}` | `IEquipementService.update(id, entity)` | 200 | 400, 404 |
| EquipementController | `DELETE /api/equipements/{id}` | `IEquipementService.delete(id)` | 204 | 404 |
| MaintenanceController | `GET /api/maintenances` | `IMaintenanceService.getAll()` | 200 | - |
| MaintenanceController | `GET /api/maintenances/{id}` | `IMaintenanceService.getRequiredById(id)` | 200 | 404 |
| MaintenanceController | `POST /api/maintenances` | `IMaintenanceService.create(entity)` | 201 | 400 |
| MaintenanceController | `PUT /api/maintenances/{id}` | `IMaintenanceService.update(id, entity)` | 200 | 400, 404 |
| MaintenanceController | `DELETE /api/maintenances/{id}` | `IMaintenanceService.delete(id)` | 204 | 404 |
| ReservationController | `GET /api/reservations` | `IReservationService.getAll()` | 200 | - |
| ReservationController | `GET /api/reservations/{id}` | `IReservationService.getRequiredById(id)` | 200 | 404 |
| ReservationController | `POST /api/reservations` | `IReservationService.create(entity)` | 201 | 400 |
| ReservationController | `PUT /api/reservations/{id}` | `IReservationService.update(id, entity)` | 200 | 400, 404 |
| ReservationController | `DELETE /api/reservations/{id}` | `IReservationService.delete(id)` | 204 | 404 |
| VehiculeController | `GET /api/vehicules` | `IVehiculeService.getAll()` | 200 | - |
| VehiculeController | `GET /api/vehicules/{id}` | `IVehiculeService.getRequiredById(id)` | 200 | 404 |
| VehiculeController | `POST /api/vehicules` | `IVehiculeService.create(entity)` | 201 | 400 |
| VehiculeController | `PUT /api/vehicules/{id}` | `IVehiculeService.update(id, entity)` | 200 | 400, 404 |
| VehiculeController | `DELETE /api/vehicules/{id}` | `IVehiculeService.delete(id)` | 204 | 404 |
| PaiementController | `GET /api/paiements` | `IPaiementService.getAll()` | 200 | - |
| PaiementController | `GET /api/paiements/{id}` | `IPaiementService.getRequiredById(id)` | 200 | 404 |

Un POST sur `/api/paiements` n a pas de mapping et renvoie 405 : un paiement se cree via le POST Contrat et sa cascade.

## B. Annotations Jackson

| Association | Annotation | Justification |
|---|---|---|
| Contrat.paiements / Paiement.contrat | `@JsonManagedReference("contrat-paiements")` / `@JsonBackReference("contrat-paiements")` | Evite le cycle JSON et laisse le contrat porter la creation/suppression en cascade. |
| Agence.employes et Agence.vehicules | `@JsonIgnore` | Les collections inverses ne sont pas necessaires dans les reponses de base et bouclent vers Agence. |
| Client.reservations | `@JsonIgnore` | Evite le cycle Client-Reservation tout en gardant Reservation.client lisible et deserialisable. |
| Vehicule.maintenances, Vehicule.reservations, Vehicule.equipements; Equipement.vehicules | `@JsonIgnore` | Cache les collections inverses; les proprietes qui portent les cles etrangeres restent visibles. |
| Contrat.reservation | `@JsonIgnore` | Evite le cycle Contrat-Reservation. |

`@JsonBackReference` supprime la propriete enfant lors de la deserialisation JSON; il convient au lien de composition Contrat-Paiement. `@JsonIgnore` masque entierement une propriete dans les deux sens; il convient aux collections inversees non necessaires. Ne pas utiliser `@JsonBackReference` sur `Reservation.client`, car le POST Reservation perdrait son client.

## C. Postman et Swagger

Postman rejoue des requetes et verifie les reponses. La collection `postman/AutoLoc-Atelier5.postman_collection.json` couvre les operations nominales, les erreurs 400/404, le refus 405 de POST Paiement et la cascade Contrat-Paiement.

Swagger UI expose le contrat OpenAPI genere par SpringDoc et permet d executer les routes. La collection Postman teste les resultats; Swagger presente la documentation a jour du code. Les deux servent des usages differents.

## D. Anomalies relevees pendant la revue de code

L action specifique « Analyze with SonarQube for IDE » n a pas ete lancee dans cette session; les outils disponibles ont permis la compilation et les inspections IntelliJ, mais pas de produire un rapport SonarQube certifie. Les deux ecarts fonctionnels suivants ont ete releves et corriges pendant la revue manuelle (ils ne sont pas presentes comme des alertes Sonar certifiees).

| Regle / constat | Explication | Correction |
|---|---|---|
| Convention de routage REST (revue manuelle) | Le prefixe `/api` etait duplique dans chaque `@RequestMapping`, contrairement a l etape 2 du support. | Centralisation dans `server.servlet.context-path=/api`; les classes exposent seulement leur nom de ressource au pluriel. |
| Paiement en lecture seule (revue manuelle) | PaiementController proposait POST, PUT et DELETE, alors que le paiement doit etre cree par Contrat avec cascade. | Suppression des routes mutatrices; seules les routes GET restent exposees et le POST renvoie 405. |

## E. Questions de comprehension

1. `DispatcherServlet` recoit la requete puis utilise les mappings Spring MVC, construits depuis les annotations de controleur et de methode, pour choisir le handler.
2. Le controleur depend de `IContratService` pour rester couple au contrat d interface, permettre le remplacement de l implementation et eviter l acces direct au repository.
3. POST renvoie 201 car une ressource est creee; DELETE renvoie 204 car la suppression reussit sans corps. Une ressource absente leve `ResourceNotFoundException`, traduite en 404 par `GlobalExceptionHandler`, et non en erreur serveur 500.
4. PaiementController ne propose que GET, car les paiements sont crees avec Contrat via cascade. Un POST direct sur `/api/paiements` renvoie 405 Method Not Allowed.
5. Exposer directement une entite lie l API au schema JPA, peut reveler des champs internes et provoquer des cycles ou proxies Hibernate. Contrat.paiements utilise la paire managed/back reference pour la composition et la cascade; Agence.vehicules utilise `@JsonIgnore` car c est une collection inverse qui n est pas requise dans la reponse simple.
