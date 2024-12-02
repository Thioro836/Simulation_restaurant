public class StandCuisson {
    private boolean platEnCours = false;// indique que le cuisinier est entrain de cuire un plat
    private boolean platPret = false; // indique que le plat est prêt à être récupéré

    /* client est au stand de cuisson */
    public synchronized void attendreCuisson() {
        while (platEnCours || platPret) {
            try {
                wait();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        platEnCours = true;
        System.out.println(Thread.currentThread().getName() + " attend que son plat soit cuit.");
        notify(); // Notifie le cuisinier qu'il y a un client

    }

    // la cuisson des plats
    public synchronized void cuirePlat() {
        while (!platEnCours) {
            try {
                wait();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        platEnCours = false;
        platPret = true; // Indique que le plat est prêt
        System.out.println(Thread.currentThread().getName() + " a terminé la cuisson.");
        notify(); // on utilise notify car on ne peut cuisiner qu'un seul plat à la fois donc on
                  // réveille un seul client
    }
}
