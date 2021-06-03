package zoujiaxv;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class Encrypt {
    File file;
    String password;
    private final JFileChooser fileChooser = new JFileChooser();

    public Encrypt(File file , String password) throws Exception {
        this.file = file;
        this.password = password;
    }

    /**
     * 生成密码学安全密钥
     * @return 密钥
     */
    private Key OperateKey() throws Exception{
        byte[] passwd = password.getBytes();
        MessageDigest md = MessageDigest.getInstance("MD5"); //MD5可生成128位密钥(必须为128位)
        //byte[] keyData = password.getBytes();
        byte[] keyData = md.digest(passwd);
        System.out.println(new String(keyData));
        SecretKeySpec key = new SecretKeySpec(keyData, "SM4");
        return key;
    }

    /**
     * ECB加密模式
     * @throws Exception
     */
    public void goECBEncry() throws Exception {
        OutputStream out = null;

        Cipher cipher = Cipher.getInstance("SM4/ECB/PKCS5PADDING",
                BouncyCastleProvider.PROVIDER_NAME);
        cipher.init(Cipher.ENCRYPT_MODE , OperateKey());

        SaveEn(cipher , null);
    }

    /**
     * CBC加密模式
     * @throws Exception
     */
    public void goCBCEncry() throws Exception{
        OutputStream out = null;

        /*CBC模式需要IV*/
        byte[] ivValue = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(ivValue);
        IvParameterSpec iv = new IvParameterSpec(ivValue);

        Cipher cipher = Cipher.getInstance("SM4/CBC/PKCS5PADDING",
                BouncyCastleProvider.PROVIDER_NAME);
        cipher.init(Cipher.ENCRYPT_MODE , OperateKey(), iv);


        SaveEn(cipher , ivValue);
    }

    /**
     * 保存加密文件
     * @param cipher 待写入cipher对象
     * @param ivValue ECB模式传进null
     * @throws Exception
     */
    public void SaveEn(Cipher cipher , byte[] ivValue) throws  Exception{
        OutputStream out = null;

        if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){ //选择输出文件
            /*如果选择的文件存在，就写入，如果不存在，就新建之后再写入*/
            if(!fileChooser.getSelectedFile().exists()) {
                out = new FileOutputStream(fileChooser.getSelectedFile());
            }
            else {
                String path = fileChooser.getSelectedFile().getAbsolutePath(); //绝对路径
                File file = new File(path);
                file.createNewFile();

                out = new FileOutputStream(file);
            }
        }

        String filename = file.getName();
        //如果IV不为空（CBC），则写入IV
        if(ivValue != null)
            out.write(ivValue);
        out.write(String.format("%02d",filename.length()).getBytes(StandardCharsets.UTF_8));
        out.write(filename.getBytes(StandardCharsets.UTF_8));

        InputStream is = null;
        try{
            is = new FileInputStream(file);
            CipherInputStream cis = new CipherInputStream(is , cipher);
            try(cis){
                byte[] buffer = new byte[1024];
                int n = -1;
                while ((n = cis.read(buffer)) != -1){
                    out.write(buffer , 0 , n);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null,
                "加密成功", "Successfully!", 1); //弹出Message
    }
}
