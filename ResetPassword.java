import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.sql.*;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class ResetPassword {


    private JFrame frame;
    private JTextField newPasswordTF;

    public ResetPassword(String userEmail) {
        frame = new JFrame();
        frame.setTitle("Reset Password");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel newPasswordLBL = new JLabel("Enter New Password:");
        newPasswordLBL.setBounds(50, 50, 150, 25);
        frame.getContentPane().add(newPasswordLBL);

        newPasswordTF = new JTextField();
        newPasswordTF.setBounds(200, 50, 150, 25);
        frame.getContentPane().add(newPasswordTF);

        JButton resetButton = new JButton("Reset Password");
        resetButton.setBounds(125, 150, 150, 40);
        frame.getContentPane().add(resetButton);

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetPassword(userEmail);
            }
        });

        frame.setVisible(true);
    }

    private void resetPassword(String userEmail) {
        try {
            String query = "UPDATE users SET user_password = ?, password_reset_required = FALSE WHERE user_email = ?";
            PreparedStatement stm = Database.connection.prepareStatement(query);

            stm.setString(1, newPasswordTF.getText());
            stm.setString(2, userEmail);

            int rowsUpdated = stm.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Password reset successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                new SignIn(); // back to login page
            } else {
                JOptionPane.showMessageDialog(null, "Error resetting password.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
