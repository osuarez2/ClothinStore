import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
import javax.swing.JScrollPane;

public class Orders {


	private JFrame frame;
	private JTable ordersTable;

	public Orders() { initialize(); }

	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 300, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		createOrdersLBL();
		createScrollPaneAndTable();
		populateTable();
		createGoToDashboardButton();
		
	}
	
	
	public void createOrdersLBL() {
		
	JLabel editLabel = new JLabel("View Orders Placed");
	editLabel.setBounds(55, 10, 150, 16);
	frame.getContentPane().add(editLabel);

	}
	
	
	public void createScrollPaneAndTable() {
		// Create Scroll Pane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(58, 40, 320, 189);
		frame.getContentPane().add(scrollPane);
		
		// Create Table Inside of Scroll Pane
	    ordersTable = new JTable();
		scrollPane.setViewportView(ordersTable);
	}
	
	// Performs a 'SELECT' query and populates the Table in the frame with the resulting data
	public void populateTable() {
		try {
			String query = "SELECT * FROM orders";
			PreparedStatement stm = Database.connection.prepareStatement(query);
			ResultSet result = stm.executeQuery(query);
			ordersTable.setModel(DbUtils.resultSetToTableModel(result));
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	public void createGoToDashboardButton() {
		JButton addDashboardButton = new JButton("Back To Dashboard");
		addDashboardButton.setBounds(55, 280, 120, 30);
		frame.getContentPane().add(addDashboardButton);
		addDashboardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				goToDashboard();
				
			}
			
			
		});
	}
	
	public void goToDashboard() {
		frame.dispose();
		new AdminDashboard();
	}	
	
}
