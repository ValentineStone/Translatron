package com.valentine.translatron;

public class Lexeme extends BasicLexeme
{
	public String source;
	public int pos;
	public int lengh;
	
	
	public Lexeme(String _source, int _pos, int _lenght, Type _type)
	{
		this(_source, _pos, _lenght, _type, Subtype.UNDEFINED);
	}
	
	public Lexeme(String _source, int _pos, int _lenght, Type _type, Subtype _subtype)
	{
		super();
		source = _source;
		pos = _pos;
		lengh = _lenght;
		type = _type;
		subtype = _subtype;
		string = source.substring(Lexer.toindex(pos, 0), Lexer.toindex(pos, lengh));
	}
	
	public Lexeme(Type _type, Subtype _subtype, String _string)
	{
		source = null;
		pos = 0;
		lengh = 0;
		type = _type;
		subtype = _subtype;
		string = _string;
	}
	
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append("Lexeme(")
			.append(type.name());
		
		if (subtype != Subtype.UNDEFINED)
		{
			stringBuilder
				.append(", ")
				.append(subtype.name());
		}
		
		stringBuilder
			.append(", ")
			.append(lengh)
			.append(", @")
			.append(pos)
			.append(")[")
			.append(string)
			.append(']');
		
		return stringBuilder.toString();
	}
}
