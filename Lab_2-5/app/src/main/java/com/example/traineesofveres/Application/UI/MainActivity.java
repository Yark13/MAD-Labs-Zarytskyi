package com.example.traineesofveres.Application.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.traineesofveres.R;
import com.example.traineesofveres.databinding.ActivityMainBinding;
import com.example.traineesofveres.Application.UI.profile.ProfileFragment;
import com.example.traineesofveres.Application.UI.quotes.QuotesFragment;
import com.example.traineesofveres.Application.UI.taplike.TapLikeFragment;
import com.example.traineesofveres.Application.UI.toplist.TopListFragment;

import java.util.HashMap;
import java.util.Map;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final Map<Integer, Fragment> _idFragmentDictionary = new HashMap<Integer, Fragment>();

    public  MainActivity(){
        FillIdFragmentDictionary();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ChangeFragment(_idFragmentDictionary.get(R.id.navigation_profile));

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            ChangeFragment(_idFragmentDictionary.get(item.getItemId()));

            return true;
        });
    }

    private void ChangeFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    private void FillIdFragmentDictionary(){
        _idFragmentDictionary.put(R.id.navigation_profile, new ProfileFragment());
        _idFragmentDictionary.put(R.id.navigation_taplike, new TapLikeFragment());
        _idFragmentDictionary.put(R.id.navigation_quotes, new QuotesFragment());
        _idFragmentDictionary.put(R.id.navigation_toplist, new TopListFragment());
    }
}