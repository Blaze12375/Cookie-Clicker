
/**
 * @author user2505384
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.lang.Math;

public class Main extends JFrame {

  // Initilizing all the components
  JFrame frame = new JFrame();
  JLabel numCookieLabel = new JLabel("0 cookies");
  JLabel cookiePerSec = new JLabel();
  JPanel cookiePanel = new JPanel();
  JPanel numCookiePanel = new JPanel();
  JPanel buildPanel = new JPanel(new GridLayout(4, 0));
  JButton[] buildButtons = new JButton[4];
  JButton cookieButton = new JButton();
  ImageIcon bigCookie = new ImageIcon("cookie.png");
  ImageIcon cookiePressed = new ImageIcon("cookie1_pressed.jpeg");
  JTextArea infoArea = new JTextArea();
  Timer timer;

  double numCookie, cookieIncr = 0;
  
  double freq = 0;
  boolean isTimerOn = false;
  
  String cursorInfo = "Autoclicks every 10 seconds";
  String grandmaInfo = "A nice grandma to bake cookies";
  String farmInfo = "Grows cookie plants from cookie seeds (Don't question it).";
  String mineInfo = "Mines out cookie dough and chocolate chips (Seriously, don't question it).";
  int cursorCost, grandmaCost, farmCost, mineCost = 0;
  int cursorLevel, grandmaLevel, farmLevel, mineLevel = 0;
  final double cursorIncr = 0.1;
  final int grandmaIncr = 1;
  final int farmIncr = 8;
  final int mineIncr = 47;
  final int bCursorCost = 15;
  final int bGrandmaCost = 100;
  final int bFarmCost = 1000;
  final int bMineCost = 2500;


  public Main() {
    
    frame.setSize(700, 600);
    frame.setLayout(null);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setBackground(Color.BLACK);

    // Cookie Panel

    cookiePanel.setBounds(75, 200, 210, 220);
    cookiePanel.setBackground(Color.BLACK);
    frame.add(cookiePanel);

    // centers cookie sprite on panel
    cookiePanel.setLayout(new GridBagLayout());
    cookiePanel.add(cookieButton, new GridBagConstraints());

    // Number Cookie Panel

    numCookiePanel.setBounds(75, 120, 200, 75);
    numCookiePanel.setBackground(Color.BLACK);
    frame.add(numCookiePanel);

    // centers labels
    numCookiePanel.setLayout(new FlowLayout());

    // Number Cookie Label

    numCookieLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
    numCookieLabel.setForeground(Color.WHITE);
    numCookiePanel.add(numCookieLabel);

    // Cookie Per Second Label

    cookiePerSec.setFont(new Font("Dialog", Font.PLAIN, 20));
    cookiePerSec.setForeground(Color.WHITE);
    numCookiePanel.add(cookiePerSec);

    // Cookie Button

    cookieButton.setBackground(Color.BLACK);
    cookieButton.setFocusPainted(false);
    cookieButton.setBorder(null);
    cookieButton.setIcon(bigCookie);
    cookiePanel.add(cookieButton);

    // makes the button look like it's being pressed
    cookieButton.getModel().addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        // changes the sprite to a smaller version when pressed
        ButtonModel model = cookieButton.getModel();
        if (model.isArmed()) {
          cookieButton.setIcon(cookiePressed);
        } else {
          // changes the sprite back to the original when the button is released
          cookieButton.setIcon(bigCookie);
        }
      }
    });

    // Info Area

    infoArea.setBounds(450, 80, 200, 110);
    infoArea.setLineWrap(true);
    infoArea.setWrapStyleWord(true);
    infoArea.setFont(new Font("Dialog", Font.PLAIN, 12));
    infoArea.setEditable(false);
    infoArea.setBackground(Color.BLACK);
    infoArea.setForeground(Color.WHITE);
    frame.add(infoArea);

    // Build Panel

    buildPanel.setBounds(450, 200, 200, 200);
    buildPanel.setBackground(Color.BLACK);
    frame.add(buildPanel);

    // Build Buttons

    for (int i = 0; i < buildButtons.length; i++) {
      buildButtons[i] = new JButton("???");
      buildButtons[i].setFont(new Font("Dialog", Font.PLAIN, 20));
      buildPanel.add(buildButtons[i]);
      buildButtons[i].setEnabled(false);
    }

    // counts cookies
    cookieButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        numCookie++;
        numCookieLabel.setText((int) numCookie + " cookies");
        buildUnlock();
      }
    });    

    // Cursor Build
    
    cursorCost = (int)Math.ceil(bCursorCost*Math.pow(1.15, cursorLevel));
    mouseHover();

    buildButtons[0].addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {       
        if (numCookie >= cursorCost) {  
          cursorLevel++;
          numCookie -= cursorCost;          
          cookieIncr += cursorIncr;
          cursorCost = (int)Math.ceil(bCursorCost*Math.pow(1.15, cursorLevel));

          infoArea.setText("Cursor\nCost: "+cursorCost+"\n"+cursorInfo+"\n- Each cursor produces "+cursorIncr+" cookies per second"); 
          double roundCookieIncr = Math.round(cookieIncr*10.0)/10.0;
          numCookieLabel.setText((int) numCookie + " cookies");
          buildButtons[0].setText("Cursor (" + cursorLevel + ")");
          cookiePerSec.setText("Per second: " + roundCookieIncr);   
          timerStart();        
        } 
        

      }
    });

    



    // Grandma Build

    grandmaCost = (int)Math.ceil(bGrandmaCost*Math.pow(1.15, grandmaLevel));
    mouseHover();

    buildButtons[1].addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {       
        if (numCookie >= grandmaCost) {  
          grandmaLevel++;
          numCookie -= grandmaCost;          
          cookieIncr += grandmaIncr;
          grandmaCost = (int)Math.ceil(bGrandmaCost*Math.pow(1.15, grandmaLevel));
          mouseHover();

          infoArea.setText("Grandma\nCost: "+grandmaCost+"\n"+grandmaInfo+"\n- Each grandma produces "+grandmaIncr+" cookie per second"); 
          double roundCookieIncr = Math.round(cookieIncr*10.0)/10.0;
          numCookieLabel.setText((int) numCookie + " cookies");
          buildButtons[1].setText("Grandma (" + grandmaLevel + ")");
          cookiePerSec.setText("Per second: " + roundCookieIncr);
          timerStart();        
        }


      }
    });



    // Farm Build

    farmCost = (int)Math.ceil(bFarmCost*Math.pow(1.15, farmLevel));
    mouseHover();

    buildButtons[2].addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {       
        if (numCookie >= farmCost) {  
          farmLevel++;
          numCookie -= farmCost;          
          cookieIncr += farmIncr;
          farmCost = (int)Math.ceil(bFarmCost*Math.pow(1.15, farmLevel));

          infoArea.setText("Farm\nCost: "+farmCost+"\n"+farmInfo+"\n- Each farm produces "+farmIncr+" cookies per second"); 
          double roundCookieIncr = Math.round(cookieIncr*10.0)/10.0;
          numCookieLabel.setText((int) numCookie + " cookies");
          buildButtons[2].setText("Farm (" + farmLevel + ")");
          cookiePerSec.setText("Per second: " + roundCookieIncr);
          timerStart();        
        }
    
      }
    });


    // Mine Build

    mineCost = (int)Math.ceil(bMineCost*Math.pow(1.15, mineLevel));
    mouseHover();

    buildButtons[3].addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {       
        if (numCookie >= mineCost) {  
          mineLevel++;
          numCookie -= mineCost;          
          cookieIncr += mineIncr;
          mineCost = (int)Math.ceil(bMineCost*Math.pow(1.15, mineLevel));

          infoArea.setText("Mine\nCost: "+mineCost+"\n"+mineInfo+"\n- Each mine produces "+mineIncr+" cookies per second"); 
          double roundCookieIncr = Math.round(cookieIncr*10.0)/10.0;
          numCookieLabel.setText((int) numCookie + " cookies");
          buildButtons[3].setText("Mine (" + mineLevel + ")");
          cookiePerSec.setText("Per second: " + roundCookieIncr);
          timerStart();        
        }

    
      }
    });

  }



  public void buildUnlock() {
    if (numCookie == cursorCost) {
      buildButtons[0].setText("Cursor (" + cursorLevel + ")");
      buildButtons[0].setEnabled(true);
    } else if (numCookie == grandmaCost) {
      buildButtons[1].setText("Grandma (" + grandmaLevel + ")");
      buildButtons[1].setEnabled(true);
    } else if (numCookie == farmCost) {
      buildButtons[2].setText("Farm (" + farmLevel + ")");
      buildButtons[2].setEnabled(true);
    } else if (numCookie == mineCost) {
      buildButtons[3].setText("Mine (" + mineLevel + ")");
      buildButtons[3].setEnabled(true);
    }
  }
  

  public void timerStart() {

    if (isTimerOn == false) {
      isTimerOn = true;
    } else if (isTimerOn == true) {
      timer.stop();
    }

    freq = 1 / (cookieIncr / 1000);
    cookieTimer();
    timer.start();
  }

  public void cookieTimer() {

    ActionListener taskPerformer = new ActionListener() {
      public void actionPerformed(ActionEvent evt) {      
        numCookie++;
        numCookieLabel.setText((int) numCookie + " cookies");
        buildUnlock();
      }
    };
    timer = new Timer((int)freq, taskPerformer);
    

  }

  public void mouseHover() {

    // shows info about the cursor button when mouse hovers over it
    buildButtons[0].addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseEntered(java.awt.event.MouseEvent evt) {
        
        if (buildButtons[0].isEnabled() && cursorLevel == 0) {

          infoArea.setText("Cursor\nCost: "+cursorCost+"\n"+cursorInfo);

        } else if (buildButtons[0].isEnabled() || !buildButtons[0].isEnabled() && cursorLevel > 0) {
          infoArea.setText("Cursor\nCost: "+cursorCost+"\n"+cursorInfo+"\n- Each cursor produces "+cursorIncr+" cookies per second");

        } else {          
          infoArea.setText("???\n[owned: 0]");
        }         
      }

      public void mouseExited(java.awt.event.MouseEvent evt) {
        infoArea.setText("");
      }
    });

    buildButtons[1].addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseEntered(java.awt.event.MouseEvent evt) {
        
        if (buildButtons[1].isEnabled() && grandmaLevel == 0) {
          
          infoArea.setText("Grandma\nCost: "+grandmaCost+"\n"+grandmaInfo);

        } else if (buildButtons[1].isEnabled() || !buildButtons[1].isEnabled() && grandmaLevel > 0) {
          infoArea.setText("Grandma\nCost: "+grandmaCost+"\n"+grandmaInfo +"\n- Each grandma produces "+grandmaIncr+" cookie per second");

        } else {          
          infoArea.setText("???\n[owned: 0]");
        }         
      }

      public void mouseExited(java.awt.event.MouseEvent evt) {
        infoArea.setText("");
      }
    });

    buildButtons[2].addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseEntered(java.awt.event.MouseEvent evt) {
        
        if (buildButtons[2].isEnabled() && farmLevel == 0) {
          
          infoArea.setText("Farm\nCost: "+farmCost+"\n"+farmInfo);

        } else if (buildButtons[2].isEnabled() || !buildButtons[2].isEnabled() && farmLevel > 0) {
          infoArea.setText("Farm\nCost: "+farmCost+"\n"+farmInfo+"\n- Each farm produces "+farmIncr+" cookies per second");

        } else {          
          infoArea.setText("???\n[owned: 0]");
        }         
      }

      public void mouseExited(java.awt.event.MouseEvent evt) {
        infoArea.setText("");
      }
    });

    buildButtons[3].addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseEntered(java.awt.event.MouseEvent evt) {
        
        if (buildButtons[3].isEnabled() && mineLevel == 0) {
          
          infoArea.setText("Mine\nCost: "+mineCost+"\n"+mineInfo);

        } else if (buildButtons[3].isEnabled() || !buildButtons[3].isEnabled() && mineLevel > 0) {
          infoArea.setText("Mine\nCost: "+mineCost+"\n"+mineInfo+"\n- Each mine produces "+mineIncr+" cookies per second");

        } else {          
          infoArea.setText("???\n[owned: 0]");
        }         
      }

      public void mouseExited(java.awt.event.MouseEvent evt) {
        infoArea.setText("");
      }
    });

  }

  

  /**
   * Creates the GUI.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    Main main = new Main();

  }
}
