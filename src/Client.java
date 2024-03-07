import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;

public class Client extends JFrame implements ActionListener {

    JTextField textField;
    static JPanel a1;

    static Box vertical = Box.createVerticalBox();

    static DataOutputStream dout;

    static JFrame frame = new JFrame();

    Client(){

        frame.setSize(450,700);
        frame.getContentPane().setBackground(new Color(140, 163, 247));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);

        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(115, 49, 74));
        panel1.setBounds(0,0,450,75);
        panel1.setLayout(null); //set bounds works only when layout is null
        frame.add(panel1);

        ImageIcon img1 = new ImageIcon(ClassLoader.getSystemResource("3.png"));
        Image img2 = img1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon img3 = new ImageIcon(img2);
        JLabel backbutton = new JLabel(img3);
        backbutton.setBounds(5,20,25,35);
        panel1.add(backbutton);

        backbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon img4 = new ImageIcon(ClassLoader.getSystemResource("two.jpg"));
        Image img5 = img4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon img6 = new ImageIcon(img5);
        JLabel profile = new JLabel(img6);
        profile.setBounds(45,13,50,50);
        panel1.add(profile);

        ImageIcon img7 = new ImageIcon(ClassLoader.getSystemResource("video.png"));
        Image img8 = img7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon img9 = new ImageIcon(img8);
        JLabel video = new JLabel(img9);
        video.setBounds(310,20,30,30);
        panel1.add(video);

        ImageIcon img10 = new ImageIcon(ClassLoader.getSystemResource("call.png"));
        Image img11 = img10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon img12 = new ImageIcon(img11);
        JLabel call = new JLabel(img12);
        call.setBounds(360,20,30,30);
        panel1.add(call);

        ImageIcon img13 = new ImageIcon(ClassLoader.getSystemResource("3icon.png"));
        Image img14 = img13.getImage().getScaledInstance(10,30,Image.SCALE_DEFAULT);
        ImageIcon img15 = new ImageIcon(img14);
        JLabel more = new JLabel(img15);
        more.setBounds(400,20,10,30);
        panel1.add(more);


        JLabel name = new JLabel("USER 2");
        name.setBounds(125,25,100,15);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,20));
        panel1.add(name);

        JLabel status = new JLabel("Online");
        status.setBounds(125,50,100,15);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,15));
        panel1.add(status);

        a1 = new JPanel();
        a1.setBounds(5,75,420,520);
        frame.add(a1);

        textField = new JTextField();
        textField.setBounds(8,610,280,40);
        frame.add(textField);

        JButton sendB = new JButton("Send");
        sendB.setBounds(300,610,123,40);
        sendB.addActionListener(this);
        frame.add(sendB);


        JPanel textarea = new JPanel();
        textarea.setBounds(5,75,425,580);
        frame.add(textarea);


        frame.setVisible(true);

    }

    public static JPanel formatLabel(String msg){

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel msgL = new JLabel(msg);
        msgL.setBackground(new Color(37,211,102));
        msgL.setOpaque(true);
        msgL.setBorder(new EmptyBorder(15,15,15,50));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));


        panel.add(msgL);
        panel.add(time);

        return panel;

    }



    public static void main(String[] args) {
        new Client();

        try{
            Socket s = new Socket("127.0.0.1",6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while (true){

                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);

                vertical.add(left);
                frame.validate();

                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);

            }


        } catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        String msg = textField.getText();

        JLabel msgL = new JLabel(msg);
        JPanel p2 = formatLabel(msg);
        //    p2.add(msgL);

        a1.setLayout(new BorderLayout());

        JPanel right = new JPanel(new BorderLayout());

        right.add(p2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));

        a1.add(vertical, BorderLayout.PAGE_START);

        try {
            dout.writeUTF(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        textField.setText("");

        a1.repaint();
        a1.invalidate();
        a1.validate();



    }
}