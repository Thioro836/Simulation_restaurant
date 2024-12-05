
public class Restaurant {

    /* Constantes de la simulation */
    int NB_Places_dispo = 25;
    private static final int NB_CLIENTS = 40;
    private Client[] clients = new Client[NB_CLIENTS];
    private Cuisinier cuisinier;
    private Employe employe;

    /* Objets partagés */
    private final Buffet buffet = new Buffet();
    private final StandCuisson standCuisson = new StandCuisson();

    public Restaurant() {

        /* Instanciation de l'employe */
        employe = new Employe(buffet);
        /* Instanciation du cuisinier */
        cuisinier = new Cuisinier(standCuisson);

        /* Instanciation des clients */

        for (int i = 0; i < NB_CLIENTS; i++) {
            clients[i] = new Client(buffet, standCuisson, this);
        }
    }

    /* Gérer l'accès au restaurant */
    public synchronized void entrerRestaurant() {
        while (NB_Places_dispo == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
        NB_Places_dispo--;
        System.out.println(Thread.currentThread().getName() + " entre dans le restaurant." + NB_Places_dispo);
    }

    /* Libérer de la place après avoir mangé */
    public synchronized void sortirRestaurant() {
        NB_Places_dispo++;
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " quitte le restaurant." + NB_Places_dispo);
        // System.out.println("il reste :"+NB_CLIENTS+ " "+ "clients");

    }

    public void demarrer() {

        try {
            for (int i = 0; i < NB_CLIENTS; i++) {
                clients[i].start();
            }
            employe.start();
            cuisinier.start();
            employe.join();
            for (int i = 0; i < NB_CLIENTS; i++) {
                clients[i].join();
            }
            cuisinier.join();
            employe.isrun = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        restaurant.demarrer();
    }
}
