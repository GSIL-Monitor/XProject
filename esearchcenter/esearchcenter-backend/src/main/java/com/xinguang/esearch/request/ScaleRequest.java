package com.xinguang.esearch.request;

import com.xinguang.esearch.beans.BaseBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ScaleRequest extends BaseBean{
    
    /**
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static final long serialVersionUID = 7741474303690271913L;

    @ApiModelProperty(value = "当前经度", required = true)
    double latitude;//经度
    
    @ApiModelProperty(value = "当前维度", required = true)
    double longitude;//维度
    
    @ApiModelProperty(value = "比例尺", required = true)
    private int scale;//比例尺
    
    @ApiModelProperty(value = "半径", required = true)
    private int radius;//半径

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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
