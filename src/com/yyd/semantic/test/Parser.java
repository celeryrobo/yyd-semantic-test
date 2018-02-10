package com.yyd.semantic.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

class Scanner implements Iterable<Character>, Iterator<Character> {
	private String target;
	private int currentIndex;
	private int endIndex;

	public Scanner(String target) {
		this.target = target.trim();
		this.currentIndex = 0;
		this.endIndex = target.length() - 1;
	}

	@Override
	public boolean hasNext() {
		if (currentIndex > endIndex) {
			return false;
		}
		return true;
	}

	@Override
	public Character next() {
		if (currentIndex > endIndex) {
			return null;
		}
		return target.charAt(currentIndex++);
	}

	@Override
	public Iterator<Character> iterator() {
		return this;
	}
}

abstract class Node {
	public final static int N = 0;
	public final static int L = 1;
	public final static int M = 2;
	public final static int H = 3;

	private char node;
	private int flag = N;
	private List<Node> nodes = new ArrayList<>();

	@Override
	public String toString() {
		String name = new String(new char[] { node });
		StringBuilder sb = new StringBuilder("[");
		sb.append(name).append(": ");
		for (Node node : nodes) {
			sb.append(node);
		}
		sb.append("]");
		return sb.toString();
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public char getNode() {
		return node;
	}

	protected void setNode(char node) {
		this.node = node;
	}

	public int getFlag() {
		return flag;
	}

	protected void setFlag(int flag) {
		this.flag = flag;
	}
}

class GroupNode extends Node {
	public GroupNode() {
		setNode('^');
	}
}

class LeftNode extends Node {
	public LeftNode(char node) {
		setNode(node);
	}
}

class RightNode extends Node {
	public RightNode(char node) {
		setNode(node);
	}
}

class OrNode extends Node {
	public OrNode(char node) {
		setNode(node);
		setFlag(L);
	}
}

class AndNode extends Node {
	public AndNode(char node) {
		setNode(node);
	}
}

class StarNode extends Node {
	public StarNode(char node) {
		setNode(node);
		setFlag(M);
	}
}

class PlusNode extends Node {
	public PlusNode(char node) {
		setNode(node);
		setFlag(M);
	}
}

class LeftMaybeNode extends Node {
	public LeftMaybeNode(char node) {
		setNode(node);
	}
}

class RightMaybeNode extends Node {
	public RightMaybeNode(char node) {
		setNode(node);
	}
}

class LetterNode extends Node {
	public LetterNode(char node) {
		setNode(node);
	}
}

class EndNode extends Node {
	public EndNode() {
		setNode('$');
		setFlag(H);
	}
}

public class Parser {
	private Scanner scanner;
	private List<Node> nodes;
	private Stack<Node> ops;
	private Stack<Node> vars;
	private Node result;

	public Parser(String target) {
		scanner = new Scanner(target);
		nodes = new ArrayList<>();
		for (Character c : scanner) {
			Node node = buildNode(c);
			if (node != null) {
				nodes.add(node);
			}
		}
		nodes.add(new EndNode());
		ops = new Stack<>();
		vars = new Stack<>();
		result = buildAst();
	}

	private void visit(Node node) {
		if (node instanceof LetterNode) {
			vars.push(node);
		} else {
			switch (node.getNode()) {
			case '(':
			case '[':
			case '&':
			case '|':
				ops.push(node);
				break;
			case '+':
			case '*': {
				Node n = vars.pop();
				node.getNodes().add(n);
				vars.push(node);
			}
				break;
			case ')': {
				Node nv = null;
				do {
					nv = ops.pop();
					catNode(nv);
				} while (nv != null && nv.getNode() != '(');
			}
				break;
			case '$': {
				while (!ops.isEmpty()) {
					catNode(ops.pop());
				}
			}
				break;
			}
		}
	}

	private void catNode(Node nv) {
		switch (nv.getNode()) {
		case '(':
		case '[': {
			Node grp = new GroupNode();
			grp.getNodes().add(vars.pop());
			vars.push(grp);
		}
			break;
		case '|':
		case '&': {
			Node n1 = vars.pop();
			Node n2 = vars.pop();
			nv.getNodes().add(n2);
			nv.getNodes().add(n1);
			vars.push(nv);
		}
			break;
		}
	}

	private Node buildAst() {
		for (Node node : nodes) {
			visit(node);
		}
		return vars.pop();
	}

	private Node buildNode(char nodeChar) {
		if (Character.isAlphabetic(nodeChar)) {
			return new LetterNode(nodeChar);
		} else {
			switch (nodeChar) {
			case '(':
				return new LeftNode(nodeChar);
			case ')':
				return new RightNode(nodeChar);
			case '[':
				return new LeftMaybeNode(nodeChar);
			case ']':
				return new RightMaybeNode(nodeChar);
			case '|':
				return new OrNode(nodeChar);
			case '*':
				return new StarNode(nodeChar);
			case '+':
				return new PlusNode(nodeChar);
			case '&':
				return new AndNode(nodeChar);
			}
		}
		return null;
	}

	public Node getResult() {
		return result;
	}

}

