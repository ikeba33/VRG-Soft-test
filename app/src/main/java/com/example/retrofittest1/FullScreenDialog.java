package com.example.retrofittest1;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

public class FullScreenDialog extends DialogFragment implements View.OnClickListener {
    private Bitmap thumbnailImgV;
    ImageView full_screen;//null
    Button btn_save;
    ImageButton btn_close;

    public FullScreenDialog(Bitmap thumbnailImgV) {
        this.thumbnailImgV = thumbnailImgV;
    }


    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_fragment, container, false);
        full_screen = v.findViewById(R.id.full_screen);
        btn_close = v.findViewById(R.id.btn_close);
        btn_save = v.findViewById(R.id.btn_save);

        full_screen.setImageBitmap(thumbnailImgV);

        btn_close.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                dismiss();
                break;
            case R.id.btn_save:

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MainActivity.MY_PERMISSIONS_REQUEST_CODE);
                    }
                }
                ImageManager.saveImage(thumbnailImgV, getContext());

        }
    }
}
