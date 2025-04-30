import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
import javax.swing.JScrollPane;


public class DeleteEmployeeAcct {




	private JFrame frame;
	private JTable employeesTable;

	public DeleteEmployeeAcct() { initialize(); }

	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 300, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		createScrollPaneAndTable();
		populateTable();
		createBackToDashboardButton();
		createDeleteSelectedButton();

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
	
	
	
	public void createDeleteSelectedButton() {
	    JButton deleteButton = new JButton("Delete Selected Employee");
	    deleteButton.setBounds(230, 240, 200, 30);
	    frame.getContentPane().add(deleteButton);
	    
	    deleteButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            int selectedRow = employeesTable.getSelectedRow();
	            if (selectedRow == -1) {
	                // No row is selected
	                javax.swing.JOptionPane.showMessageDialog(frame, "Please select a employee to delete.", "No Selection", javax.swing.JOptionPane.WARNING_MESSAGE);
	                return;
	            }
	            
	            // Assuming the first column of your table is user_id
	            int userId = (int) employeesTable.getValueAt(selectedRow, 0);
	            
	            int confirm = javax.swing.JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this employee?", "Confirm Delete", javax.swing.JOptionPane.YES_NO_OPTION);
	            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
	                try {
	                    String query = "DELETE FROM users WHERE user_id = ?";
	                    PreparedStatement stm = Database.connection.prepareStatement(query);
	                    stm.setInt(1, userId);
	                    int rowsAffected = stm.executeUpdate();
	                    
	                    if (rowsAffected > 0) {
	                        javax.swing.JOptionPane.showMessageDialog(frame, "Employee deleted successfully!");
	                        populateTable(); // Refresh the table after deleting
	                    } else {
	                        javax.swing.JOptionPane.showMessageDialog(frame, "Failed to delete employee.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	                    }
	                    
	                } catch (SQLException ex) {
	                    ex.printStackTrace();
	                    javax.swing.JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        }
	    });
	}

	
	
	
	
	public void goToAdminDashboard() {
		frame.dispose();
		new AdminDashboard();
	}
}