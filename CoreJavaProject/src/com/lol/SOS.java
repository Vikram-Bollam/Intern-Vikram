package com.lol;

import java.util.Scanner;

public class SOS {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		ServiceClass s = new ServiceClass();
		while (true) {
			System.out.println("Enter the Your Choice");
			String choice = scanner.nextLine();
			char ch = choice.charAt(0);
			switch (ch) {
			case 'a':
				s.addProduct();
				break;
			case 'u':
				s.updateProduct();
				break;
			case 'v':
				System.out.println("View by\n1.Sort \n2.Discount \n3.Random");
				int i=scanner.nextInt();
				switch (i) {
				case 1:
					s.sortByPriceInDes();
					break;
				case 2:
					s.sortByDiscount();
					break;
				case 3:
					s.sortByPriceInDes();
					break;
				default:
					System.out.println("Invalid choice");
					break;
				}
				break;

			case 'd':
				s.deleteProducts();
				break;

			case 'b':
				s.orderCust();
				break;
			default:
				break;
			}

		}
	}// main close

}// class close
