package myapp.model;

import com.google.gson.annotations.SerializedName;

import java.time.ZonedDateTime;
import java.util.List;

public class Release {

    private final int id;
    private final Employee employee;
    private final ReleaseStatus status;
    private final List<ProductRelease> productsRelease;
    @SerializedName("creationDate")
    private final String creationDateTime;
    @SerializedName("realizationDate")
    private final String realizationDateTime;

    public Release(int id, Employee employee, ReleaseStatus status, List<ProductRelease> productsRelease,
                   String creationDateTime, String realizationDateTime) {
        this.id = id;
        this.employee = employee;
        this.status = status;
        this.productsRelease = productsRelease;
        this.creationDateTime = creationDateTime;
        this.realizationDateTime = realizationDateTime;
    }

    public int getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public ReleaseStatus getStatus() {
        return status;
    }

    public List<ProductRelease> getProductsRelease() {
        return productsRelease;
    }

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public String getRealizationDateTime() {
        return realizationDateTime;
    }
}
