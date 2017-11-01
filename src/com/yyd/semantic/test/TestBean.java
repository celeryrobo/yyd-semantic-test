package com.yyd.semantic.test;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class TestBean extends AbstractSemanticResult {
	private Integer id;
	private String text;

	public TestBean(Integer id, String text) {
		this.id = id;
		this.text = text;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
