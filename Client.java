
import java.util.Random;

public class Client extends Thread {
    private Buffet buffet;
    private StandCuisson standCuisson;
    private Restaurant restaurant;

    public Client( Buffet buffet, StandCuisson standCuisson, Restaurant restaurant) {
        this.buffet = buffet;
        this.standCuisson = standCuisson;
        this.restaurant = restaurant;
    }

    public void run() {
        try {
            restaurant.entrerRestaurant();
            seServir();
            Thread.sleep(300);

            standCuisson.attendreCuisson();
            standCuisson.recupererPlat();
            // mange
            Thread.sleep(2000);

            restaurant.sortirRestaurant();

        } catch (Exception e) {
            // gestion de l'interruption
           
        }

    }

    public void seServir() {
        Random random = new Random();
        for (String compartiment : buffet.getCompartiments()) {
            double quantiteDemandee = random.nextDouble(0, 0.1);
            buffet.prendrePortion(compartiment, quantiteDemandee);

        }

    }
}
