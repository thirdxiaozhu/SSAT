package zoujiaxv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignDialog {
    public JPanel MyPanel;
    private JTextField CN;
    private JButton SaveBtn;
    private JButton CloseBtn;
    private JLabel Title;
    private JTextField OU;
    private JTextField O;
    private JTextField L;
    private JTextField ST;
    private JTextField C;
    private JButton ClearBtn;
    private JFrame mainFrame;
    private final P5form mainWindow;
    int model;

    /**
     * 构造方法
     * @param model 0：编辑密钥信息窗口  1：检查密钥信息窗口
     * @param frame 子窗口（自身）
     * @param mainWindow 传入P5form类，以便父子窗口进行传值
     */
    public SignDialog(int model , JFrame frame , P5form mainWindow){
        Title.setFont(new Font("Dialog" ,  1 , 20));
        this.model = model;
        this.mainWindow = mainWindow; //窗口之间传值
        setText();

        /* 浏览模式不可编辑 */
        if(model == 1){
            OU.setEditable(false);
            CN.setEditable(false);
            O.setEditable(false);
            L.setEditable(false);
            ST.setEditable(false);
            C.setEditable(false);
            SaveBtn.setVisible(false);
            ClearBtn.setVisible(false);
        }

        /* 关闭窗口按钮 */
        CloseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        /* 保存按钮 */
        SaveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* 父子窗口传值 */
                mainWindow.CN = CN.getText();
                mainWindow.OU = OU.getText();
                mainWindow.O = O.getText();
                mainWindow.L = L.getText();
                mainWindow.ST = ST.getText();
                mainWindow.C = C.getText();
                frame.dispose();
            }
        });

        ClearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearText();
            }
        });
    }

    /**
     * 检查模式写入信息
     */
    private void setText(){
        CN.setText(mainWindow.CN);
        OU.setText(mainWindow.OU);
        O.setText(mainWindow.O);
        L.setText(mainWindow.L);
        ST.setText(mainWindow.ST);
        C.setText(mainWindow.C);
    }

    /**
     * 清空按钮事件
     */
    private void clearText(){
        CN.setText("");
        OU.setText("");
        O.setText("");
        L.setText("");
        ST.setText("");
        C.setText("");
    }
}
