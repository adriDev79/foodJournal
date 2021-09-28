package com.example.appfood.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainHelper {
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public void getHash(Context ctx) {
        // Obtention du hash pour la conexion à l'app facebook
        try {
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo info = ctx.getPackageManager().getPackageInfo(
                    "com.example.appfood",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("TESTER", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            Log.e("TESTER", e.getMessage());
        }
    }
}
