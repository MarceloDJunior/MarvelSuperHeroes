package com.marcelo.marvelsuperheroes;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QrCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QrCodeFragment extends Fragment {

    private QRCodeReaderView qrCodeReaderView;
    private View viewQrCodeFrame;
    private TextView tvPermission;
    public boolean shown = false;

    private static final int PERMISSION_REQUEST_CODE = 200;

    private boolean alertIsShowing = false;

    public QrCodeFragment() {
        // Required empty public constructor
    }

    public static QrCodeFragment newInstance() {
        QrCodeFragment fragment = new QrCodeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_qr_code, container, false);

        tvPermission = v.findViewById(R.id.tv_permission);
        viewQrCodeFrame = v.findViewById(R.id.view_qrcode_frame);
        qrCodeReaderView = v.findViewById(R.id.qrdecoderview);

        if(checkPermission()) {
            initQrCode();
        } else {
            requestPermission();
        }

        return v;
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        this.requestPermissions(
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initQrCode();
            } else {
                qrCodeReaderView.setVisibility(View.GONE);
                viewQrCodeFrame.setVisibility(View.GONE);
            }
        }
    }

    private void initQrCode() {
        tvPermission.setVisibility(View.GONE);
        qrCodeReaderView.setVisibility(View.VISIBLE);
        viewQrCodeFrame.setVisibility(View.VISIBLE);
        qrCodeReaderView.setOnQRCodeReadListener(qrCodeListener);
        qrCodeReaderView.setQRDecodingEnabled(true);
        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);
        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);
        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();
    }

    private QRCodeReaderView.OnQRCodeReadListener qrCodeListener = new QRCodeReaderView.OnQRCodeReadListener() {
        @Override
        public void onQRCodeRead(String text, PointF[] points) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle(R.string.alert_success)
                    .setMessage(R.string.qr_code_success);

            // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            alertDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    alertIsShowing = false;
                }
            });

            if (!alertIsShowing) {
                alertIsShowing = true;
                alertDialog.show();
            }
        }
    };

}