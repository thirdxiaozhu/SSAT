package zoujiaxv;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class P5form {

    public JPanel MyPanel;
    private JList FunctionList;
    private JPanel HashPanel;
    private JPanel Card;
    private JPanel EncryptPanel;
    private JPanel DecryptPanel;
    private JPanel SignaturePanel;
    private JPanel AuthenticationPanel;
    private JPanel MACPanel;
    private JLabel HashTitle;
    private JRadioButton StringHash;
    private JRadioButton FileHash;
    private JPanel HashCard;
    private JPanel StringHashPanel;
    private JPanel FileHashPanel;
    private JPanel HashModelPanel;
    private JTextField StringHashValue;
    private JButton StringHashButton;
    private JTextField StringHashResult;
    private JLabel StringInputLabel;
    private JButton HashFileChooseBtn;
    private JPanel EncryModelPanel;
    private JLabel EncryTitle;
    private JRadioButton ECBButton;
    private JRadioButton CBCButton;
    private JPanel EnPanel;
    private JButton goEncry;
    private JTextField EnFilePath;
    private JTextField EnPasswd;
    private JButton EnFileChooseBtn;
    private JPanel DecryModelPanel;
    private JRadioButton ECBDeBtn;
    private JRadioButton CBCDeBtn;
    private JTextField DeFilePath;
    private JButton DeFileChooseBtn;
    private JButton goDecry;
    private JPasswordField DePasswd;
    private JLabel DecryTitle;
    private JTextField FileHashPath;
    private JTextField FileHashResult;
    private JButton FileHashButton;
    private JLabel SignTitle;
    private JPanel SignPanel;
    private JButton EditSignInfoBtn;
    private JButton CheckSignInfoBtn;
    private JButton goSign;
    private JTextField SignFilePath;
    private JButton SignFileChooseBtn;
    private JPasswordField SignPasswd;
    private JLabel AuthTitle;
    private JPanel AuthPanel;
    private JTextField AuthSignedPath;
    private JButton AuthSignedBtn;
    private JTextField AuthSignDataPath;
    private JTextField AuthStorePath;
    private JButton AuthSignDataBtn;
    private JButton AuthStoreBtn;
    private JButton goAuthentic;
    private JPasswordField AuthPassword;
    private JPanel MACModelPanel;
    private JLabel MACTitle;
    private JRadioButton StringMAC;
    private JRadioButton FileMAC;
    private JPanel MACCard;
    private JPanel StringMACPanel;
    private JPanel FileMACPanel;
    private JButton StringMACBtn;
    private JTextField StringMACValue;
    private JTextField FileMACPath;
    private JButton MACFileChooseBtn;
    private JButton FileMACBtn;
    private JLabel InfoTitle;
    private JPanel InfoPanel;
    private JPanel InfoModelPanel;
    private JLabel InfoIcon;
    private JLabel ToolTitle;
    private JPasswordField StringMACPasswd;
    private JPasswordField FileMACPasswd;
    private CardLayout cardLayout;
    private CardLayout HashcardLayout;
    private CardLayout MACcardLayout;
    private DefaultListModel listModel;
    private ButtonGroup btnGroupHashModel;
    private ButtonGroup btnGroupEnModel;
    private ButtonGroup btnGroupDeModel;
    private ButtonGroup btnGroupMACModel;
    private File HashFile;
    private File EnFile;
    private File DeFile;
    private File SignFile;
    private File AuthSourceFile;
    private File AuthSignedFile;
    private File AuthStoreFile;
    private File MACFile;
    public String CN , OU , O , L, ST , C;

    public P5form() {
        System.getProperty("java.version");
        initList();
        addCard();
        setPanel();

        FunctionList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                doValueChange();
            }
        });
    }

    /** ?????????????????????Layout??? */
    private void doValueChange() {
        if(FunctionList.isSelectedIndex(0)){
            cardLayout.show(Card , "0");
        }
        else if(FunctionList.isSelectedIndex(1)){
            cardLayout.show(Card , "1");
        }
        else if(FunctionList.isSelectedIndex(2)){
            cardLayout.show(Card , "2");
        }
        else if(FunctionList.isSelectedIndex(3)){
            cardLayout.show(Card , "3");
        }
        else if(FunctionList.isSelectedIndex(4)){
            cardLayout.show(Card , "4");
        }
        else if(FunctionList.isSelectedIndex(5)){
            cardLayout.show(Card , "5");
        }
        else if(FunctionList.isSelectedIndex(6)){
            cardLayout.show(Card , "6");
        }
    }

    /** ???????????????????????? */
    private void initList() {
        FunctionList.setBorder(BorderFactory.createEtchedBorder(0));
        String[] listData = {"Hash" , "????????????" , "????????????" , "????????????" , "????????????" ,
                "MAC?????????" , "????????????"};
        listModel = new DefaultListModel();
        for(String s:listData){
            listModel.addElement(s);
        }
        FunctionList.setModel(listModel);
    }

    /** ???CardLayout???????????????????????????????????????????????? */
    private void addCard() {
        cardLayout = new CardLayout();
        Card.setLayout(cardLayout);
        Card.add(HashPanel,"0");
        Card.add(EncryptPanel,"1");
        Card.add(DecryptPanel,"2");
        Card.add(SignaturePanel,"3");
        Card.add(AuthenticationPanel,"4");
        Card.add(MACPanel,"5");
        Card.add(InfoPanel, "6");
    }

    /** ??????????????????????????? */
    private void setPanel() {
        setHashPanel();
        setEnCryptPanel();
        setDeCryptPanel();
        setSignaturePanel();
        setAuthenticationPanel();
        setMACPanel();
        setInfoPanel();
    }

    /** ??????Hash?????? */
    private void setHashPanel() {
        HashModelPanel.setBorder(BorderFactory.createEtchedBorder());
        HashTitle.setFont(new Font("Dialog" ,  1 , 20));
        setStringHashPanel();
        setFileHashPanel();
        btnGroupHashModel = new ButtonGroup();
        btnGroupHashModel.add(StringHash);
        btnGroupHashModel.add(FileHash);
        StringHash.setSelected(true); //?????????????????????
        HashcardLayout = new CardLayout();
        HashCard.setLayout(HashcardLayout);
        HashCard.add(StringHashPanel,"Hash0");
        HashCard.add(FileHashPanel,"Hash1");
        StringHash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashcardLayout.show(HashCard,"Hash0");
            }
        });
        FileHash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashcardLayout.show(HashCard,"Hash1");
            }
        });
    }

    /** ?????????????????? */
    private void setEnCryptPanel() {
        EncryModelPanel.setBorder(BorderFactory.createEtchedBorder());
        EncryTitle.setFont(new Font("Dialog" ,  1 , 20));
        btnGroupEnModel = new ButtonGroup();
        btnGroupEnModel.add(ECBButton);
        btnGroupEnModel.add(CBCButton);
        ECBButton.setSelected(true);  //??????ECB??????
        EnFilePath.setEditable(false);
        EnFileChooseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //goEnChooseFile();
                EnFile = goChooseFile(EnFilePath);
            }
        });
        goEncry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!EnFilePath.getText().equals("")){
                    String passwd = EnPasswd.getText();
                    if(passwd.length() == 16){     //?????????????????????16
                        Encrypt encrypt = null;
                        try {
                            encrypt = new Encrypt(EnFile,passwd);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        if(ECBButton.isSelected()){
                            try {
                                encrypt.goECBEncry();
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                JOptionPane.showMessageDialog(null , "???????????????" , "Error!" , 0);
                            }
                        }
                        else if(CBCButton.isSelected()){
                            try {
                                encrypt.goCBCEncry();
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                JOptionPane.showMessageDialog(null , "???????????????" , "Error!" , 0);
                            }
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null , "?????????????????????16?????????128?????????" , "Error!" , 0);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null , "????????????????????????" , "Error!" , 0);
                }
            }
        });
    }

    /** ?????????????????? */
    private void setDeCryptPanel() {
        DecryModelPanel.setBorder(BorderFactory.createEtchedBorder());
        DecryTitle.setFont(new Font("Dialog" ,  1 , 20));
        btnGroupDeModel = new ButtonGroup();
        btnGroupDeModel.add(ECBDeBtn);
        btnGroupDeModel.add(CBCDeBtn);
        ECBDeBtn.setSelected(true); //??????ECB??????
        DeFilePath.setEditable(false);
        DeFileChooseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //goDeChooseFile();
                DeFile = goChooseFile(DeFilePath);
            }
        });
        goDecry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!DeFilePath.getText().equals("")){
                    String passwd = DePasswd.getText();
                    if(passwd.length() == 16){
                        Decrypt decrypt = null;
                        try {
                            decrypt = new Decrypt(DeFile,passwd);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        if(ECBDeBtn.isSelected()){
                            try {
                                decrypt.goECBDecry();
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                JOptionPane.showMessageDialog(null , "????????????????????????????????????????????????" , "Error!" , 0);
                            }
                        }
                        else if(CBCDeBtn.isSelected()){
                            try {
                                decrypt.goCBCDecry();
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                JOptionPane.showMessageDialog(null , "????????????????????????????????????????????????" , "Error!" , 0);
                            }
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null , "?????????????????????16?????????128?????????" , "Error!" , 0);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null , "????????????????????????" , "Error!" , 0);
                }
            }
        });
    }

    /** ?????????????????? */
    private void setSignaturePanel() {
        SignPanel.setBorder(BorderFactory.createEtchedBorder());
        SignTitle.setFont(new Font("Dialog" ,  1 , 20));
        SignFileChooseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignFile = goChooseFile(SignFilePath);
            }
        });
        EditSignInfoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callSignDialog(0);
            }
        });
        CheckSignInfoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callSignDialog(1);
            }
        });
        goSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(SignFilePath.getText().equals("")){
                    JOptionPane.showMessageDialog(null , "???????????????", "Error!" , 0);
                    return;
                }
                if(SignPasswd.getText().equals("")){
                    JOptionPane.showMessageDialog(null , "???????????????", "Error!" , 0);
                    return;
                }

                String subjectDN = "CN=" + CN +",OU=" + OU + ",O=" + O + ",L=" + L + ",ST=" + ST + ",C=" + C; //???????????????????????????X500Name
                Sign sign = null;
                try {
                    sign = new Sign(SignFile , SignPasswd.getText() , subjectDN );
                    sign.signFile();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    /** ???????????????????????? */
    private void setAuthenticationPanel() {
        AuthPanel.setBorder(BorderFactory.createEtchedBorder());
        AuthTitle.setFont(new Font("Dialog" ,  1 , 20));
        AuthSignedPath.setEditable(false);
        AuthSignDataPath.setEditable(false);
        AuthStorePath.setEditable(false);

        AuthSignedBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AuthSourceFile = goChooseFile(AuthSignedPath);
                if(!AuthSignedPath.getText().equals("")){
                    JFileChooser tempfilechooser = new JFileChooser();
                    FileSystemView fw = tempfilechooser.getFileSystemView();
                    String tempPath = fw.getDefaultDirectory().toString() + "\\cryptogoose\\"
                            + AuthSourceFile.getName()+".keystore"; //????????????????????????????????????
                    File defaultPath = new File(tempPath);

                    //??????????????????????????????????????????keystore,?????????????????????????????????????????????????????????????????????????????????
                    if(defaultPath.exists()) {
                        AuthStorePath.setText(tempPath);
                        AuthStoreFile = defaultPath;
                    }
                    else{
                        JOptionPane.showMessageDialog(null , "???????????????????????????????????????????????????", "Attention!" , 1);
                    }
                }
            }
        });

        AuthSignDataBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AuthSignedFile = goChooseFile(AuthSignDataPath);
            }
        });

        AuthStoreBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AuthStoreFile = goChooseFile(AuthStorePath);
            }
        });

        goAuthentic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(AuthStorePath.getText().equals("") || AuthSignedPath.getText().equals("") || AuthSignDataPath.getText().equals("")){
                    JOptionPane.showMessageDialog(null , "????????????????????????", "Error!" , 0);
                    return;
                }
                if(AuthPassword.getText().equals("")) {
                    JOptionPane.showMessageDialog(null , "???????????????", "Error!" , 0);
                    return;
                }
                Authentic authentic = new Authentic(AuthSourceFile ,AuthSignedFile  ,AuthStoreFile , AuthPassword.getText());
                try {
                    boolean result = authentic.goAuth();

                    if(result){
                        JOptionPane.showMessageDialog(null , "????????????", "Successfully!" , 1);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "????????????!???????????????????????????", "Error!", 0);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    /** ??????MAC?????? */
    private void setMACPanel() {
        MACModelPanel.setBorder(BorderFactory.createEtchedBorder());
        MACTitle.setFont(new Font("Dialog" ,  1 , 20));
        setStringMACPanel();
        setFileMACPanel();
        btnGroupMACModel = new ButtonGroup();
        btnGroupMACModel.add(StringMAC);
        btnGroupMACModel.add(FileMAC);
        StringMAC.setSelected(true); //?????????????????????
        MACcardLayout = new CardLayout();
        MACCard.setLayout(MACcardLayout);
        MACCard.add(StringMACPanel,"MAC0");
        MACCard.add(FileMACPanel,"MAC1");
        StringMAC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MACcardLayout.show(MACCard,"MAC0");
            }
        });
        FileMAC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MACcardLayout.show(MACCard,"MAC1");
            }
        });
    }

    /** ???????????????????????? */
    public void setInfoPanel(){
        InfoModelPanel.setBorder(BorderFactory.createEtchedBorder());
        InfoTitle.setFont(new Font("Dialog" , Font.BOLD, 20));
        ToolTitle.setFont(new Font("Dialog" , Font.BOLD, 13));
        setIcon();
    }

    /** ????????????????????????Hash?????? */
    private void setStringHashPanel() {
        StringHashResult.setEditable(false);
        StringHashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(StringHashValue.getText().equals("")){
                    JOptionPane.showMessageDialog(null , "??????????????????" , "Error!" , 0);
                    return;
                }
                String str = StringHashValue.getText();
                StringHashResult.setText(SM3Hash.StringHash(str));
            }
        });
    }

    /** ?????????????????????Hash?????? */
    private void setFileHashPanel() {
        FileHashResult.setEditable(false);
        HashFileChooseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashFile = goChooseFile(FileHashPath);
            }
        });

        FileHashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(FileHashPath.getText().equals("")){
                    JOptionPane.showMessageDialog(null , "???????????????" , "Error!" , 0);
                    return;
                }
                try {
                    FileHashResult.setText(SM3Hash.FileHash(HashFile));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    /** ??????SignDialog */
    private void callSignDialog(int model){
        JFrame frame = new JFrame("??????????????????"); //????????????
        //????????????, this?????????????????????????????????????????? , ??????????????????????????????,?????????????????????????????????
        frame.setContentPane(new SignDialog(model ,frame , this).MyPanel);
        frame.pack();
        frame.setSize(500,290); //????????????
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setLocation(550 , 350); //?????????????????????

        frame.setResizable(false); //??????????????????

        //?????????????????????
        ImageIcon icon = new ImageIcon("E:\\Java\\untitled2\\src\\zoujiaxv\\img\\groose.png");
        frame.setIconImage(icon.getImage());
    }

    /** ????????????????????????MAC?????? */
    private void setStringMACPanel() {

        StringMACBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(StringMACValue.getText().equals("")){
                    JOptionPane.showMessageDialog(null , "?????????????????????MAC???????????????", "Error!" , 0);
                    return;
                }
                if(StringMACPasswd.getText().equals("")){
                    JOptionPane.showMessageDialog(null , "???????????????", "Error!" , 0);
                    return;
                }
                MAC mac = null;
                try {
                    mac = new MAC(StringMACPasswd.getText());
                    mac.StringMAC(StringMACValue.getText());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    /** ?????????????????????MAC?????? */
    private void setFileMACPanel() {
        FileMACPath.setEditable(false);
        MACFileChooseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MACFile = goChooseFile(FileMACPath);
            }
        });

        FileMACBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(FileMACPath.getText().equals("")){
                    JOptionPane.showMessageDialog(null , "?????????????????????MAC????????????", "Error!" , 0);
                    return;
                }
                if(FileMACPasswd.getText().equals("")){
                    JOptionPane.showMessageDialog(null , "???????????????", "Error!" , 0);
                    return;
                }
                MAC mac = null;
                try {
                    mac = new MAC(FileMACPasswd.getText());
                    mac.FileMAC(MACFile);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    /**
     * ???????????????????????????
     */
    private void setIcon() {
        Icon icon = new ImageIcon("E:\\Java\\AES\\src\\zoujiaxv\\img\\head_gaitubao_71x71.png");
        InfoIcon.setText("");
        InfoIcon.setIcon(icon);
    }

    /** ???????????? */
    private File goChooseFile(JTextField filePath){
        File f = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(0); //?????????????????????

        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            f = fileChooser.getSelectedFile();
        }
        filePath.setText(f.getAbsolutePath());

        return f;
    }
}
