import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public abstract class PageList extends JPanel{

    private Object[] filesString = new String[]{"Intro","Help me 1","Help me 2","Licence"};
    private File[] filesObj; //It is a list of File objects
    private File markdownDirectory;

    public PageList(File markdownDirectory){
        this.markdownDirectory = markdownDirectory;
        File[] fileList = markdownDirectory.listFiles();
        ArrayList<String> files = new ArrayList<String>();//tring[fileList.length];
        ArrayList<File> filesFileList = new ArrayList<File>();
        Arrays.sort(fileList);
        for(int i = 0; i<fileList.length; i++){
            System.out.println("Handling "+((File)fileList[i]).getName());
            String newName = processName(fileList[i].getName());
            if(newName != null){
                files.add(newName);
                filesFileList.add(fileList[i]);
            }
        }
        this.filesString = files.toArray();

        File[] temp = new File[filesFileList.size()];
        for(int i=0; i<temp.length; i++){
            temp[i] = filesFileList.get(i);
        }

        this.filesObj = temp;//filesFileList.toArray();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

    private String processName(String name) {
        if(name.charAt(name.length()-1) == '~') return null;//We don't care about that

        return name.substring(3,name.lastIndexOf('.'));
    }

    private void init(){
        this.setLayout(new BorderLayout());
        System.out.println("Exists?" + markdownDirectory.exists());
        System.out.println("Is directory?" + markdownDirectory.isDirectory());
        final JList list = new JList(filesString); //data has type Object[]
        list.setLayoutOrientation(JList.VERTICAL);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent ev) {
                if(ev.getValueIsAdjusting()){
                    //System.out.println("Selected "+list.getSelectedIndex());
                    int i= list.getSelectedIndex();
                    optionPicked((File) filesObj[i]);
                }
            }
        });
        //list.setPreferredSize(new Dimension(150, 0));
        this.add(new JScrollPane(list));

    }

    public abstract void optionPicked(File helpFileSelected);

    public File[] getFileOptions() {
        return filesObj;
    }
}
