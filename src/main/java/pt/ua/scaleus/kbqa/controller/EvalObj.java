package pt.ua.scaleus.kbqa.controller;

import pt.ua.scaleus.kbqa.datastructures.Answer;

public class EvalObj {
	private String comment;
	private double fmax;
	private double pmax;
	private double rmax;
	private String question;
	private int id;
	private Answer answer;

	public EvalObj(int id, String question, double fmax, double pmax, double rmax, String comment, Answer answer) {
		this.id = id;
		this.question = question;
		this.fmax = fmax;
		this.rmax = rmax;
		this.pmax = pmax;
		this.comment = comment;
		this.answer = answer;
	}

	public Answer getAnswer() {
		return answer;
	}

	@Override
	public String toString() {
		return "EvalObj [comment=" + comment + ", fmax=" + fmax + ", pmax=" + pmax + ", rmax=" + rmax + ", question=" + question + ", id=" + id + "]";
	}

	public int getId() {
		return id;
	}

	public String getComment() {
		return comment;
	}

	public double getFmax() {
		return fmax;
	}

	public double getPmax() {
		return pmax;
	}

	public double getRmax() {
		return rmax;
	}

	public String getQuestion() {
		return question;
	}
}
