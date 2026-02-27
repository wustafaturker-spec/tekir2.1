import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class FixHibernateSequence {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/tekir2";
        String user = "postgres";
        String password = "DB_PASSWORD_PLACEHOLDER"; // I will pass password from application.properties or default
                                                     // postgres

        try (Connection conn = DriverManager.getConnection(url, user, "postgres");
                Statement stmt = conn.createStatement()) {

            System.out.println("Dropping sequence...");
            stmt.execute("DROP TABLE IF EXISTS hibernate_sequences CASCADE;");
            stmt.execute("DROP SEQUENCE IF EXISTS hibernate_sequences CASCADE;");

            System.out.println("Creating table...");
            stmt.execute(
                    "CREATE TABLE hibernate_sequences (sequence_name VARCHAR(255) NOT NULL, sequence_next_hi_value BIGINT, PRIMARY KEY (sequence_name));");

            System.out.println("Inserting genericSeq...");
            stmt.execute(
                    "INSERT INTO hibernate_sequences (sequence_name, sequence_next_hi_value) VALUES ('genericSeq', 1) ON CONFLICT DO NOTHING;");

            System.out.println("Done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
