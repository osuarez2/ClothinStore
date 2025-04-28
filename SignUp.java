import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class SignUp {

	private JFrame frame;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JTextField txtType;
	private JTextField emailtf;
	private JTextField Addresstf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		 Database.connect();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp window = new SignUp();
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
	public SignUp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 496);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sign Up");
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 30));
		lblNewLabel.setBounds(148, 10, 139, 43);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Username");
		lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(158, 63, 86, 22);
		frame.getContentPane().add(lblNewLabel_2);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(135, 95, 139, 22);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Password");
		lblNewLabel_3.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(168, 310, 86, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(135, 334, 139, 22);
		frame.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Who are you? (Type)");
		lblNewLabel_4.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(117, 247, 170, 22);
		frame.getContentPane().add(lblNewLabel_4);
		
		txtType = new JTextField();
		txtType.setBounds(135, 279, 139, 21);
		frame.getContentPane().add(txtType);
		txtType.setColumns(10);
		
		JButton btnNewButton = new JButton("SignUp");
		btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnNewButton.setBounds(148, 393, 117, 21);
		frame.getContentPane().add(btnNewButton);
		
		
		JLabel lblNewLabel_4_1 = new JLabel("Email");
		lblNewLabel_4_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_4_1.setBounds(183, 190, 61, 22);
		frame.getContentPane().add(lblNewLabel_4_1);
		
		emailtf = new JTextField();
		emailtf.setColumns(10);
		emailtf.setBounds(135, 216, 139, 21);
		frame.getContentPane().add(emailtf);
		
		JLabel lblNewLabel_4_2 = new JLabel("Address");
		lblNewLabel_4_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4_2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_4_2.setBounds(117, 127, 170, 22);
		frame.getContentPane().add(lblNewLabel_4_2);
		
		Addresstf = new JTextField();
		Addresstf.setColumns(10);
		Addresstf.setBounds(135, 159, 139, 21);
		frame.getContentPane().add(Addresstf);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToSignInPage();
			}
		});
		btnBack.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnBack.setBounds(148, 424, 117, 21);
		frame.getContentPane().add(btnBack);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Put code here that you want to run when this button is clicked
				addCustomUser(); // Adds the new patient to the database when the button is clicked
				
			}
		});
		}
		
		public void addCustomUser() {
			try {
				String query = "INSERT INTO users (user_name, user_password, user_type, user_email, user_address) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement stm = Database.connection.prepareStatement(query);
				 
				stm.setString(1, txtUsername.getText());
				stm.setString(2, txtPassword.getText()); 
				stm.setString(3, txtType.getText());
				stm.setString(4, emailtf.getText());
				stm.setString(5, Addresstf.getText());

				stm.executeUpdate();
				// The line below is ran if the query executes successfully. It shows a JOptionPane (an alert) telling the user that the patient has been added to the database.
				JOptionPane.showMessageDialog(null, "The new Account was added to the database!", "Account Made!", JOptionPane.DEFAULT_OPTION);
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}
			public void goToSignInPage() {
		        frame.dispose();
		        new SignIn();
		    
			
		
			}
}
