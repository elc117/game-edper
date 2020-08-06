package com.edper.figures;

import java.util.ArrayList;
import java.util.Random;

public class Grid {
	private static final int BLUE = 1;
	private static final int YELLOW = 2;
	private static final int RED = 3;
	
	private int[][] matrix;
	public int numRows = 16;
	public int numColumns = 8;
	
	private ArrayList<Pill> pillList;
	private ArrayList<Covid> covidList;
	private ArrayList<HalfPill> halfPillList;
	
	private int covidCount;
	
	private int clearColor;
	private float comboCounter = 0f;
	
	private boolean gameOver = false;
	
	private int maxLevel = 78;
	
	Random rand = new Random();
	
	public Grid() {
		this.matrix = new int[numRows][numColumns];
		this.pillList = new ArrayList<Pill>();
		this.covidList = new ArrayList<Covid>();
		this.halfPillList = new ArrayList<HalfPill>();
	}
	
	public void genCovids(int currentLevel) {
		if(currentLevel > maxLevel)
			currentLevel = maxLevel;
		for(int i=0; i < currentLevel + 2; i++) {
			this.covidList.add(new Covid(this.matrix, this.covidList.size()));
			this.matrix[this.covidList.get(i).getY()][this.covidList.get(i).getX()] = this.covidList.get(i).getColor();
			this.covidCount = this.covidList.size();
		}
	}
	
	private boolean wasCovid(int y, int x) {
		for(int i=0; i < this.covidList.size(); i++) {
			if(this.covidList.get(i).getY() == y && this.covidList.get(i).getX() == x) 
				return true;
		}
		return false;
	}
	
	private boolean wasHalfPill(int y, int x) {
		for(int i=0; i < this.halfPillList.size(); i++) {
			if(this.halfPillList.get(i).getY() == y && this.halfPillList.get(i).getX() == x)
				return true;
		}
		return false;
	}
	
	private int wasPill(int y, int x) {
		for(int i = 0; i < this.pillList.size()-1; i++) {
			if(this.pillList.get(i).getY1() == y && this.pillList.get(i).getX1() == x) {
				if(this.pillList.get(i).colorsAreEqual() && this.pillList.get(i).getIsHorizontal())
					return 1;
				if(this.pillList.get(i).colorsAreEqual() && !this.pillList.get(i).getIsHorizontal())
					return 2;
				if(!this.pillList.get(i).colorsAreEqual() && this.pillList.get(i).getIsHorizontal())
					return 3;
				if(!this.pillList.get(i).colorsAreEqual() && !this.pillList.get(i).getIsHorizontal())
					return 4;
			}
			if(this.pillList.get(i).getY2() == y && this.pillList.get(i).getX2() == x) {
				if(this.pillList.get(i).colorsAreEqual() && this.pillList.get(i).getIsHorizontal())
					return 5;
				if(this.pillList.get(i).colorsAreEqual() && !this.pillList.get(i).getIsHorizontal())
					return 6;
				if(!this.pillList.get(i).colorsAreEqual() && this.pillList.get(i).getIsHorizontal())
					return 7;
				if(!this.pillList.get(i).colorsAreEqual() && !this.pillList.get(i).getIsHorizontal())
					return 8;
			}
		}
		return 0;
	}
	
	public void updateGrid() {
		for (int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				this.matrix[i][j] = 0;
			}
		}
		
