-- Q1
CREATE TABLE champ_lohubert (ch varchar(3), commune varchar(40) not null, superficie integer not null, primary key (ch));

-- Q2
\COPY champ_lohubert FROM 'P:/BD-2annee/TP2/champ.txt' WITH DELIMITER ','

--Q3
CREATE TABLE culture_lohubert (ch varchar(3) references champ_lohubert, cereale varchar(20) not null, an integer not null, primary key (ch, cereale, an));

\COPY culture_lohubert FROM 'P:/BD-2annee/TP2/culture.txt' WITH DELIMITER ','

CREATE TABLE appartient_lohubert (nom varchar(20), ch varchar(3) references champ_lohubert, primary key(nom, ch));

\COPY appartient_lohubert FROM 'P:/BD-2annee/TP2/appartient.txt' WITH DELIMITER ','

-- Q4
SELECT AVG(superficie) FROM champ_lohubert, appartient_lohubert WHERE champ_lohubert.ch = appartient_lohubert.ch AND nom = 'DUPONT';

-- Q5
SELECT culture_lohubert.cereale, culture_lohubert.ch FROM champ_lohubert, culture_lohubert WHERE champ_lohubert.ch = culture_lohubert.ch AND an = 2019 AND commune = 'Chantepie' AND superficie > (SELECT AVG(superficie)FROM champ_lohubert);

-- Q6
SELECT DISTINCT A1.ch FROM appartient_lohubert A1, appartient_lohubert A2 WHERE A1.ch = A2.ch AND A1.nom <> A2.nom;

-- Q7
SELECT ch FROM appartient_lohubert GROUP BY ch HAVING COUNT(DISTINCT nom)>2; #>2 pour remplacer >= 3

-- Q8
CREATE VIEW bon AS SELECT ch FROM culture_lohubert WHERE cereale='sorgho' AND an=2019;

SELECT nom FROM appartient_lohubert A, bon B WHERE A.ch = B.ch GROUP BY nom HAVING COUNT(*) = (SELECT COUNT(*) FROM bon);

DROP VIEW bon;

-- Q9
UPDATE champ_lohubert SET superficie = 62 WHERE ch = 'ch7';
SELECT * FROM champ_lohubert WHERE ch = 'ch7';

-- Q10
INSERT INTO culture_lohubert (ch, cereale, an) VALUES ('ch8', 'sarrasin', 2020);
SELECT * FROM culture_lohubert;

-- Q11 
DELETE FROM culture_lohubert WHERE an = 2019 AND cereale = 'quinoa' AND ch IN (SELECT ch FROM appartient_lohubert WHERE nom = 'DUPONT');

-- Q12
GRANT SELECT ON champ_lohubert, culture_lohubert TO PUBLIC;

-- Q13
\z champ_lohubert;

-- Q14
GRANT ALL ON appartient_lohubert TO PUBLIC;
