package com.example.android.truecaller.calllogs;

public class CallLogModel {
	
	private String name;
	private String number;
	private String duration;
	private String date;
	
	
	public CallLogModel(String name, String number, String duration, String date)
	{
		this.name = name;
		this.number = number;
		this.duration = duration;
		this.date = date;		
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getNumber()
	{
		return number;
	}
	
	public String getDuration()
	{
		return duration;
	}
	
	public String getDate()
	{
		return date;
	}
	
	
}
