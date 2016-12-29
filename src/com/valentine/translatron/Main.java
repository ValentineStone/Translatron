package com.valentine.translatron;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import com.valentine.translatron.statemachime.*;

public class Main
{
	public static void main(String[] _args) throws IOException
	{
		System.err.println("--------------------------SOURCE:------------------------------------\n");
		
		String source = new String(Files.readAllBytes(new File("res/source.cpp").toPath()));
		
		System.err.println(source);
		
		System.err.println("\n--------------------------LEXED:------------------------------------\n");
		
		List<Lexeme> lexemes = Lexer.lex(source, true);
		
		for (Lexeme lexeme : lexemes)
		{
			System.err.println(lexeme.toString());
		}
		
		System.err.println("\n--------------------------PARSED:------------------------------------\n");
		
		//PNode pnode = Parser.parse(lexemes);
		
		//PNode pnode = PNodeStateAssembly.assemble(lexemes, State.ROOT);
		
		Machine machine = JsonStateMachine.load(new File("res/stages.json"));
		
		System.err.println(machine);
		
		PNode pnode = JsonStateMachine.assemble(machine, lexemes);
		
		System.err.println(pnode.toText());
	}
}
