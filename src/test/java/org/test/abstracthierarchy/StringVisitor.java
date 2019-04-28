package org.test.abstracthierarchy;

import com.example.*;

public class StringVisitor implements A.Visitor<Object, String>
{
	@Override
	public String visit(A a, Object par)
	{
		return "A";
	}

	@Override
	public String visit(B b, Object par)
	{
		return "B";
	}

	@Override
	public String visit(C c, Object par)
	{
		return "C";
	}

	@Override
	public String visit(D d, Object par)
	{
		return "D";
	}

	@Override
	public String visit(E e, Object par)
	{
		return "E";
	}

	@Override
	public String visit(F f, Object par)
	{
		return "F";
	}
}
