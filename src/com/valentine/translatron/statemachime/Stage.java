package com.valentine.translatron.statemachime;

import java.util.*;

public final class Stage implements Iterable<State>
{
	private Stage()
	{}
	
	public String name = null;
	public int iterations = 0;
	public boolean indefinite = true;
	
	public List<State> states = new ArrayList<>(0);
	public Map<String, State> statesMap = new HashMap<>();
	
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append("Stage(");

		boolean comma = false;

		if (name != null)
		{
			stringBuilder
				.append(name);
			
			comma = true;
		}
		

		if (comma)
			stringBuilder
				.append(", ");
		
		if (!indefinite)
			stringBuilder
				.append("i:")
				.append(iterations);
		else
			stringBuilder
				.append("indefinite");

		stringBuilder
				.append(')');
		
		if (states.size() > 0)
		{
			if (states.size() > 1)
				stringBuilder
					.append("\n\t[");
			
			for (State state : states)
				stringBuilder
					.append("\n\t\t")
					.append(state);
			
			if (states.size() > 1)
				stringBuilder
					.append("\n\t]");
		}
		
		return stringBuilder.toString();
	}

	public Iterator<State> iterator()
	{
		return states.iterator();
	}

}
