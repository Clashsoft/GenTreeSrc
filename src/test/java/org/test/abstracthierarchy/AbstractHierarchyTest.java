package org.test.abstracthierarchy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbstractHierarchyTest
{
	@Test
	public void test()
	{
		B b = B.of();
		D d = D.of();
		F f = F.of();

		StringVisitor visitor = new StringVisitor();
		assertEquals("B", b.accept(visitor, null));
		assertEquals("D", d.accept(visitor, null));
		assertEquals("F", f.accept(visitor, null));
	}
}
