/*
 * Copyright (C) 2015, Jie Chen. github.com/JChen.Byte
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the 
 * Software is furnished to do so, subject to the following conditions: 
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software. 
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE. 
 */

package _2048;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Random;

import javax.swing.*;
import javax.swing.border.Border;

public class _2048 {
	private boolean status = true;
	private int score = 0;
	private int multiplier = 1;

	/* necessary components using JSwing. */
	JLabel label[] = new JLabel[16];

	JLabel leftEmpty = new JLabel("");
	JLabel rightEmpty = new JLabel("");
	JLabel scoreLeft = new JLabel("Score: ", SwingConstants.CENTER);
	JLabel scoreRight = new JLabel("0", SwingConstants.CENTER);

	JFrame frame = new JFrame("A Simple 2048 Game");
	JPanel main = new JPanel(new GridLayout(6, 4));
	JButton left = new JButton("LEFT");
	JButton right = new JButton("RIGHT");
	JButton up = new JButton("UP");
	JButton down = new JButton("DOWN");

	// Constructor
	public _2048() {
		JOptionPane.showMessageDialog(null,
				"This is a simple 2048 game created by Jie Chen.\ngithub.com/" 
		+ "JChenByte/2048");
		JOptionPane.showMessageDialog(null,
				"Instruction: \nPlease use the buttons on screen to control " 
		+ "the direction.");
		// init the game.
		init();
	}

	/* init method. Load game window. */
	public void init() {
		Border defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

		/* Set styles for control buttons */
		left.setBorder(defaultBorder);
		left.setFont(new Font("Serif", Font.PLAIN, 20));
		left.setBackground(new Color(37, 181, 230));
		left.setForeground(Color.WHITE);

		right.setBorder(defaultBorder);
		right.setFont(new Font("Serif", Font.PLAIN, 20));
		right.setBackground(new Color(37, 181, 230));
		right.setForeground(Color.WHITE);

		up.setBorder(defaultBorder);
		up.setFont(new Font("Serif", Font.PLAIN, 20));
		up.setBackground(new Color(37, 181, 230));
		up.setForeground(Color.WHITE);

		down.setBorder(defaultBorder);
		down.setFont(new Font("Serif", Font.PLAIN, 20));
		down.setBackground(new Color(37, 181, 230));
		down.setForeground(Color.WHITE);

		/* Add actionListeners to buttons */
		left.addActionListener(new leftActionListener());
		right.addActionListener(new rightActionListener());
		up.addActionListener(new upActionListener());
		down.addActionListener(new downActionListener());
		
		scoreLeft.setFont(new Font("Serif", Font.PLAIN, 20));
		scoreRight.setFont(new Font("Serif", Font.PLAIN, 20));

		main.add(leftEmpty);
		main.add(scoreLeft);
		main.add(scoreRight);
		main.add(rightEmpty);

		/* Set styles for blocks */
		for (int i = 0; i < 16; i++) {
			label[i] = new JLabel("", SwingConstants.CENTER);
			label[i].setBorder(defaultBorder);
			label[i].setFont(new Font("Serif", Font.PLAIN, 30));
			label[i].setOpaque(true);
			main.add(label[i]);
		}

		painter();

		main.add(left);
		main.add(right);
		main.add(up);
		main.add(down);

		main.setPreferredSize(new Dimension(480, 400));
		frame.add(main);

		frame.pack();
		frame.setVisible(true);

		// Generate a new number at random location.
		renderNewBlock();

	}

