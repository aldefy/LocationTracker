package com.blueninjas.aditlal.trackingapp.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;

import java.util.HashSet;
import java.util.List;

/**
 * Created by aditlal on 23/04/15.
 */


public class GetDataUsage {

    private static String TAG = "GetDataUsage";

    private static int UID;
    private static double totalData;
    private static HashSet<PackageInformationTotal> dataHash;
    public static PackageInformationTotal pi;

    public static PackageInformationTotal getPakagesInfoUsingHashMap(Context context) {
        final PackageManager pm = context.getPackageManager();
        // get a list of installed apps.
        dataHash = new HashSet<>();
        List<ApplicationInfo> packages = pm.getInstalledApplications(0);

        // loop through the list of installed packages and see if the selected
        // app is in the list
        for (ApplicationInfo packageInfo : packages) {
            // get the UID for the selected app
            UID = packageInfo.uid;
            String package_name = packageInfo.packageName;
            ApplicationInfo app = null;
            try {
                app = pm.getApplicationInfo(package_name, 0);
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String name = (String) pm.getApplicationLabel(app);
            Drawable icon = pm.getApplicationIcon(app);
            // internet usage for particular app(sent and received)
            double received = (double) TrafficStats.getUidRxBytes(UID)

                    / (1024 * 1024);
            double send = (double) TrafficStats.getUidTxBytes(UID)
                    / (1024 * 1024);
            double total = received + send;
            Logger.e(TAG, name + " " + String.format("%.2f", total) + " MB");

            if (total > 0) {

                pi = new PackageInformationTotal();
                pi.name = name;
                pi.packageName = package_name;
                pi.icon = icon;
                pi.totalMB = String.format("%.2f", total) + " MB";
                pi.individual_mb = String.format("%.2f", total);
                totalData += Double.parseDouble(String.format("%.2f", total));
                dataHash.add(pi);
                Logger.e(TAG, name + " " + String.format("%.2f", total) + " MB");
                return pi;
            }

        }
       /* SharedPreferences.Editor edit = shared.edit();
        edit.putString("Total", String.format("%.2f", totalData));
        edit.commit();*/
        return null;

    }

}
