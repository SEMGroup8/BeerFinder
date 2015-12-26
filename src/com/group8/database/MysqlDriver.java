package com.group8.database;

import com.group8.controllers.BeerData;
import com.mysql.jdbc.MysqlParameterMetadata;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Linus Eiderström Swahn.
 *
 * Class that contains all functionality for connecting to the database.
 *
 * Contains methods for selecting and inserting into the database.
 */

public class MysqlDriver {

    public void MysqlDriver() {}

    /**
     * Created By Linus Eiderström Swahn
     *
     * Selects one item from the database and returns it as an arraylist of objects.
     * @param query
     * A string with the sql-query that selects from the database.
     * @return
     * An arraylist of objects that contains al collumns of one row in the database.
     */
    public static ArrayList<Object> select(String query) {

        ArrayList<Object> result = new ArrayList<>();

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://sql.smallwhitebird.com:3306/beerfinder";
        String user = "Gr8";
        String password = "group8";

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();

            //If nothing was found.
            if(!rs.next())
            {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (st != null) {
                        st.close();
                    }
                    if (con != null) {
                        con.close();
                    }

                } catch (SQLException ex) {
                    Logger lgr = Logger.getLogger(MysqlDriver.class.getName());
                    lgr.log(Level.WARNING, ex.getMessage(), ex);
                }

                return null;
            }

            result = new ArrayList<>();

            // Loops through the columns.
            for(int i = 1; i<=metaData.getColumnCount(); i++)
            {
                // Special case to check if a column is an image.
                if(metaData.getColumnTypeName(i).equals("MEDIUMBLOB")){
                    InputStream image =rs.getBinaryStream(i);
                    result.add(image);

                }else{
                    result.add(rs.getObject(i));
                }

            }


        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(MysqlDriver.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(MysqlDriver.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }

        return result;
    }

    /**
     * Created by Linus Eiderström Swahn
     *
     * Returns several rows from the database as a two-dimensional array of objects.
     * @param query
     * The query that selects one or more rows from the database.
     * @return
     * A two-dimensional arraylist of objects representing rows of data from the database.
     */
    public static ArrayList<ArrayList<Object>> selectMany(String query) {

        ArrayList<ArrayList <Object>> result = new ArrayList<>();

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://sql.smallwhitebird.com:3306/beerfinder";
        String user = "Gr8";
        String password = "group8";

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery(query);


            ResultSetMetaData metaData = rs.getMetaData();

            //Loops through each returned row.
            while(rs.next())
            {
                ArrayList<Object> row = new ArrayList<>();

                // Initialize all indexes we will use in the arraylist.
                for(int i = 0; i<=metaData.getColumnCount()-1; i++) {
                    row.add(null);
                }

                // Check if the data is an image or not.
                for(int i = 1; i<=metaData.getColumnCount(); i++)
                {
                    // Generic data setter
                    if(metaData.getColumnTypeName(i).equals("MEDIUMBLOB")){
                        InputStream image =rs.getBinaryStream(i);
                        row.set(i-1,image);
                    }else{

                        row.set(i-1,rs.getObject(i));
                    }

                }

                result.add(row);
                System.out.println(row.toString());
            }



        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(MysqlDriver.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);


        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(MysqlDriver.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        System.out.println(result);

        return result;
    }

    /**
     * Created by Linus Eiderström Swahn
     *
     * Inserts data into the database based on a sql-query.
     *
     * @param query
     * The String containing a sql-query for inserting into the database.
     */
    public static void insert(String query) {
        Connection con = null;
        Statement st = null;

        String url = "jdbc:mysql://sql.smallwhitebird.com:3306/beerfinder";
        String user = "Gr8";
        String password = "group8";

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            st.executeUpdate(query);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(MysqlDriver.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(MysqlDriver.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    /**
     * Created by Linus Ederström Swahn
     *
     * Updates data in the database based on a sql-query.
     *
     * @param query
     * A string with the sql-query that updates the database.
     */
    public static void update(String query) {
        Connection con = null;
        Statement st = null;

        String url = "jdbc:mysql://sql.smallwhitebird.com:3306/beerfinder";
        String user = "Gr8";
        String password = "group8";

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            st.executeUpdate(query);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(MysqlDriver.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(MysqlDriver.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}