package com.example.quizz;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class databaseManager {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USERNAME = "quizz";
    private static final String PASSWORD = "quizz";
    private static Connection connection;

    //**************METHODE POUR ETABLIR UNE CONNEXION A LA BASE DE DONNEE***************

    public static PreparedStatement connect() throws SQLException {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        if (connection != null) {
            System.out.println("Connexion à la base de données Oracle établie avec succès.");
        } else {
            System.out.println("Échec de la connexion à la base de données Oracle.");
        }
        return null;
    }

    //*****METHODE POUR FERMER LA CONNEXION A LA BASE DE DONNEE*********

    public static void close() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("Connexion à la base de données Oracle fermée avec succès.");
        }
    }

    //****METHODE POUR VERIFIER LES INFORMATIONS D'IDENTIFICATION DE L'ADMIN******

    public static boolean verifierAdmin(String mail, String motpasse) throws SQLException {
        if (connection == null) {
            connect();
        }

        String sql = "SELECT * FROM ADMIN WHERE MAIL= ? AND MOTPASSE = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, mail);
        statement.setString(2, motpasse);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            System.out.println("Authentification réussie");
            return true;
        } else {
            System.out.println("Mail ou mot de passe incorrect !!");
            return false;
        }
    }

    //****METHODE POUR VERIFIER LES INFORMATIONS D'IDENTIFICATION DE L'ETUDIANT******

    public static boolean verifierEtudiant(String mail, String motpasse) throws SQLException {
        if (connection == null) {
            connect();
        }

        String sql = "SELECT * FROM ETUDIANT WHERE MAIL= ? AND MOTPASSE = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, mail);
        statement.setString(2, motpasse);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            System.out.println("Authentification réussie");
            return true;
        } else {
            System.out.println("Mail ou mot de passe incorrect !!");
            return false;
        }
    }

    //****METHODE POUR VERIFIER LES INFORMATIONS D'IDENTIFICATION DE L'ENSEIGNANT******

    public static boolean verifierEnseignant(String mail, String motpasse) throws SQLException {
        if (connection == null) {
            connect();
        }

        String sql = "SELECT * FROM ENSEIGNANT WHERE MAIL= ? AND MOTPASSE = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, mail);
        statement.setString(2, motpasse);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            System.out.println("Authentification réussie");
            return true;
        } else {
            System.out.println("Mail ou mot de passe incorrect !!");
            return false;
        }
    }


    //******METHODE POUR INSERER UN ETUDIANT DANS LA BASE DE DONNEE************

    public static boolean insertEtudiant(String cin, String nom, String prenom, String email, String password, String niveau) throws SQLException {
        if (connection == null) {
            connect();
        }

        String sql = "INSERT INTO ETUDIANT (CIN, NOM, PRENOM, MAIL, MOTPASSE, NIVEAU) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cin);
        statement.setString(2, nom);
        statement.setString(3, prenom);
        statement.setString(4, email);
        statement.setString(5, password);
        statement.setString(6, niveau);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Insertion réussie");
            return true;
        } else {
            System.out.println("Échec de l'insertion");
            return false;
        }

    }

    //******METHODE POUR INSERER UN ENSEIGNANT DANS LA BASE DE DONNEE************

    public static boolean insertEnseignant(String cin, String nom, String prenom, String email, String password) throws SQLException {
        if (connection == null) {
            connect();
        }

        String req = "INSERT INTO enseignant (CIN, NOM, PRENOM, MAIL, MOTPASSE) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement2 = connection.prepareStatement(req);
        statement2.setString(1, cin);
        statement2.setString(2, nom);
        statement2.setString(3, prenom);
        statement2.setString(4, email);
        statement2.setString(5, password);

        int rowsAffected2 = statement2.executeUpdate();
        if (rowsAffected2 > 0) {
            System.out.println("Insertion réussie");
            return true;
        } else {
            System.out.println("Échec de l'insertion");
            return false;
        }
    }


     //-------------------------------------------------------------------------------//
    //*******************CRUD ETUDIANT************************************************//
    // METHODE POUR METTRE TOUS LES ETUDIANTS  DANS LE TABLEVIEW QUI SONT DEJA EXISTE DANS LA BASE DE DONNEE

    public ObservableList<Etudiant> getTousEtudiants() throws SQLException {
        ObservableList<Etudiant> studentsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM ETUDIANT";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String cin = resultSet.getString("CIN");
            String nom = resultSet.getString("NOM");
            String prenom = resultSet.getString("PRENOM");
            String mail = resultSet.getString("MAIL");
            String motpasse = resultSet.getString("MOTPASSE");
            String niveau = resultSet.getString("NIVEAU");
            Etudiant student = new Etudiant(cin, nom, prenom, mail, motpasse, niveau);
            studentsList.add(student);
        }
        return studentsList;
    }

    //***********METHODE POUR MOFIFIER UN ETUDIANT***********************//

    public void modifierEtudiant(Etudiant etudiant) throws SQLException {
        if (connection == null) {
            connect();
        }

        String sql = "UPDATE ETUDIANT SET NOM = ?, PRENOM = ?, MAIL = ?, MOTPASSE = ?, NIVEAU = ? WHERE CIN = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, etudiant.getNom());
            statement.setString(2, etudiant.getPrenom());
            statement.setString(3, etudiant.getMail());
            statement.setString(4, etudiant.getMotpasse());
            statement.setString(5, etudiant.getNiveau());
            statement.setString(6, etudiant.getCin());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Etudiant updated successfully.");
            } else {
                System.out.println("Failed to update Etudiant.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //*******************METHODE POUR SUPPRIMER UN ETUDIANT**************************//

    public void deleteEtudiant(Etudiant etudiant) throws SQLException {
        if (connection == null) {
            connect();
        }
        String sql = "DELETE FROM ETUDIANT WHERE CIN = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, etudiant.getCin());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Etudiant deleted successfully.");
            } else {
                System.out.println("Failed to delete Etudiant.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //*********METHODE POUR CREER UN NOUVEAU ETUDIANT*************************//

    public void CreerEtudiant(Etudiant newEtudiant) throws SQLException {
        if (connection == null) {
            connect();
        }

        String sql = "INSERT INTO ETUDIANT (CIN, NOM, PRENOM, MAIL, MOTPASSE, NIVEAU) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newEtudiant.getCin());
            statement.setString(2, newEtudiant.getNom());
            statement.setString(3, newEtudiant.getPrenom());
            statement.setString(4, newEtudiant.getMail());
            statement.setString(5, newEtudiant.getMotpasse());
            statement.setString(6, newEtudiant.getNiveau());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Etudiant inserted successfully.");
            } else {
                System.out.println("Failed to insert Etudiant.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //-----------------------------------------------------------------------------------------------//
    //****************************CRUD ENSEIGNANT***************************************************

    //METHODE POUR METTRE TOUS LES ENSEIGNANT DANS LE TABLEVIEW QUI SONT DEJA EXISTE DANS LA BASE DE DONNEE

    public ObservableList<Enseignant> getTousEnseigant() throws SQLException {
        ObservableList<Enseignant> enseignantsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM ENSEIGNANT";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String cin = resultSet.getString("CIN");
            String nom = resultSet.getString("NOM");
            String prenom = resultSet.getString("PRENOM");
            String mail = resultSet.getString("MAIL");
            String motpasse = resultSet.getString("MOTPASSE");
            Enseignant enseignant = new Enseignant(cin, nom, prenom, mail, motpasse);
            enseignantsList.add(enseignant);
        }
        return enseignantsList;
    }

    //***********METHODE POUR MOFIFIER UN ENSEIGNANT***********************//

    public void modifierEnseignant(Enseignant enseignant) throws SQLException {
        if (connection == null) {
            connect();
        }
        String sql = "UPDATE ENSEIGNANT SET NOM = ?, PRENOM = ?, MAIL = ?, MOTPASSE = ? WHERE CIN = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, enseignant.getNom());
            statement.setString(2, enseignant.getPrenom());
            statement.setString(3, enseignant.getMail());
            statement.setString(4, enseignant.getMotpasse());
            statement.setString(5, enseignant.getCin());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Enseignant updated successfully.");
            } else {
                System.out.println("Failed to update Enseignant.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } /*finally {
            close();
        }*/
    }

    //*******************METHODE POUR SUPPRIMER UN ENSEIGNANT**************************
    public void deleteEnseignant(Enseignant enseignant) throws SQLException {
        if (connection == null) {
            connect();
        }
        String sql = "DELETE FROM ENSEIGNANT WHERE CIN = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, enseignant.getCin());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Enseignant deleted successfully.");
            } else {
                System.out.println("Failed to delete Enseignant.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    //*********METHODE POUR CREER UN NOUVEAU ENSEIGNANT***************

    public void CreerEnseignant(Enseignant newEnseignant) throws SQLException {
        if (connection == null) {
            connect();
        }
        String sql = "INSERT INTO ENSEIGNANT(CIN, NOM, PRENOM, MAIL, MOTPASSE) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newEnseignant.getCin());
            statement.setString(2, newEnseignant.getNom());
            statement.setString(3, newEnseignant.getPrenom());
            statement.setString(4, newEnseignant.getMail());
            statement.setString(5, newEnseignant.getMotpasse());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Enseignant inserted successfully.");
            } else {
                System.out.println("Failed to insert Enseignant.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


//----------------------------------------------------------------------------//
//*******************CRUD COURS***********************************************//

//*********METHODE POUR CREER UN NOUVEAU COURS***************

    public static void CreerCours(Cours newCours) throws SQLException {
        if (connection == null) {
            connect();
        }
        String sql = "INSERT INTO COURS(IDCOURS, NOMCOURS, NIVEAU) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newCours.getidCours());
            statement.setString(2, newCours.getnomCours());
            statement.setString(3, newCours.getNiveau());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Cours inserted successfully.");
            } else {
                System.out.println("Failed to insert Cours.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

//METHODE POUR METTRE TOUS LES COURS  DANS LE TABLEVIEW QUI SONT DEJA EXISTE DANS LA BASE DE DONNE

    public ObservableList<Cours> getTousCours() throws SQLException {
        ObservableList<Cours> coursList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM COURS";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Integer idCours = resultSet.getInt("IDCOURS");
            String nomCours = resultSet.getString("NOMCOURS");
            String niveau = resultSet.getString("NIVEAU");
            Cours cours = new Cours(idCours, nomCours, niveau);
            coursList.add(cours);
        }
        return coursList;
    }


    //*******************METHODE POUR SUPPRIMER UN COURS**************************

    public void deleteCours(Cours cours) throws SQLException {
        if (connection == null) {
            connect();
        }
        String sql = "DELETE FROM COURS WHERE IDCOURS = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cours.getidCours());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cours deleted successfully.");
            } else {
                System.out.println("Failed to delete Cours.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } /*finally {
            close();
        }*/
    }

    //***********METHODE POUR MOFIFIER UN COURS***********************//

    public void modifierCours(Cours cours) throws SQLException {
        if (connection == null) {
            connect();
        }
        String sql = "UPDATE COURS SET NOMCOURS = ?, NIVEAU= ? WHERE IDCOURS = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cours.getnomCours());
            statement.setString(2, cours.getNiveau());
            statement.setInt(3, cours.getidCours());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cours updated successfully.");
            } else {
                System.out.println("Failed to update Cours.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // -------------------------------------------------------------------------------------------------//
    //****************************CRUD GROUPE************************************************

    //***********METHODE POUR MOFIFIER UN GROUPE***********************//

    public void modifierGroupe(Groupe groupe) throws SQLException {
        if (connection == null) {
            connect();
        }
        String sql = "UPDATE GROUPE SET NOMGROUPE = ? WHERE IDGROUPE = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, groupe.getnomGroupe());
            statement.setInt(2, groupe.getidGroupe());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Groupe updated successfully.");
            } else {
                System.out.println("Failed to update Groupe.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //*******************METHODE POUR SUPPRIMER UN GROUPE**************************

    public void deleteGroupe(Groupe groupe) throws SQLException {
        if (connection == null) {
            connect();
        }
        String sql = "DELETE FROM GROUPE WHERE IDGROUPE = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, groupe.getidGroupe());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Groupe deleted successfully.");
            } else {
                System.out.println("Failed to delete Groupe.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //*********METHODE POUR CREER UN NOUVEAU GROUPE***************

    public static void CreerGroupe(Groupe newGroupe) throws SQLException {
        if (connection == null) {
            connect();
        }
        String sql = "INSERT INTO GROUPE(IDGROUPE, NOMGROUPE) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newGroupe.getidGroupe());
            statement.setString(2, newGroupe.getnomGroupe());


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Groupe inserted successfully.");
            } else {
                System.out.println("Failed to insert Groupe.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

//METHODE POUR METTRE TOUS LES GROUPES  DANS LE TABLEVIEW QUI SONT DEJA EXISTE DANS LA BASE DE DONNE

    public ObservableList<Groupe> getTousGroupes() throws SQLException {
        ObservableList<Groupe> groupeList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM GROUPE";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Integer idGroupe = resultSet.getInt("IDGROUPE");
            String nomGroupe = resultSet.getString("NOMGROUPE");
            Groupe groupe = new Groupe(idGroupe, nomGroupe);
            groupeList.add(groupe);
        }
        return groupeList;
    }


    // -------------------------------------------------------------------------------------------------//
//****************************CRUD QUESTION************************************************
//Method to insert data into the question table
    public static int insertQuestion(String enonce, String niveau,String cours) throws SQLException {
        if (connection == null) {
            connect();
        }

        String sql = "INSERT INTO question (enonce, niveau,cours) VALUES (?, ?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, enonce);
            statement.setString(2, niveau);
            statement.setString(3, cours);


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Question inserted successfully.");
            } else {
                System.out.println("Failed to insert Question.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return 0;
    }

    public static Integer getLastInsertedQuestionId() throws SQLException {
        if (connection == null) {
            connect();
        }
        Integer id = null;
        String query = "SELECT max(id) AS max_id FROM question ";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                id = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(id);
        return id;
    }
    // Method to insert data into the Reponses table
    public static void insertReponses(String reponces, String etat, int id) throws SQLException {
        if (connection == null) {
            connect();
        }
        String sql = "INSERT INTO reponces (Reponces, etat, id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, reponces);
            statement.setString(2, etat);
            statement.setInt(3, id);
            statement.executeUpdate();
            System.out.println("Reponse inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    //--------------------------------------------------------------------------//
    //*******************CRUD question*******************************************

//METHODE POUR METTRE TOUS LES question  DANS LE TABLEVIEW QUI SONT DEJA EXISTE DANS LA BASE DE DONNEE

    public ObservableList<Question> getAllQuestions() throws SQLException {
        ObservableList<Question> questionList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM question ";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Integer id = resultSet.getInt("ID");
            String enonce = resultSet.getString("ENONCE");
            String niveau = resultSet.getString("NIVEAU");
            String cours = resultSet.getString("COURS");
            Question question = new Question(id, enonce, niveau,cours);
            questionList.add(question);
        }
        return questionList;
    }

//*******************METHODE POUR SUPPRIMER UN QUESTION**************************

    public void deleteQuestion(Question question) throws SQLException {
        if (connection == null) {
            connect();
        }
        String sql = "DELETE FROM QUESTION WHERE ID= ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, question.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Question deleted successfully.");
            } else {
                System.out.println("Failed to delete Question.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //***********METHODE POUR MOFIFIER UN QUESTION***********************//

    public void modifierQuestion(Question question) throws SQLException {
        if (connection == null) {
            connect();
        }
        String sql = "UPDATE QUESTION SET ENONCE = ? , NIVEAU= ?, COURS= ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, question.getEnonce());
            statement.setString(2, question.getNiveau());
            statement.setString(3, question.getCours());
            statement.setInt(4, question.getId());


            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Question updated successfully.");
            } else {
                System.out.println("Failed to update Question.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//**********************************************************************************************/
    //*****************gestion des quizz******************************************************/

public ObservableList<Question> getQuestions(String selectMatiere, String selectedNiveauId) throws SQLException {
    if (connection == null) {
        connect();
    }

    ObservableList<Question> questionList = FXCollections.observableArrayList();
    String sql = "SELECT enonce FROM question WHERE cours = ? AND niveau = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setString(1, selectMatiere);
    statement.setString(2, selectedNiveauId);
    ResultSet resultSet = statement.executeQuery();
    while (resultSet.next()) {
        String enonce = resultSet.getString("ENONCE");
        Question question = new Question(enonce);
        questionList.add(question);
    }
    return questionList;
}

//*************************QUIZZZZZZ**************************************************/

    public static int insertQuizz(String matiere, String niveau, int nbQuestion) throws SQLException {
        if (connection == null) {
            connect();
        }

        // SQL statement for inserting data with auto-incrementing ID
        String sql = "INSERT INTO quizz (matiere, niveau, nbquestion, created_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, matiere);
            statement.setString(2, niveau);
            statement.setInt(3, nbQuestion);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Quizz inserted successfully.");
            } else {
                System.out.println("Failed to insert Quizz.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return nbQuestion;
    }



    // Example method to get all quizzes from the database
    public static ObservableList<Quizz> getAllQuizzes() throws SQLException {
        if (connection == null) {
            connect();
        }

        ObservableList<Quizz> quizzes = FXCollections.observableArrayList();
        String sql = "SELECT  * FROM quizz";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Integer idQuizz = resultSet.getInt("idQuiz");
                String matiere = resultSet.getString("matiere");
                String niveau = resultSet.getString("niveau");
                Integer nbQuestion = resultSet.getInt("nbQuestion");
              // String created_at = resultSet.getString("created_at");

                Quizz quizz=new Quizz(idQuizz, matiere, niveau, nbQuestion);
                quizzes.add(quizz);
            }
        }

        return quizzes;
    }


  //**************************************************************************************/
  // Method to retrieve questions for a given quiz
  public static List<Question> getQuestionsForQuizz(int idQuiz) throws SQLException {
      List<Question> questions = new ArrayList<>();
      String query ="SELECT q.enonce " +
              "FROM Quizz_Question qq " +
              "JOIN Question q ON qq.Id = q.id " +
              "WHERE qq.idQuiz = ?";
      try (PreparedStatement statement = connection.prepareStatement(query)) {
          statement.setInt(1, idQuiz);
          ResultSet resultSet = statement.executeQuery();

          while (resultSet.next()) {
              int id = resultSet.getInt("id");
              String enonce = resultSet.getString("enonce");
              String niveau = resultSet.getString("niveau");
              String cours = resultSet.getString("cours");

              questions.add(new Question(id, enonce, niveau, cours));
          }
      }

      return questions;
  }

    public static List<Reponces> getReponsesForQuestions(List<Question> questions) throws SQLException {
        List<Reponces> reponcesList = new ArrayList<>();

        String query = "SELECT idReponces, reponces, etat,id FROM reponces WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Question question : questions) {
                statement.setInt(1, question.getId());
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int idReponces = resultSet.getInt("idReponces");
                    String reponces = resultSet.getString("reponces");
                    String etat = resultSet.getString("etat");
                    int id = resultSet.getInt("id");

                    reponcesList.add(new Reponces(idReponces, reponces, etat));
                }
            }
        }

        return reponcesList;
    }




}









