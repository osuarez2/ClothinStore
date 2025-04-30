
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AccountsList {

	private JFrame frame;
	private JTable table;
	

	public AccountsList() {
		initialize();
	}


	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 520);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		createScrollPaneAndTable();
		populateTable();
		createSignOutButton();
		createBackToDashboardButton();

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
	        String query = "SELECT * FROM users WHERE userType_id = 2";
	        PreparedStatement stm = Database.connection.prepareStatement(query);
	        ResultSet result = stm.executeQuery();
	        table.setModel(DbUtils.resultSetToTableModel(result));
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error loading customer accounts.");
	    }

	    JLabel lblNewLabel = new JLabel("Customer Account List");
	    lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
	    lblNewLabel.setBounds(197, 10, 262, 23);
	    frame.getContentPane().add(lblNewLabel);
	}
	
	
	
	public void createSignOutButton() {
	    JButton signOutButton = new JButton("Sign Out");
	    signOutButton.setBounds(850, 10, 100, 30); 
	    frame.getContentPane().add(signOutButton);
	    signOutButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            frame.dispose();
	            new SignIn();
	        }
	    });
	}

	public void createBackToDashboardButton() {
	    JButton backButton = new JButton("Back to Dashboard");
	    backButton.setBounds(10, 440, 180, 30); 
	    frame.getContentPane().add(backButton);
	    backButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            frame.dispose();
	            new EmployeeDashboard(); 
	        }
	    });
	}

	
	

	}





