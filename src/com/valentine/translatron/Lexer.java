package com.valentine.translatron;

import java.util.*;

import com.valentine.translatron.BasicLexeme.*;

public final class Lexer
{
	private Lexer() {}
	
	public static List<Lexeme> lex(String _source, boolean _codeOnly)
	{
		List<Lexeme> lexemes = new ArrayList<>();
		
		Lexeme lexeme;
		int pos = 0;
		
		do
		{
			lexeme = lexAt(_source, pos);
			pos += lexeme.lengh;
			
			if ((lexeme.type != Type.COMMENT && lexeme.type != Type.SPACE) || !_codeOnly)
				lexemes.add(lexeme);
		}
		while (lexeme.type != Lexeme.Type.EOF && lexeme.type != Lexeme.Type.ERROR);
		
		return lexemes;
	}
	
	
	public static Lexeme lexAt(String _source, int _pos)
	{
		Lexeme lex = null;
		
		lex = fits(_source, _pos, 0) ? null : new Lexeme(_source, _pos, 0, Type.EOF);
		if (lex != null) return lex;
		
		lex = isSymbol(_source, _pos, "{}[]()") ? new Lexeme(_source, _pos, 1, Type.BRACKET) : null;
		if (lex != null) return lex;
		
		lex = isSymbol(_source, _pos, ";") ? new Lexeme(_source, _pos, 1, Type.SEPARATOR) : null;
		if (lex != null) return lex;
		
		lex = tryIdent(_source, _pos);
		if (lex != null)
		{
			tryKeyword(lex);
			return lex;
		}
		
		lex = tryInteger(_source, _pos);
		if (lex != null) return lex;
		
		lex = trySpace(_source, _pos);
		if (lex != null) return lex;
		
		lex = tryComment(_source, _pos);
		if (lex != null) return lex;
		
		lex = tryOperator(_source, _pos);
		if (lex != null) return lex;
		
		lex = new Lexeme(_source, _pos, 1, Type.ERROR, Subtype.UNKNOWN);
		return lex;
	}
	
	
	
	
	
	public static boolean fits(String _string, int _pos, int _length)
	{
		return _pos + _length < _string.length();
	}
	
	public static char charAt(String _string, int _pos)
	{
		return _string.charAt(toindex(_pos, 0));
	}
	
	public static int toindex(int _pos, int _lenght)
	{
		return _pos + _lenght;
	}
	
	
	
	
	
	
	public static void tryKeyword(Lexeme _lex)
	{
		for (String keyword : Lexeme.KEYWORDS)
		{
			if (_lex.string.equals(keyword))
				_lex.subtype = Subtype.KEYWORD;
		}
	}
	
	
	
	
	
	
	
	public static boolean isSymbol(String _source, int _pos, String _symbols)
	{
		return _symbols.indexOf(charAt(_source, _pos)) >= 0;
	}
	
	
	
	public static Lexeme tryIdent(String _source, int _pos)
	{
		char ch = charAt(_source, _pos);
		
		if ( !(Character.isLetter(ch) || ch == '_') )
			return null;

		int length = 1;
		
		while (fits(_source, _pos, length+1))
		{
			ch = charAt(_source, _pos+length);
			if (!(Character.isLetterOrDigit(ch) || ch == '_'))
				break;

			length++;
		}
		
		return new Lexeme(_source, _pos, length, Type.IDENT);
	}
	
	/*
	public static Lexeme tryNumber(String _source, int _pos)
	{
		if (_pos >= _source.length())
			return -1;
		
		
		int subpos = 0;
		int width;
		int dots = 0;
		
		subpos += compareToInteger(_source);
		subpos += charAt(_source, _pos+subpos) == '.' ? dots = 1 : 0;
		//s
		
		return -1;
	}
	*/
	
	public static int compareToInteger(String _source, int _pos)
	{
		char ch = charAt(_source, _pos);
		
		if ( !(Character.isDigit(ch)) )
			return 0;
		
		int length = 1;
		
		while (fits(_source, _pos, length+1))
		{
			ch = charAt(_source, _pos+length);
			if (!Character.isLetterOrDigit(ch))
				break;

			length++;
		}
		
		return length;
	}
	
	
	public static Lexeme tryInteger(String _source, int _pos)
	{
		int length = compareToInteger(_source, _pos);
		
		return length > 0 ? new Lexeme(_source, _pos, length, Type.NUMBER, Lexeme.Subtype.INT) : null;
	}
	
	
	public static int compareToString(String _source, int _pos, String _string)
	{
		if (fits(_source, _pos, _string.length()))
			return 0;
		
		String crop =
			_source.substring(toindex(_pos, 0), toindex(_pos, _string.length()));
		
		return crop.equals(_string) ? _string.length() : 0;
	}
	
	
	public static int compareToString(String _source, int _pos, String... _strings)
	{
		for (String string : _strings)
		{
			int length = compareToString(_source, _pos, string);
			if (length > 0)
				return length;
		}
		return 0;
	}
	
	
	
	public static Lexeme tryOperator(String _source, int _pos)
	{
		int length;
		
		length = compareToString(_source, _pos, ">>=", "<<=");
		if (length > 0)
			return new Lexeme(_source, _pos, 3, Type.OPERATOR);
		
		length = compareToString
		(
			_source, _pos,
			"++", "--", "==", "!=", ">=", "<=", "&&",
			"||", "<<", ">>", "+=", "-=", "*=", "/=",
			"%=", "&=", "^=", "|=", "->"
		);
		if (length > 0)
			return new Lexeme(_source, _pos, 2, Type.OPERATOR);
		
		if (isSymbol(_source, _pos, "#.=+-*/%<>&|?:^,~"))
			return new Lexeme(_source, _pos, 1, Type.OPERATOR);
		
		return null;
	}
	
	
	public static Lexeme trySpace(String _source, int _pos)
	{
		char ch = charAt(_source, _pos);
		
		if ( !(Character.isWhitespace(ch)) )
			return null;
		

		int length = 1;
		
		while (fits(_source, _pos, length+1))
		{
			ch = charAt(_source, _pos+length);
			if (!Character.isWhitespace(ch))
				break;

			length++;
		}
		
		return new Lexeme(_source, _pos, length, Type.SPACE);
	}
	
	
	public static Lexeme tryComment(String _source, int _pos)
	{
		if (!fits(_source, _pos, 2))
			return null;
		
		if (charAt(_source, _pos) == '/')
		{
			if (charAt(_source, _pos+1) == '/')
			{
				int length;
				for (length = 2; fits(_source, _pos, length); length++)
				{
					if (charAt(_source, _pos+length) == '\n')
						return new Lexeme(_source, _pos, length, Type.COMMENT, Subtype.COMMENT_SINGLELINE);
				}
				return new Lexeme(_source, _pos, length, Type.COMMENT, Subtype.COMMENT_SINGLELINE);
			}
			else if (charAt(_source, _pos+1) == '*')
			{
				int length;
				for (length = 2; fits(_source, _pos, length); length++)
				{
					if (charAt(_source, _pos+length) == '*')
					{
						if (fits(_source, _pos, length+1))
						{
							if (charAt(_source, _pos+length+1) == '/')
								return new Lexeme(_source, _pos, length+2, Type.COMMENT, Subtype.COMMENT_MULTILINE);
						}
						return new Lexeme(_source, _pos, length+1, Type.ERROR, Subtype.ERR_COMMENT_MULTILINE_EOF);
					}
				}
				return new Lexeme(_source, _pos, length, Type.ERROR, Subtype.ERR_COMMENT_MULTILINE_EOF);
			}
			return null;
		}
		
		return null;
	}
	
}
