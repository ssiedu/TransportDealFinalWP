package com.wp.practise;

public class Joiner {

	public static void main(String args[]){
		String[] s= {"a","b","c"};
		String p = "order by "+String.join(",", s);
		System.out.println(p);
		
		String tmp = " a b c d ";
		tmp=tmp.replaceAll("\\s+", "");
		System.out.println(tmp);
	}
	
}
