package com.xinguang.esearch.enums;
public enum OptimizeBboxEnum {
    memory("memory"),
    indexed("indexed"),
    none("none");
 
    private String des;
    
    private OptimizeBboxEnum(String string){
        des=string;
    }
     
    public String GetDes()
    {
         return des;
    }
}