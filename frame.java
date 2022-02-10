import javax.swing.JFrame;

public class frame extends JFrame{
    
    App tetris;
    public static void main(String[] args) {
        new frame();
    }

    frame () {
        tetris = new App(this);
        setResizable(false);
        addKeyListener(tetris);
        add(tetris);
        setVisible(true);
        pack();
        setTitle("TETRIS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void restart() {
        this.remove(tetris);
        tetris = new App(this);
        addKeyListener(tetris);
        revalidate();
        add(tetris);
        revalidate();
    }
}
