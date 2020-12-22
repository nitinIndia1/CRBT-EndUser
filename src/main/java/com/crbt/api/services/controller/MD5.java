package com.crbt.api.services.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		String password="crbt7";
		
		   MessageDigest md = MessageDigest.getInstance("MD5");
		     byte[] messageDigest = md.digest(password.getBytes());
	        BigInteger number = new BigInteger(1, messageDigest);
	        String hashtext = number.toString(16);
	        System.out.println(hashtext);
	       
	}

}
