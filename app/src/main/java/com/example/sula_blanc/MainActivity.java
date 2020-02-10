package com.example.sula_blanc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements UsageContract.View {

    private ProgressBar progressBar;
    private TextView permissionMessage;

    private UsageContract.Presenter presenter;
    private UsageContract.UsageStatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progress_bar);
        permissionMessage = findViewById(R.id.grant_permission_message);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsageContract.UsageStatAdapter();
        recyclerView.setAdapter(adapter);

        permissionMessage.setOnClickListener(v -> openSettings());

        presenter = new UsagePresenter(this, this);
    }

    private void openSettings() {
        try{
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }
        catch (Exception e){
            Toast.makeText(this, "Error 101 ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgressBar(true);
        presenter.retrieveUsageStats();
    }

    @Override
    public void onUsageStatsRetrieved(List<UsageStatsWrapper> list) {
        showProgressBar(false);
        permissionMessage.setVisibility(GONE);
        adapter.setList(list);
    }

    @Override
    public void onUserHasNoPermission() {
        showProgressBar(false);
        permissionMessage.setVisibility(VISIBLE);
    }

    private void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(VISIBLE);
        } else {
            progressBar.setVisibility(GONE);
        }
    }
}
