package com.example.dim.wineroom.data;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.example.dim.wineroom.data.entities.Cellar;
import com.example.dim.wineroom.data.entities.Grape;
import com.example.dim.wineroom.data.entities.Grower;
import com.example.dim.wineroom.data.entities.User;
import com.example.dim.wineroom.data.entities.Wine;

import java.util.ArrayList;
import java.util.List;

public class ApplicationModel {
        private static ApplicationModel model;
        private DbHelper database;

        private SparseArray<Grape> grapesModel;
        private ArrayList<User> usersModel;
        private ArrayList<Grower> growersModel;
        private ArrayList<Wine> winesModel;
        private ArrayList<Cellar> cellarModel;

        public ApplicationModel(Context context) {
            database = DbHelper.getInstance(context);
            // load all lists as Model
            // make class model

            if (database.getAllGrapes().isEmpty()) {
                populateDb(database);
            }
            grapesModel = new SparseArray<>();
            for (Grape grape :
                    database.getAllGrapes()) {
                grape.setNumber(0);
                grapesModel.put(grape.getId(), grape);
            }
            usersModel = new ArrayList<>(database.getAllUsers());
            growersModel = new ArrayList<>(database.getAllGrowers());
            winesModel = new ArrayList<>(database.getAllWines());
            cellarModel = new ArrayList<>(database.getAllCellars());

            printDatabaseContent(database);
            //database.closeDB();
        }

        public static ApplicationModel getInstance(Context context) {
            if (model == null) {
                model = new ApplicationModel(context);
            }
            return model;
        }

        public void close() {
            model.close();
        }

        private void printDatabaseContent(DbHelper database) {
            for (Grape grape : database.getAllGrapes()) {
                Log.i("WELCOME_WINE_TEST", "onCreate: "+grape.toString());
            }

            for (User user : database.getAllUsers()) {
                Log.i("WELCOME_WINE_TEST", "onCreate: "+user.toString());
            }

            for (Grower grower : database.getAllGrowers()) {
                Log.i("WELCOME_WINE_TEST", "onCreate: "+grower.toString());
            }

            for (Wine wine : database.getAllWines()) {
                Log.i("WELCOME_WINE_TEST", "onCreate: "+wine.toString());
            }

            for (Cellar cellar : database.getAllCellars()) {
                Log.i("WELCOME_WINE", "onCreate : "+cellar.toString());
            }
        }

        private void populateDb(DbHelper database) {
            // adding dictionnaries data for grape/user/grower entities
            database.insertGrape(new Grape("Rouge"));
            database.insertGrape(new Grape("Blanc"));
            database.insertGrape(new Grape("RosÃ©"));
    /*     database.insertUser(new User("Alice", "Azerty", "a.a@company.com"));
        database.insertUser(new User("Bobby", "Ytreza", "bobby@item.org.fr"));
        database.insertGrower(new Grower("Wine Grower Bordeaux", "13 road Saint albert","", "","SinCity","83564 CEDEX 21 RG 52232","0000000000", "grower.1@mail.com"));
        database.insertGrower(new Grower("Some funny guy CostaRica", "road ahead of the main street's \"garry's\" restaurant","Costa Rica", "","SanJose","87250","1111111111", "amail@mail.cr"));
*/
            // adding wine referencing foreignKeys
       /* Grape g = database.getGrapeFromId(1);
        Grower gr = database.getGrowerFromId(2);
        database.insertWine(new Wine("bojolait", "domaine", "2012-01-12", g, gr));
*/
            // adding wine with new Grower !
       /* Grape g1 = database.getGrapeFromId(3);
        Grower gr1 = new Grower("Zemir", "1 rue de la marÃ©e", "chemin du terter", "", "SinCity", "87130", "1234567890", "e-mail.mail@email.ong");
        Log.i("WELCOME_WINE", "populateDb: Grower without id new id : "+gr1.getId());
        database.insertWine(new Wine("Cabernet d\'anjou", "domaine", "2013-01-12", g1, gr1));
*/
            // adding cellar referencing foreignKeys
       /* Wine w = database.getWineFromId(1);
        User u = database.getUserFromId(1);
        Cellar c = new Cellar(w, u, 10);
        database.insertCellar(c);
*/
            // adding cellar with new User
       /* Wine w1 = database.getWineFromId(1);
        User u1 = new User("utilisateur", "sansnom", "mail.mail.mail-mail.mail@dmail.gr");
        Cellar c1 = new Cellar(w1, u1, 5);
        database.insertCellar(c1);*/

        }

        public SparseArray<Grape> getGrapesModel() {
            return grapesModel;
        }

        public List<User> getUsersModel() {
            return usersModel;
        }

        public List<Grower> getGrowersModel() {
            return growersModel;
        }

        public List<Wine> getWinesModel() {
            return winesModel;
        }

        public List<Cellar> getCellarModel() {
            return cellarModel;
        }

    public DbHelper getDatabase() {
        return database;
    }
}
