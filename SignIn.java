import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.sql.*;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class SignIn {

	
	private JFrame frame;
	private JTextField emailTF;
	private JTextField passwordTF;
	
	public SignIn() { initialize(); 
	
	frame.setVisible(true);
	
	}
	
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Sign In");
		frame.setBounds(100, 100, 267, 333);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		createSigningInButton(); 
		createEmailLBL(); 
		createEmailTF(); 
		createPasswordLBL(); 
		createPasswordTF();
	
	}
	
	
	
	
	
	public void createSigningInButton() {
		JButton signInButton = new JButton("Sign In");
		signInButton.setBounds(83, 238, 117, 29);
		frame.getContentPane().add(signInButton);
		signInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Put code here that you want to run when this button is clicked
				signIn(); // Adds the new patient to the database when the button is clicked
			}
		});
	}

	
	
	public void createEmailLBL() {
	
	
	JLabel emailLBL = new JLabel("Email");
	emailLBL.setBounds(30, 30, 100, 25);
	frame.getContentPane().add(emailLBL);
	
	}
	
	public void createEmailTF() {
	
	emailTF = new JTextField();
	emailTF.setBounds(140, 30, 200, 25);
	frame.getContentPane().add(emailTF);
	emailTF.setColumns(10);
	
	}
	
	public void createPasswordLBL() {
	
	JLabel passwordLBL = new JLabel("Password");
	passwordLBL.setBounds(30, 70, 100, 25);
	frame.getContentPane().add(passwordLBL);
	
	}
	
	public void createPasswordTF () {
	
	passwordTF = new JTextField();
	passwordTF.setBounds(140, 70, 200, 25);
	frame.getContentPane().add(passwordTF);
	passwordTF.setColumns(10);
	

	}
	
	public void signIn() {
	    try {
	        String query = "SELECT * FROM users WHERE user_email = ? AND user_password = ?";
	        PreparedStatement stm = Database.connection.prepareStatement(query);
	        
	        stm.setString(1, emailTF.getText());
	        stm.setString(2, passwordTF.getText());
	        
	        ResultSet rs = stm.executeQuery();
	        
	        if (rs.next()) {
	           
	            String userName = rs.getString("user_name");
	            int userType = rs.getInt("userType_id");
	            JOptionPane.showMessageDialog(null, "Welcome back, " + userName + "!", "Sign In Successful", JOptionPane.INFORMATION_MESSAGE);
	            
	            
	            frame.dispose(); 
	            
	     
	            
	            if (userType == 1) {
	                new AdminDashboard();   
	          
	            } else {
	                JOptionPane.showMessageDialog(null, "Unknown user type.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	            
	            
	        } else {
	            
	            JOptionPane.showMessageDialog(null, "Incorrect email or password.", "Sign In Failed", JOptionPane.ERROR_MESSAGE);
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
}
	
	
	
