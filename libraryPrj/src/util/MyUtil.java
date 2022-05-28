package util;


import java.util.Scanner;

public class MyUtil {
	
	public static Scanner sc = new Scanner(System.in);
	
	public static int scInt() {
		int n = sc.nextInt();
		sc.nextLine();			//엔터키 제거 목적
		return n;
	}

}















