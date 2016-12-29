package com.valentine.translatron;

public class BasicLexeme
{
	public static class Defaults
	{
		public static final BasicLexeme ANY = new BasicLexeme(Type.UNDEFINED, Subtype.UNDEFINED, null);
		public static final BasicLexeme EOF = new BasicLexeme(Type.EOF, Subtype.UNDEFINED, "");
		public static final BasicLexeme BLOCK_OPEN = new BasicLexeme(Type.BRACKET, Subtype.UNDEFINED, "{");
		public static final BasicLexeme BLOCK_CLOSE = new BasicLexeme(Type.BRACKET, Subtype.UNDEFINED, "}");
	}
	
	public static enum Type
	{
		UNDEFINED,
		
		SPACE,
		COMMENT,
		NUMBER,
		IDENT,
		OPERATOR,
		SEPARATOR,
		BRACKET,
		EOF,
		ERROR
	};
	
	public static enum Subtype
	{
		UNDEFINED,
		
		UNKNOWN,
		
		INT,
		DBL,
		FLT,
		
		KEYWORD,
		
		ERR_COMMENT_MULTILINE_EOF,
		ERR_IN_NUMBER,
		
		COMMENT_SINGLELINE,
		COMMENT_MULTILINE
	};
	
	public static final String[] KEYWORDS = "int double string char boolean void for while do if then const switch case return".split(" ");
	
	public Type type = Type.UNDEFINED;
	public Subtype subtype = Subtype.UNDEFINED;
	public String string = null;
	
	public BasicLexeme()
	{}
	
	public BasicLexeme(Type _type, Subtype _subtype, String _string)
	{
		type = _type;
		subtype = _subtype;
		string = _string;
	}
	
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append("BasicLexeme(")
			.append(type.name());
		
		if (subtype != Subtype.UNDEFINED)
		{
			stringBuilder
				.append(", ")
				.append(subtype.name());
		}
		
		stringBuilder
			.append(")[")
			.append(string)
			.append(']');
		
		return stringBuilder.toString();
	}
	
	public boolean equals(BasicLexeme _lexeme)
	{
		//System.err.println(this + " ? " + _lexeme);
		
		return
			_lexeme != null &&
			(
				_lexeme.type == Type.UNDEFINED ||
				type == _lexeme.type
			) &&
			(
				_lexeme.subtype == Subtype.UNDEFINED ||
				subtype == _lexeme.subtype
			) &&
			(
				_lexeme.string == null ||
				string.equals(_lexeme.string)
			);
	}
}