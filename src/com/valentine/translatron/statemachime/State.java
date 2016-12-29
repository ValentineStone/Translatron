package com.valentine.translatron.statemachime;

import java.util.*;

public final class State implements Iterable<Transaction>
{
	private State()
	{}
	
	public String name = null;
	
	public List<Transaction> transactions = new ArrayList<>(0);
	public Map<String, Transaction> transactionsMap = new HashMap<>();
	
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append("State(");

		if (name != null)
			stringBuilder
				.append(name);
		
		stringBuilder
			.append(')');
		
		if (transactions.size() > 0)
		{
			if (transactions.size() > 1)
				stringBuilder
					.append("\n\t\t[");
			
			for (Transaction transaction : transactions)
				stringBuilder
					.append("\n\t\t\t")
					.append(transaction);
			
			if (transactions.size() > 1)
				stringBuilder
					.append("\n\t\t]");
		}
		
		return stringBuilder.toString();
	}

	public Iterator<Transaction> iterator()
	{
		return transactions.iterator();
	}
}
