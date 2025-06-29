-- Création de la base de données
CREATE DATABASE IF NOT EXISTS cdsir_agency;
USE cdsir_agency;

-- Table: agence
CREATE TABLE agence (
                        id_agence BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nom_agence VARCHAR(100) NOT NULL,
                        ville_agence VARCHAR(100) NOT NULL,
                        localisation VARCHAR(150),
                        telephone_agence VARCHAR(20) NOT NULL,
                        statut VARCHAR(20),
                        CONSTRAINT uk_agence_nom_agence UNIQUE (nom_agence),
                        CONSTRAINT uk_agence_telephone_agence UNIQUE (telephone_agence)
);

-- Table: administrateur
CREATE TABLE administrateur (
                                id_administrateur BIGINT AUTO_INCREMENT PRIMARY KEY,
                                nom_administrateur VARCHAR(100) NOT NULL,
                                numero_cni VARCHAR(20) NOT NULL,
                                mot_passe VARCHAR(255) NOT NULL,
                                id_agence BIGINT,
                                role VARCHAR(20) NOT NULL,
                                statut VARCHAR(20),
                                date_creation DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                date_modification DATETIME,
                                CONSTRAINT uk_administrateur_numero_cni UNIQUE (numero_cni),
                                CONSTRAINT fk_administrateur_agence FOREIGN KEY (id_agence) REFERENCES agence(id_agence)
);

-- Table: client
CREATE TABLE client (
                        id_client BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nom VARCHAR(50) NOT NULL,
                        prenom VARCHAR(50) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        mot_passe VARCHAR(255) NOT NULL,
                        telephone VARCHAR(20) NOT NULL,
                        numero_cni VARCHAR(255) NOT NULL,
                        enabled BOOLEAN DEFAULT TRUE,
                        reset_token VARCHAR(255),
                        token_expiration DATETIME,
                        date_creation DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        date_modification DATETIME,
                        role VARCHAR(20) NOT NULL,
                        CONSTRAINT uk_client_email UNIQUE (email),
                        CONSTRAINT uk_client_telephone UNIQUE (telephone),
                        CONSTRAINT uk_client_numero_cni UNIQUE (numero_cni)
);

-- Table: vehicule
CREATE TABLE vehicule (
                          id_vehicule BIGINT AUTO_INCREMENT PRIMARY KEY,
                          marque VARCHAR(50) NOT NULL,
                          modele VARCHAR(50) NOT NULL,
                          annee_fabrication INT NOT NULL,
                          capacite INT NOT NULL,
                          immatriculation VARCHAR(20) NOT NULL,
                          statut VARCHAR(20),
                          id_agence BIGINT NOT NULL,
                          CONSTRAINT uk_vehicule_immatriculation UNIQUE (immatriculation),
                          CONSTRAINT fk_vehicule_agence FOREIGN KEY (id_agence) REFERENCES agence(id_agence),
                          CONSTRAINT chk_annee_fabrication CHECK (annee_fabrication >= 1900),
                          CONSTRAINT chk_capacite CHECK (capacite >= 1)
);

-- Table: trajet
CREATE TABLE trajet (
                        id_trajet BIGINT AUTO_INCREMENT PRIMARY KEY,
                        ville_depart VARCHAR(100) NOT NULL,
                        ville_destination VARCHAR(100) NOT NULL,
                        quartier_depart VARCHAR(100),
                        quartier_destination VARCHAR(100),
                        statut VARCHAR(20),
                        CONSTRAINT uk_trajet_unique UNIQUE (ville_depart, ville_destination, quartier_depart, quartier_destination)
);

-- Table: horaire
CREATE TABLE horaire (
                         id_horaire BIGINT AUTO_INCREMENT PRIMARY KEY,
                         heure_depart TIME NOT NULL,
                         heure_arrive TIME NOT NULL,
                         duree TIME NOT NULL,
                         statut VARCHAR(20)
);

-- Table: voyage
CREATE TABLE voyage (
                        id_voyage BIGINT AUTO_INCREMENT PRIMARY KEY,
                        date_depart DATE NOT NULL,
                        date_arrivee DATE NOT NULL,
                        prix DECIMAL(10,2) NOT NULL,
                        statut VARCHAR(20) NOT NULL,
                        id_trajet BIGINT NOT NULL,
                        id_horaire BIGINT NOT NULL,
                        id_vehicule BIGINT NOT NULL,
                        id_agence BIGINT NOT NULL,
                        CONSTRAINT fk_voyage_trajet FOREIGN KEY (id_trajet) REFERENCES trajet(id_trajet),
                        CONSTRAINT fk_voyage_horaire FOREIGN KEY (id_horaire) REFERENCES horaire(id_horaire),
                        CONSTRAINT fk_voyage_vehicule FOREIGN KEY (id_vehicule) REFERENCES vehicule(id_vehicule),
                        CONSTRAINT fk_voyage_agence FOREIGN KEY (id_agence) REFERENCES agence(id_agence),
                        CONSTRAINT chk_prix CHECK (prix > 0)
);

