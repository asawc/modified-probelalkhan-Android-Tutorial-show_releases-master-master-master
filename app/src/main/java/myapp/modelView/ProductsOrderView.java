package myapp.modelView;

public class ProductsOrderView {

    private int id;
    private String status;
    private String name;
    private int req_quantity;

    public ProductsOrderView(int id, String status, String name, int req_quantity) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.req_quantity = req_quantity;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getReqQuantity() {
        return req_quantity;
    }
}