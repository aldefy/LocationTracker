package com.blueninjas.aditlal.trackingapp.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by aditlal on 26/04/15.
 */
public class PackageInformationTotal {
    public String name;
    public String packageName;
    public Drawable icon;
    public String totalMB;
    public String individual_mb;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getIndividual_mb() {
        return individual_mb;
    }

    public void setIndividual_mb(String individual_mb) {
        this.individual_mb = individual_mb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTotalMB() {
        return totalMB;
    }

    public void setTotalMB(String totalMB) {
        this.totalMB = totalMB;
    }
}
