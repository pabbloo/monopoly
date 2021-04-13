import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;



public class okienko extends JFrame implements ActionListener {

    private JComboBox cbgraczt,cbgraczz;
    private JLabel lstan,lwyborr,lstrzalka,lkwota,lpabbloo;
    private JButton bok,bsave;
    private JTextField tfkwota;
    private JRadioButton rb1,rb2,rb3,rb4;
    ButtonGroup bg;
    private int dzialanie=1;
    private JLabel[] Timiona,Tstany;
    private konto[] Tkont;
    int graczy=99;

public okienko(){

    setSize(550,400);
    setTitle("Manager gry Monopoly");
    setLayout(null);

    while((graczy>4) || (graczy<0)) {
        String Sgraczy = JOptionPane.showInputDialog(null,"Podaj liczbe graczy: (0 aby zaladowac stan)","Monopoly",JOptionPane.QUESTION_MESSAGE);
        graczy = Integer.parseInt(Sgraczy);
    }

    boolean importowanko = false;
    int Pgraczy = 0;


    if (graczy==0) {
        importowanko = true;

        try {
            Scanner odczyt = new Scanner(new File("zapis.txt"));

            String data = odczyt.nextLine();
            Pgraczy = Integer.parseInt(data);
            odczyt.close();
            graczy=Pgraczy;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Nie znaleziono pliku","Bład", JOptionPane.ERROR_MESSAGE);
            System.exit(5);
        }
    }

    Tkont = new konto[graczy];

    if (importowanko){
        try {
            Scanner odczyt = new Scanner(new File("zapis.txt"));

            String data = odczyt.nextLine();

            for (int i=0; i<graczy;i++){
                Tkont[i] = new konto();

                data = odczyt.nextLine();
                Tkont[i].setimie(data);
                data = odczyt.nextLine();
                int Pstan=Integer.parseInt(data);
                Tkont[i].setstan(Pstan);
            }

            odczyt.close();
            JOptionPane.showMessageDialog(null,"Zaladowano poprawnie");

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }

    else {

        for (int i = 0; i < graczy; i++) {
            Tkont[i] = new konto();
            String tmpImie=JOptionPane.showInputDialog("Podaj imie gracza "+i);
            Tkont[i].setimie(tmpImie);
        }

    }

    lstan = new JLabel("STAN KONT");
    lstan.setBounds(5,5,200,20);
    lstan.setFont(new Font("SansSerif",Font.BOLD,16));
    add(lstan);

    Timiona = new JLabel[graczy];
    Tstany = new JLabel[graczy];

    for (int i=0; i<graczy;i++){

        Timiona[i] = new JLabel(Tkont[i].getImie() + ": ");
        Tstany[i] = new JLabel(String.valueOf((double)Tkont[i].stan()/1000000)+"M");

        Timiona[i].setBounds(5,(i*20)+25,200,20);
        Tstany[i].setBounds(50,(i*20)+25,200,20);

        add(Timiona[i]);
        add(Tstany[i]);
    }


    ButtonGroup bg = new ButtonGroup();

    JRadioButton rb1 = new JRadioButton("Kupno",true);
    JRadioButton rb2 = new JRadioButton("Przelew",false);
    JRadioButton rb3 = new JRadioButton("Przejscie przez start",false);
    JRadioButton rb4 = new JRadioButton("Dodaj pieniadze",false);

    rb1.setBounds(5,130,100,20);
    rb2.setBounds(105,130,100,20);
    rb3.setBounds(225,130,150,20);
    rb4.setBounds(385,130,150,20);


    bg.add(rb1);bg.add(rb2);bg.add(rb3);bg.add(rb4);
    add(rb1);add(rb2);add(rb3);add(rb4);

    rb1.addItemListener(new obsluga(1));
    rb2.addItemListener(new obsluga(2));
    rb3.addItemListener(new obsluga(3));
    rb4.addItemListener(new obsluga(4));


    lwyborr = new JLabel("KUPNO");
    lwyborr.setFont(new Font("SansSerif",Font.BOLD,16));
    lwyborr.setBounds(5,180,250,20);
    add(lwyborr);


    String players[] = new String[graczy];

    for (int i=0; i<graczy;i++) {
        players[i] = Tkont[i].getImie();
    }

    cbgraczt = new JComboBox(players);
    cbgraczt.setBounds(60,220,100,20);
    add(cbgraczt);

    cbgraczz = new JComboBox(players);
    cbgraczz.setBounds(320,220,100,20);
    add(cbgraczz);
    cbgraczz.setVisible(false);

    lstrzalka = new JLabel(">>>");
    lstrzalka.setBounds(230,220,50,20);
    add(lstrzalka);
    lstrzalka.setVisible(false);

    lkwota = new JLabel("Kwota: ");
    lkwota.setBounds(60,260,100,20);
    add(lkwota);

    tfkwota = new JTextField("0");
    tfkwota.setBounds(160,260,100,20);
    add(tfkwota);

    bok = new JButton("OK");
    bok.setBounds(210,300,60,40);
    add(bok);
    bok.addActionListener(this);

    bsave= new JButton("Zapisz stan gry");
    bsave.setBounds(380,5,140,20);
    add(bsave);
    bsave.addActionListener(this);

    lpabbloo= new JLabel("Created by pabbloo in 2019");
    lpabbloo.setBounds(360,330,180,20);
    lpabbloo.setForeground(Color.GRAY);
    add(lpabbloo);
}

    @Override
    public void actionPerformed(ActionEvent e){

        Object zrodlo = e.getSource();
        int t=99,z=99,tmp=1,poz;
        String tmpk,subtmpk;
        Double k=0.0;

        if (zrodlo==bok) {

            tmpk= tfkwota.getText();
            poz =tmpk.length()-1;

            if ((tmpk.charAt(poz)=='M') || (tmpk.charAt(poz)=='m')){
                subtmpk = tmpk.substring(0,poz);
                k=(Double.parseDouble(subtmpk))*1000000;
            }
            else if((tmpk.charAt(poz)=='K') || (tmpk.charAt(poz)=='k')){
                subtmpk = tmpk.substring(0,poz);
                k=(Double.parseDouble(subtmpk))*1000;
            }
            else{
                k=Double.parseDouble(tmpk);
            }

            switch (dzialanie) {
                case 1:
                    //kupno

                    t=cbgraczt.getSelectedIndex();

                    tmp=Tkont[t].stan();

                    this.Tkont[t].odejmij(k);

                    if (Tkont[t].stan()<0){
                        JOptionPane.showMessageDialog(null,"Brak srodkow na koncie","Bład", JOptionPane.WARNING_MESSAGE);
                        Tkont[t].setstan(tmp);
                    }

                    break;
                case 2:
                    //przelew
                    t=cbgraczt.getSelectedIndex();
                    z=cbgraczz.getSelectedIndex();

                    if (t==z) {
                        JOptionPane.showMessageDialog(null,"Nie mozna wybrac tych samych graczy","Bład", JOptionPane.WARNING_MESSAGE);
                        break;
                    }

                    tmp=Tkont[t].stan();

                    this.Tkont[t].odejmij(k);

                    if (Tkont[t].stan()<0){
                        JOptionPane.showMessageDialog(null,"Brak srodkow na koncie","Bład", JOptionPane.WARNING_MESSAGE);
                        Tkont[t].setstan(tmp);
                    }
                    else this.Tkont[z].dodaj(k);

                    break;
                case 3:
                    //przejscie przez start
                    z=cbgraczz.getSelectedIndex();
                    k=2000000.0;
                    this.Tkont[z].dodaj(k);

                    break;
                case 4:
                    //dodaj
                    z=cbgraczz.getSelectedIndex();
                    this.Tkont[z].dodaj(k);
                    break;

            }
        }

        if (zrodlo==bsave) {
            try {
                File plik = new File("zapis.txt");

                if (plik.exists()){
                    plik.delete();
                }

                plik.createNewFile();

            }
            catch (IOException ev) {
                System.out.println("bład pliku");
                ev.printStackTrace();
            }

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("zapis.txt", true));

                writer.write(String.valueOf(graczy));
                writer.newLine();

                for (int m=0;m<graczy;m++){
                    writer.write(Tkont[m].getImie());
                    writer.newLine();
                    writer.write(String.valueOf(Tkont[m].stan()));
                    writer.newLine();
                }

                writer.close();
                System.out.println("Zapis udany!");
            } catch (IOException ev) {
                System.out.println("blad zapisu");
                ev.printStackTrace();
            }
        }

