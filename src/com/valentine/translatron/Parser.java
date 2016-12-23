package com.valentine.translatron;

import java.util.*;

import com.valentine.translatron.PNode.*;

public class Parser
{
	/*
	public static PNode parse(List<Lexeme> _lexemes)
	{
		PNode root = new PNode();
		
		PNode pnode;
		int pos = 0;
		
		do
		{
			pnode = parseAt(_lexemes, pos);
			if (pnode != null)
			{
				pos += pnode.length;
				root.add(pnode);
			}
			else
				break;
		}
		while (pnode.type != Type.ERROR);
		
		return root;
	}
	*/
	
	/*
	public static PNode parseAt(List<Lexeme> _lexemes, int _pos)
	{
		if (_pos >= _lexemes.size())
			return null;
		
		PNode pnode = null;
		
		pnode = tryBlock(_lexemes, _pos);
		if (pnode != null)
			return pnode;
		
		return new PNode(_lexemes, _pos, 1, Type.SIMPLE_LEXEME);
	}
	
	public static PNode tryBlock(List<Lexeme> _lexemes, int _pos)
	{
		Lexeme lex = _lexemes.get(_pos);
		
		if (!lex.string.equals("{"))
			return null;
		
		int length = 1;
		PNode root = new PNode(_lexemes, _pos, length, Type.SIMPLE_BLOCK);
		PNode pnode;
		
		while (true)
		{
			if (_pos + length + 1 < _lexemes.size())
			{
				if (_lexemes.get(_pos + length).string.equals("}"))
				{
					root.length = length + 1;
					return root;
				}
				else
				{
					pnode = parseAt(_lexemes, _pos + length);
					if (pnode == null || pnode.type == Type.ERROR)
					{
						root.type = Type.ERROR;
						root.subtype = Subtype.ERROR_BLOCK_NOT_CLOSED;
						
						if (pnode != null)
							root.add(pnode);
						
						return root;
					}
					else
					{
						length += pnode.length;
					}
				}
			}
			else
			{
				root.type = Type.ERROR;
				root.subtype = Subtype.ERROR_BLOCK_NOT_CLOSED;
				
				return root;
			}
		}
	}
	*/
	
	public static PNode tryBlock(List<Lexeme> _lexemes)
	{
		if (_lexemes.get(0).equals(Lexeme.Defaults.BLOCK_OPEN))
		{
			PNode block = new PNode();
			
			block.lexeme = _lexemes.get(0);
			block.type = Type.SIMPLE_BLOCK;
			
			block.childs.addAll
			(
				makeStatementsUntil
				(
					_lexemes.subList(1, _lexemes.size()),
					Lexeme.Defaults.BLOCK_CLOSE
				)
			);
			
			return block;
		}
		else
		{
			return null;
		}
	}
	
	public static PNode tryDefenition(List<Lexeme> _lexemes)
	{
		if (_lexemes.get(0).equals(Lexeme.Defaults.BLOCK_OPEN))
		{
			PNode block = new PNode();
			
			block.lexeme = _lexemes.get(0);
			block.type = Type.SIMPLE_BLOCK;
			
			block.childs.addAll
			(
				makeStatementsUntil
				(
					_lexemes.subList(1, _lexemes.size()),
					Lexeme.Defaults.BLOCK_CLOSE
				)
			);
			
			return block;
		}
		else
		{
			return null;
		}
	}
	
	public static List<PNode> makeStatementsUntil(List<Lexeme> _lexemes, BasicLexeme _out)
	{
		List<PNode> pnodes = new ArrayList<>();
		
		for (int i = 0; i < _lexemes.size(); i++)
		{
			List<Lexeme> lexemes = _lexemes.subList(i, _lexemes.size());
			
			if (lexemes.get(0).equals(_out))
			{
				pnodes.add(new PNode(lexemes.get(0), 1, Type.SIMPLE_LEXEME, Subtype.CLOSING_LEXEME));
				break;
			}
			
			pnodes.add(makeStatement(lexemes));
		}
		return pnodes;
	}
	
	public static PNode makeStatement(List<Lexeme> _lexemes)
	{
		PNode pnode = null;
		
		pnode = tryBlock(_lexemes);
		if (pnode != null)
			return pnode;
		
		return new PNode(_lexemes.get(0), 1, Type.SIMPLE_LEXEME);
	}
	
	public static PNode parse(List<Lexeme> _lexemes)
	{
		PNode root = new PNode();
		
		root.childs.addAll(makeStatementsUntil(_lexemes, Lexeme.Defaults.EOF));
		
		return root;
	}
	
}
