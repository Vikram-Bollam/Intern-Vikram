package com.lol;

import java.util.HashMap;

public class Practise {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
      HashMap<BillDetails, CustomerDetails> aa=new HashMap<>();
      BillDetails key = new BillDetails();
      key.setOrderProduct("11");
      key.setOrderQuantity(2);
      key.setTotalProductprice(123);
	  CustomerDetails value = null;
	  aa.put(key, value);
      }

}
