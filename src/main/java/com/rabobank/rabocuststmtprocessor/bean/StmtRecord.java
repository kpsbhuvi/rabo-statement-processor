package com.rabobank.rabocuststmtprocessor.bean;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StmtRecord {
	private Integer transactionRef;
	private String accountNum;
	private String desc;
	private BigDecimal startBal;
	private BigDecimal endBal;
	private BigDecimal mutation;
	public Integer getTransactionRef() {
		return transactionRef;
	}
	public void setTransactionRef(Integer transactionRef) {
		this.transactionRef = transactionRef;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public BigDecimal getStartBal() {
		return startBal;
	}
	public void setStartBal(BigDecimal startBal) {
		this.startBal = startBal;
	}
	public BigDecimal getEndBal() {
		return endBal;
	}
	public void setEndBal(BigDecimal endBal) {
		this.endBal = endBal;
	}
	public BigDecimal getMutation() {
		return mutation;
	}
	public void setMutation(BigDecimal mutation) {
		this.mutation = mutation;
	}
	@Override
	public String toString() {
		return "StmtRecord [transactionRef=" + transactionRef + ", accountNum=" + accountNum + ", desc=" + desc
				+ ", startBal=" + startBal + ", endBal=" + endBal + ", mutation=" + mutation + "]";
	}
	
	
}
