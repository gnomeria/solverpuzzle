package jyt.game.muxxu.kingdom.puzzle.help.ui;

import java.awt.Robot;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Canvas;
import java.awt.Graphics;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class CaptureScreen extends Canvas
{
Rectangle screenRectangle=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

Robot myRobot;

BufferedImage screenImage;

public CaptureScreen()
{
try
{
 myRobot=new Robot();
}
catch(Exception exception)
{
 exception.printStackTrace();
}

screenImage=myRobot.createScreenCapture(new Rectangle(0, 0, 1800, 1000));

JFrame myFrame=new JFrame("Capture Screen");

myFrame.add(this);

myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
myFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
myFrame.setVisible(true);
}

public void paint(Graphics g)
{
g.drawImage(screenImage,-50,0,this);
}

public static void main(String[]args)
{
CaptureScreen cs=new CaptureScreen();
}
}