public class CartItem {
    private int itemId;
    private String name;
    private String size;
    private double price;
    private int quantity;
    private int inStock;

    public CartItem(int itemId, String name, String size, double price, int inStock) {
        this.itemId = itemId;
        this.name = name;
        this.size = size;
        this.price = price;
        this.inStock = inStock;
        this.quantity = 1;
    }

    public int getItemId() { return itemId; }
    public String getName() { return name; }
    public String getSize() { return size; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getInStock() { return inStock; }
    
    public double getTotalPrice() {
        return price * quantity;
    }

  
    public void setQuantity(int qty) {
        if (qty >= 1 && qty <= inStock) {
            this.quantity = qty;
        }
    }

    
    public void increaseQuantity() {
        if (quantity < inStock) {
            quantity++;
        }
    }
    
    public void decreaseQuantity() {
        if (quantity > 1) {
            quantity--;
        }
    }

    @Override
    public String toString() {
    	return name + " - " + size + " - $" + String.format("%.2f", price) + " (x" + quantity + ")";
    }
}
