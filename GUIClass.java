/* Green Glass Door
 * Objective: figure out the rule behind what objects can/can't enter through the door
 * Currently Implemented: Logic works, colors for entirely correct and incorrect answers. Is playable.
 * TBI: "Clear history" button, some kind of win criteria, specific colors in different scenarios so it is easier to see which
 * options actually can enter, counter for correct answers.

 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension; 
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
	
public class GUIClass implements ActionListener
{
	//Vars
	JFrame frame;
	JPanel contentPane;
	JLabel will, wont, period,space, picture, explanation,explanation2, counter;
	JButton button, button2;
	JTextField willBring,wontBring;	
	JPanel topPanel;
	JTextPane tPane;
	Color custGreen =   new Color(10, 160, 0);
	int correct = 0;
	
	//GUI constructor
	public GUIClass()
	{
		//Title on  top of frame
		frame = new JFrame("The Door");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Layout
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.PAGE_AXIS));
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		//Text Pane
		tPane= new JTextPane();
		tPane.setBounds(10,10,200,200);
		//tPane.setEditable(false);
		contentPane.add(tPane);
		tPane.setPreferredSize(new Dimension(300,300)); 
		
		
		//Scrollpanel on text area
		JScrollPane scroll = new JScrollPane(tPane);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scroll);
		
		space= new JLabel();
		space.setText("  ");
		contentPane.add(space);
		
		//Explanation
		explanation= new JLabel();
		explanation.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		explanation.setText("Figure out the rule of what can enter through the door...");
		contentPane.add(explanation);
		
		explanation2= new JLabel();
		explanation2.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		explanation2.setText("Example: Through the door, I will bring an apple, but not an orange. \n");
		contentPane.add(explanation2);
		
		space= new JLabel();
		space.setText("  ");
		contentPane.add(space); 
		
		//Counter for correct answers
		counter= new JLabel();
		counter.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		counter.setText("Fully Correct Answers: " + correct );
		contentPane.add(counter);
		
		space= new JLabel();
		space.setText("  ");
		contentPane.add(space); 
		
		//Input and filler labels
		will= new JLabel();
		will.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		will.setText("Through the door I will bring a/an");
		contentPane.add(will);
		
		willBring=new JTextField(15);
		contentPane.add(willBring);
		
		wont = new JLabel();
		wont.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		wont.setText("but I won't bring a/an");
		contentPane.add(wont);
		
		wontBring=new JTextField(15);
		contentPane.add(wontBring);

		
		//Enter button
		button = new JButton("Enter");
		button.setAlignmentX(JButton.CENTER_ALIGNMENT);
		button.setActionCommand ("test");
		button.addActionListener(this);
		contentPane.add(button);
		
		//Reset button
		button2 = new JButton("Reset");
		button2.setAlignmentX(JButton.CENTER_ALIGNMENT);
		button2.setActionCommand ("reset");
		button2.addActionListener(this);
		contentPane.add(button2);

		frame.setContentPane(contentPane);
		frame.pack();
		frame.setVisible(true);
	}
	
	//Action Performed definition
	public void actionPerformed(ActionEvent event)
	{
		String eventName = event.getActionCommand();
		
		//reset button
		if(eventName.equals ("reset"))
		{
			tPane.setText("");
			correct = 0;
			counter.setText("Fully Correct Answers: " + correct );
		}
		
		//Tests the input to see which can enter and which cannot
		if(eventName.equals ("test"))
		{
			String will = willBring.getText(); 
			String wont = wontBring.getText();
			boolean willchecker = false;
			boolean wontchecker = true;
			boolean allspace1 = false;
			boolean allspace2 = false;
			
			//Trims the input
			will.trim();
			wont.trim();
			
			//Checks to make sure it isn't all whitespace
			for(int i = 0; i < will.length()-1; i++)
			{
				if (will.substring(1, 1+1).equals(" "))
					allspace1 = true;
			}
			
			for(int i = 0; i < wont.length()-1; i++)
			{
				if (wont.substring(1, 1+1).equals(" "))
					allspace2 = true;
			}
			
			//If the input isnt whitespace, checks to see if it passes the Green Glass Door Test
			if(!allspace1 && !allspace2)
			{
				for(int i = 0; i < will.length()-1; i++)
				{
					if (will.substring(i,i+1).equalsIgnoreCase(will.substring(i+1,i+2)))
							{
								willchecker = true;
							}
				}
				
				for(int i = 0; i < wont.length()-1; i++)
				{
					if (wont.substring(i,i+1).equalsIgnoreCase(wont.substring(i+1,i+2)))
							{
								wontchecker = false;
							}
				}
				
				
				if(!will.equals("") || !wont.equals("") )
				{
					//Prints results in textarea
					if (willchecker && wontchecker)
					{
						appendToPane(tPane, "You have brought a/an " + will + ", and left behind a/an " + wont + ".\n", custGreen);
						correct++;
						counter.setText("Fully Correct Answers: " + correct );
					}
					else if (willchecker && !wontchecker)
					{
						appendToPane(tPane, "You can bring both a/an " + will + " and a/an " + wont + ".\n", custGreen);
					}
					else if (!willchecker && wontchecker)
					{
						appendToPane(tPane,"You cannot bring either a/an " + will + " or a/an " + wont + ".\n",Color.RED);
					}
					else
					{	
						appendToPane(tPane,"You cannot bring a/an " + will, Color.RED);
						appendToPane(tPane, ", but you CAN bring a/an " + wont + ".\n", custGreen);
					}
				}
				else
				{
					appendToPane(tPane,"You must enter valid input.\n", Color.RED);
				}
			}
			else
			{
				appendToPane(tPane,"You must enter valid input.\n", Color.RED);
			}
			
			//Resets entry to blank
			willBring.setText("");
			wontBring.setText("");
		}	
	}	
	
	//adds formatted text to tPane.
	private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucidia Sans");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
	//Runs GUI
	private static void runGUI()
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		GUIClass clicker = new GUIClass();
	}
	
	public static void main (String [] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				runGUI();
			}
		});
	}
}	

/* Test Data:
Input: apple, orange - returns correct - PASS
Input: orange, apple - returns incorrect - PASS
Input: orange, orange - returns incorrect - PASS
Input: apple, apple - returns incorrect - PASS
Input: app, orange - returns correct - PASS
Input: pple, orange - returns correct - PASS
Input: pp, orange - returns correct - PASS
Input: p, o - returns incorrect - PASS
Input: "","" - returns error - PASS
Input: "         ", "         " - returns error - PASS
*/



