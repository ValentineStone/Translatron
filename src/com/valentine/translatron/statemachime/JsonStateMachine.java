package com.valentine.translatron.statemachime;

import java.io.*;
import java.util.*;

import com.google.gson.*;
import com.valentine.translatron.*;

public final class JsonStateMachine
{
	private JsonStateMachine()
	{}

	
	public static PNode assemble(Machine _machine, List<Lexeme> _lexemes)
	{
		return assemble(_machine, _lexemes, "root");
	}
	
	private static PNode assemble(Machine _machine, List<Lexeme> _lexemes, String _stage)
	{
		return assemble(_machine, _lexemes, _stage, null);
	}
	
	private static PNode assemble(Machine _machine, List<Lexeme> _lexemes, String _stage, String _state)
	{
		PNode pnode = new PNode();
		pnode.comments = _stage + "::" + _state;
		
		return assemble(_machine, _lexemes, _stage, _state, pnode);
	}
	
	private static PNode assemble(Machine _machine, List<Lexeme> _lexemes, String _stage, String _state, PNode _pnode)
	{
		System.err.println(">---------------------------------------------------------");
		System.err.println(_stage + " " + _stage);
		System.err.println(_pnode.toText());
		
		Stage stage = _machine.stagesMap.get(_stage);
		State state = stage.statesMap.get(_state);
		
		for (int i = 0; i < stage.iterations || stage.indefinite;)
		{
			boolean matched = false;
			
			for (Transaction transaction : state.transactions)
			{
				On on = transaction.on;
				To to = transaction.to;
				
				System.err.print(i); 
				System.err.println(": matched " + _lexemes.get(i) + " " + on.type + " " + on.subtype + " " + on.text);
				if (PNodeStateAssembly.match(_lexemes.get(i), on.type, on.subtype, on.text))
				{
					System.err.println("+");
					
					matched = true;
					
					if (to.isUp())
					{
						_pnode.lexemes.add(_lexemes.get(i));
						return _pnode;
					}
					
					if (to.stage != null)
					{
						PNode child = assemble(_machine, _lexemes.subList(i, _lexemes.size()), to.stage, to.state);
						_pnode.childs.add(child);
						i += child.getLength(true);
						System.err.println("\tchild len: " + child.getLength(false));
						System.err.println("\tchild: " + child);
					}
					else 
					{
						i++;
						if (to.state == null)
							return _pnode;
					}
					
					
					break;
				}
				else
					System.err.println("-");
			}
			
			if (!matched)
				_pnode.comments += "ERROR";
		}
		

		System.err.println(_pnode.toText());
		System.err.println("<---------------------------------------------------------");
		return _pnode;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static Machine load(File _file)
	{
		Reader rdr;
		Machine machine;
		
		try
		{
			rdr = new FileReader(_file);
			machine = new Gson().fromJson(rdr, Machine.class);
			
			for (Stage stage : machine)
			{
				if (stage.iterations > 0)
					stage.indefinite = false;
				
				machine.stagesMap.put(stage.name, stage);
				
				for (State state : stage.states)
				{
					stage.statesMap.put(state.name, state);
					
					for (Transaction transaction : state.transactions)
					{
						state.transactionsMap.put(transaction.name, transaction);
						
						if (transaction.on == null)
							transaction.on =  On.ANY;
					}
				}
			}
			
			return machine;
		}
		catch (Exception _e)
		{
			System.err.println(_e.getMessage());
			return null;
		}
	}

}
