package com.rabobank.rabocuststmtprocessor.bean;

import java.math.BigDecimal;

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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNum == null) ? 0 : accountNum.hashCode());
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((endBal == null) ? 0 : endBal.hashCode());
		result = prime * result + ((mutation == null) ? 0 : mutation.hashCode());
		result = prime * result + ((startBal == null) ? 0 : startBal.hashCode());
		result = prime * result + ((transactionRef == null) ? 0 : transactionRef.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StmtRecord other = (StmtRecord) obj;
		if (accountNum == null) {
			if (other.accountNum != null)
				return false;
		} else if (!accountNum.equals(other.accountNum))
			return false;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc)){
			System.out.println("DESC not match");
			System.out.println("desc = "+desc);
			System.out.println("other.desc = "+other.desc);
			return false;
		}
		if (endBal == null) {
			if (other.endBal != null)
				return false;
		} else if (!endBal.equals(other.endBal))
			return false;
		if (mutation == null) {
			if (other.mutation != null)
				return false;
		} else if (!mutation.equals(other.mutation))
			return false;
		if (startBal == null) {
			if (other.startBal != null)
				return false;
		} else if (!startBal.equals(other.startBal))
			return false;
		if (transactionRef == null) {
			if (other.transactionRef != null)
				return false;
		} else if (!transactionRef.equals(other.transactionRef))
			return false;
		return true;
	}
	
	
}
