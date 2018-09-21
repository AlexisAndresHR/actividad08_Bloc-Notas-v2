
package main;

import controllers.ControllerBlocNotasv2;
import models.ModelBlocNotasv2;
import views.ViewBlocNotasv2;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ModelBlocNotasv2 modelblocnotas = new ModelBlocNotasv2();
        ViewBlocNotasv2 viewblocnotas = new ViewBlocNotasv2();
        ControllerBlocNotasv2 controllerblocnotas = new ControllerBlocNotasv2(modelblocnotas, viewblocnotas); // Integra los componentes del modelo MVC.
        
    }
    
}
