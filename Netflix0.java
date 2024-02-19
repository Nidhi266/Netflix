import java.sql.*;
import java.util.Scanner;

public class Netflix0 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/Netflix";
        String username = "root";
        String password = "N26$$";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {

            // Display the welcome message
            System.out.println("Welcome to My Database App!");
            System.out.println("This app is designed to help you retrieve data from Netflix database.");

            // Process user input and execute the query
            boolean exit = false;
            ResultSet rs = null;
            Scanner scanner = new Scanner(System.in);
            while (!exit) {
                // Display a menu of available queries
                System.out.println("Please select a query:");
                System.out.println("1. List all records from Director");
                System.out.println("2. List down the show names whose average rating is TV-14.");
               System.out.println("3. List down names of all Director name and their rating.");
                System.out.println("4. List down names of all actors who have won in  2008");
                System.out.println("5. List all awards won by shows with genre-Comedies. List down their show names as well");
                System.out.println("6. Find all shows by names with genre-Documentaries");
                System.out.println("7. Find all shows and their director names for which they have won award between years 1984 and 2000(both included)");
                System.out.println("8.  Find list of all actors along with the show names who have worked in shows with rating -'PG-13");
                System.out.println("9  Find all shows and their director names for which they have won award between years 1984 and 2000(both included");
                System.out.println("10. Exit");
                System.out.println("Enter Your Choice");

                // Get user input and execute the selected query
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        rs = stmt.executeQuery("SELECT * FROM Director");
                        break;
                   case 2:

                        rs = stmt.executeQuery("SELECT Show_name from Shows \n" +
                                "INNER JOIN Rating ON \n" +
                                "Shows.Rating_id=Rating.Rating_id\n" +
                                "WHERE Rating = 'TV-14';");
                        break;
                    case 3:
                        rs = stmt.executeQuery("SELECT d.Director_name, r.rating \n" +
                                "FROM director d\n" +
                                "JOIN shows s ON d.director_id = s.director_id\n" +
                                "JOIN rating r ON s.rating_id = r.rating_id");
                        break;
                    case 4:
                        rs = stmt.executeQuery("SELECT Actor_name from Actor INNER JOIN won_by\n" +
                                " ON Actor.Actor_id = won_by.Actor_id\n" +
                                "INNER JOIN Awards ON won_by.Award_id = Awards.Award_id\n" +
                                "WHERE year_of_award = 2008");
                        break;
                    case 5:
                        rs = stmt.executeQuery("select Show_name, Award_name FROM Shows INNER JOIN Are_Given_To ON Are_Given_To.Award_ID = Award_ID INNER JOIN Awards ON Are_Given_To.Show_ID= Shows.Show_ID INNER JOIN Genre ON Shows.Genre_ID = Genre.Genre_ID WHERE Genre_name = 'Comedies'");
                        break;
                    case 6:
                        rs = stmt.executeQuery("select Show_name from Shows INNER JOIN Genre ON Shows.Genre_ID=Genre.Genre_ID WHERE Genre_name='Documentaries'");
                        break;
                    case 7:
                        rs = stmt.executeQuery(" select Show_name, Director_name FROM Shows INNER JOIN Director ON Shows.Director_ID= Director.Director_ID INNER JOIN Are_Given_To ON Shows.Show_ID= Are_Given_To.Show_ID INNER JOIN Awards ON Are_Given_To.Award_ID=Awards.Award_ID WHERE Year_of_Award >1983 AND Year_of_Award <2001");
                        break;
                    case 8:
                        rs = stmt.executeQuery(" SELECT Actor_name, Show_name FROM Actor INNER JOIN Shows ON Actor.Show_ID = Shows.Show_ID INNER JOIN Rating ON Shows.Rating_ID = Rating.Rating_ID WHERE Rating = 'PG-13'");
                    case 9:
                        rs = stmt.executeQuery("select Show_name from Shows INNER JOIN Are_Given_To ON Shows.Show_ID=Are_Given_to.Show_ID INNER JOIN Awards ON Are_Given_To.Award_ID = Awards.Award_ID WHERE Award_name='Best Actor'");
                    case 10:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

                // Display the result of the query
                while (rs != null && rs.next()) {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnsNumber = rsmd.getColumnCount();
                    for (int i = 1; i <= columnsNumber; i++) {
                        System.out.print(rs.getString(i) + " ");
                    }
                    System.out.println();
                }
            }

            // Display the exit message
            System.out.println("Thank you for using My Database App!");
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }
}
