package com.yyd.semantic.test;

public class Template {
	private String template;

	public Template(String template) {
		this.template = template;
		System.out.println(template);
	}

	public String generate(String... args) {
		String rs = this.template;
		for (int i = 0; i < args.length; i++) {
			rs = rs.replaceAll("\\{\\{" + i + "\\}\\}", args[i]);
		}
		return rs;
	}
}
