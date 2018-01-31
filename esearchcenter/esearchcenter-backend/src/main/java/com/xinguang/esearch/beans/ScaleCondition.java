package com.xinguang.esearch.beans;

import org.elasticsearch.common.geo.GeoDistance;

public class ScaleCondition {
    private String startDistance;//距离范围开始
    private String endDistance;//距离范围结束
    private GeoDistance geoDistance;
    private double latitude;//经度
    private double longitude;//维度
    private String optimizeBbox;
    private int scale;//比例尺
    
    //新加条件(deleted=0 and status=1 是为删除)
    private int deleted =0;
    private int status =1;
    
    public String getStartDistance() {
        return startDistance;
    }
    public void setStartDistance(String startDistance) {
        this.startDistance = startDistance;
    }
    public String getEndDistance() {
        return endDistance;
    }
    public void setEndDistance(String endDistance) {
        this.endDistance = endDistance;
    }
    public GeoDistance getGeoDistance() {
        return geoDistance;
    }
    public void setGeoDistance(GeoDistance geoDistance) {
        this.geoDistance = geoDistance;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public int getScale() {
        return scale;
    }
    public void setScale(int scale) {
        this.scale = scale;
    }
    public String getOptimizeBbox() {
        return optimizeBbox;
    }
    public void setOptimizeBbox(String optimizeBbox) {
        this.optimizeBbox = optimizeBbox;
    }
    public int getDeleted() {
        return deleted;
    }
    public int getStatus() {
        return status;
    } 
}
