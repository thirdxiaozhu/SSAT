package zoujiaxv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MACDialog {
    public JPanel MyPanel;
    public JTextField ZUC128;
    public JTextField ZUC256;
    public JTextField ZUC32;
    public JTextField ZUC64;
    private JButton CloseBtn;
    private JLabel Title;
    private final JFrame frame;

    public MACDialog(JFrame frame){
        this.frame = frame;
        initPanel();
    }

    private void initPanel(){
        Title.setFont(new Font("Dialog" ,  1 , 20));

        ZUC128.setEditable(false); //四个条目全部设成不可更改
        ZUC256.setEditable(false);
        ZUC32.setEditable(false);
        ZUC64.setEditable(false);

        CloseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

}
