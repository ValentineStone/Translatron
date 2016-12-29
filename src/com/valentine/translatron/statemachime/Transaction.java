package com.valentine.translatron.statemachime;

import com.valentine.translatron.*;

public final class Transaction
{
	private Transaction()
	{}
	
	public String name = null;
	public On on = On.ANY;
	public To to = To.UP;
	
	
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append("Transaction(");

		boolean comma = false;

		if (name != null)
		{
			stringBuilder
				.append(name);
			
			comma = true;
		}

		if (on != null)
		{
			if (comma)
				stringBuilder
					.append(", ");
				
			stringBuilder
				.append(on);
			
			comma = true;
		}

		if (to != null)
		{
			if (comma)
				stringBuilder
					.append(", ");
			
			stringBuilder
				.append(to);
		}
		
		stringBuilder
			.append(")");
		
		return stringBuilder.toString();
	}

}

final class On
{
	private On()
	{}
	
	public final Lexeme.Type type = null;
	public final Lexeme.Subtype subtype = null;
	public final String text = null;
	
	public static final On ANY = new On();
	
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append("On(");
		
		boolean comma = false;

		if (type != null)
		{
			stringBuilder
				.append(type.name());
			comma = true;
		}
		else
		{
			stringBuilder
			.append("ANY");
		comma = true;
		}

		if (subtype != null)
		{
			if (comma)
				stringBuilder
					.append(", ");
				
			stringBuilder
				.append(subtype.name());
			
			comma = true;
		}

		if (text != null)
		{
			if (comma)
				stringBuilder
					.append(", ");
			
			stringBuilder
				.append('"')
				.append(text)
				.append('"');
		}
		
		stringBuilder
			.append(")");
		
		return stringBuilder.toString();
	}
}

final class To
{
	private To()
	{}
	
	public final String stage = null;
	public final String state = null;
	
	public static final To UP = new To();
	
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append("To(");
		
		if (stage == null)
		{
			if (state == null)
				stringBuilder
					.append("..");
			else
				stringBuilder
					.append(".::")
					.append(state);
		}
		else
		{
			if (state == null)
				stringBuilder
					.append(stage);
			else
				stringBuilder
					.append(stage)
					.append("::")
					.append(state);
		}
		
		stringBuilder
			.append(")");
		
		return stringBuilder.toString();
	}
	
	public boolean isUp()
	{
		return stage == null && state == null;
	}
}
