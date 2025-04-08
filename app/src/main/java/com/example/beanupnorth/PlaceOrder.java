package com.example.beanupnorth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class PlaceOrder extends AppCompatActivity {

    ImageView imQrCode;
    Bitmap qrBitMap;
    Boolean flag=false;
    Button buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_place_order);


        imQrCode = findViewById(R.id.iVQrCode);
        QRCodeWriter writer = new QRCodeWriter();
        try{
            String orderId="";
            Bundle extras =getIntent().getExtras();
            if(extras.containsKey("myorder"))
            {
                buttonBack = findViewById(R.id.btnBackToHomescreen);
                buttonBack.setText("Back to My Order");
                orderId = getIntent().getExtras().get("myorder").toString();
                flag=true;
            }else{

                orderId = getIntent().getExtras().get("orderId").toString();
            }



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
    public void  ONCLICKBACKHOMESCREEN (View v){
        if(flag==true)
        {
            finish();
        }else{
            startActivity(new Intent(PlaceOrder.this, HomeScreen.class));
        }

    }

    public void ONCLICKMAP(View v){
        startActivity(new Intent(PlaceOrder.this, Maps.class));
    }
}