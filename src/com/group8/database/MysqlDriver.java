package com.group8.database;

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
 * Class that contains all functionality for connecting to the database.
 *
 * Contains methods for selecting and inserting into the database.
 */

public class MysqlDriver {

    public void MysqlDriver() {}

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

            for(int i = 1; i<=metaData.getColumnCount(); i++)
            {
                // Generic data setter
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
    /*
     Select multiple rows and return an array of objects
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

            while(rs.next())
            {
                ArrayList<Object> row = new ArrayList<>();

                for(int i = 0; i<=metaData.getColumnCount()-1; i++) {
                    row.add(null);
                    // test output
                   // System.out.println("type name-->"+metaData.getColumnTypeName(i+1));
                }

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

    /*
   Select multiple rows and return an array of objects
   */
    public static ArrayList<ArrayList<Object>> selectManyOther(String query) {

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

            while(rs.next())
            {
                ArrayList<Object> row = new ArrayList<>();

                for(int i = 1; i<=metaData.getColumnCount(); i++)
                {
                        row.add(rs.getObject(i));
                }

                result.add(row);
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
}