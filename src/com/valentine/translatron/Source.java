package com.valentine.translatron;

public class Source
{
	public int pos;
	public final String source;
	
	public Source(String _source)
	{
		source = _source;
		pos = 0;
	}
	
	public char peekChar()
	{
		return peekChar(0);
	}
	
	public char peekChar(int _offset)
	{
		return source.charAt(pos + _offset);
	}
	
	public boolean isEof()
	{
		return isOutOfRange(0);
	}
	
	public boolean isOutOfRange(int _offset)
	{
		return
			   pos + _offset >= source.length() - 1
			|| pos + _offset < 0;
	}
	
	public Source tune(int _offset)
	{
		if (!isOutOfRange(_offset))
			pos += _offset;
		else
			pos = source.length() - 1;
		return this;
	}
}