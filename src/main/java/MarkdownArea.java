import org.markdown4j.Markdown4jProcessor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MarkdownArea extends JPanel{

    private String text = "";
    private JLabel label = new JLabel(text);
    private int minWidth = 400;

    public MarkdownArea(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

    private void init(){
        this.setLayout(new BorderLayout());
        JPanel p = new JPanel();
        this.add(label);
        this.setBackground(Color.WHITE);
    }

    public void setText(String text){
        this.text = text;
        try {
            int width = getWidth()-50; //50px margin
            if(width < minWidth) width = minWidth;
            //Parse the markdown and turn it into HTML
            //Then add HTML tags and set the width so it wraps the text
            label.setText(String.format("<html><div WIDTH=%d>%s</div><html>", width,new Markdown4jProcessor().process(text)));

        } catch (IOException e) {
            e.printStackTrace();
            label.setText("");
        }
    }

    public void setMinWidth(int minWidth){
        this.minWidth = minWidth;
    }
    public int getMinWidth(){
        return minWidth;
    }

}
