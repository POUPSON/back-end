-- Création de la base de données et sélection
CREATE DATABASE IF NOT EXISTS CDSIR_AGENCE;
USE CDSIR_AGENCE;

-- Table pour les administrateurs
CREATE TABLE IF NOT EXISTS administrateur (
  id_administrateur INT PRIMARY KEY AUTO_INCREMENT,
  nom_administrateur VARCHAR(100),
  numero_cni VARCHAR(20),
  id_agence INT,
  statut VARCHAR(20)
);

-- Table pour les agences
CREATE TABLE IF NOT EXISTS agence (
  id_agence INT PRIMARY KEY AUTO_INCREMENT,
  id_administrateur INT,
  nom_agence VARCHAR(100),
  ville_agence VARCHAR(100),
  localisation VARCHAR(150),
  telephone_agence VARCHAR(20),
  statut VARCHAR(20),
  FOREIGN KEY (id_administrateur) REFERENCES administrateur(id_administrateur)
);

-- Table pour les trajets
CREATE TABLE IF NOT EXISTS trajet (
  id_trajet INT PRIMARY KEY AUTO_INCREMENT,
  id_voyage INT,
  ville_depart VARCHAR(100),
  ville_destination VARCHAR(100),
  quartier_depart VARCHAR(100),
  quartier_destination VARCHAR(100),
  statut VARCHAR(20)
);

-- Table pour les horaires
CREATE TABLE IF NOT EXISTS horaire (
  id_horaire INT PRIMARY KEY AUTO_INCREMENT,
  heure_depart TIME,
  heure_arrive TIME,
  duree TIME,
  statut VARCHAR(20)
);

-- Table pour les clients
CREATE TABLE IF NOT EXISTS client (
  id_client INT PRIMARY KEY AUTO_INCREMENT,
  nom_client VARCHAR(100),
  prenom_client VARCHAR(100),
  date_de_naissance DATE,
  sexe VARCHAR(10),
  numero_telephone VARCHAR(20),
  numero_cni VARCHAR(20),
  email VARCHAR(100),
  mot_passe VARCHAR(100),
  pseudo VARCHAR(50),
  statut VARCHAR(20)
);

-- Table pour les véhicules
CREATE TABLE IF NOT EXISTS vehicule (
  id_vehicule INT PRIMARY KEY AUTO_INCREMENT,
  id_trajet_parcourir INT,
  id_agence INT,
  immatriculation VARCHAR(50),
  modele_vehicule VARCHAR(100),
  capacite INT,
  id_trajet INT,
  statut VARCHAR(20),
  FOREIGN KEY (id_agence) REFERENCES agence(id_agence),
  FOREIGN KEY (id_trajet) REFERENCES trajet(id_trajet)
);

-- Table pour les voyages
CREATE TABLE IF NOT EXISTS voyage (
  id_voyage INT PRIMARY KEY AUTO_INCREMENT,
  id_horaire_propose INT,
  prix DECIMAL(10,2),
  id_trajet INT,
  id_horaire INT,
  id_vehicule INT,
  id_agence INT,
  statut VARCHAR(20),
  FOREIGN KEY (id_horaire) REFERENCES horaire(id_horaire),
  FOREIGN KEY (id_trajet) REFERENCES trajet(id_trajet),
  FOREIGN KEY (id_vehicule) REFERENCES vehicule(id_vehicule),
  FOREIGN KEY (id_agence) REFERENCES agence(id_agence)
);

-- Table pour les réservations
CREATE TABLE IF NOT EXISTS reservation (
  id_reservation INT PRIMARY KEY AUTO_INCREMENT,
  id_client INT,
  id_voyage INT,
  date_reservation DATE,
  trajet VARCHAR(100),
  siege_reserver VARCHAR(10),
  classe VARCHAR(20),
  mode_paiment VARCHAR(20),
  reference_paiement VARCHAR(100),
  montant DECIMAL(10,2),
  statut VARCHAR(20),
  FOREIGN KEY (id_client) REFERENCES client(id_client),
  FOREIGN KEY (id_voyage) REFERENCES voyage(id_voyage)
);

-- Table pour les colis
CREATE TABLE IF NOT EXISTS colis (
  id_colis INT PRIMARY KEY AUTO_INCREMENT,
  id_agence INT,
  id_client INT,
  nom_expediteur VARCHAR(100),
  numero_expediteur VARCHAR(20),
  ville_d_expedition VARCHAR(100),
  quartier_expedition VARCHAR(100),
  nom_destinataire VARCHAR(100),
  numero_destinataire VARCHAR(20),
  ville_de_destination VARCHAR(100),
  quartier_de_destination VARCHAR(100),
  statut VARCHAR(20),
  FOREIGN KEY (id_agence) REFERENCES agence(id_agence),
  FOREIGN KEY (id_client) REFERENCES client(id_client)
);
