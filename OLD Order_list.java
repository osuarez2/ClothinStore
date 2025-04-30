import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.SwingConstants;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Order_list {

	private JFrame frame;
	private JTextField txtOrderList;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Order_list window = new Order_list();
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
	public Order_list() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 636, 477);
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
			String query = "SELECT * FROM orders";
			PreparedStatement stm = Database.connection.prepareStatement(query);
			ResultSet result = stm.executeQuery(query);
			table.setModel(DbUtils.resultSetToTableModel(result));
		} catch (Exception e) {
			System.out.println(e);
		}
		
		txtOrderList = new JTextField();
		txtOrderList.setHorizontalAlignment(SwingConstants.CENTER);
		txtOrderList.setFont(new Font("Arial Black", Font.PLAIN, 20));
		txtOrderList.setText("Order List");
		txtOrderList.setBounds(219, 10, 151, 34);
		frame.getContentPane().add(txtOrderList);
		txtOrderList.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(39, 76, 532, 270);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}

}
