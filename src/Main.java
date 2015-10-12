import java.util.ArrayList;
import java.util.Scanner;
import com.group8.database.*;

import java.sql.*;


/**
 * Created by Shiratori on 12/10/15.
 */
public class Main {

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        ArrayList<ArrayList> sqlData = new ArrayList<>();

        sqlData = MysqlDriver.selectMany("Select * from pubs");

       for(int i = 0; i < sqlData.size(); i++)
        {
            ArrayList<Object> row = sqlData.get(i);

            for(int j = 0; j<row.size(); j++)
            {
                System.out.println(row.get(j).toString());
            }
        }
    }
}
