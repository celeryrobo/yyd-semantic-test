package com.yyd.semantic.test;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;

public class TestSemantic implements Semantic<TestBean> {

	@Override
	public TestBean handle(YbnfCompileResult ybnfCompileResult) {
		String text = ybnfCompileResult.toString();
		TestBean rs = new TestBean(1, text);
		rs.setErrCode(0);
		return rs;
	}

}
