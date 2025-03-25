package com.example.beanupnorth;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class PlaceOrder extends AppCompatActivity {

    ImageView imQrCode;
    Bitmap qrBitMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_place_order);

        imQrCode = findViewById(R.id.iVQrCode);
        QRCodeWriter writer = new QRCodeWriter();
        try{
            String orderId = getIntent().getExtras().getString("orderId");
            BitMatrix bitMatrix = writer.encode(orderId, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            qrBitMap = barcodeEncoder.createBitmap((bitMatrix));

            imQrCode.setImageBitmap(qrBitMap);
        }catch (WriterException e){
            e.printStackTrace();
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}