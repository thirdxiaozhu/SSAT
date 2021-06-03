package zoujiaxv;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.util.encoders.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class SM3Hash {

    /**
     * 计算字符串的Hash
     * @param str 传入的源字符串
     * @return 返回Hash
     */
    public static String StringHash(String str) {
        Digest sm3 = new SM3Digest();
        sm3.update(str.getBytes(), 0, str.getBytes().length);
        byte[] sm3bytes = new byte[sm3.getDigestSize()];
        sm3.doFinal(sm3bytes, 0);

        return Hex.toHexString(sm3bytes);
    }

    /**
     * 计算文件的Hash
     * @param f 传入的源文件
     * @return 返回Hash
     */
    public static String FileHash(File f) throws Exception {
        try (FileInputStream fis = new FileInputStream(f)) {
            MessageDigest md = MessageDigest.getInstance("SM3");
            byte[] buffer = new byte[1024];
            int n = -1;
            while ((n = fis.read(buffer)) != -1){
                md.update(buffer,0,n);
            }
            byte[] digestValue = md.digest();
            return Hex.toHexString(digestValue);
        }
    }
}

