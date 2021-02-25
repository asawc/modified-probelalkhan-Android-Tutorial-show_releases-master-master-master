package myapp.model;

import com.google.gson.annotations.SerializedName;

public class ProductRelease {

    //@SerializedName("id")
    private final int productId;
    private final ProductStatus status;
    private final int quantity;

    public ProductRelease(int productId, ProductStatus status, int quantity) {
        this.productId = productId;
        this.status = status;
        this.quantity = quantity;
    }

    public int getProduct() {
        return productId;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public int getQuantity() {
        return quantity;
    }
}
