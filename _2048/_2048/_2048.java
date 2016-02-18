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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class _2048 {

	private JLabel label[];

	private JLabel leftEmpty;
	private JLabel rightEmpty = new JLabel("");
	private JLabel scoreLeft = new JLabel("Score: ", SwingConstants.CENTER);
	private JLabel scoreRight = new JLabel("0", SwingConstants.CENTER);

	private JFrame frame;
	private JPanel main;
	private JButton left;
	private JButton right;
	private JButton up;
	private JButton down;

	private Utilities utilities;

	public _2048() {
		// Initialize all necessary elements.
		label = new JLabel[16];
		leftEmpty = new JLabel("");
		rightEmpty = new JLabel("");
		scoreLeft = new JLabel("Score: ", SwingConstants.CENTER);
		scoreRight = new JLabel("0", SwingConstants.CENTER);
		frame = new JFrame("A Simple 2048 Game");
		main = new JPanel(new GridLayout(6, 4));
		left = new JButton("LEFT");
		right = new JButton("RIGHT");
		up = new JButton("UP");
		down = new JButton("DOWN");
		utilities = new Utilities(label, scoreRight, frame);
	}

	private void createAndShowGUI() {
		Border defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

		/* Set styles for control buttons */
		styleForControlButton(left);
		styleForControlButton(right);
		styleForControlButton(down);
		styleForControlButton(up);

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

		utilities.painter();

		main.add(left);
		main.add(right);
		main.add(up);
		main.add(down);

		main.setPreferredSize(new Dimension(480, 400));
		frame.add(main);

		frame.pack();
		frame.setVisible(true);

		// Generate a new number at random location.
		utilities.renderNewBlock();

	}

	public static void styleForControlButton(JButton button) {
		button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		button.setFont(new Font("Serif", Font.PLAIN, 20));
		button.setBackground(new Color(37, 181, 230));
		button.setForeground(Color.WHITE);
	}

	/* Add action listener for left button. */
	class leftActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			utilities.performLeft();
		}
	}

	/* Add action listener for up button. */
	class upActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			utilities.performUp();
		}
	}

	/* Add action listener for right button. */
	class rightActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			utilities.performRight();
		}
	}

	/* Add action listener for down button. */
	class downActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			utilities.performDown();
		}
	}

	public static void main(String[] args) {
		/* Scheduling the GUI processing in the EDT */
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(null,
						"Instruction: \nPlease use the buttons on screen to "
						+ "control " + "the direction.");

				_2048 game = new _2048();
				game.createAndShowGUI();
			}
		});

	}

	public JLabel[] getLable() {
		return label;
	}

}
