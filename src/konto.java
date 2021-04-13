public class konto {
    private int wartosc;
    private String imie;


    public konto (int a){
        this.wartosc=a;
    }
    public konto(){
        this(20000000);
    }

    public void dodaj(double kwota){
        wartosc+=kwota;
    }

    public void odejmij(double kwota){
        wartosc-=kwota;
    }

    public int stan(){
        return this.wartosc;
    }

    public void setimie(String aaa){
        this.imie=aaa;
    }

    public String getImie(){
        return this.imie;
    }

    public void setstan(int bbb){this.wartosc=bbb;}
}
