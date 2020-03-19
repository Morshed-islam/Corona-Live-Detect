package qtec.live.corona.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import qtec.live.corona.R;
import qtec.live.corona.model.GetCountryModel;

/**
 * Created By Morshed
 * Software Engineer -> Qtec Solution
 * Date 18/03/2020
 */
public class CountryRecyclerAdapter extends RecyclerView.Adapter<CountryRecyclerAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private List<GetCountryModel> list = new ArrayList<>();
    private List<GetCountryModel> filteredDataList;

    public CountryRecyclerAdapter(Context mContext, List<GetCountryModel> list) {
        this.mContext = mContext;
        this.filteredDataList = list;
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

        DecimalFormat formatter = new DecimalFormat("###,###");

        holder._country.setText(""+filteredDataList.get(position).getCountry());

        String cases = formatter.format(filteredDataList.get(position).getCases());
        holder._cases.setText(cases);

        String cases_today = formatter.format(filteredDataList.get(position).getTodayCases());
        holder._cases_today.setText(cases_today);

        String cases_active = formatter.format(filteredDataList.get(position).getActive());
        holder._cases_active.setText(cases_active);

        String total_death = formatter.format(filteredDataList.get(position).getDeaths());
        holder._death.setText(total_death);

        String today_death = formatter.format(filteredDataList.get(position).getTodayDeaths());
        holder._death_today.setText(today_death);

        String recovered = formatter.format(filteredDataList.get(position).getRecovered());
        holder._recovered.setText(recovered);

        String critical = formatter.format(filteredDataList.get(position).getCritical());
        holder._critical.setText(critical);


    }

    @Override
    public int getItemCount() {
        return filteredDataList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView _cases,_cases_today,_cases_active;
        public TextView _death,_death_today;
        public TextView _recovered,_critical;
        public TextView _country;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            _country = itemView.findViewById(R.id.country);
            _cases = itemView.findViewById(R.id.tv_cases);
            _cases_today = itemView.findViewById(R.id.tv_cases_today);
            _cases_active = itemView.findViewById(R.id.tv_cases_active);
            _death = itemView.findViewById(R.id.tv_deaths);
            _death_today = itemView.findViewById(R.id.tv_deaths_today);
            _recovered = itemView.findViewById(R.id.tv_recovered);
            _critical = itemView.findViewById(R.id.tv_critical);

        }


    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {

                    filteredDataList = list;

                } else {

                    ArrayList<GetCountryModel> tempFilteredList = new ArrayList<>();

                    for (GetCountryModel country : list) {

                        // search for user title
                        if (country.getCountry().toLowerCase().contains(searchString)) {

                            tempFilteredList.add(country);
                        }
                    }

                    filteredDataList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredDataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredDataList = (ArrayList<GetCountryModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }




    public StringBuilder convertNumberIntoBangla(String str){
        char[] arabicChars = {'০','১','২','৩','৪','৫','৬','৭','৮','৯'};
        StringBuilder builder = new StringBuilder();
        for(int i =0;i<str.length();i++)
        {
            if(Character.isDigit(str.charAt(i)))
            {
                builder.append(arabicChars[(int)(str.charAt(i))-48]);
            }
            else
            {
                builder.append(str.charAt(i));
            }
        }
        return builder;
    }


}
