import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;


public class CustomerDashboard {

    private JFrame frame;
    private JTable accountInfo;

    public CustomerDashboard() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setVisible(true);
        frame.setBounds(100, 300, 1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel titleLabel = new JLabel("My Account");
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
        titleLabel.setBounds(400, 10, 200, 30);
        frame.getContentPane().add(titleLabel);

        createScrollPaneAndTable();
        populateTable();
        createEditInfoButton();
        createBackButton();
    }

    private void createScrollPaneAndTable() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(200, 60, 600, 150);
        frame.getContentPane().add(scrollPane);

        accountInfo = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scrollPane.setViewportView(accountInfo);
    }

    private void populateTable() {
        try {
            String query = "SELECT user_id, user_name, user_email, user_password, user_address FROM users WHERE user_id = ?";
            PreparedStatement stm = Database.connection.prepareStatement(query);
            stm.setInt(1, GlobalSession.getUserId());
            ResultSet result = stm.executeQuery();
            accountInfo.setModel(DbUtils.resultSetToTableModel(result));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void createEditInfoButton() {
        JButton editButton = new JButton("Edit My Info");
        editButton.setBounds(200, 230, 150, 30);
        frame.getContentPane().add(editButton);

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editMyInfo();
            }
        });
    }

    private void editMyInfo() {
        try {
            // Fetch current user data again
            String query = "SELECT user_name, user_email, user_password, user_address FROM users WHERE user_id = ?";
            PreparedStatement stm = Database.connection.prepareStatement(query);
            stm.setInt(1, GlobalSession.getUserId());
            ResultSet result = stm.executeQuery();

            if (result.next()) {
                String currentName = result.getString("user_name");
                String currentEmail = result.getString("user_email");
                String currentPassword = result.getString("user_password");
                String currentAddress = result.getString("user_address");

                JPanel panel = new JPanel(new java.awt.GridLayout(0, 2, 10, 10));

                panel.add(new JLabel("Name:"));
                JTextField nameField = new JTextField(currentName);
                panel.add(nameField);

                panel.add(new JLabel("Email:"));
                JTextField emailField = new JTextField(currentEmail);
                panel.add(emailField);

                panel.add(new JLabel("Password:"));
                JTextField passwordField = new JTextField(currentPassword);
                panel.add(passwordField);

                panel.add(new JLabel("Address:"));
                JTextField addressField = new JTextField(currentAddress);
                panel.add(addressField);

                int resultOption = JOptionPane.showConfirmDialog(frame, panel, "Edit My Info", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (resultOption == JOptionPane.OK_OPTION) {
                    String newName = nameField.getText();
                    String newEmail = emailField.getText();
                    String newPassword = passwordField.getText();
                    String newAddress = addressField.getText();

                    String updateQuery = "UPDATE users SET user_name = ?, user_email = ?, user_password = ?, user_address = ? WHERE user_id = ?";
                    PreparedStatement updateStm = Database.connection.prepareStatement(updateQuery);
                    updateStm.setString(1, newName);
                    updateStm.setString(2, newEmail);
                    updateStm.setString(3, newPassword);
                    updateStm.setString(4, newAddress);
                    updateStm.setInt(5, GlobalSession.getUserId());

                    int rowsAffected = updateStm.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(frame, "Your information has been updated successfully!");
                        populateTable(); // Refresh table
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to update your information.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.setBounds(370, 230, 100, 30);
        frame.getContentPane().add(backButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new storeMainMenu();
            }
        });
    }
}
