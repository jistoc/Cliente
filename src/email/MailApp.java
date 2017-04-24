/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.util.MailSSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;

/**
 *
 * @author satna
 */
public class MailApp {
    private IMAPFolder f;
    private Store store;
    private Message[] mensagens;
    private Session session;
    
    private String login = "exsm2017@gmail.com";
    private String senha = "klapaucius";
    
    private String hostSmtp = "smtp.gmail.com";
    private String hostImap = "imap.gmail.com";
    private String portaSmtp = "587";
    private String portaImap = "993";
    
    public MailApp(){        
    }
    public MailApp(String login, String senha){
       this.login = login;
       this.senha = senha;
    }
    public void responderEmail(Message mensagem, String resposta) throws MessagingException{
        Message replyMessage = new MimeMessage(session);
        replyMessage = (MimeMessage) mensagem.reply(false);
        replyMessage.setFrom(new InternetAddress(login));
        replyMessage.setText(resposta);
        replyMessage.setReplyTo(mensagem.getReplyTo());
        Transport t = session.getTransport("smtp");
        t.connect(hostSmtp,Integer.parseInt(portaSmtp),login,senha);
        t.sendMessage(replyMessage,replyMessage.getAllRecipients());
        t.close();
        mensagem.setFlag(Flag.SEEN, true);
    }
    public void desconectar() throws MessagingException{
        f.close(false);
    }
    public void conectar() throws MessagingException, IOException, GeneralSecurityException{

        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        Properties props = new Properties();

        props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.imap.port",  portaImap);
        props.setProperty("mail.imap.socketFactory.port", portaImap);
        props.setProperty("mail.imap.ssl.trust", hostImap);
        props.setProperty("mail.smtp.ssl.trust", hostSmtp);
        props.setProperty("mail.smtp.starttls.enable","true"); 

        URLName url = new URLName("imap", hostImap, Integer.parseInt(portaImap), "",
                login, senha);
        
        session = Session.getInstance(props, null);
        store = session.getStore(url);
        
        store.connect();
        f = (IMAPFolder) store.getFolder("INBOX");
        f.open(Folder.READ_WRITE);
        atualizar();
        
    }
    private void atualizar() throws MessagingException{
        mensagens = getF().search(new FlagTerm(new Flags(Flags.Flag.ANSWERED), false));
        /*Arrays.sort(getMensagens(), ( m1, m2 ) -> {
            try {
                return m2.getSentDate().compareTo( m1.getSentDate() );
              } catch ( MessagingException e ) {
                throw new RuntimeException( e );
            }
        } );        
        */
    }
    public String processMessageBody(Message message) throws MessagingException { 
        
        String s = "";
        try { 
            Object content = message.getContent();
            if (content instanceof String) { 
                return content.toString(); 
            } else if (content instanceof Multipart) { 
                Multipart multiPart = (Multipart) content; 
                s = procesMultiPart(multiPart); 
            } else if (content instanceof InputStream) { 
                InputStream inStream = (InputStream) content; 
                int ch; 
                while ((ch = inStream.read()) != -1) { 
                    s += ch;
                } 
            } 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } catch (MessagingException e) { 
            e.printStackTrace(); 
        }
        message.setFlag(Flag.SEEN, false);
        return s;
    } 
    public String procesMultiPart(Multipart content) { 
        String s = "";
        try { 
            int multiPartCount = content.getCount(); 
            for (int i = 0; i < multiPartCount; i++) { 
                BodyPart bodyPart = content.getBodyPart(i); 
                Object o; o = bodyPart.getContent(); 
                if (o instanceof String) { 
                    return o.toString(); 
                } 
                else if (o instanceof Multipart) { 
                    s += procesMultiPart((Multipart) o); 
                } 
            } 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } catch (MessagingException e) { 
            e.printStackTrace(); 
        } 
        return s;
    } 
    /**
     * @return the f
     */
    public IMAPFolder getF() {
        return f;
    }

    /**
     * @param f the f to set
     */
    public void setF(IMAPFolder f) {
        this.f = f;
    }

    /**
     * @return the mensagens
     */
    public Message[] getMensagens() {
        return mensagens;
    }

    /**
     * @param mensagens the mensagens to set
     */
    public void setMensagens(Message[] mensagens) {
        this.mensagens = mensagens;
    }
    
}
