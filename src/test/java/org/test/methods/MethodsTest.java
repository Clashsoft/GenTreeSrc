package org.test.methods;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MethodsTest
{
	@Test
	public void method1()
	{
		final Methods m = Methods.of(1, "a");
		assertEquals(m.method(), 42);
	}

	@Test
	public void method2()
	{
		final Methods m = Methods.of(1, "a");
		assertEquals(m.method2(3), 3);
	}

	@Test
	public void method3()
	{
		final Methods m = Methods.of(1, "a");
		assertEquals(m.method3(3, "ab"), 5);
	}
}
