
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.ModelBlocNotasv2;
import views.ViewBlocNotasv2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser; // Para utilizar el "selector de archivos".
import javax.swing.filechooser.FileNameExtensionFilter; // Para el filtro de extensiones de archivos.


public class ControllerBlocNotasv2 {
    
    ModelBlocNotasv2 modelblocnotas; // Crea un objeto para acceder al contenido del Model.
    ViewBlocNotasv2 viewblocnotas; // Crea un objeto para acceder al contenido de la View.
    
    JFileChooser selector_archivos = new JFileChooser(); // Objeto para utilizar el "selector de archivos".
    FileNameExtensionFilter filtro_extensiones = new FileNameExtensionFilter("Archivos de Texto", "txt"); // Objeto para aplicar un filtro de extensiones.
    
    ActionListener actionlistener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == viewblocnotas.jmi_leer) { // Comprueba si se selecciona el ítem "Leer".
                jmi_leer_action_performed();
            }
            else if (e.getSource() == viewblocnotas.jmi_guardar) { // Comprueba si se selecciona el ítem "Guardar".
                jmi_guardar_action_performed();
            }
        }
    };
    
    /**
     * Método para integrar los componentes del módelo MVC.
     * @param modelBloc
     * @param viewBloc 
     */
    public ControllerBlocNotasv2(ModelBlocNotasv2 modelBloc, ViewBlocNotasv2 viewBloc) {
        this.modelblocnotas = modelBloc;
        this.viewblocnotas = viewBloc;
        
        this.viewblocnotas.jmi_leer.addActionListener(actionlistener);
        this.viewblocnotas.jmi_guardar.addActionListener(actionlistener);
        initComponents();
    }
    
    /**
     * Método para el ítem "Leer" del menú (ViewBlocNotasv2).
     */
    public void jmi_leer_action_performed() {
        selector_archivos.setFileFilter(filtro_extensiones); // Asigna el filtro de extensión .txt
        selector_archivos.showOpenDialog(viewblocnotas.jfc_cuadro_dialogo); // Abre el "selector de archivos" para abrir un archivo.
        File archivo = selector_archivos.getSelectedFile(); // Obtiene el archivo y lo guarda em la variable "archivo".
        String ruta = archivo.getPath(); // Guarda la ruta del archivo obtenido en la variable "ruta".
        
        modelblocnotas.setPath(ruta); // Asigna el valor de "ruta" (ubicación del archivo) a la variable "path".
        
        this.readFile(modelblocnotas.getPath()); // Invoca al método "readFile" y le da como parámetro el contenido de la variable "path" (Ubicada en el Model).
    }
    
    /**
     * Método para el ítem "Guardar" del menú (ViewBlocNotasv2).
     */
    public void jmi_guardar_action_performed() {
        selector_archivos.setFileFilter(filtro_extensiones); // Asigna el filtro de extensión .txt
        selector_archivos.showSaveDialog(viewblocnotas.jfc_cuadro_dialogo); // Abre el "selector de archivos" para guardar un archivo.
        File archivo = selector_archivos.getSelectedFile(); // Obtiene el archivo y lo guarda em la variable "archivo".
        String ruta = archivo.getPath(); // Guarda la ruta del archivo obtenido en la variable "ruta".
        
        modelblocnotas.setPath(ruta); // Asigna el valor de "ruta" (ubicación del archivo) a la variable "path".
        
        modelblocnotas.setMessage(viewblocnotas.jta_bloc_notas.getText()); // Asigna el contenido del área de texto (interfaz) a la variable "message".
        this.writeFile(modelblocnotas.getPath(), modelblocnotas.getMessage()); // Invoca al método "writeFile" y le da como parámetros el contenido de la variable "path" y de la variable "message" (ubicadas en el Model).
    }
    
    
// Métodos para la lectura y escritura del archivo de texto...
    
    /**
     * Método para mostrar en el área de texto, el contenido del archivo.
     * @param path: Indica la ruta de almacenamiento del archivo a manipular.
     * @return 
     */
    public String readFile (String path) {
        try {
            String row; // Variable que indica una "fila".
            String acumulador_texto = ""; // Variable para acumular todas las lineas de texto del archivo.
            try (FileReader archivo = new FileReader(path)) { // Permite leer el contenido del archivo.
                BufferedReader bufferedreader = new BufferedReader(archivo); // Permite almacenar el contenido del archivo de texto de forma temporal.
                while ((row = bufferedreader.readLine()) != null ) {
                    acumulador_texto += row + "\n"; // Acumulador de lineas.
                }
                viewblocnotas.jta_bloc_notas.setText(acumulador_texto);
            }
        }
        catch (FileNotFoundException err) { // Detecta un error en caso de no encontrar el archivo en el path indicado.
            System.err.println("Archivo no encontrado: " + err.getMessage());
        }
        catch (IOException err) { // Marca error en caso de no contar con los permisos para acceder al archivo indicado.
            System.err.println("Error en operación I/O: " + err.getMessage());
        }
        return null;
    };
    
    /**
     * Método para escribir (guardar) nuevo contenido en el archivo.
     * @param path: Indica la ruta de almacenamiento del archivo a manipular.
     * @param message: Variable que almacena el contenido del área de texto.
     */
    public void writeFile (String path, String message) {
        try {
            File archivo = new File(path); // Abre el archivo de la ruta especificada, si no existe, lo crea (según el path o ruta).
            FileWriter filewriter = new FileWriter(archivo, false); // Permite escribir en el archivo especificado.
            try (PrintWriter printwriter = new PrintWriter(filewriter) ) { // Permite guardar el nuevo contenido en del archivo especificado.
                printwriter.println(message);
                printwriter.close();
            }
        }
        catch (FileNotFoundException err) { // Detecta un error en caso de no encontrar el archivo en el path indicado.
            System.err.println("Archivo no encontrado: " + err.getMessage());
        }
        catch (IOException err) { // Marca error en caso de no contar con los permisos para acceder al archivo indicado.
            System.err.println("Error en operación I/O: " + err.getMessage());
        }
    }
    
    /**
     * Método para acceder a los componentes de la interfaz "ViewBlocNotasv2".
     */
    public void initComponents() {
        viewblocnotas.setVisible(true);
    }
    
}
