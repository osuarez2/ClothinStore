import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTable;

import net.proteanit.sql.DbUtils;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Accounts_list {

	private JFrame frame;
	private JTable table;
	private static JTextField Deletetxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		 Database.connect();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Accounts_list window = new Accounts_list();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Accounts_list() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 636, 520);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		createScrollPaneAndTable();
		populateTable();
}
	
	public void createScrollPaneAndTable() {
		// Create Scroll Pane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(58, 40, 510, 300);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
	

	// Performs a 'SELECT' query and populates the Table in the frame with the resulting data
	public void populateTable() {
		try {
			String query = "SELECT * FROM users";
			PreparedStatement stm = Database.connection.prepareStatement(query);
			ResultSet result = stm.executeQuery(query);
			table.setModel(DbUtils.resultSetToTableModel(result));
		} catch (Exception e) {
			System.out.println(e);
		}
		
	


		

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(87, 43, 262, 119);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Customer Account List");
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblNewLabel.setBounds(197, 10, 262, 23);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Delete");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteUser();
			}
		});
		btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnNewButton.setBounds(273, 413, 96, 21);
		frame.getContentPane().add(btnNewButton);
		
		Deletetxt = new JTextField();
		Deletetxt.setBounds(273, 383, 96, 19);
		frame.getContentPane().add(Deletetxt);
		Deletetxt.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Select the account ID you wish to delete");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(143, 362, 372, 13);
		frame.getContentPane().add(lblNewLabel_1);
}
	public static void deleteUser() {
		try {
			String query = "DELETE FROM users WHERE user_id = ?";
			PreparedStatement stm = Database.connection.prepareStatement(query);
			
			stm.setString(1, Deletetxt.getText() ); 
			
			stm.executeUpdate();
			JOptionPane.showMessageDialog(null, "Your item was Deleted from the Database", query, JOptionPane.DEFAULT_OPTION);
		} catch (Exception e) {
			System.out.println(e);
		}
	}}
