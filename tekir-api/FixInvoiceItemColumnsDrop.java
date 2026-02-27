import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class FixInvoiceItemColumnsDrop {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/tekir2";
        String user = "postgres";
        String password = "DB_PASSWORD_PLACEHOLDER";

        try (Connection conn = DriverManager.getConnection(url, user, "postgres");
                Statement stmt = conn.createStatement()) {

            System.out.println("Dropping invoice_id from INVOICE_ITEM...");

            try {
                stmt.execute("ALTER TABLE invoice_item DROP COLUMN IF EXISTS invoice_id CASCADE;");
                System.out.println("Dropped invoice_id successfully.");
            } catch (Exception e) {
                System.out.println("Drop failed: " + e.getMessage());
            }

            System.out.println("Done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
