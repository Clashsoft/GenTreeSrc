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

	// --------------- Properties ---------------

	private static String property(String templateName, String name, String type)
	{
		final ST getter = propertiesGroup.getInstanceOf(templateName);
		getter.add("name", name);
		getter.add("type", type);
		return getter.render();
	}

	// @formatter:off
	public static String property(String name, String type) { return property("property", name, type); }
	public static String getter(String name, String type) { return property("getter", name, type); }
	public static String setter(String name, String type) { return property("setter", name, type); }
	public static String propertyImpl(String name, String type) { return property("propertyImpl", name, type); }
	public static String field(String name, String type) { return property("field", name, type); }
	public static String getterImpl(String name, String type) { return property("getterImpl", name, type); }
	public static String setterImpl(String name, String type) { return property("setterImpl", name, type); }
	// @formatter:on
}
