package model.repository;

import utiles.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

import static org.postgresql.core.Oid.UUID;

public class Insert10MRecord {
    public static void insertTenMillionProducts() {
        String sql = """
                   INSERT INTO products (p_name, price, qty, is_deleted, p_uuid) VALUES (?, ?, ?, ?, ?)
                 """;
        int totalProducts = 10_000_000; // 10 million
        int batchSize = 1000;

        long startTime = System.currentTimeMillis(); // start time

        try (Connection con = DatabaseConfig.getDataConnection()) {
            PreparedStatement pre = con.prepareStatement(sql);
            con.setAutoCommit(false); // Disable auto-commit for better performance

            for (int i = 1; i <= totalProducts; i++) {
                pre.setString(1, "Product_" + i);
                pre.setFloat(2, (float) (Math.random() * 100)); // Random price between 0 - 100
                pre.setInt(3, (int) (Math.random() * 50));      // Random quantity between 0 - 50
                pre.setBoolean(4, false); // Default is_deleted to false
                pre.setString(5, java.util.UUID.randomUUID().toString());

                pre.addBatch();

                if (i % batchSize == 0) {
                    pre.executeBatch();
                    con.commit(); // commit every batch
                }
            }
            pre.executeBatch(); // execute remaining
            con.commit();       // commit remaining

            long endTime = System.currentTimeMillis();
            System.out.println("✅ Inserted " + totalProducts + " products successfully!");
            System.out.println("⏱️ Time taken: " + (endTime - startTime) / 1000 + " seconds");

        } catch (Exception e) {
            System.out.println("❌ Error during inserting 10 million products: " + e.getMessage());
        }
    }

}
