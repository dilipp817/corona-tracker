package com.corona.coronatracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.corona.coronatracker.R;
import com.corona.coronatracker.models.DistrictData;

import java.util.List;

public class CoronaCaseAdapter extends RecyclerView.Adapter<CoronaCaseAdapter.CoronaCaseViewHolder> {

    private List<DistrictData> caseList;

    public class CoronaCaseViewHolder extends RecyclerView.ViewHolder {
        public TextView place, caseCount, delta;
        public ImageView deltaIndicator;

        public CoronaCaseViewHolder(View view) {
            super(view);
            place = view.findViewById(R.id.placeName);
            caseCount = view.findViewById(R.id.caseCount);
            delta = view.findViewById(R.id.delta);
            deltaIndicator = (ImageView) view.findViewById(R.id.ic_increase);
        }
    }


    public CoronaCaseAdapter(List<DistrictData> caseList) {
        this.caseList = caseList;
    }

    @Override
    public CoronaCaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cases_item_element, parent, false);

        return new CoronaCaseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CoronaCaseViewHolder holder, int position) {
        DistrictData districtData = caseList.get(position);
        holder.place.setText(districtData.getDistrict());
        holder.caseCount.setText(String.valueOf(districtData.getConfirmedCount()));

        int deltaCount = districtData.getConfirmedCountDelta();
        if (deltaCount > 0) {
            holder.delta.setText(String.valueOf(deltaCount));
            holder.delta.setVisibility(View.VISIBLE);
            holder.deltaIndicator.setVisibility(View.VISIBLE);
        }
        else {
            holder.delta.setVisibility(View.INVISIBLE);
            holder.deltaIndicator.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return caseList.size();
    }
}

