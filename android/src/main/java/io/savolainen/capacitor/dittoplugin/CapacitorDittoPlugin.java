package io.savolainen.capacitor.dittoplugin;

import android.util.Log;

public class CapacitorDittoPlugin {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
