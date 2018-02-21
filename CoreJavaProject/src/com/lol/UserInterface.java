package com.lol;

import java.util.Scanner;

public class UserInterface {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ServiceClass s = new ServiceClass();
		System.out.println("ENTER YOUR CHOICE (i.e 1,2) \n1. USER ACCESS 2. ADMIN ACCESS");
		Scanner sc = new Scanner(System.in);
		char ch1;
		do{
			int n=0;
			do{
				try {
					n=new Scanner(System.in).nextInt();
				} catch (Exception e) {
					System.out.println("Choose the Displayed Options!!!");
				}
			}while(n<=0);
			
		switch (n) {
		
		case 1: 
			s.orderCust();
			break;
		case 2:
			System.out.print("Enter Admin Name : ");
			String un = sc.next();
			System.out.print("Enter Admin Pass : ");
			String up = sc.next();
			if (un.equals("admin") && up.equals("admin")) {
				System.out.println("1. ADD PRODUCT       2. VIEW PRODUCT\n3. DELETE PRODUCT    4. UPDATE PRODUCT");
				char ch;
				do {
					int choice = 0 ;
					do {
						try {
							System.out.println("Enter your choice (i.e 1,2,3,4) ");
							choice = sc.nextInt();
						} catch (Exception e) {
							System.out.println("Choose the Displayed Options!!!");
						}
						sc.nextLine();
					} while (choice <= 0);

					switch (choice) {
					case 1:
					        s.addProduct();
						break;
					case 2:
						s.viewProducts();
						break;
					case 3:
						s.deleteProducts();
						break;
					case 4:
						s.updateProduct();
						break;
					default:
						System.out.println("Invalid entry");
					}
					System.out.println("If You Want to Continue to the Admin Access Yes/No");
					ch = new Scanner(System.in).next().charAt(0);
				} while (ch == 'Y' || ch == 'y');

			}//if close
			else{
				System.out.println("Invalid UserName and Password");
			}
			break;
		default:
			System.out.println("INVALID SELECTION 1.USER 2.ADMIN");
			break;
		}
		System.out.print("Enter your choice");
		}while(true);
		
	} //main close

} //class close
