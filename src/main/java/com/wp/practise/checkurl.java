package com.wp.practise;

public class checkurl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path="/customer/newdeal/axcn";
		System.out.println(path.indexOf('/', 1));
		System.out.println(path.substring(0, path.indexOf('/', 1)+1));
	}

}
