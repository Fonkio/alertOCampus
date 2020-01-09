
***** GIT
Le projet a été développé sur GIT :
https://gitlab.com/Fonkio/projet-s5


***** Lancement de l'application

Lancer le serveur en premier "AlertOCampusAdminServeur.jar"
Lancer le client "AlertOCampus.jar"
Se connecter avec un des utilisateurs
Pour simuler plusieurs clients il faut séparer le AlertOCampus.jar du fichier data.txt généré à la fermeture car l'utilisateur est enregistré.


**** Requêtes de création de base de données et des tables, à éxécuter sans sélectionner de bases de données :

CREATE DATABASE alertocampus;

USE alertocampus;

CREATE TABLE Utilisateur(
   Id_Utilisateur INT AUTO_INCREMENT,
   Nom VARCHAR(50),
   Prenom VARCHAR(50),
   login VARCHAR(100),
   motDePasse VARCHAR(50),
   PRIMARY KEY(Id_Utilisateur)
);

CREATE TABLE Groupe(
   Id_Groupe INT AUTO_INCREMENT,
   Libelle VARCHAR(50),
   PRIMARY KEY(Id_Groupe)
);

CREATE TABLE Fil_de_discussion(
   Id_Fil_de_discussion INT AUTO_INCREMENT,
   Titre VARCHAR(50),
   Id_Utilisateur INT NOT NULL,
   Id_Groupe INT NOT NULL,
   PRIMARY KEY(Id_Fil_de_discussion),
   FOREIGN KEY(Id_Utilisateur) REFERENCES Utilisateur(Id_Utilisateur),
   FOREIGN KEY(Id_Groupe) REFERENCES Groupe(Id_Groupe)
);

CREATE TABLE Message(
   Id_Message INT AUTO_INCREMENT,
   Texte TEXT,
   dCreation DATETIME,
   Id_Fil_de_discussion INT NOT NULL,
   Id_Utilisateur INT NOT NULL,
   PRIMARY KEY(Id_Message),
   FOREIGN KEY(Id_Fil_de_discussion) REFERENCES Fil_de_discussion(Id_Fil_de_discussion),
   FOREIGN KEY(Id_Utilisateur) REFERENCES Utilisateur(Id_Utilisateur)
);

CREATE TABLE Appartenir(
   Id_Utilisateur INT,
   Id_Groupe INT,
   PRIMARY KEY(Id_Utilisateur, Id_Groupe),
   FOREIGN KEY(Id_Utilisateur) REFERENCES Utilisateur(Id_Utilisateur) ON DELETE CASCADE,
   FOREIGN KEY(Id_Groupe) REFERENCES Groupe(Id_Groupe) ON DELETE CASCADE
);

CREATE TABLE Status(
   Id_Utilisateur INT,
   Id_Message INT,
   Status VARCHAR(10),
   PRIMARY KEY(Id_Utilisateur, Id_Message),
   FOREIGN KEY(Id_Utilisateur) REFERENCES Utilisateur(Id_Utilisateur),
   FOREIGN KEY(Id_Message) REFERENCES Message(Id_Message)
);

***** Requêtes de création d'un set de données de base :

INSERT INTO utilisateur(login, motDePasse, Nom, Prenom)  VALUES ('djn', 'pinpix', 'Doe', 'John');

INSERT INTO utilisateur(login, motDePasse, Nom, Prenom)  VALUES ('mco', 'pinpin', 'Costa', 'Marie');

INSERT INTO utilisateur(login, motDePasse, Nom, Prenom) VALUES ('lhn', '$iutinfo', 'Louahadj', 'Ines');

INSERT INTO utilisateur(login, motDePasse, Nom, Prenom)  VALUES ('fbm', '$iutinfo', 'Fabre', 'Maxime');

INSERT INTO utilisateur (login, motDePasse, Nom, Prenom) VALUES ('slm', 'pinpix', 'SALVAGNAC', 'Maxime');

INSERT INTO groupe(Libelle) VALUES ('TDA3');

INSERT INTO groupe(Libelle) VALUES ('Enseignants L3');

INSERT INTO appartenir VALUES (1, 2);

INSERT INTO appartenir VALUES (2, 2);

INSERT INTO appartenir VALUES (3, 1);

INSERT INTO appartenir VALUES (4, 1);

