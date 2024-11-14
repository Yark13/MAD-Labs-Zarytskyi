package com.example.traineesofveres.Application.UI;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.traineesofveres.Application.UI.login.LoginActivity;
import com.example.traineesofveres.Domain.Connection.ConnectionManager.IConnectionManager;
import com.example.traineesofveres.Domain.Connection.NetworkChangeReceiver;
import com.example.traineesofveres.R;
import com.example.traineesofveres.databinding.ActivityMainBinding;
import com.example.traineesofveres.Application.UI.profile.ProfileFragment;
import com.example.traineesofveres.Application.UI.quotes.QuotesFragment;
import com.example.traineesofveres.Application.UI.taplike.TapLikeFragment;
import com.example.traineesofveres.Application.UI.toplist.TopListFragment;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    IConnectionManager ConnectionManager;

    public static final String UserPrefs = "UserPrefs";
    public static final String UserIdPref = "UserId";

    private NetworkChangeReceiver _networkChangeReceiver;
    private ActivityMainBinding binding;
    private final Map<Integer, Fragment> _idFragmentDictionary = new HashMap<Integer, Fragment>();
    private int _traineeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        _networkChangeReceiver = new NetworkChangeReceiver(ConnectionManager);

        _traineeId = getIntent().getIntExtra(LoginActivity.INTENT_PARAM_KEY_TRAINEE_ACCOUNT, 0);

        FillIdFragmentDictionary();

        ChangeFragment(_idFragmentDictionary.get(R.id.navigation_profile));

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            ChangeFragment(_idFragmentDictionary.get(item.getItemId()));

            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(_networkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        registerReceiver(_networkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    private void ChangeFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    private void FillIdFragmentDictionary(){
        _idFragmentDictionary.put(R.id.navigation_profile, ProfileFragment.newInstance(_traineeId));
        _idFragmentDictionary.put(R.id.navigation_taplike, TapLikeFragment.newInstance(_traineeId));
        _idFragmentDictionary.put(R.id.navigation_quotes, QuotesFragment.newInstance(_traineeId));
        _idFragmentDictionary.put(R.id.navigation_toplist, TopListFragment.newInstance(_traineeId));
    }
}