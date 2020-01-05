package com.dsa.beans;

public class HuffmanNode implements Comparable<HuffmanNode> {
	public int frequency;
	public char data;
	public HuffmanNode left, right;

	public int compareTo(HuffmanNode node) {
		return frequency - node.frequency;
	}
}