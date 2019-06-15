package org.test.properties;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersonTest
{
	@Test
	public void testDelegate()
	{
		Person person = Person.of("Peter", "Parker");
		assertEquals("Peter Parker", person.getFullName());

		person.setFullName("Alice Bobson");

		assertEquals("Alice", person.getFirstName());
		assertEquals("Bobson", person.getLastName());
	}
}
