package qtec.live.corona.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import qtec.live.corona.R;
import qtec.live.corona.model.GetGlobalModel;

public class CountryRecyclerAdapter extends RecyclerView.Adapter<CountryRecyclerAdapter.MyViewHolder>{
    private Context mContext;
    private List<GetGlobalModel> list = new ArrayList<>();

    public CountryRecyclerAdapter(Context mContext, List<GetGlobalModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public CountryRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_custom_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryRecyclerAdapter.MyViewHolder holder, int position) {
        holder.name.setText(contacts.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
