package com.xinguang.esearchcenter.request;

import java.io.Serializable;
import java.math.BigInteger;

public class GeoRequest implements Serializable{

    /**
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static final long serialVersionUID = -5046133260140436972L;

    private Integer sourceType;
    
    private BigInteger scenicRegionId;//景区id
    
    double latitude;//经度
    
    double longitude;//维度
    
    private int size;//获取结果的最大数量
    
    private int offset;//当前页面

    public BigInteger getScenicRegionId() {
        return scenicRegionId;
    }

    public void setScenicRegionId(BigInteger scenicRegionId) {
        this.scenicRegionId = scenicRegionId;
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

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

}
