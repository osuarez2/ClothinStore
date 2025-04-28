import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;


import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

public class Item_Delete_Add {

	private JFrame frame;
	private JTextField Name;
	private JTextField Price;
	private JTextField Quantity;
	private static JTextField IDtxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		 Database.connect();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Item_Delete_Add window = new Item_Delete_Add();
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
	public Item_Delete_Add() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 521, 521);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Make or Delete an Item");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		lblNewLabel.setBounds(53, 31, 392, 41);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Item Name");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(201, 94, 91, 13);
		frame.getContentPane().add(lblNewLabel_1);
		
		Name = new JTextField();
		Name.setBounds(187, 117, 120, 19);
		frame.getContentPane().add(Name);
		Name.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Price");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(201, 146, 91, 13);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		Price = new JTextField();
		Price.setColumns(10);
		Price.setBounds(187, 169, 120, 19);
		frame.getContentPane().add(Price);
		
		JLabel lblNewLabel_1_2 = new JLabel("Quantity");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1_2.setBounds(201, 198, 91, 13);
		frame.getContentPane().add(lblNewLabel_1_2);
		
		Quantity = new JTextField();
		Quantity.setColumns(10);
		Quantity.setBounds(187, 221, 120, 19);
		frame.getContentPane().add(Quantity);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCustomItem();
			
			}
		});
		btnAdd.setBounds(207, 256, 85, 21);
		frame.getContentPane().add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteItem();
			}
		});
		btnDelete.setBounds(207, 367, 85, 21);
		frame.getContentPane().add(btnDelete);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Item ID");
		lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1_2_1.setBounds(201, 311, 91, 13);
		frame.getContentPane().add(lblNewLabel_1_2_1);
		
		IDtxt = new JTextField();
		IDtxt.setColumns(10);
		IDtxt.setBounds(187, 334, 120, 19);
		frame.getContentPane().add(IDtxt);
	}
	public void addCustomItem() {
		try {
			String query = "INSERT INTO items (item_name, item_price, item_count) VALUES (?, ?, ?)";
			PreparedStatement stm = Database.connection.prepareStatement(query);
			 
			stm.setString(1, Name.getText());
			stm.setString(2, Price.getText()); 
			stm.setString(3, Quantity.getText());
			

			stm.executeUpdate();
			// The line below is ran if the query executes successfully. It shows a JOptionPane (an alert) telling the user that the patient has been added to the database.
			JOptionPane.showMessageDialog(null, "The new item was Created!", "Item Made!", JOptionPane.DEFAULT_OPTION);
		} catch (Exception e) {
			System.out.println(e);
		}
		}
		public static void deleteItem() {
			try {
				String query = "DELETE FROM items WHERE item_id = ?";
				PreparedStatement stm = Database.connection.prepareStatement(query);
				
				stm.setString(1, IDtxt.getText() ); 
				
				stm.executeUpdate();
				JOptionPane.showMessageDialog(null, "Your item was Deleted from the Database", query, JOptionPane.DEFAULT_OPTION);
			} catch (Exception e) {
				System.out.println(e);
			}
		
	}}	
