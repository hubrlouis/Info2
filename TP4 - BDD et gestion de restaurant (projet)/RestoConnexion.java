import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
/**
* Classe gerant la connexion a la base de donnees
*/
public class RestoConnexion {
/**
* Login et mot de passe de l'utilisateur
*/
String userId, password;
/**
* Code de l'employe
*/
String idEmp;
/**
* Date courante
*/
MyDate dateCourante;
/**
* Pour connexion a la base INSA2
*/
Connection connexion;
/**
* Pour instructions simples (select, lock...)
*/
Statement r_select;
/**
* Pour ajouts de reservations
*/
PreparedStatement r_insert;
/**
* Pour modifications de reservations
*/
PreparedStatement r_update;
/**
* Pour suppressions de reservations futures
*/
PreparedStatement r_delete;
/**
* Pour suppressions de reservations anterieures
*/
PreparedStatement r_payer;
/**
* Pour ajouts de une categorie d employe a un restaurant
*/
PreparedStatement r_tarifs;
/**
* Tampon de lecture pour la saisie des requetes en mode console
*/
static BufferedReader br =
new BufferedReader (new InputStreamReader (System.in));

/**
* CONSTRUCTEUR etablissant une connexion avec la base (attribut connexion).
* @param uid le nom de l'utilisateur
* @param pwd le mot de passe de l'utilisateur
* @throws Exception si erreur lors de la connexion a la base de donnees
*/
public RestoConnexion (String uid, String pwd) throws Exception {
Class.forName("org.postgresql.Driver"); // Chargement du driver
userId = uid;
password = pwd;
connexion = DriverManager.getConnection (
"jdbc:postgresql://psqlstpi-vmp-app01.educ.insa/INSA2",
userId, password);
connexion.setAutoCommit(false);
r_select =
connexion.createStatement (ResultSet.TYPE_SCROLL_INSENSITIVE,
ResultSet.CONCUR_READ_ONLY);

//!\\ ATTENTION //!\\
// Les noms des tables utilisees ci-dessous sont a adapter
// aux noms de vos propres tables
r_insert = connexion.prepareStatement(
"INSERT INTO Reservations VALUES (?,?,?)");
r_update = connexion.prepareStatement(
"UPDATE Reservations SET R = ? "+
"WHERE E=? AND D=?");
r_delete = connexion.prepareStatement(
"DELETE FROM Reservations "+
"WHERE E=? AND D=?");
r_payer = connexion.prepareStatement (
"DELETE FROM Reservations "+
"WHERE E=? AND D < ?");
r_tarifs =
connexion.prepareStatement(
"INSERT INTO Tarifs VALUES (?,?,?)");
}

/**
* Methode posant une question a l'utilisateur et retournant la donnee saisie.
* @param prompt la question posee a l'utilisateur
* @return la chaine de caracteres reponse de l'utilisateur
*/
private static String acquisition (final String prompt) {
String chaine = "";
System.out.print(prompt+ " > ");
System.out.flush();
try {
chaine = br.readLine();
}
catch(java.io.IOException e) {
System.out.println(e + "ds lecture de <" + prompt+ ">");
}
return (chaine);
}

/**
* Cloture des instructions puis de la session.
* @throws SQLException
*/
public void terminer () throws SQLException {
r_select.close();
r_insert.close();
r_update.close();
r_delete.close();
r_payer.close();
r_tarifs.close();
connexion.close();
}

/**
* Mise a jour de l'attribut dateCourante.
* @param date la nouvelle date
* @throws ParseException si la nouvelle date n'est pas au bon format
*/
public void setDate(String date) throws ParseException {
dateCourante = new MyDate(date);
}

/**
* Mise a jour de l'attribut idEmp.
* @param emp la nouvelle valeur de l'attribut idEmp
*/
public void setEmp (String emp) {
idEmp = emp;
}

/*************************************************************************
* Requetes de selection
*************************************************************************/

/**
* SELECTION: verification de l'existence du code employe.
* @param id le code employe a verifier
* @return vrai si le code existe dans la base
* @throws SQLException si erreur lors de l'execution de la requete SQL
*/
public boolean verifEmp (String id) throws SQLException {
// A COMPLETER
return false;
}

/**
* SELECTION: liste des restaurants.
* @return un tableau de chaines a 1 dimension qui contient exactement
* les restaurants existants
* @throws SQLException si erreur lors de l'execution de la requete SQL
*/
public String[] requeteResto() throws SQLException {
// A COMPLETER (en adaptant aussi la partie return)
return null;
}

/**
* SELECTION: liste des reservations anterieures a la date courante.
* @return un tableau de chaines a 2 dimensions. Pour n reservations
* passees, le tableau rendu contient n lignes et 3 colonnes
* (une ligne par date); la premiere colonne contient les dates
* (sous forme de chaine), la deuxieme colonne contient le nom d'un
* restaurant, et la troisieme contient le prix du repas
* @throws SQLException si erreur lors de l'execution de la requete SQL
* @throws ParseException si erreur de format
*/
public String[][] requeteConso() throws SQLException, ParseException {
// A COMPLETER (en adaptant aussi la partie return)
return null;
}

/**
* SELECTION: recapitulatif des reservations anterieures a la date courante.
* @return un tableau de chaines. Le tableau rendu contient 3 cases.
* Il contient dans l'ordre le nombre total de reservations passees
* (premiere case), le nombre total de restaurants frequentes (deuxieme
* case) et la somme due (troisieme case).
* ATTENTION: il est fortement conseille de ne pas utiliser le tableau
* de resultat de requeteConso, car ce serait beaucoup plus
* problematique que de tout reecrire.
* En revanche, cette methode s'inspire assez fortement de la methode
* requeteConso, en particulier dans la partie "where" du "select".
* NB: penser au cas ou l'employe n'a pas de reservations passees.
* "sum()" calcule sur une colonne vide ne rend pas "0"
* mais "NULL", contrairement a "count()" qui rend bien "0"
* @throws SQLException si erreur lors de l'execution de la requete SQL
*/
public String[] requeteTotal() throws SQLException {
// A COMPLETER (en adaptant aussi la partie return)
return null;
}

/*************************************************************************
* Requetes de mise a jour
*************************************************************************/

/**
* MAJ: ajout d'un nouveau restaurant.
* Cette methode permet d'ajouter un restaurant.
*
* /!\ ATTENTION /!\
* Une ville ne peut pas avoir plus de 10 restaurants.
* Si 10 restaurants existent deja dans la ville ou se trouve le
* restaurant a ajouter, alors le restaurant n'est pas ajoute.
* Vous devez de-commenter la ligne try{, la ligne catch... et les deux
* suivantes, et taper votre code entre les accolades de try{ et catch...
* @param nomresto nom du restaurant a ajouter (chaine de caracteres).
* @param villeresto nom de la ville du restaurant (chaine de caracteres).
* @param capresto capacite du restaurant (entier).
* @return une chaine de caracteres indiquant un message d'erreur,
* ou NULL si la transaction est correcte
* @throws SQLException si erreur lors de l'execution de la requete SQL
* @throws ParseException si erreur de format
*/
public String addResto (String nomresto, String villeresto, int capresto)
throws SQLException, ParseException {
//try{
// A COMPLETER
// INDICE: ordre des operations:
// 1. Creation d un PreparedStatement pour ajouter un restaurant
// 2. Verrouillage de la table ATTENTION: pour eviter de bloquer votre
// table en cas d'erreur, mettez, dans un premier temps, la ligne
// de verrouillage en commentaire et ne la de-commentez que
// lorsque vous êtes certain.e que tout est ok
// 3. Ajout du restaurant
// si la contrainte de 10 restaurants maximum par ville est bien respectee.
// Attention aussi au cas ou la ville n a encore aucun restaurant
// 4. Fermer le PreparedStatement
return null;
//}catch(SQLException se) {
// return "ERREUR: "+se+", "+nomresto+" n a pas ete ajoute";
//}
}

/*************************************************************************
* Nouvelle requete de selection
*************************************************************************/

/**
* SELECTION: liste des reservations pour les 30 jours a venir.
* @return un tableau de chaines a 2 dimensions: 30 lignes et 2 colonnes
* (une ligne par date); la premiere colonne contient les dates
* (sous forme de chaine), et la seconde contient le nom d'un
* restaurant (en cas de reservation pour cette date) ou la chaine vide
* (non pas NULL)
* @throws SQLException si erreur lors de l'execution de la requete SQL
*/
public String[][] requeteReserv() throws SQLException {
// A COMPLETER (en adaptant aussi la partie return)
return null;
}

/*************************************************************************
* Si vous avez le temps: requetes de mise a jour
*************************************************************************/

/**
* MAJ: reglement des reservations de l'employe anterieures a la date courante.
* Cette methode a pour effet de supprimer les reservations
* passees de l'employe.
* @throws SQLException si erreur lors de l'execution de la requete SQL.
* @throws ParseException si erreur de format.
*/
public void payer() throws SQLException, ParseException {
// NON IMPLEMENTEE
System.out.println("Methode non implementee");
// A COMPLETER SI VOUS LE VOULEZ
}

/**
* MAJ: ajout/modification/suppression de reservations.
* Cette methode permet d'ajouter, de modifier ou de supprimer des
* reservations. Ces trois types mises a jour peuvent potentiellement
* etre effectues simultanement.
*
* /!\ ATTENTION /!\
* On ne tente ici qu'UNE SEULE transaction. S'il y a un probleme,
* la transaction ENTIERE est annulee.
* @param insert tableau (a 2 colonnes) des nouvelles reservations.
* Chaque ligne correspond a un couple (date, restaurant) que vous devrez
* ajouter a la base
* @param update tableau (a 2 colonnes) des modifications de reservations.
* Chaque ligne correspond a un couple (date, restaurant)
* @param delete tableau (a 1 colonne) des suppressions de reservations.
* Chaque element correspond a une date pour laquelle il faut supprimer
* la reservation
* @return une chaine de caracteres indiquant un message d'erreur,
* ou NULL si la transaction est correcte
* @throws SQLException si erreur lors de l'execution de la requete SQL
* @throws ParseException si erreur de format
*/
public String majReserv (String[][] insert, String[][] update, String[] delete)
throws SQLException, ParseException {
// A COMPLETER (en adaptant aussi la partie return)
// INDICE: ordre des operations :
// 1. Verrouillage de la table ATTENTION: pour eviter de bloquer votre
// table en cas d'erreur, mettez, dans un premier temps, la ligne
// de verrouillage en commentaire et ne la de-commentez que
// lorsque vous êtes certain.e que tout est ok
// 2. Mises a jour
// ajouts
// modifications
// suppressions
// 3. Verification des contraintes d'integrite
// contraintes de capacite
// contraintes de categorie
// => connexion.rollback() ou connexion.commit();
return null;
// A COMPLETER SI VOUS LE VOULEZ
}

/**
* MAJ: ajout d'une categorie a un restaurant avec le tarif
* @param cat catégorie (chaine de caracteres).
* @param resto restaurant (chaine de caracteres).
* @param tarifCR tarif associe a la categorie pour ce restaurant (entier).
* @return une chaine de caracteres indiquant un message d'erreur,
* ou NULL si la transaction est correcte
* @throws SQLException si erreur lors de l'execution de la requete SQL
*/
public String addTarif (String cat, String resto, int tarifCR)
throws SQLException, ParseException {
//try {
// A COMPLETER SI VOUS LE VOULEZ
return null;
//}catch(SQLException se) {
// return "ERREUR: "+se+". \nLa categorie "+cat+" n a pas ete ajoutee au restaurant "+resto+".";
//}
}

/*************************************************************************
* MAIN
*************************************************************************/

/**
* Affichage de l'aide.
* @return la chaine de caracteres d'aide
*/
private static String aide () {
return
"ne : connexion comme autre employe\n"+
"nd : nouvelle date courante\n"+
"resto : liste des restaurants\n"+
"reserv: liste des reservations (pour les 30 prochains jours)\n"+
"conso : liste des reservations anterieures a la date courante\n"+
"total : bilan total des reservations anterieures a la date courante\n"+
"nr : ajout d un nouveau restaurant\n"+
"tarif : ajout d une categorie a un restaurant avec le tarif associe \n"+
"maj : ajouts, modifications et suppressions de reservations futures\n"+
"pay : paiement des reservations anterieures a la date courante\n"+
"q : sortie\n";
}

/**
* Affichage d'un tableau en 1D.
* @param t1 le tableau a afficher
*/
private static void afftab1 (String[] t1) {
for (int i=0; i<t1.length; i++)
System.out.print("| "+t1[i]+" ");
System.out.println("|");
}

/**
* Affichage d'un tableau en 2D.
* @param t2 le tableau a afficher
*/
private static void afftab2 (String[][] t2) {
for (int i=0; i<t2.length; i++) afftab1 (t2[i]);
}

/**
* Acquisition et verification du code employe.
* @param client la connexion a la base de donnees
* @return le code de l'employe
* @throws SQLException si erreur lors de l'execution de la requete SQL
*/
private static String saisieEmp (RestoConnexion client)
throws SQLException {
while (true) {
String code = acquisition ("Code employe");
if (client.verifEmp(code)) return code;
}
}

/**
* Fonction principale du programme.
* @param args
*/
public static void main (String args[]) {
try {
// Connexion
RestoConnexion client =
new RestoConnexion (acquisition ("Utilisateur"),
acquisition ("Mot de passe"));
client.setEmp(saisieEmp(client));
client.setDate (acquisition ("Date courante"));

// Menu
String choix = "";
while(!choix.equals("q")) {
choix = acquisition ("*** MENU (?: aide) ***");
if (choix.equals("?"))
System.out.print(aide());
else if (choix.equals("ne"))
client.setEmp(saisieEmp (client));
else if (choix.equals("nd"))
client.setDate (acquisition ("Date courante"));
else if (choix.equals("resto"))
afftab1 (client.requeteResto());
else if (choix.equals("reserv"))
afftab2(client.requeteReserv());
else if (choix.equals("conso"))
afftab2(client.requeteConso());
else if (choix.equals("total"))
afftab1 (client.requeteTotal());
else if (choix.equals("nr")) {
String nomresto = acquisition("Nom du nouveau restaurant");
String villeresto = acquisition("Ville du nouveau restaurant");
int capresto = Integer.parseInt(acquisition("Capacite du nouveau restaurant"));
String res = client.addResto (nomresto, villeresto, capresto);
System.out.println(res==null? "OK": res);
}
else if (choix.equals("tarif")) {
String cat = acquisition("Categorie");
String resto = acquisition("Restaurant");
int tarifCR = Integer.parseInt(acquisition("Tarif pour cette catégorie dans ce restaurant"));
String res = client.addTarif (cat, resto, tarifCR);
System.out.println(res==null? "OK" : res);
}
else if (choix.equals("maj")) {
int nbins;
try { nbins = Integer.parseInt(acquisition ("nombre d'ajouts")); }
catch (Exception e) { nbins = 0; };
String[][] ins = new String [nbins] [2];
for (int i=0; i<nbins; i++) {
ins [i] [0] = acquisition ("INSERT "+(i+1)+" date");
ins [i] [1] = acquisition ("INSERT "+(i+1)+" resto");
}
int nbmod;
try { nbmod = Integer.parseInt(acquisition ("nombre de modifications")); }
catch (Exception e) { nbmod = 0; };
String[][] mod = new String [nbmod] [2];
for (int i=0; i<nbmod; i++) {
mod [i] [0] = acquisition ("UPDATE "+(i+1)+" date");
mod [i] [1] = acquisition ("UPDATE "+(i+1)+" resto");
}
int nbsup;
try { nbsup = Integer.parseInt(acquisition ("nombre de suppressions")); }
catch (Exception e) { nbsup = 0; };
String[] sup = new String [nbsup];
for (int i=0; i<nbsup; i++)
sup[i] = acquisition ("DELETE "+(i+1)+" date");
String res = client.majReserv (ins, mod, sup);
System.out.println(res==null? "Ok": res);
}
else if (choix.equals("pay")) {
client.payer();
System.out.println("Ok");
}
}
client.terminer();
}
catch (Exception e) {
System.out.println(e);
try{
// Attendre 10 secondes
Thread.sleep(10000);
}catch (InterruptedException e2){
}
}
}
}