package ch.picturedesk.sbbapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import calculator.picturedesk.ch.sbbapp.R;

public class ListViewAdapter extends ArrayAdapter<Train> {

    ArrayList<Train> manager;
    Context ctx;

    public ListViewAdapter(Context ctx, ArrayList<Train> manager) {
        super(ctx, R.layout.celllayout, manager);
        this.manager = manager;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return manager.size();
    }

    @Override
    public Train getItem(int i) {
        return manager.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View vi = view;

        if (vi == null) {
            LayoutInflater inflater = (LayoutInflater) ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.celllayout, null);
        }

        TextView departure = vi.findViewById(R.id.departure);
        TextView to = vi.findViewById(R.id.to);
        TextView name = vi.findViewById(R.id.name);

        Train train = manager.get(i);
        departure.setText(train.getDeparture());
        to.setText(train.getTo());
        name.setText(train.getName());

        return vi;
    }
}
