package org.test.methods;

public class MethodsDelegate
{
	public static int method(Methods methods)
	{
		return 42;
	}

	public static int method2(Methods methods, int a)
	{
		return a;
	}

	public static int method3(Methods methods, int a, String b)
	{
		return a + b.length();
	}
}
