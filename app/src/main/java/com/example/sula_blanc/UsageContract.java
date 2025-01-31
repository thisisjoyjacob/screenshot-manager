package com.example.sula_blanc;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public interface UsageContract {

    interface View{
        void onUsageStatsRetrieved(List<UsageStatsWrapper> list);
        void onUserHasNoPermission();
    }

    interface Presenter{
        void retrieveUsageStats();
    }

    class UsageStatAdapter extends RecyclerView.Adapter<UsageStatVH> {

        private List<UsageStatsWrapper> list;

        public UsageStatAdapter(){
            list = new ArrayList<>();
        }

        @Override
        public UsageStatVH onCreateViewHolder(ViewGroup parent, int viewType) {
            android.view.View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usage_stat_item, parent, false);
            return new UsageStatVH(view);
        }

        @Override
        public void onBindViewHolder(UsageStatVH holder, int position) {
            holder.bindTo(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setList(List<UsageStatsWrapper> list) {
            this.list = list;
            notifyDataSetChanged();
        }
    }
}
