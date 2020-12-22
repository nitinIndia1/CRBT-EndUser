package com.crbt.api.services.bean;

public class Views {

	public static class Public {}
	public static class ExtendedPublic extends Public {}
	public static class Internal extends ExtendedPublic{}
	public static class WithMapping extends Public{}
	public static class PageData {}
}
