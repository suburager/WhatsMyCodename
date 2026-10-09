package com.suburager.whatsmycodename;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends Activity {

    private TextView valDevice;
    private TextView valProduct;
    private TextView valBoard;
    private TextView valHardware;
    private TextView valModel;
    private TextView valManufacturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valDevice = (TextView) findViewById(R.id.val_device);
        valProduct = (TextView) findViewById(R.id.val_product);
        valBoard = (TextView) findViewById(R.id.val_board);
        valHardware = (TextView) findViewById(R.id.val_hardware);
        valModel = (TextView) findViewById(R.id.val_model);
        valManufacturer = (TextView) findViewById(R.id.val_manufacturer);

        showCodename();
    }

    private void showCodename() {
        // Build.DEVICE - это и есть кодовое имя устройства
        // (mako, hammerhead, jflte, rain и т.д.) в подавляющем
        // большинстве случаев. Остальные поля - для справки
        // и на случай расхождений у конкретного вендора/варианта.
        valDevice.setText(safe(Build.DEVICE));
        valProduct.setText(safe(Build.PRODUCT));
        valBoard.setText(safe(Build.BOARD));
        // Build.HARDWARE появилось только в API 8 (Android 2.2),
        // на 1.5/1.6 такого поля нет вовсе - читаем через reflection,
        // чтобы не падать на старых прошивках при компиляции под них.
        valHardware.setText(safe(getHardwareField()));
        valModel.setText(safe(Build.MODEL));
        valManufacturer.setText(safe(Build.MANUFACTURER));
    }

    private String getHardwareField() {
        try {
            Field f = Build.class.getField("HARDWARE");
            Object value = f.get(null);
            return value == null ? null : value.toString();
        } catch (Exception e) {
            // Поля нет на этой версии Android - это ожидаемо для 1.5/1.6
            return null;
        }
    }

    private String safe(String value) {
        if (value == null || value.length() == 0) {
            return "unknown";
        }
        return value;
    }
}
