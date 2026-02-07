package com.example.learnclaudedemo.entity.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * Request Entity
 * Contains the detailed fields for the customs notice.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * CheckFormId
     */
    @XmlElement(name = "CheckFormId", required = true, nillable = true)
    private String checkFormId;

    @XmlElement(name = "NoticeType", required = true, nillable = true)
    private String noticeType;

    @XmlElement(name = "CheckId", required = true, nillable = true)
    private String checkId;

    @XmlElement(name = "CorporateName", required = true, nillable = true)
    private String corporateName;

    @XmlElement(name = "EntryId", required = true, nillable = true)
    private String entryId;

    @XmlElement(name = "ManifestId", required = true, nillable = true)
    private String manifestId;

    @XmlElement(name = "BillNo", required = true, nillable = true)
    private String billNo;

    @XmlElement(name = "VoyageNo", required = true, nillable = true)
    private String voyageNo;

    @XmlElement(name = "TrafName", required = true, nillable = true)
    private String trafName;

    @XmlElement(name = "CustomsCode", required = true, nillable = true)
    private String customsCode;

    @XmlElement(name = "CheckType", required = true, nillable = true)
    private String checkType;

    @XmlElement(name = "ExamType", required = true, nillable = true)
    private String examType;

    @XmlElement(name = "WeightFlag", required = true, nillable = true)
    private String weightFlag;

    /**
     * 集装箱信息
     */
    @XmlElementWrapper(name = "ContainInfos")
    @XmlElement(name = "ContainInfo")
    private List<ContainInfo> containInfos;
}
