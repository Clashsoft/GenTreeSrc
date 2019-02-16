package de.clashsoft.gentreesrc;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

public class Templates
{
	// =============== Static Fields ===============

	private static final STGroup classesGroup    = group("classes");
	private static final STGroup commentsGroup   = group("comments");
	private static final STGroup methodsGroup   = group("methods");
	private static final STGroup propertiesGroup = group("properties");

	// =============== Static Methods ===============

	private static STGroup group(String name)
	{
		final STGroupFile groupFile = new STGroupFile(Templates.class.getResource("templates/" + name + ".stg"));
		groupFile.registerRenderer(String.class, new StringRenderer());
		return groupFile;
	}

	// --------------- Classes ---------------

	public static String importDeclaration(String qualifiedType)
	{
		final ST template = classesGroup.getInstanceOf("importDeclaration");
		template.add("qualifiedType", qualifiedType);
		return template.render();
	}

	public static String treeInterface(String packageName, String imports, String className, String superClass,
		String classBody)
	{
		final ST template = classesGroup.getInstanceOf("treeInterface");
		template.add("packageName", packageName);
		template.add("imports", imports);
		template.add("className", className);
		template.add("superClass", superClass);
		template.add("classBody", classBody);
		return template.render();
	}

	public static String treeImplClass(String className, String superClass, String implClassBody)
	{
		final ST template = classesGroup.getInstanceOf("treeImplClass");
		template.add("className", className);
		template.add("superClass", superClass);
		template.add("implClassBody", implClassBody);
		return template.render();
	}

	// --------------- Comments ---------------

	private static String comment(String templateName, String text)
	{
		final ST template = commentsGroup.getInstanceOf(templateName);
		template.add("text", text);
		return template.render();
	}

	// @formatter:off
	public static String generatedNotice(String text) { return comment("generatedComment", text); }
	public static String sectionComment(String text) { return comment("sectionComment", text); }
	public static String subsectionComment(String text) { return comment("subsectionComment", text); }
	// @formatter:on

	// --------------- Methods ---------------

	public static String ofMethod(String className, String parameters, String arguments)
	{
		final ST template = methodsGroup.getInstanceOf("ofMethod");
		template.add("className", className);
		template.add("parameters", parameters);
		template.add("arguments", arguments);
		return template.render();
	}

	public static String constructor(String className, String parameters, String body)
	{
		final ST template = methodsGroup.getInstanceOf("constructor");
		template.add("className", className);
		template.add("parameters", parameters);
		template.add("body", body);
		return template.render();
	}

	// --------------- Properties ---------------

	private static String property(String templateName, String name, String type)
	{
		final ST getter = propertiesGroup.getInstanceOf(templateName);
		getter.add("name", name);
		getter.add("type", type);
		return getter.render();
	}

	// @formatter:off
	public static String getter(String name, String type) { return property("getter", name, type); }
	public static String setter(String name, String type) { return property("setter", name, type); }
	public static String field(String name, String type) { return property("field", name, type); }
	public static String parameter(String name, String type) { return property("parameter", name, type); }
	public static String setThis(String name, String type) { return property("setThis", name, type); }
	public static String getterImpl(String name, String type) { return property("getterImpl", name, type); }
	public static String setterImpl(String name, String type) { return property("setterImpl", name, type); }
	// @formatter:on
}
