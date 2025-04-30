import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;


public class MainMenu {

	private JFrame frame;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
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
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setTitle("Clothing Store");
		frame.setVisible(true);
		frame.setBounds(100, 300, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.WHITE);
		
		
		Database.connect(); // Establish connection to database
		setupClosingDBConnection();
		
		
		createLogoLBL();
		createSignUpButton();
		createSignInButton();
		createContinueAsGuestButton();
		
	}
	
	public static void setupClosingDBConnection() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	            try { Database.connection.close(); System.out.println("Application Closed - DB Connection Closed");
				} catch (SQLException e) { e.printStackTrace(); }
	        }
	    }, "Shutdown-thread"));
	}
	
	
	
	
		
	public void createLogoLBL() {
		
		JLabel logoLBL = new JLabel("Clothing Store");
		logoLBL.setBounds(450, 113, 100, 16);
		frame.getContentPane().add(logoLBL);
		
		}
	
	public void createSignInButton() {
	
	
		JButton signInButton = new JButton("Sign In");
		signInButton.setBounds(390, 163, 117, 29);
		frame.getContentPane().add(signInButton);
		
		signInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { goToSignInPage();
			}
		});
		
	}
	
	
	
	public void createSignUpButton() {
		JButton signUpButton = new JButton("Sign Up");
		signUpButton.setBounds(510, 163, 120, 29);
		frame.getContentPane().add(signUpButton);
	
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { goToSignUpPage();
			}
		});
		
	}
	
	public void createContinueAsGuestButton() {
	    JButton guestButton = new JButton("Continue As Guest ->");
	    guestButton.setBounds(430, 200, 180, 29);
	    frame.getContentPane().add(guestButton);

	    guestButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) { 
	            goToStoreMainMenuPageAsGuest();
	        }
	    });
	}

	public void goToStoreMainMenuPageAsGuest() {
	    frame.dispose();
	    new storeMainMenu();
	}
	
	public void goToSignUpPage() {
		frame.dispose();
		new SignUp();
	}
	
	
	
	public void goToSignInPage() {
		frame.dispose();
		new SignIn();
	}
	
	
	
}
