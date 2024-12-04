import java.util.HashMap;
import java.util.Map;

public class Buffet {
    // map pour stocker les differents compartiments
    Map<String, Double> compartiments;

    public Buffet() {
        compartiments = new HashMap<>();

        // Initialisation des compartiments
        compartiments.put("poisson", 1.0);
        compartiments.put("viande", 1.0);
        compartiments.put("legumes", 1.0);
        compartiments.put("nouilles", 1.0);

    }

    /* gérer la prise d'ingrédients par un client */

    public synchronized void prendrePortion(String compartiment, double quantite_demande) {

        // Vérifie si le compartiment existe
        // if (!compartiments.containsKey(compartiment)) {
        // System.out.println("Le compartiment " + compartiment + " n'existe pas.");
        // return;
        // }
        double quantiteDisponible = compartiments.get(compartiment);

        /*
         * on utilise while car on veut que les
         * threads réacquèrent de nouveau le verrou avant de continuer
         */
        while (quantite_demande > quantiteDisponible) {
            System.out.println(
                    Thread.currentThread().getName() + " " + " Quantité insuffisante "+quantiteDisponible +" dans le compartiment "
                            + compartiment);

            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            quantiteDisponible = compartiments.get(compartiment);
        }

        // Vérifie que la quantité demandée ne dépasse pas la quantité disponible
        double quantitePrelevee = Math.min(quantite_demande, quantiteDisponible);

        // Réduction de la quantité disponible
        compartiments.put(compartiment, quantiteDisponible - quantitePrelevee);
        System.out.println(Thread.currentThread().getName() + " " + " Portion prise : " + quantite_demande
                + " dans le compartiment " + compartiment);
    }

    /* gérer le remplissage du buffet par l'employé */

    public synchronized void reapprovisionnerCompartiment(String id) {
        double quantite_restante = compartiments.get(id);

        if (quantite_restante < 0.1) {
            double quantite_ajouter = 1.0 - quantite_restante;
            double quantite_totale = quantite_ajouter + quantite_restante;
            compartiments.put(id, quantite_totale);
            System.out.println(Thread.currentThread().getName() + " Réapprovisionnement effectué pour le compartiment "
                    + id + " quantite: " + quantite_totale);
            notifyAll(); // Réveiller les clients en attente

        }
    }

    public synchronized String[] getCompartiments() {
        return compartiments.keySet().toArray(new String[0]); // Retourne les clés sous forme de tableau
    }

}
