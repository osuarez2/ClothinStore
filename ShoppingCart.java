import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class ShoppingCart {

    private static List<CartItem> cartItems = new ArrayList<>();

    public static boolean addItem(int itemId, String name, String size, double price, int inStock) {
        for (CartItem item : cartItems) {
            if (item.getItemId() == itemId && item.getSize().equals(size)) {
                if (item.getQuantity() < inStock) {
                    item.increaseQuantity();
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Only " + inStock + " in stock.");
                    return false;
                }
            }
        }

        if (inStock > 0) {
            cartItems.add(new CartItem(itemId, name, size, price, inStock));
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Out of stock.");
            return false;
        }
    }

    public static List<CartItem> getItems() {
        return cartItems;
    }

    public static void clearCart() {
        cartItems.clear();
    }

    public static JPanel createCartPage(CardLayout cardLayout, JPanel centerPanel) {
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setName("CartPage");

        if (cartItems.isEmpty()) {
            JLabel emptyLabel = new JLabel("Your Cart is Empty.");
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            cartPanel.add(Box.createVerticalGlue());
            cartPanel.add(emptyLabel);
            cartPanel.add(Box.createVerticalGlue());
        } else {
            for (CartItem item : cartItems) {
                JLabel itemLabel = new JLabel(item.toString());
                itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                cartPanel.add(itemLabel);
            }

            cartPanel.add(Box.createVerticalStrut(20));
        }
        

        return cartPanel;
    
    }
    public static double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

}
