package myapp.releaseActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import net.simplifiedcoding.simplifiedcoding.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtilsHC4;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import myapp.JSONParser;
import myapp.api.ApiBuilder;
import myapp.api.MyApi;
import myapp.model.Employee;
import myapp.model.Product;
import myapp.model.Release;
import myapp.model.ReleaseStatus;
import myapp.modelView.ProductView;
import myapp.modelView.ProductsOrderView;
import myapp.modelView.ReleaseView;

import static myapp.api.URLs.URL_GET_PRODUCTS_ORDERS;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;


public class ReleaseInfoActivity extends AppCompatActivity {

    protected RecyclerView mRecyclerView;
//    protected ProductAdapter mProductAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<ProductsOrderView> mProductViews;

    protected MyApi apiService;

    protected List<Product> mProductsList;
    private final String GET_RELEASES_TAG = "GET_RELEASES";

    JSONParser jsonParser = new JSONParser();

    public ArrayList<HashMap<String, String>> products;

    // JSON Node names
    private static final String TAG_SUCCESS = "error";
    private static final String TAG_MESSAGE = "message";

    private static final String TAG_ID = "id";

    private static final String TAG_EMPLOYEES = "employeeslist";

    private static final String TAG_EMPLOYEE = "employee";

    //private static final String TAG_NAME = "name";

    // employees JSONArray
    JSONArray employees = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_info_activity);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout,new GeneralReleaseInfFragment()).commit();

     /*   TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2")); */

        mRecyclerView = findViewById(R.id.releasesProdRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);

        apiService = new ApiBuilder().getApiService();
        mProductsList = new ArrayList<Product>();
        mProductViews = new ArrayList<ProductsOrderView>();

        // get releases via async request
        // getReleases();

//        mProductAdapter = new ProductAdapter(mProductViews);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setAdapter(mProductAdapter);

        products = new ArrayList<HashMap<String, String>>();
    //    new getProductsList().execute();

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

 /*   class getReleasesList extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
 /*       @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ReleaseInfoActivity.this.setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            // Building Parameters
            List<NameValuePair> parametres = new ArrayList<NameValuePair>();

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(URL_GET_PRODUCTS_ORDERS);
            String json = null;
            try {
                HttpResponse response = client.execute(request);
                HttpEntity httpEntity = response.getEntity();
                json = EntityUtilsHC4.toString(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return json;
        }
*/
        /**
         * After completing background task Dismiss the progress dialog
         * **/
 /*       protected void onPostExecute(String json) {

            Boolean result = null;
            String message = null;

            Log.d("All Products Orders: ", json);
            // dismiss the dialog after getting all releases
            try {
                JSONObject jsonObj = new JSONObject(json);
                // wybranie tablicy releases
                result = jsonObj.getBoolean("error");
                message = jsonObj.getString("message");

                JSONArray jsonArray = jsonObj.getJSONArray("object");
                for (int i = 0; i < jsonArray.length(); i++) {
                    // Wybranie pojedyńczego obiektu w tablicy - release
                    JSONObject jsonObj2 = jsonArray.getJSONObject(i);
                    // Wybranie poszczególnych
                    // id= jsonObj.getString("id").toInteger ?? // id konwertuj na int
                    String id = jsonObj2.getString("id");
                    String productName = jsonObj2.getString("productName");
                    String quantity = jsonObj2.getString("quantity");
                    JSONObject empl = jsonObj2.getJSONObject("employee");
                    Employee employee = new Employee(empl.getInt("id"),
                            empl.getString("symbol"),empl.getString("name"),
                            empl.getString("surname")) ;

                    HashMap<String, String> map = new HashMap<String, String>();

                    Release rel = new Release(Integer.parseInt(id), employee,  ReleaseStatus.enumOfValue(status), //ReleaseStatus.valueOf(status),
                            null, date_creation, null);
                    mReleasesList.add(rel);
                    mReleaseViews.add(new ReleaseView(rel.getId(),
                            rel.getCreationDateTime(),
                            rel.getStatus().name(),
                            rel.getEmployee().getSurname(),
                            rel.getEmployee().getName()));

                    // adding each child node to HashMap key => value
                    map.put("id", id);
                    map.put("date_creation", date_creation);
                    map.put("status", status);
                    map.put("id_employee", String.valueOf(employee.getId()));


                    // adding HashList to ArrayList
                    //releases.add(map);
                }
                mReleaseAdapter.notifyDataSetChanged();
            } catch(Exception e) {
                Log.e("Error", e.getMessage());
                Toast.makeText(getBaseContext(), "Error while parsing response - " + e.getMessage(),  Toast.LENGTH_LONG).show();
            }

            if (result != null && (result == false)) {

                ReleaseActivity.this
                        .setProgressBarIndeterminateVisibility(false);
                /*
                // updating UI from Background Thread
                runOnUiThread(new Runnable() {
                    public void run() {

                        ListAdapter adapter = new SimpleAdapter(
                                ReleaseActivity.this, releases,
                                R.layout.content_release, new String[] { //TAG_ID,
                                "id", "date_creation", "status", "surname", "name"}, new int[] { //R.id.id,
                                R.id.id_release, R.id.date_creation,  R.id.status, R.id.employee_surname, R.id.employee_name});
                        // updating listview
                        setListAdapter(adapter);
                    }
                });


                Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG)
                        .show();

            }
        }

    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

        private static final String TAG = "ProductAdapter";

        private List<ProductsOrderView> mProductsView;

        // VievHolder do przechowywania widoków elementów listy
        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView mIdTextView;
            private final TextView mNameTextView;
            private final TextView mStatusTextView;
            private final TextView mRequestedQuantityTextView;
         //   private final TextView mEmployeeNameTextView;

            public ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                      //  openReleaseInfoActivity();
                    }
                });
                mIdTextView = (TextView) v.findViewById(R.id.id_product_order);
                mNameTextView = (TextView) v.findViewById(R.id.product_name);
                mRequestedQuantityTextView = (TextView) v.findViewById(R.id.requested_quantity);
                mStatusTextView = (TextView) v.findViewById(R.id.product_order_status);
            //    mEmployeeNameTextView = (TextView) v.findViewById(R.id.employee_name);
            }



            public TextView getIdTextView() {
                return mIdTextView;
            }

            public TextView getNameTextView() {
                return mNameTextView;
            }

            public TextView getRequestedQuantityTextView() {
                return mRequestedQuantityTextView;
            }

            public TextView getStatusTextView() {
                return mStatusTextView;
            }

        /*    public TextView getEmployeeNameTextView() {
                return mEmployeeNameTextView;
            }*/
        }



        /**
         * Initialize the dataset of the Adapter.
         *
         * @param prodView List<ReleaseView> containing the data to populate views to be used by RecyclerView.
         */
/*        public ProductAdapter(List<ProductsOrderView> prodView) {
            mProductsView = prodView;
        }

        // Create new views (invoked by the layout manager)
        @NonNull
        @Override
        public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.content_product_orders, parent, false);
            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Log.d(TAG, "Element " + position + " set.");
            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            holder.getIdTextView().setText(String.valueOf(mProductsView.get(position).getId()));
            holder.getNameTextView().setText(mProductsView.get(position).getName());
            holder.getRequestedQuantityTextView().setText(String.valueOf(mProductsView.get(position).getReqQuantity()));
            holder.getStatusTextView().setText(mProductsView.get(position).getStatus());
         //   holder.getEmployeeNameTextView().setText(mReleaseView.get(position).getName());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mProductsView == null ? 0 : mProductsView.size();
        }

        public List<ProductsOrderView> getProductView() {
            return mProductsView;
        }
    }

}

 */
