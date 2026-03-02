package com.example.learnclaudedemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 查验结果信息 (对应表 HEAI_RES_INFO)
 * Translated from C# HeaiResInfoEntity
 */
@Data
@Entity
@Table(name = "HEAI_RES_INFO")
public class HeaiResInfoEntity {

    /**
     * 记录ID (GUID)
     */
    @Id
    @Column(name = "ID", nullable = false, length = 36)
    private String id;

    /**
     * 记录状态@1=有效,0=无效,2=成功,3=失败
     */
    @Column(name = "STATUS", length = 2)
    private String status;
    /**
     * 记录创建时间
     */
    @Column(name = "CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 记录创建者@用户名
     */
    @Column(name = "CREATOR", length = 20)
    private String creator;

    /**
     * 记录创建者@关员编号
     */
    @Column(name = "CREATOR_ID", length = 36)
    private String creatorId;

    /**
     * 记录修改时间
     */
    @Column(name = "MODIFY_TIME")
    private LocalDateTime modifyTime;

    /**
     * MESSAGEID关联用的标识ID
     */
    @Column(name = "MESSAGE_ID", length = 100)
    private String messageId;

    /**
     * 回执发送时间
     */
    @Column(name = "ISSUE_DATE_TIME")
    private LocalDateTime issueDateTime;

    /**
     * 回执代码,S:接收成功,F:接收失败
     */
    @Column(name = "STATEMENT_CODE", length = 2)
    private String statementCode;

    /**
     * 回执描述
     */
    @Column(name = "STATEMENT_DESCRIPTION", length = 4000)
    private String statementDescription;

}