		for(int i=0; i < this.covidList.size(); i++) {
			this.matrix[this.covidList.get(i).getY()][this.covidList.get(i).getX()] = this.covidList.get(i).getColor();
		}
		for(int i=0; i < this.halfPillList.size(); i++) {
			this.matrix[this.halfPillList.get(i).getY()][this.halfPillList.get(i).getX()] = this.halfPillList.get(i).getColor();
		}
		for(int i=0; i < this.pillList.size()-1; i++) {
			this.matrix[this.pillList.get(i).getY1()][this.pillList.get(i).getX1()] = this.pillList.get(i).getColor1();
			this.matrix[this.pillList.get(i).getY2()][this.pillList.get(i).getX2()] = this.pillList.get(i).getColor2();
		}
	}
	
	public void detectGameOver() {
		if((this.matrix[0][3] != 0 || this.matrix[0][4] != 0) && !hasPillFalling()) 
			this.gameOver = true;
	}
	
	public void resetGrid(int currentLevel) {
		gameOver = false;
		for (int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				this.matrix[i][j] = 0;
			}
		}
		
		this.pillList.clear();
		this.covidList.clear();
		this.halfPillList.clear();
		genCovids(currentLevel);
	}
	
	public void createPill() {
		if(this.pillList.size() == 0) {
			this.pillList.add(new Pill());
			this.pillList.add(new Pill());
		}
		else
			this.pillList.add(new Pill());
	}
	
	private void createHalfPill(int y, int x, int pos) {
		for(int i=0; i < this.pillList.size()-1; i++) {
			if(this.pillList.get(i).getX1() == x && this.pillList.get(i).getY1() == y || this.pillList.get(i).getX2() == x && this.pillList.get(i).getY2() == y) {
				if(pos == 1) {
					this.halfPillList.add(new HalfPill(this.pillList.get(i).getX1(), this.pillList.get(i).getY1(), this.pillList.get(i).getColor1()));
				}
				if(pos == 2) {
					this.halfPillList.add(new HalfPill(this.pillList.get(i).getX2(), this.pillList.get(i).getY2(), this.pillList.get(i).getColor2()));
				}
			}
		}
	}
	
	private void removeCovid(int y, int x){
		for(int i=0; i < this.covidList.size(); i++) {
			if(this.covidList.get(i).getY() == y && this.covidList.get(i).getX() == x)
				this.covidList.remove(i);
		}
	}
	
	private void removePill(int y, int x) {
		for(int i=0; i < this.pillList.size()-1; i++) {
			if(this.pillList.get(i).getY1() == y && this.pillList.get(i).getX1() == x || this.pillList.get(i).getY2() == y && this.pillList.get(i).getX2() == x)
				this.pillList.remove(i);
		}
	}
	
	private void removeHalfPill(int y, int x) {
		for(int i=0; i < this.halfPillList.size(); i++) {
			if(this.halfPillList.get(i).getY() == y && this.halfPillList.get(i).getX() == x)
				this.halfPillList.remove(i);
		}
	}
	
	public float removeLine() {
		comboCounter = 0;
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				if(this.matrix[i][j] != 0) {
					if(i+1 < 16 && i+2 < 16 && i+3 < 16) {
						if(this.matrix[i][j] == this.matrix[i+1][j] && this.matrix[i][j] == this.matrix[i+2][j] && this.matrix[i][j] == this.matrix[i+3][j]) {
							clearColor = this.matrix[i][j];
							for(int k = i; k < numRows; k++) {	
								if(clearColor == this.matrix[k][j]) {
									this.matrix[k][j] = 0;
									if(wasCovid(k,j) ) {
										this.covidCount-=1;
										removeCovid(k,j);
										comboCounter += 2f;
									}
									if(wasHalfPill(k,j) ) {
										removeHalfPill(k,j);
										comboCounter += 0.5f;
									}
									if(wasPill(k,j) != 0) {
										if(wasPill(k,j) == 1 || wasPill(k,j) == 3 || wasPill(k,j) == 4)
											createHalfPill(k,j,2);
										if(wasPill(k,j) == 5 || wasPill(k,j) == 7 || wasPill(k,j) == 8)
											createHalfPill(k,j,1);
										removePill(k,j);
										comboCounter += 0.5f;
									}
								} else {
									return comboCounter;
								}
							}
						}
					}
					if(j+1 < 8 && j+2 < 8 && j+3 < 8) {
						if(this.matrix[i][j] == this.matrix[i][j+1] && this.matrix[i][j] == this.matrix[i][j+2] && this.matrix[i][j] == this.matrix[i][j+3]) {
							clearColor = this.matrix[i][j];
							for(int k = j; k < numColumns; k++) {
								if(clearColor == this.matrix[i][k]) {
									this.matrix[i][k] = 0;
									if(wasCovid(i,k) ) {
										this.covidCount-=1;
										removeCovid(i,k);
										comboCounter += 2f;
									}
									if(wasHalfPill(i,k) ) {
										removeHalfPill(i,k);
										comboCounter += 0.5f;
									}
									if(wasPill(i,k) != 0 ) {
										if(wasPill(i,k) == 2 || wasPill(i,k) == 3 || wasPill(i,k) == 4)
											createHalfPill(i,k,2);
										if(wasPill(i,k) == 6 || wasPill(i,k) == 7 || wasPill(i,k) == 8)
											createHalfPill(i,k,1);
										removePill(i,k);
										comboCounter += 0.5f;
									}
								} else {
									return comboCounter;
								}
							}
						}
					}	
				}
			}
		}
		return comboCounter;
	}
	
	public boolean detectWin() {
		if(this.covidCount == 0) return true;
		return false;
	}

	public ArrayList<Pill> getPillList() {
		return this.pillList;
	}
	
	public ArrayList<Covid> getCovidList(){
		return this.covidList;
	}
	
	public ArrayList<HalfPill> getHalfPillList(){
		return this.halfPillList;
	}

	public boolean hasBlueCovid() {
		for(int i = 0; i < this.covidList.size(); i++) {
			if(this.covidList.get(i).getColor() == BLUE)
				return true;
		}
		return false;
	}

	public boolean hasYellowCovid() {
		for(int i = 0; i < this.covidList.size(); i++) {
			if(this.covidList.get(i).getColor() == YELLOW)
				return true;
		}
		return false;
	}

	public boolean hasRedCovid() {
		for(int i = 0; i < this.covidList.size(); i++) {
			if(this.covidList.get(i).getColor() == RED)
				return true;
		}
		return false;
	}
	
	public boolean hasPillFalling() {
		for(int i=0; i < pillList.size()-1; i++) {
			if(pillList.get(i).getIsFalling())
				return true;
		}
		return false;
	}
	
	public boolean hasOtherPillsFalling() {
		for(int i=0; i < pillList.size()-2; i++) {
			if(pillList.get(i).getIsFalling())
				return true;
		}
		return false;
	}
	
	public boolean hasHalfPillFalling() {
		for(int i=0; i < halfPillList.size(); i++) {
			if(halfPillList.get(i).getIsFalling())
				return true;
		}
		return false;
	}
	
	public int[][] getMatrix(){
		return matrix;
	}

	public boolean getGameOver() {
		return gameOver;
	}

	public int getCovidCount() {
		return covidCount;
	}
}
