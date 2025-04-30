import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
import javax.swing.JScrollPane;

public class EditEmployeeAcct {



	private JFrame frame;
	private JTable employeesTable;

	public EditEmployeeAcct() { initialize(); }

	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 300, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		createScrollPaneAndTable();
		populateTable();
		createBackToDashboardButton();
		createEditSelectedButton();
	}
	
	
	public void createScrollPaneAndTable() {
		// Create Scroll Pane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(58, 40, 320, 189);
		frame.getContentPane().add(scrollPane);
		
		// Create Table Inside of Scroll Pane
	    employeesTable = new JTable();
		scrollPane.setViewportView(employeesTable);
	}
	
	// Performs a 'SELECT' query and populates the Table in the frame with the resulting data
	public void populateTable() {
		try {
			String query = "SELECT * FROM users WHERE userType_id = 2";
			PreparedStatement stm = Database.connection.prepareStatement(query);
			ResultSet result = stm.executeQuery();
			employeesTable.setModel(DbUtils.resultSetToTableModel(result));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
	
	public void createBackToDashboardButton() {
		JButton backButton = new JButton("Back To Dashboard");
		backButton.setBounds(55, 240,160,30);
		frame.getContentPane().add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Put code here that you want to run when this button is clicked
				goToAdminDashboard(); // Adds the new patient to the database when the button is clicked
			}
		});
	}
	
	
	
	
	public void createEditSelectedButton() {
	    JButton editButton = new JButton("Edit Selected Employee");
	    editButton.setBounds(230, 240, 200, 30);
	    frame.getContentPane().add(editButton);

	    editButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            int selectedRow = employeesTable.getSelectedRow();
	            if (selectedRow == -1) {
	                javax.swing.JOptionPane.showMessageDialog(frame, "Please select a employee to edit.", "No Selection", javax.swing.JOptionPane.WARNING_MESSAGE);
	                return;
	            }

	            // Assuming your table has columns in this order:
	            // 0: user_id, 1: user_name, 2: user_email, 3: user_address, 4: user_password
	            int userId = (int) employeesTable.getValueAt(selectedRow, 0);
	            String currentName = employeesTable.getValueAt(selectedRow, 1).toString();
	            String currentEmail = employeesTable.getValueAt(selectedRow, 2).toString();
	            String currentPassword = employeesTable.getValueAt(selectedRow, 3).toString();
	            

	            // Create a custom panel for input
	            javax.swing.JPanel panel = new javax.swing.JPanel();
	            panel.setLayout(new java.awt.GridLayout(0, 2, 10, 10));

	            panel.add(new javax.swing.JLabel("Name:"));
	            javax.swing.JTextField nameField = new javax.swing.JTextField(currentName);
	            panel.add(nameField);

	            panel.add(new javax.swing.JLabel("Email:"));
	            javax.swing.JTextField emailField = new javax.swing.JTextField(currentEmail);
	            panel.add(emailField);

	            panel.add(new javax.swing.JLabel("Password:"));
	            javax.swing.JTextField passwordField = new javax.swing.JTextField(currentPassword);
	            panel.add(passwordField);

	        

	            int result = javax.swing.JOptionPane.showConfirmDialog(frame, panel, "Edit Employee", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);

	            if (result == javax.swing.JOptionPane.OK_OPTION) {
	                String newName = nameField.getText();
	                String newEmail = emailField.getText();
	                String newPassword = passwordField.getText();
	             

	                try {
	                    String query = "UPDATE users SET user_name = ?, user_email = ?, user_password = ? WHERE user_id = ?";
	                    PreparedStatement stm = Database.connection.prepareStatement(query);
	                    stm.setString(1, newName);
	                    stm.setString(2, newEmail);
	                    stm.setString(3, newPassword);
	                    stm.setInt(4, userId);

	                    int rowsAffected = stm.executeUpdate();

	                    if (rowsAffected > 0) {
	                        javax.swing.JOptionPane.showMessageDialog(frame, "Employee updated successfully!");
	                        populateTable(); // Refresh table
	                    } else {
	                        javax.swing.JOptionPane.showMessageDialog(frame, "Failed to update employee.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	                    }

	                } catch (SQLException ex) {
	                    ex.printStackTrace();
	                    javax.swing.JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	                }
	            } else {
	                javax.swing.JOptionPane.showMessageDialog(frame, "Update cancelled.");
	            }
	        }
	    });
	}

	
	public void goToAdminDashboard() {
		frame.dispose();
		new AdminDashboard();
	}
}
