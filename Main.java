import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import javax.swing.JTextArea;


public class Main extends JFrame  {

	private JPanel contentPane;
	private JButton btnNewButton;
	private JButton btnGenerate; 
	private JTextField textField;
	private JTextArea textArea;
	private JLabel lblEnterTheAngular;
	
	

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Main frame = new Main();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

////////////////////////////////////////////GUI for Graham Scan Convex Hull///////////////////////////////////////	
	public Main() {
				
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea("Running Time:");
		textArea.setBounds(10, 533, 564, 118);
		contentPane.add(textArea);
		
		lblEnterTheAngular = new JLabel("Enter the angular increase(<= 6 ie 2PI)");
		lblEnterTheAngular.setBounds(10, 11, 240, 24);
		lblEnterTheAngular.setForeground(Color.RED);		
		contentPane.add(lblEnterTheAngular);
		
		textField = new JTextField();
		textField.setBounds(240, 13, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnGenerate = new JButton("Generate");
		btnGenerate.setBounds(240, 51, 89, 23);
		btnGenerate.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				long start_time=System.currentTimeMillis();
				textArea.append("\nThe start of Graham Scan algorithm :"+start_time+" millisec\n");
				if(textField.getText().equals(""))
				{
				JOptionPane.showMessageDialog(contentPane, "Must enter a value between 0 to 6");
				}
				else
				{
				String angle_incr=textField.getText();
				float increase=Float.parseFloat(angle_incr);
				CircularRandomGenerator pnts=new CircularRandomGenerator(increase);
				pnts.doDrawing(getGraphics());
				pnts.setLocation(0, -20);				
				pnts.setSize(pnts.getPreferredSize());
				contentPane.add(pnts);
				long end_time=System.currentTimeMillis();
				textArea.append("The end of Graham Scan algorithm:"+end_time+" millisec \n");
				textArea.append("Total time taken for execution is :"+(end_time-start_time)+" millisec\n");
				repaint();
			   }
		}
		});
	
		
		contentPane.add(btnGenerate);
		
		setTitle("GRAHAM SCAN CONVEX ALGORITHM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     	setBounds(100, 100, 533, 300);
	    setResizable(true);
		setSize(600,700);			
		
	}
	
	
}//	End of Graham Class
