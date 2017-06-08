package me.ealanhill.wtfitnesschallenge;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import me.ealanhill.wtfitnesschallenge.databinding.ActivityCalendarBinding;

public class CalendarActivity extends AppCompatActivity {

    private ActivityCalendarBinding binding;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calendar);
        binding.calendarRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        binding.calendarRecyclerView.setLayoutManager(linearLayoutManager);
    }
}
