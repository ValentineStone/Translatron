package com.valentine.translatron;

import java.util.*;

public class PNodeStateAssembly
{
	public static enum MatchStrictness
	{
		TYPE,
		SUBTYPE,
		STRING,
		SKIP_SUBTYPE
	}
	
	public static boolean match(Lexeme _l1, Lexeme _l2, MatchStrictness _matchStrictness)
	{
		if
		(
			_l1 == null              ||
			_l2 == null              ||
			_matchStrictness == null
		)
			return false;
		
		switch(_matchStrictness)
		{
			case TYPE:
				return
					_l1.type == _l2.type;
			case SUBTYPE:
				return
					_l1.type == _l2.type          &&
					_l1.subtype == _l2.subtype;
			case STRING:
				return
					_l1.type == _l2.type          &&
					_l1.subtype == _l2.subtype    &&
					_l1.string.equals(_l2.string);
			case SKIP_SUBTYPE:
				return
					_l1.type == _l2.type          &&
					_l1.string.equals(_l2.string);
			default:
				return false;
		}
	}
	
	public static boolean match(Lexeme _lexeme, Lexeme.Type _type)
	{
		return _lexeme != null && _lexeme.type == _type;
	}
	
	public static boolean match(Lexeme _lexeme, Lexeme.Type _type, Lexeme.Subtype _subtype)
	{
		return match(_lexeme, _type) && _lexeme.subtype == _subtype;
	}
	
	public static boolean match(Lexeme _lexeme, Lexeme.Type _type, Lexeme.Subtype _subtype, String _string)
	{
		return match(_lexeme, _type, _subtype) && _lexeme.string != null && _lexeme.string.equals(_string);
	}
	
	
	
	
	
	
	public static final class Transaction
	{
		public final BasicLexeme lexeme;
		public final State state;
		
		public Transaction(BasicLexeme _lexeme, State _state)
		{
			lexeme = _lexeme;
			state = _state;
		}
		
		public String toString()
		{
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder
				.append("Transaction(")
				.append(lexeme)
				.append(" -> ")
				.append(state == null ? '^' : state);
			
			return stringBuilder.toString();
		}
	}
	
	
	
	public static class State
	{
		public final String name;
		private State(String _name){name = _name;}
		public String toString() {return name;}
		
		public final List<Transaction> transactions = new ArrayList<>();
		

		
		public static final State SIMPLE_LEXEME = new State("SIMPLE_LEXEME")
		{
			{
				transactions.add(new Transaction(BasicLexeme.Defaults.ANY, null));
			}
		};
		
		public static final State SIMPLE_BLOCK = new State("SIMPLE_BLOCK")
		{
			{
				transactions.add(new Transaction(BasicLexeme.Defaults.BLOCK_CLOSE, null));
				transactions.add(new Transaction(BasicLexeme.Defaults.ANY, STATEMENT));
			}
		};
		
		public static final State STATEMENT = new State("STATEMENT")
		{
			{
				transactions.add(new Transaction(BasicLexeme.Defaults.BLOCK_OPEN, SIMPLE_BLOCK));
				transactions.add(new Transaction(BasicLexeme.Defaults.ANY, SIMPLE_LEXEME));
			}
		};
		
		public static final State ROOT = new State("ROOT")
		{
			{
				transactions.add(new Transaction(BasicLexeme.Defaults.EOF, null));
				transactions.add(new Transaction(BasicLexeme.Defaults.ANY, STATEMENT));
			}
		};
	}
	
	
	
	
	
	
	public static PNode assemble(List<Lexeme> _lexemes, State _state)
	{
		System.err.println(">------------------------------" + _state);
		
		if (_state == null)
			return null;
		
		PNode pnode = new PNode();
		
		for (int i = 0; i < _lexemes.size(); i++)
		{
			for (Transaction transaction : _state.transactions)
			{
				System.err.print("\t" + i + ": " + transaction.state);
				
				if (_lexemes.get(i).equals(transaction.lexeme))
				{
					System.err.println("+");
					
					if (transaction.state == null)
					{
						pnode.lexemes.add(_lexemes.get(i));
						System.err.println("<n-----------------------------" + _state);
						return pnode;
					}
					
					else if (transaction.state == _state)
					{
						pnode.lexemes.add(_lexemes.get(i));
						break;
					}
					
					else
					{
						PNode child = assemble(_lexemes.subList(i, _lexemes.size()), transaction.state);
						i += child.getLength(true) - 1; // -1 to compensate for i++ in the loop
						System.err.println("child $ " + child.getLength(false) + " : " + child);
						break;
					}
				}
				else
				{
					System.err.println("-");
				}
			}
		}
		
		System.err.println("<r-----------------------------" + _state);
		return pnode;
	}
}
