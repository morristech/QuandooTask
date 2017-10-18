package krtkush.github.io.quandootask.TableActivity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import krtkush.github.io.quandootask.DaoSession;
import krtkush.github.io.quandootask.QuandooTaskApplication;
import krtkush.github.io.quandootask.R;
import krtkush.github.io.quandootask.Table;
import krtkush.github.io.quandootask.TableDao;

/**
 * Created by kartikeykushwaha on 16/10/17.
 */

public class TableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Table> tableList;
    private WeakReference<Context> context;
    private TableDao tableDao;

    public TableListAdapter(List<Table> tableList, Context context) {

        this.tableList = tableList;
        this.context = new WeakReference<>(context);

        DaoSession daoSession = QuandooTaskApplication
                .getApplicationInstance().getDaoSession();
        tableDao = daoSession.getTableDao();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.table_list_item_layout, parent, false);
        viewHolder = new TableViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        final TableViewHolder tableViewHolder = (TableViewHolder) viewHolder;

        tableViewHolder.tableNumber.setText("Table number " + String.valueOf(position));

        if (tableList.get(position).getReservationStatus()) {
            tableViewHolder.tableItem.setBackgroundColor(Color.GREEN);
            tableViewHolder.statusText.setText("(Free)");
        } else {
            tableViewHolder.tableItem.setBackgroundColor(Color.RED);
            tableViewHolder.statusText.setText("(Reserved)");
        }
    }

    @Override
    public int getItemCount() {

        if (tableList != null && tableList.size() > 0)
            return tableList.size();
        else
            return 0;
    }

    private class TableViewHolder extends RecyclerView.ViewHolder {

        private TextView tableNumber;
        private TextView statusText;
        private LinearLayout tableItem;

        public TableViewHolder(View view) {
            super(view);

            tableNumber = (TextView) view.findViewById(R.id.tableNumber);
            statusText = (TextView) view.findViewById(R.id.statusText);
            tableItem = (LinearLayout) view.findViewById(R.id.tableItem);

            tableItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Table table = tableDao.queryBuilder()
                            .where(TableDao.Properties.Id.eq(getAdapterPosition() + 1)).list().get(0);

                    if (table.getReservationStatus()) {

                        // Waiter has marked the table as reserved.

                        table.setReservationStatus(false);
                        statusText.setText("(Reserved)");
                        tableDao.update(table);
                        tableItem.setBackgroundColor(Color.RED);
                    } else {

                        // Waiter has marked the table as free.

                        table.setReservationStatus(true);
                        statusText.setText("(Free)");
                        tableDao.update(table);
                        tableItem.setBackgroundColor(Color.GREEN);
                    }
                }
            });
        }
    }
}
