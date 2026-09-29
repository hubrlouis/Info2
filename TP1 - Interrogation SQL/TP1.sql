# TP1 - Interrogation 
# Lancer la commande : \i 'H:/Info S3/TP1.txt'

-- Q1 : 
\d F
\d MF
\d P

-- Q2 :
SELECT DISTINCT nom, remise FROM F,MF,P WHERE F.f = MF.f AND MF.p = P.p AND origine = 'Dijon';

-- Q3 : 
SELECT DISTINCT nom FROM F,MF,P WHERE F.f = MF.f AND MF.p = P.p AND origine in ('Riec', 'Dijon') AND ville ='Paris';

-- Q4 : 
SELECT nom_p FROM F,MF,P WHERE F.f = MF.f AND MF.p = P.p AND nom = 'Bornibus' AND qte < 5;

-- Q5 :
SELECT DISTINCT nom, ville FROM F,MF,P WHERE F.f = MF.f AND MF.p = P.p AND F.ville = P.origine;

-- Q6 :
SELECT f FROM MF,P WHERE MF.p = P.p AND couleur = 'vert' EXCEPT SELECT f FROM MF,P WHERE MF.p = P.p AND not couleur = 'vert'; 

-- Q7 :
SELECT f FROM MF,P WHERE MF.p = P.p AND not couleur = 'vert' EXCEPT SELECT f FROM MF,P WHERE MF.p = P.p AND couleur = 'vert'; 

-- Q8 : 
SELECT F2.nom, F2.ville FROM F F1, F F2 WHERE F1.nom = 'Bornibus' AND F1.remise < F2.remise;

-- Q9 : 
SELECT COUNT(p) FROM P WHERE couleur = 'vert';

-- Q10 :
SELECT p FROM MF GROUP BY p HAVING SUM(qte)>10;

-- Q11 :  
SELECT nom_p FROM P WHERE p IN (SELECT p FROM MF GROUP BY p HAVING SUM(qte)>10);

-- Q12 :
SELECT COUNT(DISTINCT f), p FROM MF GROUP BY p;

-- Q13 :
SELECT COUNT(*)::numeric/COUNT(DISTINCT p) FROM MF;
# Ici : on ne peut pas récupérer le résultat de Q12 et l'utiliser avec AVG(). Le ::numeric permet de rendre la valeur flottante (décimale) pour obtenir la moyenne non arrondie.

-- Q14 :
SELECT F.f FROM F,MF,P WHERE F.f = MF.f AND MF.p = P.p AND ville=origine EXCEPT SELECT F.f FROM F,MF,P WHERE F.f = MF.f AND MF.p = P.p AND ville<>origine;

-- Q 15 :
SELECT f FROM MF WHERE p in (SELECT p FROM MF GROUP BY p HAVING COUNT(DISTINCT f)=1);