package com.example.traineesofveres.Domain.Connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.traineesofveres.Domain.Connection.ConnectionManager.IConnectionManager;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private final IConnectionManager _connectionManager;
    private boolean _isNoInternetToastShown = false;
    private boolean _wasDisconnected = false;

    public NetworkChangeReceiver(IConnectionManager connectionManager) {
        _connectionManager = connectionManager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = _connectionManager.isInternetAvailable(context);

        if (!isConnected) {
            ShowNoInternetDialog(context);
            _wasDisconnected = true;
        } else {
            if (_wasDisconnected) {
                ShowConnectionRestoredToast(context);
                _wasDisconnected = false;
            }
            _isNoInternetToastShown = false;
        }
    }

    private void ShowNoInternetDialog(Context context) {
        if (!_isNoInternetToastShown) {
            Toast.makeText(context, "No Internet Connection. Please check your network.", Toast.LENGTH_LONG).show();
            _isNoInternetToastShown = true;
        }
    }

    private void ShowConnectionRestoredToast(Context context) {
        Toast.makeText(context, "Internet connection restored!", Toast.LENGTH_SHORT).show();
    }
}
