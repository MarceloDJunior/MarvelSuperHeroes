package com.marcelo.marvelsuperheroes;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QrCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QrCodeFragment extends Fragment {

    private QRCodeReaderView qrCodeReaderView;

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

        qrCodeReaderView = (QRCodeReaderView) v.findViewById(R.id.qrdecoderview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                initQrCode();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 401);
            }
        } else {
            initQrCode();
        }


        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 401) {
            if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initQrCode();
            } else {
                Toast.makeText(getContext(), "Você precisa dar permissão de acesso a camera", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initQrCode() {
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
                    .setTitle("QR Code")
                    .setMessage("QR Code lido com sucesso!");

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
            alertDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Continue with delete operation
                }
            });
            alertDialog.show();
        }
    };

}