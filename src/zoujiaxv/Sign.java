package zoujiaxv;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Calendar;
import java.util.Date;

public class Sign {
    private final File f;
    private final String passwd;
    private final String subDN;
    private PrivateKey privateKey;
    //private PublicKey publicKey;

    /**
     * 构造方法
     * @param f 待签名文件
     * @param passwd 口令
     * @param subDN 秘钥信息字符串
     * @throws Exception
     */
    public Sign(File f , String passwd , String subDN) throws Exception {
        this.f = f;
        this.passwd = passwd;
        this.subDN = subDN;
        this.genKeyPair(); //构造的时候就生成密钥对并
    }

    /**
     * 写入签名文件
     * @throws Exception
     */
    public void signFile() throws Exception{
        System.out.println(f.getAbsolutePath()+".sig");

        try(FileInputStream fis=  new FileInputStream(f);
            FileOutputStream fos = new FileOutputStream(f.getAbsolutePath() + ".sig")){
            Signature signature = Signature.getInstance("SM3WITHSM2");
            signature.initSign(privateKey);

            byte[] buffer = new byte[1024];
            int n = 0;
            while((n = fis.read(buffer)) != -1){
                signature.update(buffer , 0 , n);
            }

            byte[] signaturValue = signature.sign();
            fos.write(signaturValue);
        }
        JOptionPane.showMessageDialog(null , "签名成功", "Successfully!" , 1);
    }

    /**
     * 生成密钥对
     * @throws Exception
     */
    public void genKeyPair() throws Exception{
        String signaturealg = "SM3WITHSM2";
        //生成密钥对
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
        KeyPair keyPair = kpg.generateKeyPair();
        privateKey = keyPair.getPrivate();
        //publicKey = keyPair.getPublic();

        Certificate certificate = selfSign(keyPair , signaturealg);
        System.out.println(certificate);

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        char[] password = passwd.toCharArray();
        keyStore.load(null , password);
        keyStore.setKeyEntry("myeckey" , privateKey, password ,
                new Certificate[]{certificate});


        //获取当前系统的“我的文档”文件夹，并生成/cryptogoose文件夹用以存储.keystore文件
        JFileChooser tempfilechooser = new JFileChooser();
        FileSystemView fw = tempfilechooser.getFileSystemView();
        String tempPath = fw.getDefaultDirectory().toString() + "\\cryptogoose"; //拼接字符串，指向我的文档
        File defaultPath = new File(tempPath);
        //如果我的文档目录下没有cryptogoose文件夹，那么就创建一个cryptogoose文件夹
        if(!defaultPath.exists() && !defaultPath.isDirectory()) {
            defaultPath.mkdir();
        }

        //写入文件
        FileOutputStream fos = new FileOutputStream(defaultPath + "\\" + f.getName() +
                ".keystore");
        keyStore.store(fos , password);

    }

    /**
     * 生成自签名数字证书
     * @param keyPair 密钥对
     * @param signaturealg 模式
     * @return 证书
     * @throws Exception
     */
    public Certificate selfSign(KeyPair keyPair , String signaturealg) throws Exception{
        BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();
        Security.addProvider(bouncyCastleProvider);

        long now = System.currentTimeMillis();
        Date startDate = new Date(now);
        X500Name dnName = new X500Name(subDN);

        BigInteger certSerialNumber = new BigInteger(Long.toString(now));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.YEAR , 1);
        Date endDate = calendar.getTime();

        ContentSigner contentSigner = new JcaContentSignerBuilder(signaturealg)
                .build(keyPair.getPrivate());

        JcaX509v3CertificateBuilder certificateBuilder = new
                JcaX509v3CertificateBuilder(dnName , certSerialNumber ,
                startDate , endDate , dnName , keyPair.getPublic());

        BasicConstraints basicConstraints = new BasicConstraints(true);

        certificateBuilder.addExtension(new ASN1ObjectIdentifier("2.5.29.19"),
                true,basicConstraints);

        return new JcaX509CertificateConverter().setProvider(bouncyCastleProvider)
                .getCertificate(certificateBuilder.build(contentSigner));
    }
}
