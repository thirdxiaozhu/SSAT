package zoujiaxv;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.security.Key;
import java.security.MessageDigest;

public class Decrypt {

    File file;
    String password;
    private final JFileChooser fileChooser = new JFileChooser();

    /**
     * 构造方法
     * @param file 待解密文件
     * @param password 口令
     * @throws Exception
     */
    public Decrypt(File file , String password) throws Exception {
        this.file = file;
        this.password = password;
        fileChooser.setFileSelectionMode(1); //相对路径
    }

    /**
     * 生成密码学安全密钥
     * @return 返回密钥
     * @throws Exception
     */
    private Key OperateKey() throws Exception{
        byte[] passwd = password.getBytes();
        MessageDigest md = MessageDigest.getInstance("MD5");
        //byte[] keyData = password.getBytes();
        byte[] keyData = md.digest(passwd);
        System.out.println(new String(keyData));
        SecretKeySpec key = new SecretKeySpec(keyData, "SM4");
        return key;
    }

    /**
     * ECB解密模式
     * @throws Exception
     */
    public void goECBDecry() throws Exception {
        FileInputStream is = new FileInputStream(file);

        Cipher cipher = Cipher.getInstance("SM4/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, OperateKey());

        goSaveDe(is , cipher);
    }

    /**
     * CBC解密模式
     * @throws Exception
     */
    public void goCBCDecry() throws Exception{
        FileInputStream is = new FileInputStream(file);

        /* 读取IV向量 */
        byte[] ivValue = new byte[16];
        is.read(ivValue);
        IvParameterSpec iv = new IvParameterSpec(ivValue);

        Cipher cipher = Cipher.getInstance("SM4/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, OperateKey(),iv);

        goSaveDe(is , cipher);
    }

    /**
     * 保存解密后文件（原样保存）
     * @param is 输入流
     * @param cipher 待写入cipher对象
     * @throws Exception
     */
    public void goSaveDe(FileInputStream is , Cipher cipher) throws  Exception{
        byte[] fileNameLengthByte = new byte[2];
        is.read(fileNameLengthByte);
        int fileNameLength = Integer.valueOf(new String(fileNameLengthByte));
        byte[] fileName = new byte[fileNameLength];
        is.read(fileName);

        String path = null;//相对路径
        if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            path = fileChooser.getSelectedFile().getPath();
        }

        /* 读取密文前的加密文件名，并原样解密 */
        File writeFile = new File(path, "/" + new String(fileName));
        writeFile.createNewFile();

        CipherInputStream cis = new CipherInputStream(is , cipher);
        FileOutputStream fos = new FileOutputStream(writeFile);

        try(fos){
            byte[] buffer = new byte[1024];
            int n = -1;
            while((n= cis.read(buffer)) != -1){
                fos.write(buffer , 0 , n);
            }
        }

        JOptionPane.showMessageDialog(null,
                "解密成功", "解密成功", 1); //弹出Message
    }
}
