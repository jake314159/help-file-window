import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.*;

public class HelpWindow extends JFrame{

    private MarkdownArea markdownArea = new MarkdownArea();
    private PageList pageList;
    private Map<File, String> textMap;

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //An example of running the program
        new HelpWindow("Help window -- My Program",new File("exampleHelpFiles")).popup();
    }

    public HelpWindow(String title, File helpDir){
        super(title);
        pageList = new PageList(helpDir){
            @Override
            public void optionPicked(File helpFile) {
                //When we pick an option we should find the help string and
                //Put it into the markdownArea
                markdownArea.setText(textMap.get(helpFile));
            }
        };

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });

        //Read all the help information into a map so we don't have to read from file every time the user changes
        //the view
        File[] files = pageList.getFileOptions();
        textMap = readFiles(files);
    }
    private void init(){
        this.setLayout(new BorderLayout());

        this.add(new JScrollPane(markdownArea), BorderLayout.CENTER);
        this.add(pageList, BorderLayout.WEST);
        this.setSize(750,550);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void popup(){
        this.setVisible(true);
    }


    //These 2 methods handle reading the help files and storing them in a map
    //To save having to read the file every time the user hits a button
    private Map<File, String> readFiles(File[] files){
        TreeMap<File, String> map = new TreeMap<File, String>();
        for(File f : files){
            map.put(f, readFile(f));
        }
        return map;
    }
    private String readFile(File f){
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));

            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();

        } catch(Exception e){
            e.printStackTrace();
        }

        return "";
    }

}
