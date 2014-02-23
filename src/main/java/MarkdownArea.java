import javax.swing.*;
import java.awt.*;

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

        int width = getWidth()-50; //50px margin
        if(width < minWidth) width = minWidth;
        //Parse the markdown and turn it into HTML
        //Then add HTML tags and set the width so it wraps the text
        label.setText(String.format("<html><div WIDTH=%d>%s</div><html>", width, parse(text)));
    }

    public void setMinWidth(int minWidth){
        this.minWidth = minWidth;
    }
    public int getMinWidth(){
        return minWidth;
    }

    private static String parse(String input) {
        return input.replaceAll("#(.*)\n","<h1>$1</h1>")      //Header 1
                .replaceAll("##(.*)\n","<h2>$1</h2>")         //header 2
                .replaceAll("###(.*)\n","<h3>$1</h3>")        //Header 3
                .replaceAll("###(.*)\n","<h4>$1</h4>")        //Header 4
                .replaceAll("\\*\\*(.*)\\*\\*","<b>$1</b>")   //Bold
                .replaceAll("\\*(.*)\\*","<i>$1</i>")        //Italic
                .replaceAll("((\\+.*\n)+)","<ul>$1</ul>")    // + list
                .replaceAll("\\+(.*)\n","<li>$1</li>\n")
                .replaceAll("((\\-.*\n)+)","<ul>$1</ul>")    // - list
                .replaceAll("\\-(.*)\n","<li>$1</li>\n")
                .replaceAll("((\\*.*\n)+)","<ul>$1</ul>")    // * list
                .replaceAll("\\*(.*)\n","<li>$1</li>\n")
                .replaceAll("\n","<br>");
    }

}
