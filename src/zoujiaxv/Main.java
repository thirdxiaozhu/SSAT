package zoujiaxv;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.swing.*;
import java.security.Security;

public class Main {

    private static void createGUI(){
        JFrame frame = new JFrame("国密算法集成工具"); //窗口标题
        frame.setContentPane(new P5form().MyPanel); //构建窗口
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500,260); //窗口大小
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null); //在屏幕中间显示

        frame.setResizable(false); //禁止调整大小

        //设置通知栏图标
        ImageIcon icon = new ImageIcon("E:\\Java\\untitled2\\src\\zoujiaxv\\img\\groose.png");
        frame.setIconImage(icon.getImage());
    }

    /**
     * 主函数
     * @param args
     */
    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info :
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch(Exception e) {
            System.out.println(e);
        }

        /**
         * 创建父窗口
         */
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI();
            }
        });
    }
}
