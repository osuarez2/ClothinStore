import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.sql.Connection;

public class SignIn {

    private JFrame frame;
    private JTextField usernamefield;
    private JTextField passwordfield;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
		 Database.connect();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SignIn window = new SignIn();
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
    public SignIn() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 389, 404);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JLabel SI = new JLabel("Sign in");
        SI.setBounds(133, 0, 145, 40);
        SI.setFont(new Font("Arial Black", Font.BOLD, 30));
        frame.getContentPane().add(SI);
        
        JButton btnNewButton = new JButton("New user?");
        btnNewButton.setBounds(153, 264, 85, 21);
        frame.getContentPane().add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToSignUpPage();
            }
        });
        
        JLabel lblNewLabel_1 = new JLabel("User Name");
        lblNewLabel_1.setBounds(146, 99, 119, 22);
        lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
        frame.getContentPane().add(lblNewLabel_1);
        
        usernamefield = new JTextField();
        usernamefield.setBounds(120, 131, 145, 22);
        frame.getContentPane().add(usernamefield);
        usernamefield.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("Password");
        lblNewLabel_2.setBounds(153, 163, 85, 13);
        lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 15));
        frame.getContentPane().add(lblNewLabel_2);
        
        passwordfield = new JTextField();
        passwordfield.setBounds(120, 187, 145, 22);
        frame.getContentPane().add(passwordfield);
        passwordfield.setColumns(10);
        
        JButton btnSignIn = new JButton("Sign In");
        btnSignIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signIn();
            }
        });
        btnSignIn.setBounds(153, 233, 85, 21);
        frame.getContentPane().add(btnSignIn);
        
        setLookAndFeel();
        
        Database.connect(); 
        setupClosingDBConnection();
    }
    
    public void signIn() {
    	try {
			String query = "SELECT * FROM users WHERE user_name = ?"; // Enter the query
			PreparedStatement stm = Database.connection.prepareStatement(query); // Create statement
			stm.setString(1, usernamefield.getText()); // Sets the int value of 4 to the first question mark in the query string
			ResultSet result = stm.executeQuery(); // Execute the query
			
			if (!result.first()) {
				JOptionPane.showMessageDialog(null, "User not found!.");
			} else {
			String userType = result.getString("user_type");
			String passwordFromDB = result.getString("user_password");
			
			if (passwordFromDB.equals(passwordfield.getText())) {
	             switch (userType.toLowerCase()) {
	                 case "admin":
	                     loadAdminPage();
	                     break;
	                 case "employee":
	                	 frame.dispose(); // Close current sign-in window
	                     new Menu();
	                     break;
	                 case "customer":
	                     frame.dispose(); // Close current sign-in window
	                     new Menu(); // Open menu for customer
	                     break;
	                 default:
	                     JOptionPane.showMessageDialog(null, "Unknown user type.");
	             }
			} else {
				JOptionPane.showMessageDialog(null, "Invalid password.");
			}
		}
			
		} catch (Exception e) {
			System.out.println(e);
		}
    }

    public void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) { }
    }

    public static void setupClosingDBConnection() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try {
                    Database.connection.close(); 
                    System.out.println("Application Closed - DB Connection Closed");
                } catch (SQLException e) { 
                    e.printStackTrace(); 
                }
            }
        }, "Shutdown-thread"));
    }

    public void goToSignUpPage() {
        frame.dispose();
        new SignUp();
    }

    // Placeholder methods for loading different user pages
    public void loadAdminPage() {
        // Code to load the admin page
        JOptionPane.showMessageDialog(null, "Welcome Admin!");
        frame.dispose();
        //new AdminPage();
    }

    public void loadEmployeePage() {
        // Code to load the employee page
        JOptionPane.showMessageDialog(null, "Welcome Employee!");
        frame.dispose();
        //new EmployeePage();
    }
}
