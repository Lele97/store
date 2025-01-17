# TEMPO: 5GG

## Creare un'applicazione basata su microservizi per gestire un sistema di ordini per un negozio online.

### Descrizione

Il sistema deve essere composto da tre microservizi principali:

#### User Service
Gestisce le informazioni degli utenti, come registrazione e autenticazione.

**Endpoints:**
- `POST /users/register` - Registra un nuovo utente.
- `POST /users/login` - Effettua il login e restituisce un token JWT.

**Database:** 
- MySQL (tabella `users` con campi `id`, `username`, `password`, `email`).

#### Product Service
Gestisce i prodotti disponibili nel negozio.

**Endpoints:**
- `GET /products` - Elenca tutti i prodotti disponibili.
- `POST /products` - Aggiunge un nuovo prodotto (autorizzato solo agli admin).

**Database:** 
- PostgreSQL o MySQL (tabella `products` con campi `id`, `name`, `price`, `quantity`).

#### Order Service
Gestisce gli ordini effettuati dagli utenti.

**Endpoints:**
- `POST /orders` - Crea un nuovo ordine (richiede token JWT).
- `GET /orders/{userId}` - Visualizza tutti gli ordini effettuati da un utente specifico.

**Database:** 
- MySQL (tabella `orders` con campi `id`, `userId`, `productId`, `quantity`, `status`).

### Specifiche

**Autenticazione:**
- Utilizzare Spring Security per implementare l'autenticazione basata su JWT. Solo gli utenti autenticati possono accedere al servizio ordini e alle operazioni protette del servizio prodotti.

**Comunicazione tra Servizi:**
- Utilizzare REST API per la comunicazione tra microservizi.
- Ad esempio, l'Order Service deve chiamare il Product Service per verificare la disponibilità del prodotto prima di confermare un ordine.

**Bilanciamento del Carico:**
- Integrare un gateway API utilizzando Spring Cloud Gateway per bilanciare il carico e instradare le richieste ai microservizi.

**Service Discovery:**
- Configurare un sistema di service discovery con Eureka Server per gestire i microservizi.

**Configurazione Centrale:**
- Usare Spring Cloud Config per centralizzare la configurazione dei microservizi.

### Test

- Scrivere test unitari per i componenti principali.
- Scrivere test di integrazione per verificare la comunicazione tra i servizi.