-- Table: colis
CREATE TABLE colis (
                       id_colis BIGINT AUTO_INCREMENT PRIMARY KEY,
                       id_agence BIGINT NOT NULL,
                       id_client_expediteur BIGINT,
                       description TEXT NOT NULL,
                       weight DOUBLE NOT NULL,
                       dimensions VARCHAR(100),
                       estimated_cost DOUBLE NOT NULL,
                       tracking_number VARCHAR(255) NOT NULL,
                       date_enregistrement DATETIME NOT NULL,
                       date_expedition DATETIME,
                       date_envoi DATETIME,
                       date_arrivee_agence_destination DATETIME,
                       date_livraison_estimee DATETIME,
                       date_livraison_prevue DATETIME,
                       date_livraison_reelle DATETIME,
                       statut VARCHAR(50) NOT NULL,
                       nom_expediteur VARCHAR(100) NOT NULL,
                       telephone_expediteur VARCHAR(20) NOT NULL,
                       email_expediteur VARCHAR(255),
                       ville_origine VARCHAR(100) NOT NULL,
                       quartier_expedition VARCHAR(100),
                       nom_destinataire VARCHAR(100) NOT NULL,
                       numero_destinataire VARCHAR(20) NOT NULL,
                       email_destinataire VARCHAR(255),
                       ville_de_destination VARCHAR(100) NOT NULL,
                       quartier_destination VARCHAR(100),
                       mode_paiement VARCHAR(50) NOT NULL,
                       reference_paiement VARCHAR(255),
                       code_validation VARCHAR(255),
                       voyage_id BIGINT,
                       CONSTRAINT uk_colis_tracking_number UNIQUE (tracking_number),
                       CONSTRAINT uk_colis_code_validation UNIQUE (code_validation),
                       CONSTRAINT fk_colis_agence FOREIGN KEY (id_agence) REFERENCES agence(id_agence),
                       CONSTRAINT fk_colis_client FOREIGN KEY (id_client_expediteur) REFERENCES client(id_client),
                       CONSTRAINT fk_colis_voyage FOREIGN KEY (voyage_id) REFERENCES voyage(id_voyage),
                       CONSTRAINT chk_weight CHECK (weight > 0),
                       CONSTRAINT chk_estimated_cost CHECK (estimated_cost > 0)
);

-- Table: reservation
CREATE TABLE reservation (
                             id_reservation BIGINT AUTO_INCREMENT PRIMARY KEY,
                             id_client BIGINT,
                             client_nom_invite VARCHAR(100),
                             client_prenom_invite VARCHAR(100),
                             client_email_invite VARCHAR(255),
                             client_telephone_invite VARCHAR(20),
                             id_voyage BIGINT NOT NULL,
                             date_reservation DATETIME NOT NULL,
                             siege_reserver VARCHAR(50) NOT NULL,
                             classe VARCHAR(50) NOT NULL,
                             mode_paiement VARCHAR(50) NOT NULL,
                             reference_paiement VARCHAR(255),
                             code_validation VARCHAR(255),
                             montant DOUBLE NOT NULL,
                             statut VARCHAR(50) NOT NULL,
                             CONSTRAINT uk_reservation_code_validation UNIQUE (code_validation),
                             CONSTRAINT fk_reservation_client FOREIGN KEY (id_client) REFERENCES client(id_client),
                             CONSTRAINT fk_reservation_voyage FOREIGN KEY (id_voyage) REFERENCES voyage(id_voyage),
                             CONSTRAINT chk_montant CHECK (montant > 0)
);

-- Table: historique (version modifiée selon vos spécifications)
CREATE TABLE historique (
                            id_historique BIGINT AUTO_INCREMENT PRIMARY KEY,
                            description TEXT NOT NULL,
                            id_reservation BIGINT,
                            id_client BIGINT,
                            id_administrateur BIGINT,
                            id_colis BIGINT,
                            date_creation DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            date_modification DATETIME,
                            date_evenement DATETIME NOT NULL,
                            CONSTRAINT fk_historique_reservation FOREIGN KEY (id_reservation) REFERENCES reservation(id_reservation),
                            CONSTRAINT fk_historique_client FOREIGN KEY (id_client) REFERENCES client(id_client),
                            CONSTRAINT fk_historique_administrateur FOREIGN KEY (id_administrateur) REFERENCES administrateur(id_administrateur),
                            CONSTRAINT fk_historique_colis FOREIGN KEY (id_colis) REFERENCES colis(id_colis)
);

-- Création des index
CREATE INDEX idx_historique_dates ON historique(date_creation, date_evenement);
CREATE INDEX idx_historique_reservation ON historique(id_reservation);
CREATE INDEX idx_historique_client ON historique(id_client);
CREATE INDEX idx_historique_admin ON historique(id_administrateur);
CREATE INDEX idx_historique_colis ON historique(id_colis);