	/* Add action listener for left button. */
	class leftActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			performLeft();
		}
	}

	/* Perform a left action. */
	private void performLeft() {
		if (status == true) {
			for (int i = 0; i < 4; i++) {
				for (int k = 4 * i + 1; k < 4 * i + 4; k++) {
					for (int n = k - 1; n >= 4 * i; n--) {

						/*
						 * If the block on the left is empty, or else if they
						 * have same value.
						 */
						if (label[n].getText().equals("")) {
							label[n].setText(label[n + 1].getText());
							label[n + 1].setText("");

							/* Set background color. */
							label[n].setBackground(getColor(n));
							label[n + 1].setBackground(getColor(n + 1));
						} else if (label[n].getText().equals(label[n + 1].
								getText())) {
							// Label n equals its value times 2.
							label[n].setText(Integer.toString((Integer.
									parseInt(label[n].getText()) * 2)));
							label[n + 1].setText("");

							/* Set background color. */
							label[n].setBackground(getColor(n));
							label[n + 1].setBackground(getColor(n + 1));

							// Increase score when successfully merge two
							// blocks.
							score += Integer.parseInt(label[n].getText()) * 2;
							// System.out.println(score);
						}
					}
				}
			}

			postAction();
		}
	}

	/* Add action listener for right button. */
	class rightActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			performRight();
		}
	}

	/* Perform method when user click RIGHT button. */
	private void performRight() {
		// Only perform when the status is true.
		if (status == true) {
			for (int i = 0; i < 4; i++) {
				for (int k = 4 * i + 3; k >= 4 * i; k--) {
					for (int n = k + 1; n <= 4 * i + 3; n++) {

						/*
						 * If the block on the right is empty, or else if they
						 * have same value.
						 */
						if (label[n].getText().equals("")) {
							label[n].setText(label[n - 1].getText());
							label[n - 1].setText("");

							/* Set background color. */
							label[n].setBackground(getColor(n));
							label[n - 1].setBackground(getColor(n - 1));
						} else if (label[n].getText().equals(label[n - 1].
								getText())) {
							// Label n equals its value times 2.
							label[n].setText(Integer.toString((Integer.
									parseInt(label[n].getText()) * 2)));
							label[n - 1].setText("");

							/* Set background color. */
							label[n].setBackground(getColor(n));
							label[n - 1].setBackground(getColor(n - 1));

							// Increase score when successfully merge two
							// blocks.
							score += Integer.parseInt(label[n].getText()) * 2;
							// System.out.println(score);
						}
					}
				}
			}

			postAction();
		}
	}

	/* Add action listener for up button. */
	class upActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			performUp();
		}
	}

	/* Perform method when user click RIGHT button. */
	private void performUp() {
		// Only perform when the status is true.
		if (status == true) {
			for (int i = 12; i < 16; i++) {
				for (int k = i - 8; k <= i; k += 4) {
					for (int n = k - 4; n >= i - (4 * 3); n -= 4) {

						/*
						 * If the block on the top is empty, or else if they
						 * have same value.
						 */
						if (label[n].getText().equals("")) {
							label[n].setText(label[n + 4].getText());
							label[n + 4].setText("");

							/* Set background color. */
							label[n].setBackground(getColor(n));
							label[n + 4].setBackground(getColor(n + 4));

						} else if (label[n].getText().equals(label[n + 4].
								getText())) {
							// Label n equals its value times 2.
							label[n].setText(Integer.toString((Integer.
									parseInt(label[n].getText()) * 2)));
							label[n + 4].setText("");

							/* Set background color. */
							label[n].setBackground(getColor(n));
							label[n + 4].setBackground(getColor(n + 4));

							// Increase score when successfully merge two
							// blocks.
							score += Integer.parseInt(label[n].getText()) * 2;
							// System.out.println(score);
						}
					}
				}
			}

			postAction();
		}
	}

	/* Add action listener for down button. */
	class downActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			performDown();
		}
	}

	/* Perform method when user click DOWN button. */
	private void performDown() {
		// Only perform when the status is true.
		if (status == true) {
			for (int i = 0; i < 4; i++) {
				for (int k = i + 8; k >= i; k -= 4) {
					for (int n = (k + 4); n <= i + 12; n += 4) {

						/*
						 * If the block below is empty, or else if they have
						 * same value.
						 */
						if (label[n].getText().equals("")) {
							label[n].setText(label[n - 4].getText());
							label[n - 4].setText("");

							/* Set background color. */
							label[n].setBackground(getColor(n));
							label[n - 4].setBackground(getColor(n - 4));

						} else if (label[n].getText().equals(label[n - 4].
								getText())) {
							// Label n equals its value times 2.
							label[n].setText(Integer.toString((Integer.
									parseInt(label[n].getText()) * 2)));
							label[n - 4].setText("");

							/* Set background color. */
							label[n].setBackground(getColor(n));
							label[n - 4].setBackground(getColor(n - 4));

							// Increase score when successfully merge two
							// blocks.
							score += Integer.parseInt(label[n].getText()) * 2;
							// System.out.println(score);
						}
					}
				}
			}

			postAction();
		}
	}

	/* Perform postAction after each clicking action. */
	private void postAction() {
		isOver();
		setMultiplier();
		renderNewBlock();

		// Update Score
		scoreRight.setText(Integer.toString(score));

	}

	/*
	 * Determine the current multiplier based on current score, used to
	 * calculating potential value for generated block.
	 */
	private void setMultiplier() {
		if (score < 40) {
			multiplier = 1;
		} else if ((score >= 40) && (score < 120)) {
			multiplier = 2;
		} else if ((score >= 120) && (score < 280)) {
			multiplier = 3;
		} else if ((score >= 280) && (score < 600)) {
			multiplier = 4;
		} else if ((score >= 600) && (score < 1240)) {
			multiplier = 5;
		} else if ((score >= 1240) && (score < 2520)) {
			multiplier = 6;
		} else if ((score >= 2520) && (score < 5080)) {
			multiplier = 7;
		} else if ((score >= 5080) && (score < 10200)) {
			multiplier = 8;
		} else if ((score >= 10200) && (score < 20440)) {
			multiplier = 9;
		} else if ((score >= 20440) && (score < 40920)) {
			multiplier = 10;
		} else {
			multiplier = 11;
		}
	}

	/* Randomly select an empty block. */
	public int genNewNumber() {
		Random gen = new Random();
		int key;

		// Find an empty block.
		do {
			key = gen.nextInt(16);
		} while (!(label[key].getText().equals("")));

		// Return block ID.
		return key;
	}

	// Render a block using location generated by genNewNumber().
	public void renderNewBlock() {
		if (status == true) {
			int key = genNewNumber();
			Random gen = new Random();
			if (multiplier <= 3) {
				int newMultiplier = gen.nextInt(multiplier) + 1;
				label[key].setText(Integer.toString((int) Math.pow(2, 
						newMultiplier)));
			} else {
				int newMultiplier = gen.nextInt(4) + (multiplier - 3);
				label[key].setText(Integer.toString((int) Math.pow(2, 
						newMultiplier)));
			}

			// Set background color.
			label[key].setBackground(getColor(key));
		}
	}

	/* Paint the entier main JPanel. */
	public void painter() {
		for (int n = 0; n < 16; n++) {
			label[n].setBackground(getColor(n));
		}
	}

	/* Get color of block n. */
	public Color getColor(int n) {

		Color tempColor;

		/*
		 * Check if block n has a value, if yes, get the color that rep its
		 * value.
		 */
		if (label[n].getText().equals("")) {
			tempColor = Color.WHITE;
		} else {
			int num = Integer.parseInt(label[n].getText());

			switch (num) {
			case 2:
				tempColor = new Color(141, 90, 151);
				break;
			case 4:
				tempColor = new Color(128, 155, 206);
				break;
			case 8:
				tempColor = new Color(254, 147, 140);
				break;
			case 16:
				tempColor = new Color(127, 229, 203);
				break;
			case 32:
				tempColor = new Color(226, 207, 234);
				break;
			case 64:
				tempColor = new Color(244, 97, 151);
				break;
			case 128:
				tempColor = new Color(78, 205, 196);
				break;
			case 256:
				tempColor = new Color(215, 255, 171);
				break;
			case 512:
				tempColor = new Color(239, 123, 69);
				break;
			case 1024:
				tempColor = new Color(197, 174, 135);
				break;
			case 2048:
				tempColor = new Color(183, 110, 121);
				break;
			default:
				tempColor = new Color(216, 226, 220);
				break;
			}
		}

		return tempColor;
	}

	/* Check if the game is over */
	private void isOver() {
		int counter = 0;

		/* Check how many blocks are currently empty. */
		for (int i = 0; i < 16; i++) {
			if (label[i].getText().equals("")) {
				counter++;
			}
		}

		// If there is no more empty block.
		if (counter == 0) {
			status = false;

			// Show message
			JOptionPane.showMessageDialog(null, "Game Over. Your Final Score "
					+ "is: " + score + ".");

			// Exit the game
			frame.setVisible(false);
			frame.dispose();
		}

	}

}
