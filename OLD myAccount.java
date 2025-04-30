package storeFront;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import net.proteanit.sql.DbUtils;

import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class myAccount {

	private JFrame frame;
	private JTextField nameField;
	private JTextField addressField;
	public static int userID;
	private JTable table;
	private JTable accountInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					myAccount window = new myAccount();
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
	public myAccount() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 633, 427);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Database.connect();
		setupClosingDBConnection();
		
		JLabel lblNewLabel = new JLabel("My Account");
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		lblNewLabel.setBounds(0, 0, 221, 14);
		frame.getContentPane().add(lblNewLabel);
		
		createTable();
		populateTable();
		
		nameField = new JTextField();
		nameField.setBounds(10, 123, 150, 20);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		addressField = new JTextField();
		addressField.setBounds(10, 216, 159, 20);
		frame.getContentPane().add(addressField);
		addressField.setColumns(10);
		
		JButton changeName = new JButton("Confirm");
		changeName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameChange();
			}
		});
		changeName.setBounds(10, 154, 89, 23);
		frame.getContentPane().add(changeName);
		
		JButton changeAddress = new JButton("Confirm");
		changeAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addressChange();
			}
		});
		changeAddress.setBounds(10, 244, 89, 23);
		frame.getContentPane().add(changeAddress);
		
		JButton toStoreMain = new JButton("Back");
		toStoreMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backToMain();
			}
		});
		toStoreMain.setBounds(10, 354, 89, 23);
		frame.getContentPane().add(toStoreMain);
		
		JLabel lblNewLabel_1 = new JLabel("Change Name");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 101, 116, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Change Address");
		lblNewLabel_1_1.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblNewLabel_1_1.setBounds(10, 191, 116, 14);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		
		
		
		
		
	}
	
	//shutdown disconnect
		public static void setupClosingDBConnection() {
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		        public void run() {
		            try { Database.connection.close(); System.out.println("Application Closed - DB Connection Closed");
					} catch (SQLException e) { e.printStackTrace(); }
		        }
		    }, "Shutdown-thread"));
		}
		
		public void setupLookandFeel()
		{
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowLookAndFeel");
			} catch (Exception e) {}
		}
		
		
	public void nameChange()
	{
			try {
				String query = "UPDATE users SET user_name = ? WHERE user_id = ?";
				PreparedStatement stm = Database.connection.prepareStatement(query);
				stm.setString(1, nameField.getText()); 
				stm.setInt(2, userID);
				
				stm.executeUpdate();
				JOptionPane.showMessageDialog(null, "Data Updated", "Account name Changed!", JOptionPane.DEFAULT_OPTION);

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	
	public void addressChange()
	{
		try {
			String query = "UPDATE users SET user_address = ? WHERE user_id = ?";
			PreparedStatement stm = Database.connection.prepareStatement(query);
			stm.setString(1, addressField.getText()); 
			stm.setInt(2, userID);
			
			stm.executeUpdate();
			JOptionPane.showMessageDialog(null, "Data Updated", "Account name Changed!", JOptionPane.DEFAULT_OPTION);

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void createTable()
	{
		accountInfo = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) // prevents users from editing table
			{
				return false;
			}
		};
		accountInfo.setBounds(10, 25, 581, 65);
		frame.getContentPane().add(accountInfo);
		
		//scroll pane so table header can be used
		JScrollPane scrollPane = new JScrollPane(accountInfo); // Wrap the table in a scroll pane
	    scrollPane.setBounds(10, 25, 581, 65);
	    frame.getContentPane().add(scrollPane); // Add the scroll pane, NOT the table directly
	}
	
	public void populateTable()
	{
		try {
			String query = "SELECT * FROM users WHERE user_id = ?";
			PreparedStatement stm = Database.connection.prepareStatement(query);
			stm.setInt(1, userID);
			ResultSet result = stm.executeQuery();
			accountInfo.setModel(DbUtils.resultSetToTableModel(result));
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	public void backToMain()
	{
		frame.dispose();
		new storeMainMenu();
	}
}
