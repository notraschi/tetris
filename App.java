import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class App extends JPanel implements ActionListener, Runnable, KeyListener{

    // public static void main(String[] args) throws Exception {
    //     new App();
    // }

    JLabel scoreLabel = new JLabel("score: 0"); 
    JLabel lostLabel = new JLabel("<html>"+"YOU LOST <br/><br/> press space to play again."+"</html>"); 
    JLabel versionLabel = new JLabel("version 1.0", 0); 
    frame parent;
    // JFrame f = new JFrame();
    Random rand = new Random();
    private boolean running=true;
    // private boolean justRestarted = true;
    private int score = 0;
    final int BS = 20;
    final int FRAMESPEED = 2;
    int[][] piece = new int[5][2];
    int[][] grid = 
    {{0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,},
    {0,0,0,0,0,0,0,0,0,0,}};

    public App(frame p){

        Thread thread = new Thread(this, "tetris"); //thread shi
        thread.start(); //same

        scoreLabel.setBounds(207, 5, 100, 20);
        versionLabel.setBounds(207, 460, 75, 20);
        lostLabel.setBounds(207, 100, 75, 65);
        lostLabel.setVisible(false);
        add(scoreLabel);
        add(lostLabel);
        add(versionLabel);
        setLayout(null);
        setPreferredSize(new Dimension(300/*actual game board size: 200*/, 480));

        parent = p;
        // f.setResizable(false);
        // f.addKeyListener(this);
        // f.add(this);
        // f.setVisible(true);
        // f.pack();
        // f.setTitle("TETRIS");
        // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newPiece();
    }

    private void update(){

        if (!moveDown()) newPiece();
        
        repaint();
    }

    private void checkRemoveRow() {

        for (int i=0; i<grid.length; i++){
            for (int j=0; j<10; j++) {
                if (grid[i][j]==0) break;
                else if (j==9 && grid[i][j]==1) {
                    score += 10;
                    scoreLabel.setText("score: "+score);
                    for (int k=0; k<10; k++){
                        grid[i][k]=0;
                    }
                    for (int r=i; r>7; r--){
                        for (int rr=0; rr<10; rr++){
                            grid[r][rr] = grid[r-1][rr];
                        }
                    }
                }
            }
        }  
    }

    private void newPiece() {

        if (!running) return;
        // else if (justRestarted) justRestarted = false;
        int v =  rand.nextInt(7)+1; 
        if (v==1) {                     // I
            piece[4][0] = 1;
            piece[4][1] = 0;
            for (int i=0 ; i<4; i++){
                grid[i][4] = 1;
                piece[i][0] = 0+i;    //piece [x][0] prende la y del pezzo
                piece[i][1] = 4;    //piece [x][1] prende la x del pezzo
            }
        } else if (v==2){               // cube
            piece[4][0] = 2;
            piece[4][1] = 0;
            for (int i =0; i<2;i++){
                grid[i][4] = 1;
                piece[i][0] = 0+i;    
                piece[i][1] = 4;
                grid[i][5] = 1;
                piece[i+2][0] = 0+i;
                piece[i+2][1] = 5;
            }
        } else if (v==3){               // s
            piece[4][0] = 3;
            for (int i =0; i<2;i++){
                grid[i][4] = 1;
                piece[i][0] = 0+i;    
                piece[i][1] = 4;
                grid[i+1][5] = 1;
                piece[i+2][0] = 1+i;
                piece[i+2][1] = 5;
            }
        } else if (v==4){               // z
            piece[4][0] = 4;
            for (int i =0; i<2;i++){
                grid[i+1][4] = 1;
                piece[i][0] = 1+i;    
                piece[i][1] = 4;
                grid[i+0][5] = 1;
                piece[i+2][0] = 0+i;
                piece[i+2][1] = 5;
            }
        } else if (v==5){               // T
            piece[4][0] = 5;
            for (int i =0; i<3;i++){
                grid[i][4] = 1;
                piece[i][0] = i;    
                piece[i][1] = 4;
            }
            grid[1][5] = 1;
            piece[3][0] = 1;
            piece[3][1] = 5;
        } else if (v==6){               // L
            piece[4][0] = 6;
            for (int i =0; i<3;i++){
                grid[i][4] = 1;
                piece[i][0] = i;    
                piece[i][1] = 4;
            }
            grid[2][5] = 1;
            piece[3][0] = 2;
            piece[3][1] = 5;
        } else if (v==7){               // J
            piece[4][0] = 7;
            for (int i =0; i<3;i++){
                grid[i][5] = 1;
                piece[i][0] = i;    
                piece[i][1] = 5;
            }
            grid[2][4] = 1;
            piece[3][0] = 2;
            piece[3][1] = 4;
        }
        checkRemoveRow();
        /*
        piece[4][0]
        0 = cubetto da 1 (dont use)
        1 = I
        2 = cubo
        3 = S
        4 = Z
        5 = L
        6 = J
        7 = T        

        piece[4][1]
        1 = in orizzontale 
        0 = in verticale
        */ 
    }

    private boolean moveDown() {

        if(!running) return false;
        if (checkMove(0, 0, +1)) {
            for (int k=0; k<4; k++) {
                grid[piece[k][0]][piece[k][1]] = 0;
            }
            for (int i =3; i>=0; i--){
                grid[piece[i][0] +1][piece[i][1]] = 1;
                piece[i][0]++;
            }
            repaint();
            return true;
        }
        else if (piece [0][0] < 4/*&& grid[piece[0][0] +1][piece[0][1]] == 1*/) {
            lostLabel.setVisible(true);
            running=false;
            revalidate();
        }
        repaint();
        return false;
    }

    private void moveLeft() {

        if (!running) return;
        if (checkMove(-1, 1, 0)) {
            for (int k=0; k<4; k++) {
                grid[piece[k][0]][piece[k][1]] = 0;
            }
            for (int i =0; i<4; i++){
                grid[piece[i][0]][piece[i][1] -1] = 1;
                piece[i][1]--;
            }
        }
        repaint();
    }

    private void moveRight() {

        if (!running) return;
        if (checkMove(1, 1, 0)) {
            for (int k=0; k<4; k++) {
                grid[piece[k][0]][piece[k][1]] = 0;
            }
            for (int i =3; i>=0; i--){
                grid[piece[i][0]][piece[i][1] +1] = 1;
                piece[i][1]++;
            }
        }
        repaint();
    }

    private boolean checkMove(int sdws, int xory, int dwn) {

        int notxory = (xory==0 ? 1 : 0);
        for (int i=0; i<4; i++) {
            if (sdws==0 && xory==0 && dwn==1 && piece[i][0]==23) return false;
            else if (sdws==-1 && xory==1 && dwn==0 && piece[i][1]==0) return false;
            else if (sdws==1 && xory==1 && dwn==0 && piece[i][1]==9) return false;
            else if (grid[piece[i][0] +dwn][piece[i][1] +sdws] == 1 && !(
                ((piece[i][xory] +sdws +dwn)==piece[2][xory] && piece[i][notxory]==piece[2][notxory]) || ((piece[i][xory] +sdws +dwn)==piece[0][xory] && piece[i][notxory]==piece[0][notxory]) ||
                ((piece[i][xory] +sdws +dwn)==piece[1][xory] && piece[i][notxory]==piece[1][notxory]) || ((piece[i][xory] +sdws +dwn)==piece[3][xory] && piece[i][notxory]==piece[3][notxory])) 
            ) return false;
        }
        return true;
    }

    private void rotate() {

        int pivot; 
        if (piece[4][0]==4) pivot=3;
        else pivot =1;
        int[][] matrixVtoO = {{0,1}, {-1,0}}; // rotates cw
        int[][] matrixOtoV = {{0,-1}, {1,0}}; // rotates ccw ig

        if (checkRotation(matrixVtoO, matrixOtoV, pivot)) {
            for (int k=0; k<4; k++) {
                if (k==pivot) continue;
                grid[piece[k][0]][piece[k][1]] = 0;
            }
            for (int i=0; i<4; i++){
                if (i==pivot) continue;

                int relativeX = piece[i][1] - piece[pivot][1];
                int relativeY = piece[i][0] - piece[pivot][0];
                
                if (piece[4][1] == 1) {
                    piece[i][1] = piece[pivot][1] + (matrixVtoO[0][0] * relativeX) + (matrixVtoO[1][0] * relativeY); //new X
                    piece[i][0] = piece[pivot][0] + (matrixVtoO[0][1] * relativeX) + (matrixVtoO[1][1] * relativeY); //new Y
                } else if (piece[4][1] == 0) {
                    piece[i][1] = piece[pivot][1] + (matrixOtoV[0][0] * relativeX) + (matrixOtoV[1][0] * relativeY); //new X
                    piece[i][0] = piece[pivot][0] + (matrixOtoV[0][1] * relativeX) + (matrixOtoV[1][1] * relativeY); //new Y
                }
                
                grid[piece[0][0]][piece[0][1]] = 1;  // somehow fixes a visual bug // yep, it still does guess it'll stay here lol
                grid[piece[i][0]][piece[i][1]] = 1;
            }
        }
        repaint();
    }

    private boolean checkRotation (int[][] matrixVtoO, int[][] matrixOtoV, int pivot) {

        int newPieceX=0, newPieceY=0;

        for (int t=0; t<4; t++){

            int relativeX2 = piece[t][1] - piece[pivot][1];
            int relativeY2 = piece[t][0] - piece[pivot][0];

            if (piece[4][1] == 1) {
                newPieceX /*piece[i][1]*/ = piece[pivot][1] + (matrixVtoO[0][0] * relativeX2) + (matrixVtoO[1][0] * relativeY2); //new X
                newPieceY /*piece[i][0]*/ = piece[pivot][0] + (matrixVtoO[0][1] * relativeX2) + (matrixVtoO[1][1] * relativeY2); //new Y
            } else if (piece[4][1] == 0) {
                newPieceX /*piece[i][1]*/ = piece[pivot][1] + (matrixOtoV[0][0] * relativeX2) + (matrixOtoV[1][0] * relativeY2); //new X
                newPieceY /*piece[i][0]*/ = piece[pivot][0] + (matrixOtoV[0][1] * relativeX2) + (matrixOtoV[1][1] * relativeY2); //new Y
            }
            
            if (newPieceX<0 || newPieceX>9 || newPieceY<0 || newPieceY>23) return false;
            else if (grid[newPieceY][newPieceX]==1 && !((newPieceY==piece[2][0] && newPieceX==piece[2][1]) || (newPieceY==piece[0][0] && newPieceX==piece[0][1]) || (newPieceY==piece[1][0] && newPieceX==piece[1][1]) 
                || (newPieceY==piece[3][0] && newPieceX==piece[3][1]))) {
                return false;
            } 
        }
        return true;
    }

    private void rotateI() {
        
        if (!checkRotationI()) return;
        if (piece[0][1] == piece[1][1] && piece[0][1]>=1 && piece[0][1]<8) {   // pezzo e' in verticale

            grid[piece[0][0]][piece[0][1]] = 0;     // v -> o
            grid[++piece[0][0]][--piece[0][1]] = 1;
            grid[piece[2][0]][piece[2][1]] = 0;
            grid[--piece[2][0]][++piece[2][1]] = 1;
            grid[piece[3][0]][piece[3][1]] = 0;
            piece[3][0]-=2;
            piece[3][1]+=2;
            grid[piece[3][0]][piece[3][1]] = 1;
            repaint();
            piece[4][1] = 1;

        } else if (piece[0][1] != piece[1][1] && piece[0][0]<22) {    // pezzo e' in orizzontale

            grid[piece[0][0]][piece[0][1]] = 0;     // o -> v
            grid[--piece[0][0]][++piece[0][1]] = 1;
            grid[piece[2][0]][piece[2][1]] = 0;
            grid[++piece[2][0]][--piece[2][1]] = 1;
            grid[piece[3][0]][piece[3][1]] = 0;
            piece[3][1]-=2;
            piece[3][0]+=2;
            grid[piece[3][0]][piece[3][1]] = 1;
            repaint();
            piece[4][1] = 0;
        }
    }

    private boolean checkRotationI() {

        if (piece[0][1] == piece[1][1] && piece[0][1]>=1 && piece[0][1]<8) {   // pezzo e' in verticale
            if (grid[piece[0][0]+1][piece[0][1]-1] == 1 ||
                grid[piece[2][0]-1][piece[2][1]+1] == 1 || 
                grid[piece[3][0]-2][piece[3][1]+2] == 1) return false;

        } else if (piece[0][1] != piece[1][1] && piece[0][0]<22) {    // pezzo e' in orizzontale
            if (grid[piece[0][0]-1][piece[0][1]+1] == 1 ||
                grid[piece[2][0]+1][piece[2][1]-1] == 1 || 
                grid[piece[3][0]+2][piece[3][1]-2] == 1) return false;
        }
        return true;
    }

    // private void restart() {


    //     // System.out.println(f.getLocation().getX()+" "+f.getLocation().getY());
    //     // new App();
    // }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;
        gg.setColor(Color.lightGray);
        for(int i =0; i<24;i++){
            gg.drawLine(0, i*BS, 200, i*BS);
        }
        for(int i=0;i<11;i++){
            gg.drawLine(i*BS, 0, i*BS, 480);
        }
        gg.setColor(Color.BLACK);
        for (int i=0; i<24; i++){
            for(int j=0;j<10;j++){
                if (grid[i][j]==1) gg.fillRect(j*BS, i*BS, BS, BS);
            }
        }
    }

    @Override
    public void run(){

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0/FRAMESPEED;
        double delta = 0;
        // int frame =0;
       
        while(running){
            long currentTime = System.nanoTime();
            delta += (currentTime-lastTime)/ns;
            lastTime = currentTime;
            while(delta>=1){
                delta--;
                update();
                // frame++;
            }

            if(System.currentTimeMillis()-timer>1000){
                timer+=1000;
                // f.setTitle("current fps: "+frame);
                // frame=0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if (e.getKeyCode()==KeyEvent.VK_LEFT) moveLeft();
        else if (e.getKeyCode()==KeyEvent.VK_RIGHT) moveRight();
        else if (e.getKeyCode()==KeyEvent.VK_DOWN) moveDown();
        else if (e.getKeyCode()==KeyEvent.VK_UP && piece[4][0] == 1) rotateI();
        else if (e.getKeyCode()==KeyEvent.VK_UP && piece[4][0] == 2) return;
        else if (e.getKeyCode()==KeyEvent.VK_UP && piece[4][0] > 2) rotate();
        else if (e.getKeyCode()==KeyEvent.VK_SPACE && !running) parent.restart(); // restart();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
