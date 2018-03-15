package com.ai.ecs.modules.tm.entity;/**
 * Created by liulu6 on 2017/12/26 0026.
 */

import com.ai.ecs.common.persistence.DataEntity;

import java.util.Date;

/**
 * @author liulu6
 * @create 2017-12-26 下午 4:08
 * @desc 用于返回toolID接收
 **/
public class SysTollAllReturn extends DataEntity<SysToolAll> {
    private Integer toolId;
    private String toolName;		// tool_name
    private String specification;		// specification
    private Date buyDate;		// buy_date
    private Date deleteDate;		// delete_date
    private Date checkDate;		// check_date
    private Date preCheckDate;		// pre_check_date
    private String keeper;		// keeper
    private String phone;		// phone
    private String amount;
    private String checkTimes;
    private String checkFlag;
    private String recordFlag;

    private String period;  //定义预警时间，定时5天，却提前5天提醒用户。

    private String checkPeriod;  //定义周期，比如30天。即30天后会校验。

    private String isCycle; //是否循环校验工具，0为循环校验，1为仅校验一次

    private String origin; //定义来源，确定是否是来源于校验工具之后  0为是，1为否

    private String remainDay; //离校验还剩多少天   计算方式：校验日期 - 当前日期

    private String toolIdReal ;//用户实际录入ID

    private String price ; //价格

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getRemainDay() {
        return remainDay;
    }

    public void setRemainDay(String remainDay) {
        this.remainDay = remainDay;
    }

    public String getToolIdReal() {
        return toolIdReal;
    }

    public void setToolIdReal(String toolIdReal) {
        this.toolIdReal = toolIdReal;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getToolId() {
        return toolId;
    }

    public void setToolId(Integer toolId) {
        this.toolId = toolId;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getPreCheckDate() {
        return preCheckDate;
    }

    public void setPreCheckDate(Date preCheckDate) {
        this.preCheckDate = preCheckDate;
    }

    public String getKeeper() {
        return keeper;
    }

    public void setKeeper(String keeper) {
        this.keeper = keeper;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCheckTimes() {
        return checkTimes;
    }

    public void setCheckTimes(String checkTimes) {
        this.checkTimes = checkTimes;
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCheckPeriod() {
        return checkPeriod;
    }

    public void setCheckPeriod(String checkPeriod) {
        this.checkPeriod = checkPeriod;
    }

    public String getIsCycle() {
        return isCycle;
    }

    public void setIsCycle(String isCycle) {
        this.isCycle = isCycle;
    }
}
