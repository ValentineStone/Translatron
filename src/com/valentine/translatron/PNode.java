package com.valentine.translatron;

import java.util.*;

public class PNode
{
	public PNode master = null;
	public final List<PNode> childs = new ArrayList<>();
	public final List<Lexeme> lexemes = new ArrayList<>();
	public int length = 0;
	public Lexeme lexeme = null;
	public Type type = Type.UNDEFINED;
	public Subtype subtype = Subtype.UNDEFINED;
	
	public static enum Type
	{
		UNDEFINED,
		
		SIMPLE_LEXEME,
		SIMPLE_BLOCK,
		
		ERROR
	}
	
	public static enum Subtype
	{
		UNDEFINED,
		ERROR_BLOCK_NOT_CLOSED,
		
		CLOSING_LEXEME
	}
	
	public PNode()
	{}
	
	public PNode(Lexeme _lexeme, int _lenght, Type _type)
	{
		this(_lexeme, _lenght, _type, Subtype.UNDEFINED);
	}
	
	public PNode(Lexeme _lexeme, int _lenght, Type _type, Subtype _subtype)
	{
		lexeme = _lexeme;
		length = _lenght;
		type = _type;
		subtype = _subtype;
		
	}
	
	public boolean add(PNode _child)
	{
		if (_child == null)
			return false;
		
		boolean result = childs.add(_child);
		if (result)
			_child.master = this;
		return result;
	}
	
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append("PNode(");
		
		if (master == null)
			stringBuilder
				.append("root, ");
		
		if (!childs.isEmpty())
			stringBuilder
				.append(childs.size());
		else
			stringBuilder
				.append("-");
		
		if (type != Type.UNDEFINED)
			stringBuilder
				.append(", ")
				.append(type.name());
		
		if (subtype != Subtype.UNDEFINED)
			stringBuilder
				.append(", ")
				.append(subtype.name());
		
		if (lexeme != null)
			stringBuilder
				.append(", of ")
				.append(length);
		
		stringBuilder
			.append(")[");
		
		if (lexeme != null)
			stringBuilder
				.append(lexeme.toString());
		if (!lexemes.isEmpty())
			stringBuilder
				.append(lexemes.toString());
					
		stringBuilder
			.append("]");
		
		return stringBuilder.toString();
	}
	
	public String toText()
	{
		return toText("");
	}
	
	private String indentationString = "-    ";
	
	private String toText(String _indentationString)
	{
		String nextIndentationString = _indentationString + indentationString;
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder
			.append(this);
		
		if (!childs.isEmpty())
		{
			stringBuilder
				.append('\n')
				.append(_indentationString)
				.append('{');
		
			for (PNode child : childs)
			{
				stringBuilder
					.append('\n')
					.append(nextIndentationString)
					.append(child.toText(nextIndentationString));
			}
			
			stringBuilder
				.append('\n')
				.append(_indentationString)
				.append("}");
		}
		
		return stringBuilder.toString();
	}
	
	private int __length = -1;
	
	public int getLength(boolean _remake)
	{
		if (_remake)
			__length = -1;
		
		if (__length == -1)
		{
			for (PNode child : childs)
				__length += child.getLength(_remake);
			
			__length += lexemes.size();
		}
		
		return __length;
	}
}
