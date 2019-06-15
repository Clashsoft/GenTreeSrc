package org.test.properties;

class PersonDelegate
{
	static String getFullName(Person person)
	{
		return person.getFirstName() + " " + person.getLastName();
	}

	static void setFullName(Person person, String fullName)
	{
		final int delim = fullName.indexOf(' ');
		assert delim >= 0;

		person.setFirstName(fullName.substring(0, delim));
		person.setLastName(fullName.substring(delim + 1));
	}
}
