package com.app.trackit.ui.charts;

import com.robinhood.spark.SparkAdapter;

public class ChartsAdapter extends SparkAdapter {

    private float[] yData;

    public ChartsAdapter(float[] yData) {
        this.yData = yData;
    }

    @Override
    public int getCount() {
        return yData.length;
    }

    @Override
    public Object getItem(int index) {
        return yData[index];
    }

    @Override
    public float getY(int index) {
        return yData[index];
    }
}
