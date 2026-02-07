package com.example.learnclaudedemo.entity.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * ContainInfo
 * Recreated based on C# ContainInfo class images.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ContainInfo")
public class ContainInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 箱号
     */
    @XmlElement(name = "ContaId", required = true, nillable = true)
    private String contaId;

    /**
     * 查验方式@第一位:1=人工,2=机检,3=人工加机检; 第二位:J=大型集装箱检查设备, X=X货检X光机;
     * 第三位:A=彻底查验,B=抽查,C=外形查验,D=100%抽查
     */
    @XmlElement(name = "CheckType", required = true, nillable = true)
    private String checkType;

    /**
     * 系统来源@风控系统下发的查验集装箱为R，查验系统新增的集装箱为C，空箱情况下风控不带箱号则取舱单号为B
     */
    @XmlElement(name = "CheckResource", required = true, nillable = true)
    private String checkResource;

    /**
     * 类型 空:集装箱 V:车辆
     */
    @XmlElement(name = "ObjType", required = true, nillable = true)
    private String objType;
}
