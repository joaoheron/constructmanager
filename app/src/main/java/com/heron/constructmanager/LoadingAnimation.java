package com.heron.constructmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingAnimation {

    private Activity activity;
    private AlertDialog dialog;

    LoadingAnimation(Activity a) {
        activity = a;
    }

    void loadingAnimationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    void dismissLoading() {
        dialog.dismiss();
    }
}

