package de.clashsoft.gentreesrc;

import de.clashsoft.gentreesrc.tool.Tool;

public final class Main
{
	public static void main(String[] args)
	{
		System.exit(new Tool().run(System.in, System.out, System.err, args));
	}
}
