package zoujiaxv;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.macs.Zuc128Mac;
import org.bouncycastle.crypto.macs.Zuc256Mac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Hex;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.net.StandardSocketOptions;
import java.security.MessageDigest;

public class MAC {
    private MACDialog macDialog;
    public String password;
    CipherParameters ivAndKey_128 ;
    CipherParameters ivAndKey_256 ;

    /**
     * 构造方法
     */
    public MAC(String password) throws Exception {
        this.password = password;
        createKidDialog();
        setKeyAndIV();
    }

    /**
     * 计算字符窜MAC值
     * @param str 源字符串
     */
    public void StringMAC(String str){

        System.out.println(str);
        /* ZUC128 */
        Mac zuc = new Zuc128Mac();
        zuc.init(ivAndKey_128);
        zuc.update(str.getBytes(), 0, str.getBytes().length);
        byte[] zuc128bytes = new byte[zuc.getMacSize()];
        zuc.doFinal(zuc128bytes, 0);
        macDialog.ZUC128.setText(Hex.toHexString(zuc128bytes));


        /* ZUC256 */
        int[] i = new int[3];
        String[] result = new String[3];
        JTextField[] textFields = new JTextField[3];
        i[0] = 128;
        i[1] = 32;
        i[2] = 64;
        textFields[0] = macDialog.ZUC256;
        textFields[1] = macDialog.ZUC32;
        textFields[2] = macDialog.ZUC64;

        for(int c = 0 ; c < 3 ; c++){
            StringZuc256Digest(i[c] , textFields[c] , ivAndKey_256 , str);
        }
    }

    /**
     * 计算文件MAC码
     * @param f 源文件
     * @throws Exception
     */
    public void FileMAC(File f) throws Exception{
        try (FileInputStream fis = new FileInputStream(f)) {

            Mac zuc128 = new Zuc128Mac();
            Mac zuc256 = new Zuc256Mac(128);
            Mac zuc32 = new Zuc256Mac(32);
            Mac zuc64 = new Zuc256Mac(64);

            zuc128.init(ivAndKey_128);
            zuc256.init(ivAndKey_256);
            zuc32.init(ivAndKey_256);
            zuc64.init(ivAndKey_256);

            byte[] buffer = new byte[1024];
            int n = -1;
            while ((n = fis.read(buffer)) != -1){
                zuc128.update(buffer,0,n);
                zuc256.update(buffer,0,n);
                zuc32.update(buffer,0,n);
                zuc64.update(buffer,0,n);
            }
            System.out.println(new String(buffer));

            byte[] zuc128bytes = new byte[zuc128.getMacSize()];
            byte[] zuc256bytes = new byte[zuc256.getMacSize()];
            byte[] zuc32bytes = new byte[zuc32.getMacSize()];
            byte[] zuc64bytes = new byte[zuc64.getMacSize()];

            zuc128.doFinal(zuc128bytes, 0);
            zuc256.doFinal(zuc256bytes, 0);
            zuc32.doFinal(zuc32bytes, 0);
            zuc64.doFinal(zuc64bytes, 0);

            macDialog.ZUC128.setText(Hex.toHexString(zuc128bytes));
            macDialog.ZUC256.setText(Hex.toHexString(zuc256bytes));
            macDialog.ZUC32.setText(Hex.toHexString(zuc32bytes));
            macDialog.ZUC64.setText(Hex.toHexString(zuc64bytes));
        }
    }


    /**
     * 对字符串计算其MAC256的值
     * @param i ZUC256:128  ZUC256-32：32  ZUC256-64:64
     * @param textField  传入子窗口组件
     * @param ivAndKey  算出的初始向量和Key
     * @param str 源字符串
     */
    private void StringZuc256Digest(int i , JTextField textField, CipherParameters ivAndKey , String str){
        Mac zuc256 = new Zuc256Mac(i);
        zuc256.init(ivAndKey);
        zuc256.update(str.getBytes(), 0, str.getBytes().length);
        byte[] zuc256bytes = new byte[zuc256.getMacSize()];
        zuc256.doFinal(zuc256bytes, 0);

        textField.setText(Hex.toHexString(zuc256bytes));
    }

    /**
     * 构建会话窗口
     */
    private void createKidDialog(){
        JFrame frame = new JFrame("MAC"); //窗口标题
        macDialog = new MACDialog(frame);
        //构建窗口, this是父窗口，传入子窗口以便传值 , 同时要传入子窗口自身,以保证实现窗口关闭功能
        frame.setContentPane(macDialog.MyPanel);
        frame.pack();
        frame.setSize(500,230); //窗口大小
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setLocation(550 , 350); //在屏幕中间显示

        frame.setResizable(false); //禁止调整大小

        //设置通知栏图标
        ImageIcon icon = new ImageIcon("E:\\Java\\untitled2\\src\\zoujiaxv\\img\\groose.png");
        frame.setIconImage(icon.getImage());
    }

    /**
     * 计算初始向量和iv
     * 具体计算方法：对传入的口令进行MD5摘要，得到32位十六进制字符串，对于ZUC128，取其8-23位作为IV，对于ZUC256，取其0-24作为IV
     * @throws Exception
     */
    private void setKeyAndIV() throws Exception{
        byte[] passwd = password.getBytes();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(passwd);
        byte[] keyData = md.digest();
        String temp = Hex.toHexString(keyData);
        System.out.println(temp);

        KeyParameter keyParameter_128 = new KeyParameter(temp.substring(8,24).getBytes());
        KeyParameter keyParameter_256 = new KeyParameter(temp.getBytes());

        byte[] ivValue_128 = temp.substring(8,24).getBytes();
        byte[] ivValue_200 = temp.substring(0,25).getBytes();
        System.out.println(ivValue_200.length);
        System.out.println(temp.substring(8,24));
        System.out.println(temp.substring(0,25));

        ivAndKey_128 = new ParametersWithIV(keyParameter_128, ivValue_128);
        ivAndKey_256 = new ParametersWithIV(keyParameter_256, ivValue_200);
    }
}
