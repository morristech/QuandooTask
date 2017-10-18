package krtkush.github.io.quandootask.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import krtkush.github.io.quandootask.Constants;
import krtkush.github.io.quandootask.NetworkClient.CustomerListDM;
import krtkush.github.io.quandootask.R;
import krtkush.github.io.quandootask.TableActivity.TableSelectorActivity;

/**
 * Created by kartikeykushwaha on 16/10/17.
 */

public class CustomerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CustomerListDM> customerList;
    private WeakReference<Context> context;

    public CustomerListAdapter(ArrayList<CustomerListDM> customerList, Context context) {

        this.customerList = customerList;
        this.context = new WeakReference<>(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.customer_list_item_layout, parent, false);
        viewHolder = new CustomerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        final CustomerViewHolder customerViewHolder = (CustomerViewHolder) viewHolder;

        customerViewHolder.customerName
                .setText(customerList.get(position).getCustomerFirstName()
                + " " + customerList.get(position).getCustomerLastName());

        customerViewHolder.customerId.setText(String.valueOf(customerList.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        if (customerList != null && customerList.size() > 0) {
            return customerList.size();
        } else {
            return 0;
        }
    }

    private class CustomerViewHolder extends RecyclerView.ViewHolder {

        private TextView customerName;
        private TextView customerId;
        private RelativeLayout customerListItem;

        private CustomerViewHolder(View view) {
            super(view);

            customerName = (TextView) view.findViewById(R.id.customerName);
            customerId = (TextView) view.findViewById(R.id.customerID);
            customerListItem = (RelativeLayout) view.findViewById(R.id.customerListItem);

            customerListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent goToTableSelector = new Intent(context.get(), TableSelectorActivity.class);
                    goToTableSelector.putExtra(Constants.CUSTOMER_ID,
                            customerList.get(getAdapterPosition()).getId());
                    context.get().startActivity(goToTableSelector);
                }
            });
        }
    }
}
