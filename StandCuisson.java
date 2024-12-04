public class StandCuisson {

    private boolean isClient;// variable pour indiquer si le client est présent
   // private boolean platPret = false; // indique si le plat est prêt à être récupéré
     /* client est au stand de cuisson */
     public StandCuisson(){
        this.isClient=false;
     }
     public synchronized void attendreCuisson() {
        while (isClient) {
       
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
         isClient = true;
        System.out.println(Thread.currentThread().getName() + " attend que son plat soit cuit.");
        notifyAll();
    }
    public synchronized void cuirePlat() {
         while (isClient==false) {
       
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() + " commence à cuire le plat ");
        try {
            Thread.sleep(2000);
           isClient=true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println(Thread.currentThread().getName() + " a terminé la cuisson.");
       notify();
    }

   

    public synchronized void recupererPlat() {
        while (!isClient) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
         isClient = false;
        System.out.println(Thread.currentThread().getName() + " récupère son plat cuit.");
        notifyAll();
    }

}
