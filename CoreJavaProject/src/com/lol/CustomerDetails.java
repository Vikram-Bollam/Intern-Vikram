package com.lol;

import java.util.Random;

public class CustomerDetails {
 private String userName;
 private String phoneNumber;
 private int billNo;
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getPhoneNumber() {
	return phoneNumber;
}
public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}
public int getBillNo() {
	Random numberBill=new Random(); 
	int a=numberBill.nextInt(1234);
	return a;
}
public void setBillNo(int billNo) {
	this.billNo = billNo;
}
@Override
public String toString() {
	return "CustomerDetails [userName=" + userName + ", phoneNumber=" + phoneNumber + ", billNo=" + billNo + "]";
} 

}
