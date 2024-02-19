import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.opencsv.CSVReader;
import java.sql.SQLIntegrityConstraintViolationException;

public class Netflix {
    public static void main(String[] args) throws Exception {
        // Establish a connection to the MySQL database
        String url = "jdbc:mysql://localhost:3306/Netflix";
        String user = "root";
        String password = "$$";
        Connection conn = DriverManager.getConnection(url, user, password);

        // Read the CSV file using OpenCSV
        CSVReader reader = new CSVReader(new FileReader("C:/Users/nidhi/Downloads/netflix_project.csv"));

        // Skip the header row
        String[] headerRow = reader.readNext();

        // Insert each row of data into the MySQL table using JDBC
        String[] row;
        String sql;

        // Insert data into the "Director" table
         sql = "INSERT INTO Director (Director_ID, Director_Name) VALUES (?, ?)";
        PreparedStatement statement1 = conn.prepareStatement(sql);
        while ((row = reader.readNext()) != null) {
            int directorID = Integer.parseInt(row[0]);
            String directorName = row[1];
            statement1.setInt(1, directorID);
            statement1.setString(2, directorName);


            try {
                statement1.executeUpdate();
            } catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    System.out.println("Ignoring duplicate key violation for director ID " + directorID);
                } else {
                    throw e;
                }
            }
        }

        // Insert data into the "Genre" table
        sql = "INSERT INTO are_given_to (Genre_Id, Genre_Name) VALUES (?, ?)";
        PreparedStatement statement2 = conn.prepareStatement(sql);
        while ((row = reader.readNext()) != null) {
            int Genre_Id = Integer.parseInt(row[10]);
            String Genre_Name = row[8];
            statement2.setInt(1, Genre_Id);
            statement2.setString(2, Genre_Name);

            try {
                statement2.executeUpdate();
            } catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    System.out.println("Ignoring duplicate key violation for Genre Id " + Genre_Id);
                    System.out.println("Ignoring duplicate key violation for Genre Name " + Genre_Name);
                } else {
                    throw e;
                }
            }
        }

        sql = "INSERT INTO Awards (Award_ID, Award_Name, Year_of_Award, Actor_ID) VALUES (?, ?, ?, ?)";
        PreparedStatement statement3 = conn.prepareStatement(sql);

        while ((row = reader.readNext()) != null) {
            int Award_ID = Integer.parseInt(row[16]);
            String Award_Name = row[17];
            int Year_of_Award = Integer.parseInt(row[15]);
            int Actor_ID = Integer.parseInt(row[12]);
            statement3.setInt(1, Award_ID);
            statement3.setString(2, Award_Name);
            statement3.setInt(3, Year_of_Award);
            statement3.setInt(4, Actor_ID);


            try {
                statement3.executeUpdate();
            } catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    System.out.println("Ignoring duplicate key violation for Show ID " + Award_ID);
                    System.out.println("Ignoring duplicate key violation for Show ID " + Award_Name);
                    System.out.println("Ignoring duplicate key violation for Actor ID " + Year_of_Award);

                } else {
                    throw e;
                }
            }
        }



        // Insert data into the "Rating" table

        sql = "INSERT INTO Rating (Rating_ID, Rating, Source) VALUES (?, ?, ?)";
        PreparedStatement statement4 = conn.prepareStatement(sql);
        while ((row = reader.readNext()) != null) {
            int Rating_ID = Integer.parseInt(row[13]);
            String Rating = row[6];
            String Source = row[14];
            statement4.setInt(1, Rating_ID);
            statement4.setString(2, Rating);
            statement4.setString(3,Source);


            try {
                statement4.executeUpdate();
            } catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    System.out.println("Ignoring duplicate key violation for Rating ID " + Rating_ID);
                    System.out.println("Ignoring duplicate key violation for Rating" + Rating);
                    System.out.println("Ignoring duplicate key violation for Source " + Source);
                } else {
                    throw e;
                }
            }
        }




        // Insert data into the "Shows" table
        sql = "INSERT INTO Shows (Show_ID, Show_Name, Show_Type, Genre_ID, Rating_ID, Director_ID, Release_Year, Duration, Description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? )";
        PreparedStatement statement5 = conn.prepareStatement(sql);
        while ((row = reader.readNext()) != null) {
            int Show_ID = Integer.parseInt(row[2]);
            String Show_Name = row[4];
            String Show_Type = row[3];
            int Genre_ID = Integer.parseInt(row[10]);
            int Rating_ID = Integer.parseInt(row[13]);
            int Director_ID = Integer.parseInt(row[0]);
            int Release_Year = Integer.parseInt(row[5]);
            String Duration = row[6];
            String Description = row[9];


            statement5.setInt(1, Show_ID);
            statement5.setString(2, Show_Name);
            statement5.setString(3, Show_Type);
            statement5.setInt(4, Genre_ID);
            statement5.setInt(5, Rating_ID);
            statement5.setInt(6, Director_ID);
            statement5.setInt(7, Release_Year);
            statement5.setString(8, Duration);
            statement5.setString(9, Description);



            try {
                statement5.executeUpdate();
            } catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    System.out.println("Ignoring duplicate key violation for Show ID " + Show_ID);
                    System.out.println("Ignoring duplicate key violation for Show Name " + Show_Name);
                    System.out.println("Ignoring duplicate key violation for Show Name " + Show_Type);
                    System.out.println("Ignoring duplicate key violation for Show Name " + Genre_ID);
                    System.out.println("Ignoring duplicate key violation for Show ID " + Rating_ID);
                    System.out.println("Ignoring duplicate key violation for Show Name " + Director_ID);
                    System.out.println("Ignoring duplicate key violation for Show Name " + Release_Year);
                    System.out.println("Ignoring duplicate key violation for Show Name " + Duration);
                    System.out.println("Ignoring duplicate key violation for Show ID " + Description);

                } else {
                    throw e;
                }
            }
        }


        // Insert data into the "Actor" table
        sql = "INSERT INTO Actor (Actor_ID, Show_ID, Director_ID, Actor_Name) VALUES (?, ?, ?, ?)";
        PreparedStatement statement6 = conn.prepareStatement(sql);
        reader = new CSVReader(new FileReader("C:/Users/nidhi/Downloads/netflix_project.csv")); // reset the reader
        reader.readNext(); // skip the header row again
        while ((row = reader.readNext()) != null) {
            int  Actor_ID = Integer.parseInt(row[12]);
            int Show_ID = Integer.parseInt(row[2]);
            int Director_ID = Integer.parseInt(row[0]);
            String Actor_Name = row[11];

            statement6.setInt(1, Actor_ID);
            statement6.setInt(2, Show_ID);
            statement6.setInt(3, Director_ID);
            statement6.setString(4, Actor_Name);
            try {
                statement6.executeUpdate();
            } catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    System.out.println("Ignoring duplicate key violation for Actor ID " + Actor_ID);
                    System.out.println("Ignoring duplicate key violation for Show ID " + Show_ID);
                    System.out.println("Ignoring duplicate key violation for Director ID " + Director_ID);
                    System.out.println("Ignoring duplicate key violation for Actor Name " + Actor_Name);
                } else {
                    throw e;
                }
            }
        }

        // Insert data into the "are_given_to" table
        sql = "INSERT INTO are_given_to (Award_ID, Show_ID) VALUES (?, ? )";
        PreparedStatement statement7 = conn.prepareStatement(sql);
        while ((row = reader.readNext()) != null) {
            int Award_ID = Integer.parseInt(row[16]);
            int Show_ID = Integer.parseInt(row[2]);

            statement7.setInt(1, Award_ID);
            statement7.setInt(2, Show_ID);


            try {
                statement7.executeUpdate();
            } catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    System.out.println("Ignoring duplicate key violation for Award ID " + Award_ID);
                    System.out.println("Ignoring duplicate key violation for Show ID " + Show_ID);


                } else {
                    throw e;
                }
            }
        }

        // Insert data into the "Acts" table
        sql = "INSERT INTO Acts ( Show_ID, Actor_ID) VALUES (?, ?)";
        PreparedStatement statement8 = conn.prepareStatement(sql);
        reader = new CSVReader(new FileReader("C:/Users/nidhi/Downloads/netflix_project.csv")); // reset the reader
        reader.readNext(); // skip the header row again
        while ((row = reader.readNext()) != null) {
            int Show_ID = Integer.parseInt(row[2]);
            int  Actor_ID = Integer.parseInt(row[12]);

            statement8.setInt(1, Show_ID);
            statement8.setInt(2, Actor_ID);

            try {
                statement8.executeUpdate();
            } catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    System.out.println("Ignoring duplicate key violation for Show ID " + Show_ID);
                    System.out.println("Ignoring duplicate key violation for Actor ID " + Actor_ID);


                } else {
                    throw e;
                }
            }
        }



        // Insert data into the "Won_by" table
        sql = "INSERT INTO won_by (Actor_ID, Award_ID) VALUES (?, ? )";
        PreparedStatement statement9 = conn.prepareStatement(sql);
        while ((row = reader.readNext()) != null) {
            int Actor_ID = Integer.parseInt(row[12]);
            int Award_ID = Integer.parseInt(row[16]);

            statement9.setInt(2, Actor_ID);
            statement9.setInt(1, Award_ID);



            try {
                statement9.executeUpdate();
            } catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    System.out.println("Ignoring duplicate key violation for Award ID " + Actor_ID);
                    System.out.println("Ignoring duplicate key violation for Show ID " + Award_ID);


                } else {
                    throw e;
                }
            }
        }

        // Insert data into the "Works_with" table
        sql = "INSERT INTO Works_with ( Actor_ID, Director_ID) VALUES (?, ?)";
        PreparedStatement statement10 = conn.prepareStatement(sql);
        reader = new CSVReader(new FileReader("C:/Users/nidhi/Downloads/netflix_project.csv")); // reset the reader
        reader.readNext(); // skip the header row again
        while ((row = reader.readNext()) != null) {
            int Actor_ID = Integer.parseInt(row[12]);
            int  Director_ID = Integer.parseInt(row[0]);

            statement10.setInt(1, Actor_ID);
            statement10.setInt(2, Director_ID);

            try {
                statement10.executeUpdate();
            } catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {

                    System.out.println("Ignoring duplicate key violation for Show ID " + Actor_ID);
                    System.out.println("Ignoring duplicate key violation for Actor ID " + Director_ID);



                } else {
                    throw e;
                }
            }
        }




        // Close the resources
      statement1.close();
        statement2.close();
        statement3.close();
      statement4.close();
        statement5.close();
        statement6.close();
        statement7.close();
        statement8.close();
        statement9.close();
        statement10.close();
        reader.close();
        conn.close();
    }
}
