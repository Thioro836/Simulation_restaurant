import java.util.HashMap;
import java.util.Map;

public class Buffet {
    //map pour stocker les differents compartiments
    Map<String, Double> compartiment;

    public Buffet() {
        compartiment = new HashMap<>();
       
        // Initialisation des compartiments
        compartiment.put("poisson", 1.0);
        compartiment.put("viande", 1.0);
        compartiment.put("legumes", 1.0);
        compartiment.put("nouilles", 1.0);

    }
     /* gérer la prise d'ingrédients par un client */
    public synchronized void prendrePortion(String id, double quantite_demande) {

        // Vérifie si le compartiment existe
        if (!compartiment.containsKey(id)) {
            System.out.println("Le compartiment " + id + " n'existe pas.");
            return;
        }
            double quantiteDisponible = compartiment.get(id);
            // Si quantité insuffisante, attendre, on utilise while car on veut que les
            // threads réacquèrent de nouveau le verrou avant de continuer
            while (quantiteDisponible < quantite_demande) {
                System.out.println(Thread.currentThread().getName() +"Quantité insuffisante dans le compartiment " + id);
                try {
                    wait(); // Attente sur l'objet représentant le compartiment
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                //quantiteDisponible = compartiment.get(id); // Recalcule après réveil
            }
            // Réduction de la quantité disponible
            compartiment.put(id, quantiteDisponible - quantite_demande);
            System.out.println(Thread.currentThread().getName() +"Portion prise : " + quantite_demande + " dans le compartiment " + id);
        }
    
         /* gérer le remplissage du buffet par l'employé */
    public synchronized void reapprovisionnerCompartiment(String id, double quantite_demande) {
        if (!compartiment.containsKey(id)) {
            System.out.println("Le compartiment " + id + " n'existe pas.");
            return;
        }
       
            double quantite_restante = compartiment.get(id);
            if (quantite_restante < 0.1) {
                compartiment.put(id, quantite_demande + quantite_restante);
            }
            System.out.println(Thread.currentThread().getName() +"Réapprovisionnement effectué pour le compartiment " + id);
            notifyAll(); // Réveiller les clients en attente

        }

    }


