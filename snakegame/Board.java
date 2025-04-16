package snakegame;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class Board extends JPanel implements  ActionListener{

    private final int maxdots = 900;
    private final int dotsize = 10;
    private final int randomlocation = 25;

    private final int x[] = new int[maxdots];
    private final int y[] = new int[maxdots];


    private int dots;

    private Image head;
    private Image dot;
    private Image apple;

    private int apple_x;
    private int apple_y;

    private Timer timer ;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private boolean intGAme = true;

    Board(){
        addKeyListener(new TAdapter());
        
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300, 300));
        setFocusable(true);
        initgame();
        loadimage();
        
        
    }

    public void loadimage() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png"));
         apple = i1.getImage();
        ImageIcon i2= new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png"));
         dot = i2.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
         head = i3.getImage();
    }

    public void initgame() {
        dots = 3;
        for (int i=0; i<dots; i++){
            y[i] = 50;
            x[i] = 50 - i*dotsize;
        }

        locateapple();

         timer = new Timer(140,this);
         timer.start();
        
    }

    public void locateapple() {
        double r =  Math.random() * randomlocation;
        int val = (int)r;
        apple_x = val * dotsize;
        System.out.println(apple_x);

        r =  Math.random() * randomlocation;
        int value = (int)r;
        apple_y = value * dotsize;
        System.out.println(apple_y);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent( g);

        draw(g);
    }

    public void draw(Graphics g) {

        g.drawImage(apple, apple_x, apple_y, this);

        for(int i=0; i<dots; i++){
            if(i == 0){
                g.drawImage(head, x[i], y[i], this);
            }else{
                g.drawImage(dot,x[i],y[i],this);
            }
        }
        Toolkit.getDefaultToolkit().sync();

    }
    public void move(){
        for(int i=dots; i>0; i--){
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if(leftDirection){
            x[0] -= dotsize;
        }
        if(rightDirection){
            x[0] += dotsize;
        }
        if(upDirection){
            y[0] -= dotsize;
        }
        if(downDirection){
            y[0] += dotsize;
        }
        //x[0] += dotsize;
        //y[0] += dotsize;
    }
    
    public void checkapple(){
        if( (x[0] == apple_x) && (y[0] == apple_y) ){
            dots++;
            locateapple();
        }
    }

    public void checkcollision() {
        for(int i = dots; i > 0; i--){
            if((i > 4) && (x[0] == x[i]) && (y[0] == y[i])){
                intGAme = false;
            }
        }

        if((y[0] >= 300)){
            intGAme = false;
        }
        if((x[0] >= 300)){
            intGAme = false;
        }
        if((y[0] < 0)){
            intGAme = false;
        }
        if((x[0] < 0)){
            intGAme = false;
        }
        if(!intGAme){
            timer.stop();
        }
        
    }

    public void actionPerformed(ActionEvent ae){
        
        checkapple();
        checkcollision();
        move();
        
        repaint();
        
    }
    


    public class TAdapter extends KeyAdapter{
        @Override
         public  void keyPressed(KeyEvent e){
            
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_D && (!leftDirection)){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
                System.out.println(key);
            }
            if(key == KeyEvent.VK_A && (!rightDirection)){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_W && (!downDirection)){
                rightDirection = false;
                upDirection = true;
                leftDirection = false;
            }
            if(key == KeyEvent.VK_S && (!upDirection)){
                rightDirection = false;
                leftDirection = false;
                downDirection = true;
            }
         }
    }
}
