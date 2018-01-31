package com.xinguang.esearch.beans;

import java.math.BigInteger;

import org.elasticsearch.common.geo.GeoDistance;

public class GeoCondition {
    private int sourceType;//数据类别
    private String startDistance;//距离范围开始
    private String endDistance;//距离范围结束
    private GeoDistance geoDistance;
    private double latitude;//经度
    private double longitude;//维度
    private String optimizeBbox;
    private int size;//获取结果的最大数量
    private int offset;//当前页面
    
    private BigInteger scenicRegionId;
    
    //新加条件(deleted 0:可用)
    private int deleted= 0;
    private int status=-1;
    
    public String getOptimizeBbox() {
        return optimizeBbox;
    }
    public void setOptimizeBbox(String optimizeBbox) {
        this.optimizeBbox = optimizeBbox;
    }
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
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public int getDeleted() {
        return deleted;
    }
    public int getStatus() {
        return status;
    }
    public BigInteger getScenicRegionId() {
        return scenicRegionId;
    }
    public void setScenicRegionId(BigInteger scenicRegionId) {
        this.scenicRegionId = scenicRegionId;
    }
    public int getSourceType() {
        return sourceType;
    }
    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
