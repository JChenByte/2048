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

import java.util.Random;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.Border;
import java.util.*;

public class _2048 {
	private boolean status = true;
	private int score = 0;
	private int multiplier = 1;

	/* necessary components using JSwing. */
	JLabel label[] = new JLabel[16];
	JFrame frame = new JFrame("2048 Demo");
	JPanel main = new JPanel(new GridLayout(5, 4));
	JButton left = new JButton("LEFT");
	JButton right = new JButton("RIGHT");
	JButton up = new JButton("UP");
	JButton down = new JButton("DOWN");

	// Constructor
	public _2048() {
		JOptionPane.showMessageDialog(null, "This is a simple 2048 demo. "
				+ "\nCreated by Jie Chen.");

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

		/* Set styles for blocks */
		for (int i = 0; i < 16; i++) {
			label[i] = new JLabel("", SwingConstants.CENTER);
			label[i].setBorder(defaultBorder);
			label[i].setFont(new Font("Serif", Font.PLAIN, 30));

			main.add(label[i]);
		}

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
						} else if (label[n].getText().equals(label[n + 1]
								.getText())) {
							// Label n equals its value times 2.
							label[n].setText(Integer.toString((Integer.
									parseInt(label[n].getText()) * 2)));
							label[n + 1].setText("");

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
						} else if (label[n].getText().equals(label[n - 1].
								getText())) {
							// Label n equals its value times 2.
							label[n].setText(Integer.toString((Integer.
									parseInt(label[n].getText()) * 2)));
							label[n - 1].setText("");

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
				for (int k = i; k >= i - (4 * 2); k = k - 4) {
					for (int n = k - 4; n >= i - (4 * 3); n = n - 4) {

						/*
						 * If the block on the top is empty, or else if they
						 * have same value.
						 */
						if (label[n].getText().equals("")) {
							label[n].setText(label[n + 4].getText());
							label[n + 4].setText("");
						} else if (label[n].getText().equals(label[n + 4].
								getText())) {
							// Label n equals its value times 2.
							label[n].setText(Integer.toString((Integer.
									parseInt(label[n].getText()) * 2)));
							label[n + 4].setText("");

							// Increase score when successfully merge two
							// blocks.
							score += Integer.parseInt(label[n].getText()) * 2;
							System.out.println(score);
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
				for (int k = i; k <= i + 12; k = k + 4) {
					for (int n = (k + 4); n <= i + 12; n = n + 4) {

						/*
						 * If the block below is empty, or else if they have
						 * same value.
						 */
						if (label[n].getText().equals("")) {
							label[n].setText(label[n - 4].getText());
							label[n - 4].setText("");
						} else if (label[n].getText().equals(label[n - 4].
								getText())) {
							// Label n equals its value times 2.
							label[n].setText(Integer.toString((Integer.
									parseInt(label[n].getText()) * 2)));
							label[n - 4].setText("");

							// Increase score when successfully merge two
							// blocks.
							score += Integer.parseInt(label[n].getText()) * 2;
							System.out.println(score);
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
	}

	/*
	 * Determine the current multiplier based on current score, used to
	 * calculating potential value for generated block.
	 */
	private void setMultiplier() {
		if (score < 30) {
			multiplier = 1;
		} else if ((score >= 30) && (score < 60)) {
			multiplier = 2;
		} else if ((score >= 60) && score < (120)) {
			multiplier = 3;
		} else if ((score >= 120) && score < (240)) {
			multiplier = 4;
		} else if ((score >= 240) && score < (480)) {
			multiplier = 5;
		} else if ((score >= 480) && score < (960)) {
			multiplier = 6;
		} else if ((score >= 960) && score < (1920)) {
			multiplier = 7;
		} else if ((score >= 1920) && score < (3840)) {
			multiplier = 8;
		} else if ((score >= 3820) && score < (7680)) {
			multiplier = 9;
		} else if ((score >= 7680) && (score < (15360))) {
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
		}
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
			JOptionPane.showMessageDialog(null, "Game OVER!!!");
		}

	}

}
