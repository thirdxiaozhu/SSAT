package zoujiaxv;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.X509Certificate;

public class Authentic {
    private final File AuthSourceFile;
    private final File AuthSignedFile;
    private final File AuthStoreFile;
    private final String password;

    /**
     * 构造方法
     * @param AuthSourceFile 被签名文件
     * @param AuthSignedFile 签名值文件
     * @param AuthStoreFile 密钥库文件
     * @param password 口令
     */
    public Authentic(File AuthSourceFile , File AuthSignedFile , File AuthStoreFile , String password){
        this.AuthSourceFile = AuthSourceFile;
        this.AuthSignedFile = AuthSignedFile;
        this.AuthStoreFile = AuthStoreFile;
        this.password = password;
    }

    /**
     * 签名验证
     * @return 返回boolean
     * @throws Exception
     */
    public boolean goAuth() throws Exception{
        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        try(FileInputStream ftv = new FileInputStream(AuthSourceFile);
            FileInputStream fsv = new FileInputStream(AuthSignedFile);
            FileInputStream fkv = new FileInputStream(AuthStoreFile)){

            System.out.println(AuthStoreFile);

            char[] passwd = password.toCharArray();
            System.out.println(passwd);
            try {
                keyStore.load(fkv, passwd);
            }catch (IOException e){
                /* 如果报IOException错误，即为口令不匹配，输出对话框 */
                JOptionPane.showMessageDialog(null, "口令错误！请检查后重新输入",
                        "Error!", 0);
            }

            X509Certificate certificate = (X509Certificate) keyStore.getCertificate("myeckey");

            PublicKey publicKey = certificate.getPublicKey();

            Signature signature = Signature.getInstance("SM3WITHSM2");
            //公钥初始化签名对象
            signature.initVerify(publicKey);

            //文件加载到数字签名对象
            byte[] buffer = new byte[1024];
            int n = 0;
            while((n = ftv.read(buffer)) != -1){
                signature.update(buffer , 0 , n);
            }

            //读取数字签名值
            byte[] signatureValue = new byte[fsv.available()];
            fsv.read(signatureValue);

            return signature.verify(signatureValue);
        }

    }
}
