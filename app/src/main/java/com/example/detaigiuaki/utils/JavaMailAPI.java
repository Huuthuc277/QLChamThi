package com.example.detaigiuaki.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.detaigiuaki.model.MonHoc;
import com.example.detaigiuaki.model.ThongTinMail;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailAPI extends AsyncTask<Void,Void,Void> {
    private Context mContext;
    private Session mSession;

    private String mEmail;
    private String mSubject;
    ArrayList<ThongTinMail> listMH;

    public JavaMailAPI(Context mContext, String mEmail, String mSubject,ArrayList<ThongTinMail> list) {
        this.mContext = mContext;
        this.mEmail = mEmail;
        this.mSubject = mSubject;
        this.listMH=list;
    }
    @Override
    protected Void doInBackground(Void... params) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        mSession = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Utils.EMAIL, Utils.PASSWORD);
                    }
                });
        try {
            MimeMessage mm = new MimeMessage(mSession);
            mm.setFrom(new InternetAddress(Utils.EMAIL));
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
            mm.setSubject(mSubject);
            String mMessage=mSubject +"\n";
            for(ThongTinMail mh: listMH)
            {
                mMessage+="Số phiếu: ";
                mMessage+=mh.getSoPhieu();
                mMessage+="----";
                mMessage+="Giáo Viên: ";
                mMessage+=mh.getHoTenGV();
                mMessage+="----";
                mMessage+="Ngày Giao: ";
                mMessage+=mh.getNgayGiao();
                mMessage+="----";
                mMessage+="Số Bài: ";
                mMessage+=mh.getSoBai();
                mMessage+="\n";
            }
            mm.setText(mMessage);
            Transport.send(mm);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}