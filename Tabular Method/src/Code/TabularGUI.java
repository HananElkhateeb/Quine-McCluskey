package Code;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;
import java.awt.SystemColor;
import javax.swing.JSlider;

public class TabularGUI {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TabularGUI window = new TabularGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TabularGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		TabularMethod h=new TabularMethod();
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 160, 122));
		frame.setBounds(100, 100, 996, 620);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setBounds(251, 171, 449, 54);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblTabularMethod = new JLabel("Tabular Method");
		lblTabularMethod.setForeground(new Color(25, 25, 112));
		lblTabularMethod.setFont(new Font("Comic Sans MS", Font.BOLD, 82));
		lblTabularMethod.setBounds(117, 13, 663, 83);
		frame.getContentPane().add(lblTabularMethod);
		
		JLabel lblMintermsspaceOrComma = new JLabel("Minterms(space or comma separated):");
		lblMintermsspaceOrComma.setForeground(new Color(25, 25, 112));
		lblMintermsspaceOrComma.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblMintermsspaceOrComma.setBounds(204, 129, 523, 29);
		frame.getContentPane().add(lblMintermsspaceOrComma);
		
		JLabel lblDontcaresspaceOrComma = new JLabel("Don'tCares(space or comma separated):");
		lblDontcaresspaceOrComma.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblDontcaresspaceOrComma.setForeground(new Color(25, 25, 112));
		lblDontcaresspaceOrComma.setBounds(207, 254, 503, 34);
		frame.getContentPane().add(lblDontcaresspaceOrComma);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		textField_1.setBounds(251, 301, 449, 54);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnSolve = new JButton("Solve");
		btnSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String minterm=textField.getText();
				String dc=textField_1.getText();
				h.lists(minterm, dc);
				String result=h.printResults();
				textField_2.setText(result);
				
			
			}
		});
		btnSolve.setBackground(new Color(255, 239, 213));
		btnSolve.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnSolve.setForeground(new Color(25, 25, 112));
		btnSolve.setBounds(412, 394, 125, 34);
		frame.getContentPane().add(btnSolve);
		
		textField_2 = new JTextField();
		textField_2.setForeground(new Color(25, 25, 112));
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_2.setBounds(48, 452, 906, 83);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JSlider slider = new JSlider();
		slider.setBounds(94, 501, 200, 26);
		frame.getContentPane().add(slider);
	}
}
