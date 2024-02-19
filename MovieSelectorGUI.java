import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.io.*;

public class MovieSelectorGUI extends JFrame {
    private JComboBox<String> actor_name, Director_Name, Actor_Name;

    public MovieSelectorGUI() {
        setTitle("Movie Selector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200, 200);
        setLayout(new GridLayout(4, 2));

        // Initialize combo boxes
        actor_name = new JComboBox<>();
        Director_Name = new JComboBox<>();
        Actor_Name = new JComboBox<>();

        // Add combo boxes to the frame
        add(new JLabel("Actor:"));
        add(actor_name);
        add(new JLabel("Director:"));
        add(Director_Name);
        add(new JLabel("Rating:"));
        add(Actor_Name);


        String url = "jdbc:mysql://localhost:3306/Netflix";
        String username = "root";
        String password = "N26$$";


        // Connect to database and populate combo boxes
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Netflix", "root", "N26$$");
            Statement stmt = conn.createStatement();

            // Populate actor combo box
            ResultSet actorResult = stmt.executeQuery("SELECT *   FROM Actor");
            while (actorResult.next()) {
                actor_name.addItem(actorResult.getString("Actor_Name"));
            }

            // Populate director combo box
            ResultSet directorResult = stmt.executeQuery("SELECT DISTINCT Director_Name FROM director");
            while (directorResult.next()) {
                Director_Name.addItem(directorResult.getString("Director_Name"));
            }

            /*Populate genre combo box
            ResultSet genreResult = stmt.executeQuery("SELECT DISTINCT genre FROM movies");
            while (genreResult.next()) {
                genreCombo.addItem(genreResult.getString("genre"));
            }

            // Populate rating combo box
            ResultSet ratingResult = stmt.executeQuery("SELECT DISTINCT rating FROM movies");
            while (ratingResult.next()) {
                ratingCombo.addItem(ratingResult.getString("rating"));
            }*/

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to database.");
            e.printStackTrace();
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        // Path to the CSV file
        String csvFilePath = "movies.csv";

        // SQLite database url
        String url = "jdbc:mysql://localhost:3306/Netflix";

        try {
            // create a connection to the database
            Connection conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

            // create a statement object
            Statement stmt = conn.createStatement();

            // read the CSV file line by line
            try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
                String line;
                boolean firstLine = true;
                while ((line = br.readLine()) != null) {
                    // skip the first line (header row)
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }

                    // split the line into values using comma as separator
                    String[] values = line.split(",");

                    // get the column names from the first line of the CSV file
                    if (firstLine) {
                        for (int i = 0; i < values.length; i++) {
                            // use the column name to create a column in the table
                            String columnName = values[i];
                            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS movies (" + columnName + " TEXT)");
                        }
                        firstLine = false;
                        continue;
                    }

                    // insert the values into the table
                    String query = "INSERT INTO movies VALUES (";
                    for (int i = 0; i < values.length; i++) {
                        if (i > 0) {
                            query += ", ";
                        }
                        query += "'" + values[i] + "'";
                    }
                    query += ")";
                    stmt.executeUpdate(query);
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }

            // close the statement and connection objects
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // start the GUI
        new MovieSelectorGUI();
    }
}
