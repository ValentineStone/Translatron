package com.valentine.translatron.statemachime;

import java.util.*;

public final class Machine implements Iterable<Stage>
{
	private Machine()
	{}
	
	public String name = null;
	public int version = 0;
	public int subVersion = 0;
	public String langType = null;
	
	public List<Stage> stages = new ArrayList<>(0);
	public Map<String, Stage> stagesMap = new HashMap<>();
	
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
			.append("Machine(");

		if (name != null)
			stringBuilder
			.append("n:")
				.append(name)
				.append(", ");
		
		if (langType != null)
			stringBuilder
				.append("lt:")
				.append(langType)
				.append(", ");
		
		stringBuilder
			.append("v:")
			.append(version)
			.append(", s:")
			.append(subVersion)
			.append(")");
		
		if (stages.size() > 0)
		{
			if (stages.size() > 1)
				stringBuilder
					.append("\n[");
			
			for (Stage stage : stages)
				stringBuilder
					.append("\n\t")
					.append(stage);
			
			if (stages.size() > 1)
				stringBuilder
					.append("\n]");
		}
		
		return stringBuilder.toString();
	}

	public Iterator<Stage> iterator()
	{
		return stages.iterator();
	}
}
