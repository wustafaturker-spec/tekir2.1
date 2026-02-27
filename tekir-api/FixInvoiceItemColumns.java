import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class FixInvoiceItemColumns {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/tekir2";
        String user = "postgres";
        String password = "DB_PASSWORD_PLACEHOLDER";

        try (Connection conn = DriverManager.getConnection(url, user, "postgres");
                Statement stmt = conn.createStatement()) {

            System.out.println("Altering INVOICE_ITEM table...");

            try {
                stmt.execute("ALTER TABLE invoice_item RENAME COLUMN invoice_id TO owner_id;");
                System.out.println("Renamed invoice_id to owner_id in INVOICE_ITEM");
            } catch (Exception e) {
                System.out.println("Rename failed: " + e.getMessage());
            }

            System.out.println("Done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