        for (int i=0; i<graczy;i++) {
            Tstany[i].setText(String.valueOf((double)Tkont[i].stan()/1000000)+"M");;
        }
    }

    public void setDzialanie (int yy){
        dzialanie=yy;
    }

    private class obsluga implements ItemListener {

        int operacja=0;

        public obsluga(int rr){
            operacja=rr;
        }

        public void itemStateChanged(ItemEvent event){

            switch(operacja){
                case 1:
                    setDzialanie(1);
                    lwyborr.setText("KUPNO");
                    lstrzalka.setVisible(false);
                    cbgraczz.setVisible(false);
                    cbgraczt.setVisible(true);
                    tfkwota.setVisible(true);
                    lkwota.setVisible(true);
                    break;
                case 2:
                    setDzialanie(2);
                    lwyborr.setText("PRZELEW");
                    lstrzalka.setVisible(true);
                    cbgraczz.setVisible(true);
                    cbgraczt.setVisible(true);
                    tfkwota.setVisible(true);
                    lkwota.setVisible(true);
                    break;
                case 3:
                    setDzialanie(3);
                    lwyborr.setText("PRZEJSCIE PRZEZ START");
                    lstrzalka.setVisible(false);
                    cbgraczz.setVisible(true);
                    cbgraczt.setVisible(false);
                    tfkwota.setVisible(false);
                    lkwota.setVisible(false);
                    break;
                case 4:
                    setDzialanie(4);
                    lwyborr.setText("DODAJ PIENIĄDZE");
                    lstrzalka.setVisible(false);
                    cbgraczt.setVisible(false);
                    cbgraczz.setVisible(true);
                    tfkwota.setVisible(true);
                    lkwota.setVisible(true);
                    break;
            }
        }
    }

    public static void main(String[] args){

        okienko aplikacja = new okienko();
        aplikacja.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aplikacja.setVisible(true);
    }

}
