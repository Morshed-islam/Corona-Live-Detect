package qtec.live.corona.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import qtec.live.corona.model.GetGlobalModel;

/**
 * Created By Morshed
 * Software Engineer -> Qtec Solution
 * Date 18/03/2020
 */

public class GlobalRecyclerViewAdapter extends RecyclerView.Adapter<GlobalRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<GetGlobalModel> list = new ArrayList<>();

    public GlobalRecyclerViewAdapter(Context mContext, List<GetGlobalModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public GlobalRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GlobalRecyclerViewAdapter.MyViewHolder holder, int position) {

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
