package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class printDbOutput_SignUp {

    // Method to fetch activation link from the database using the given email
    public static String fetchActivationLink(String email) {
        String activationLink = null;

        // Database connection details
        String jdbcURL    = "jdbc:mysql://meet2.synesisit.info:35559/organization_settings";
        String dbUser     = "conVeyDevloperUse1ly";
        String dbPassword = "iZTlLI6ujUkvAul4hW";

        // SQL with placeholder
        String sql = "SELECT activation_link FROM sit_sign_up_user WHERE sign_up_email = ?";

        // Try-with-resources for Connection, PreparedStatement, ResultSet
        try (
            Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            // Bind the email parameter
            ps.setString(1, email);
            System.out.println("Executing query: " + ps);   // logs the actual SQL + bound param

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    activationLink = rs.getString("activation_link");
                } else {
                    System.out.println("No activation link found for email: " + email);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return activationLink;
    }

    // Method to fetch email from Excel (sheet #1, row 1, cell 0)
    public static String getEmailFromExcel() {
        String email = null;

        File excelFile = new File("TestData\\TestDataFile.xlsx");
        try (FileInputStream fis = new FileInputStream(excelFile);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            // Adjust the sheet index if your signup sheet is not at 1
            XSSFSheet signUpSheet = workbook.getSheetAt(1);

            // Read row 1 (second row), cell 0
            email = signUpSheet
                      .getRow(1)
                      .getCell(0)
                      .toString()
                      .trim();  // remove leading/trailing whitespace

            System.out.println("Email fetched from Excel: " + email);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return email;
    }

    public static void main(String[] args) {
        // 1) Get the email
        String email = getEmailFromExcel();

        // 2) Lookup activation link in DB
        String activationLink = fetchActivationLink(email);

        System.out.println("Activation Link: " + activationLink);
    }
}
