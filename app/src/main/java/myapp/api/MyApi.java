package myapp.api;

import java.util.List;

import myapp.model.Employee;
import myapp.model.Product;
import myapp.model.Release;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static myapp.api.URLs.URL_ADD_RELEASE;
import static myapp.api.URLs.URL_GET_ALL_EMPLOYEES;
import static myapp.api.URLs.URL_GET_ALL_PRODUCTS;
import static myapp.api.URLs.URL_GET_ALL_RELEASES;

public interface MyApi {

    @GET(URL_GET_ALL_EMPLOYEES)
    Call<ResponseContainer<List<Employee>>> getEmployees();

    @GET(URL_GET_ALL_PRODUCTS)
    Call<ResponseContainer<List<Product>>> getProducts();

    @POST(URL_ADD_RELEASE)
    Call<ResponseContainer<Release>> addRelease(@Body Release release);

    @GET(URL_GET_ALL_RELEASES)
    Call<ResponseContainer<List<Release>>> getReleases();

    // @Query("apicall") String apiCall
    /*
    @GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @POST("users/new")
    Call<User> createUser(@Body User user);
    */
}
