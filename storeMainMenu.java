
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


import javax.swing.*;
import java.awt.*;

public class storeMainMenu {

    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel centerPanel;

    public storeMainMenu() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setVisible(true);
        frame.setBounds(100, 300, 1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);

        // Set layout and add centerPanel to frame
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(centerPanel, BorderLayout.CENTER);

        // Main panel with buttons
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        centerPanel.add(menuPanel, "MainMenu");

        JLabel welcomeLabel = new JLabel("Welcome, " + GlobalSession.getUserName() + "!");
        welcomeLabel.setBounds(12, 8, 300, 30);
        menuPanel.add(welcomeLabel);

        JLabel lblNewLabel = new JLabel("Clothing Store");
        lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 25));
        lblNewLabel.setBounds(450, 50, 280, 28);
        menuPanel.add(lblNewLabel);

        addAccountButton(menuPanel);
        addCartButton(menuPanel);
        addNavigationButtons(menuPanel);

        if (GlobalSession.getUserId() > 0) {
            createSignOutButton(menuPanel);
        }

        // Show main menu
        cardLayout.show(centerPanel, "MainMenu");
    }

    private void addAccountButton(JPanel panel) {
        JButton accountButton = new JButton("My Account");
        accountButton.addActionListener(e -> {
            if (GlobalSession.getUserId() <= 0) {
                notSignedInPopup();
            } else {
                goToMyAccount();
            }
        });
        accountButton.setBounds(700, 1, 107, 23);
        panel.add(accountButton);
    }

    private void addCartButton(JPanel panel) {
        JButton cartButton = new JButton("My Cart");
        cartButton.addActionListener(e -> {
            if (GlobalSession.getUserId() <= 0) {
                notSignedInPopup();
            } else {
                goToCart();
            }
        });
        cartButton.setBounds(800, 1, 89, 23);
        panel.add(cartButton);
    }

    private void addNavigationButtons(JPanel panel) {
        JButton mensButton = new JButton("Men's Clothing");
        mensButton.setBounds(400, 90, 153, 23);
        mensButton.addActionListener(e -> goToMensClothing());
        panel.add(mensButton);

        JButton womensButton = new JButton("Women's Clothing");
        womensButton.setBounds(400, 130, 153, 23);
        womensButton.addActionListener(e -> goToWomensClothing());
        panel.add(womensButton);

        JButton kidsButton = new JButton("Kid's Clothing");
        kidsButton.setBounds(400, 170, 153, 23);
        kidsButton.addActionListener(e -> goToKidsClothing());
        panel.add(kidsButton);

        JButton couponButton = new JButton("Today's Deals");
        couponButton.setBounds(400, 210, 153, 23);
        couponButton.addActionListener(e -> goToTodaysDeals());
        panel.add(couponButton);
    }

    private void createSignOutButton(JPanel panel) {
        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setBounds(900, 1, 89, 23);
        signOutButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to sign out?", "Confirm Sign Out", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                GlobalSession.setUser(0, "Guest", "");
                frame.dispose();
                new MainMenu();
            }
        });
        panel.add(signOutButton);
    }

    private void notSignedInPopup() {
        int option = JOptionPane.showConfirmDialog(frame, "You are not signed in. Sign in now?", "Not Signed In", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            frame.dispose();
            new SignIn(); // Go to Sign In screen
        }
    }

    public void goToMyAccount() {
        frame.dispose();
        new CustomerDashboard();
    }

    public void goToCart() {
        // Clear and rebuild the cart view every time it's opened
        JPanel cartPanel = new JPanel(new BorderLayout());

        // Actual cart content in center
        JPanel cartContent = ShoppingCart.createCartPage(cardLayout, centerPanel);
        cartPanel.add(cartContent, BorderLayout.CENTER);

        // Buttons at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton backButton = new JButton("Keep Shopping");
        backButton.addActionListener(e -> cardLayout.show(centerPanel, "MainMenu"));

        JButton checkoutButton = new JButton("Checkout Now");
        checkoutButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Proceeding to checkout (not implemented yet)."));

        buttonPanel.add(backButton);
        buttonPanel.add(checkoutButton);
        cartPanel.add(buttonPanel, BorderLayout.SOUTH);

        centerPanel.add(cartPanel, "CartPage");
        cardLayout.show(centerPanel, "CartPage");
    }


    public void goToMensClothing() {
        frame.dispose();
        new MensClothing();
    }

    public void goToWomensClothing() {
        frame.dispose();
        new WomensClothing();
    }

    public void goToKidsClothing() {
        frame.dispose();
        new KidsClothing();
    }

    public void goToTodaysDeals() {
        frame.dispose();
        new TodaysDeals();
    }
}
