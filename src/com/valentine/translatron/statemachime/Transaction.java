package com.valentine.translatron.statemachime;

import com.valentine.translatron.*;

public final class Transaction
{
	private Transaction()
	{}
	
	private Transaction(On _on, To _to)
	{
		on = _on;
		to = _to;
	}
	
	public static final Transaction UP_ON_EOF = new Transaction(On.EOF, To.UP);
	
	public String name = null;
	public On on = On.ANY;
	public To to = To.UP;
	
	public boolean consume = true;
	
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
	{
		type = null;
		subtype = null;
		text = null;
	}
	
	private On(Lexeme.Type _type)
	{
		type = _type;
		subtype = null;
		text = null;
	}
	
	public final Lexeme.Type type;
	public final Lexeme.Subtype subtype;
	public final String text;
	
	public static final On ANY = new On();
	public static final On EOF = new On(Lexeme.Type.EOF);
	
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
	{
		up = false;
	}
	
	private To(boolean _up)
	{
		up = _up;
	}
	
	public final String stage = null;
	public final String state = null;
	public final boolean up;
	
	public static final To UP = new To(true);
	
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
		return up;
	}
	
	public boolean isToState()
	{
		return stage == null;
	}
	
	public boolean isToStage()
	{
		return stage != null;
	}
}
