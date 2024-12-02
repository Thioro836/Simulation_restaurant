public class Client extends Thread {
    private Buffet buffet;
    private StandCuisson standCuisson;
    private String name;
    public Client(String name ,Buffet buffet, StandCuisson standCuisson){
        this.buffet=buffet;
        this.standCuisson=standCuisson;
        this.name=name;
    }
    public void seServir(){

    }
    
}
