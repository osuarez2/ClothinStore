import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
import javax.swing.JScrollPane;

public class DeleteCustomerAcct {



	private JFrame frame;
	private JTable customersTable;

	public DeleteCustomerAcct() { initialize(); }

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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(58, 40, 320, 189);
		frame.getContentPane().add(scrollPane);
		
	
	    customersTable = new JTable();
		scrollPane.setViewportView(customersTable);
	}
	
	
	public void populateTable() {
		try {
			String query = "SELECT * FROM users WHERE userType_id = 3";
			PreparedStatement stm = Database.connection.prepareStatement(query);
			ResultSet result = stm.executeQuery();
			customersTable.setModel(DbUtils.resultSetToTableModel(result));
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
		
				goToAdminDashboard(); 
			}
		});
	}
	
	
	public void createDeleteSelectedButton() {
	    JButton deleteButton = new JButton("Delete Selected Customer");
	    deleteButton.setBounds(230, 240, 200, 30);
	    frame.getContentPane().add(deleteButton);
	    
	    deleteButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            int selectedRow = customersTable.getSelectedRow();
	            if (selectedRow == -1) {
	              
	                javax.swing.JOptionPane.showMessageDialog(frame, "Please select a customer to delete.", "No Selection", javax.swing.JOptionPane.WARNING_MESSAGE);
	                return;
	            }
	            
	            
	            int userId = (int) customersTable.getValueAt(selectedRow, 0);
	            
	            int confirm = javax.swing.JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this customer?", "Confirm Delete", javax.swing.JOptionPane.YES_NO_OPTION);
	            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
	                try {
	                    String query = "DELETE FROM users WHERE user_id = ?";
	                    PreparedStatement stm = Database.connection.prepareStatement(query);
	                    stm.setInt(1, userId);
	                    int rowsAffected = stm.executeUpdate();
	                    
	                    if (rowsAffected > 0) {
	                        javax.swing.JOptionPane.showMessageDialog(frame, "Customer deleted successfully!");
	                        populateTable();
	                    } else {
	                        javax.swing.JOptionPane.showMessageDialog(frame, "Failed to delete customer.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
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
