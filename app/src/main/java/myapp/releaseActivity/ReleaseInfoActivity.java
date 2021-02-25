package myapp.releaseActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.simplifiedcoding.simplifiedcoding.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import myapp.JSONParser;
import myapp.api.ApiBuilder;
import myapp.api.MyApi;
import myapp.model.Product;
import myapp.model.Release;
import myapp.modelView.ProductView;
import myapp.modelView.ReleaseView;

public class ReleaseInfoActivity extends AppCompatActivity {

    protected RecyclerView mRecyclerView;
    protected ProductAdapter mProductAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<ProductView> mProductViews;

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

        mRecyclerView = findViewById(R.id.releasesProdRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);

        apiService = new ApiBuilder().getApiService();
        mProductsList = new ArrayList<Product>();
        mProductViews = new ArrayList<ProductView>();

        // get releases via async request
        // getReleases();

        mProductAdapter = new ProductAdapter(mProductViews);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mProductAdapter);

        products = new ArrayList<HashMap<String, String>>();
        new getProductsList().execute();

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    public class ProductAdapter extends RecyclerView.Adapter<ReleaseActivity.ReleaseAdapter.ViewHolder> {

        private static final String TAG = "ProductAdapter";

        private List<ProductView> mProductView;

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
         * @param relView List<ReleaseView> containing the data to populate views to be used by RecyclerView.
         */
        public ProductAdapter(List<ProductView> prodView) {
            mProductView = prodView;
        }

        // Create new views (invoked by the layout manager)
        @NonNull
        @Override
        public ReleaseActivity.ReleaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
            holder.getIdTextView().setText(String.valueOf(mReleaseView.get(position).getId()));
            holder.getDateTimeTextView().setText(mReleaseView.get(position).getCreationDateTime());
            holder.getReleaseStatusTextView().setText(String.valueOf(mReleaseView.get(position).getStatus()));
            holder.getEmployeeSurnameTextView().setText(mReleaseView.get(position).getSurname());
            holder.getEmployeeNameTextView().setText(mReleaseView.get(position).getName());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mReleaseView == null ? 0 : mReleaseView.size();
        }

        public List<ReleaseView> getReleaseViews() {
            return mReleaseView;
        }
    }

}
