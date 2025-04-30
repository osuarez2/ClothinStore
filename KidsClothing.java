import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class KidsClothing {

    private JFrame frame;
   
    private JPanel centerPanel;
    private CardLayout cardLayout;

    public KidsClothing() {
       
    	
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setVisible(true);
        frame.setBounds(100, 100, 1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); 

        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(1000, 60));
        topPanel.setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Welcome, " + GlobalSession.getUserName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        JPanel rightButtons = new JPanel();
        rightButtons.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightButtons.setOpaque(false); // Transparent background

        JButton myCartButton = new JButton("My Cart");
        myCartButton.addActionListener(e -> {
            if (GlobalSession.getUserId() <= 0) {
                notSignedInPopup("You need to sign in to view your cart.");
            } else {
                for (Component comp : centerPanel.getComponents()) {
                    if ("CartPage".equals(comp.getName())) {
                        centerPanel.remove(comp);
                        break;
                    }
                }

                
                JPanel cartContent = ShoppingCart.createCartPage(cardLayout, centerPanel);
                cartContent.setName("CartPage");

               
                JPanel fullCartPanel = new JPanel(new BorderLayout());
                fullCartPanel.setName("CartPage");
                fullCartPanel.add(cartContent, BorderLayout.CENTER);

             
                JPanel buttonPanel = new JPanel(new FlowLayout());

                JButton keepShoppingButton = new JButton("Keep Shopping");
                keepShoppingButton.addActionListener(ev -> cardLayout.show(centerPanel, "Products"));

                JButton checkoutButton = new JButton("Checkout Now");
                checkoutButton.addActionListener(ev -> JOptionPane.showMessageDialog(frame, "Proceeding to checkout (not implemented yet)."));

                buttonPanel.add(keepShoppingButton);
                buttonPanel.add(checkoutButton);

                fullCartPanel.add(buttonPanel, BorderLayout.SOUTH);

               
                centerPanel.add(fullCartPanel, "Cart");
                cardLayout.show(centerPanel, "Cart");
                centerPanel.revalidate();
                centerPanel.repaint();
            }
        });

        JButton myAccountButton = new JButton("My Account");
        myAccountButton.addActionListener(e -> {
            if (GlobalSession.getUserId() <= 0) {
                notSignedInPopup("You need to sign in to access your account.");
            } else {
            	 goToCustomerDashboard(); 
            }
        });

        rightButtons.add(myCartButton);
        rightButtons.add(myAccountButton);

        topPanel.add(welcomeLabel, BorderLayout.WEST);
        topPanel.add(rightButtons, BorderLayout.EAST);


        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(150, 600)); // Fixed width for navigation
        leftPanel.setBackground(Color.LIGHT_GRAY);
        
        mensButton(leftPanel);
        womensButton(leftPanel);
        couponButton(leftPanel);
        mainMenuButton(leftPanel);
       

        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);
        
        JPanel productsPage = createProductsPage();
  

        centerPanel.add(productsPage, "Products");
   
        cardLayout.show(centerPanel, "Products");

        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        frame.getContentPane().add(leftPanel, BorderLayout.WEST);
        frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createProductsPage() {
        JPanel productsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        JLabel logoLabel = new JLabel("Kid's Clothing", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 30));

        JPanel logoPanel = new JPanel();
        logoPanel.add(logoLabel);

        JPanel container = new JPanel(new BorderLayout());
        container.add(logoPanel, BorderLayout.NORTH);

        try {
            String query = "SELECT item_id, item_name, item_price, item_size, in_stock FROM inventory WHERE dept_id = 3";
            PreparedStatement stm = Database.connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            Map<String, List<ItemVariant>> itemMap = new HashMap<>();
            
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String name = rs.getString("item_name");
                String size = rs.getString("item_size");
                String priceString = rs.getString("item_price"); 
                if (priceString != null && priceString.startsWith("$")) {
                    priceString = priceString.substring(1);
                }

                double price = Double.parseDouble(priceString);
                int stock = rs.getInt("in_stock");

                String key = name + "_" + price;
                itemMap.putIfAbsent(key, new ArrayList<>());
                itemMap.get(key).add(new ItemVariant(itemId, size, price, stock));
            }

            for (Map.Entry<String, List<ItemVariant>> entry : itemMap.entrySet()) {
                List<ItemVariant> variants = entry.getValue();
                if (!variants.isEmpty()) {
                    String name = entry.getKey().split("_")[0];
                    double price = variants.get(0).price;
                    addProductWithSizes(productsPanel, name, price, variants);
                    
                   
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading products from database.");
        }

        JScrollPane scrollPane = new JScrollPane(productsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        container.add(scrollPane, BorderLayout.CENTER);
        return container;
    }


    private void addProductWithSizes(JPanel panel, String name, double price, List<ItemVariant> variants) {
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        productPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        productPanel.setPreferredSize(new Dimension(200, 250));

        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel priceLabel = new JLabel("$" + price, SwingConstants.CENTER);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JComboBox<String> sizeBox = new JComboBox<>();
        for (ItemVariant variant : variants) {
            sizeBox.addItem(variant.size + " (" + variant.stock + " left)");
        }

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addToCartButton.setFont(new Font("Arial", Font.BOLD, 14));
        addToCartButton.setBackground(new Color(0, 153, 76));
        addToCartButton.setForeground(Color.BLACK);
        addToCartButton.setFocusPainted(false);

        addToCartButton.addActionListener(e -> {
            if (GlobalSession.getUserId() <= 0) {
                notSignedInPopup("You need to sign in to add items to your cart.");
            } else {
                int selectedIndex = sizeBox.getSelectedIndex();
                if (selectedIndex >= 0) {
                    ItemVariant selected = variants.get(selectedIndex);
                    if (selected.stock > 0) {
                        boolean added = ShoppingCart.addItem(selected.itemId, name, selected.size, selected.price, selected.stock);
                        if (added) {
                            JOptionPane.showMessageDialog(frame, name + " (" + selected.size + ") added to cart.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Out of stock for selected size.");
                    }
                }
            }
        });

        productPanel.add(Box.createVerticalStrut(10));
        productPanel.add(nameLabel);
        productPanel.add(priceLabel);
        productPanel.add(sizeBox);
        productPanel.add(Box.createVerticalGlue());
        productPanel.add(addToCartButton);
        productPanel.add(Box.createVerticalStrut(10));

        panel.add(productPanel);
    }

    private static class ItemVariant {
        int itemId;
        String size;
        double price;
        int stock;

        ItemVariant(int itemId, String size, double price, int stock) {
            this.itemId = itemId;
            this.size = size;
            this.price = price;
            this.stock = stock;
        }
    }



    // Pop-up to prompt user to sign in
    private void notSignedInPopup(String message) {
        int option = JOptionPane.showConfirmDialog(frame, message + " Would you like to sign in?", "Not Signed In", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            frame.dispose();
            new SignIn(); // Redirect to Sign In screen
        }
    }

    
    private void mensButton(JPanel leftPanel) {
        JButton btn = new JButton("Kid's Clothing");
        btn.addActionListener(e -> goToKidsClothing());
        leftPanel.add(btn);
    }

    private void womensButton(JPanel leftPanel) {
        JButton btn = new JButton("Women's Clothing");
        btn.addActionListener(e -> goToWomensClothing());
        leftPanel.add(btn);
    }

  
    private void couponButton(JPanel leftPanel) {
        JButton btn = new JButton("Today's Deals");
        btn.addActionListener(e -> goToTodaysDeals());
        leftPanel.add(btn);
    }

    private void mainMenuButton(JPanel leftPanel) {
        JButton btn = new JButton("Back to Main Menu");
        btn.addActionListener(e -> goToStoreMainMenu());
        leftPanel.add(btn);
    }

    
    public void goToWomensClothing() {
        frame.dispose();   
        new WomensClothing(); 
    }
   
    
    public void goToKidsClothing() {
        frame.dispose();  
        new MensClothing();
    }
    
    public void goToTodaysDeals() {
        frame.dispose();  
        new TodaysDeals(); 
    }
   
    public void goToStoreMainMenu() {
        frame.dispose();  
        new storeMainMenu(); 
    }
    
    public void goToCustomerDashboard() {
        frame.dispose();         
        new CustomerDashboard(); 

     }


   
   
}