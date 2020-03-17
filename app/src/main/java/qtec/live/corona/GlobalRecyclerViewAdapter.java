package qtec.live.corona;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import qtec.live.corona.model.GetGlobalModel;

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
