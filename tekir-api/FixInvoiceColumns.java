import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class FixInvoiceColumns {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/tekir2";
        String user = "postgres";
        String password = "DB_PASSWORD_PLACEHOLDER";

        try (Connection conn = DriverManager.getConnection(url, user, "postgres");
                Statement stmt = conn.createStatement()) {

            System.out.println("Altering INVOICE table...");

            // Try to add TXNTIME if missing, rename TXN_DATE to TXNDATE
            try {
                stmt.execute("ALTER TABLE invoice RENAME COLUMN txn_date TO txndate;");
                System.out.println("Renamed txn_date to txndate");
            } catch (Exception e) {
                System.out.println(
                        "Rename failed (maybe txndate already exists). Dropping txn_date to avoid null constraint...");
                try {
                    stmt.execute("ALTER TABLE invoice DROP COLUMN txn_date;");
                    System.out.println("Dropped txn_date");
                } catch (Exception ex) {
                    System.out.println("Drop failed: " + ex.getMessage());
                }
            }

            System.out.println("Done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
