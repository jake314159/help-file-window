import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class PageList extends JPanel{

    private String[] filesString = new String[]{"Intro","Help me 1","Help me 2","Licence"};
    private File[] filesObj; //It is a list of File objects
    private File markdownDirectory;

    public PageList(File markdownDirectory){
        this.markdownDirectory = markdownDirectory;
        File[] fileList = markdownDirectory.listFiles();
        ArrayList<String> files = new ArrayList<String>();
        ArrayList<File> filesFileList = new ArrayList<File>();
        Arrays.sort(fileList);
        for(int i = 0; i<fileList.length; i++){

            String newName = processName(fileList[i].getName());
            if(newName != null){
                files.add(newName);
                filesFileList.add(fileList[i]);
            }
        }
        String[] fileStringTemp = new String[files.size()];
        for(int i=0; i<fileStringTemp.length; i++){
            fileStringTemp[i] = files.get(i);
        }
        this.filesString = fileStringTemp;

        File[] temp = new File[filesFileList.size()];
        for(int i=0; i<temp.length; i++){
            temp[i] = filesFileList.get(i);
        }
        this.filesObj = temp;

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

        //Here is the list of options
        final JList list = new JList(filesString);
        list.setSelectedIndex(0);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //On click we should get the picked File and run the abstract method
        //optionPicked(File helpFileSelected);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent ev) {
                if(ev.getValueIsAdjusting()){
                    int i= list.getSelectedIndex();
                    optionPicked(filesObj[i]);
                }
            }
        });
        this.add(new JScrollPane(list));

        optionPicked(filesObj[0]); //Set default
        list.setSelectedIndex(0);
    }

    public File[] getFileOptions() {
        return Arrays.copyOf(filesObj, filesObj.length);
    }

    public File getDefaultFile(){
        return filesObj[0];
    }

    /**
     * This method is called when a new file has been picked on the PageList
     * @param helpFileSelected The file which the user has selected
     */
    public abstract void optionPicked(File helpFileSelected);
}
