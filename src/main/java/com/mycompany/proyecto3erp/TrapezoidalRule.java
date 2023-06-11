/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto3erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author crisb
 */
public class TrapezoidalRule extends JFrame implements ActionListener {
	
    public  Registry registry;
    public TrapezoidalRuleRMI remoteCalculator;
    public int cont=1; 
    
    private JFrame frame = new JFrame();
    
    private JLabel LimSupLabel = new JLabel("Limite Superior: ");
    private JTextField LimSupTextField = new JTextField("10");

    private JLabel operacionLabel = new JLabel("Tipo de operación: ");
    private JTextField operacionTextField = new JTextField("X");
    
    private JLabel InterLabel = new JLabel("Numero de intervalos: ");
    private JTextField InterTextField = new JTextField("2147483647");

    private JLabel LimInfLabel = new JLabel("Limite Inferior: ");
    private JTextField LimInfTextField = new JTextField("0");
    
    private JLabel HiloLabel = new JLabel("Numero de Hilos: ");
    private JTextField HiloTextField = new JTextField("8");
    
    private JTextArea textArea = new JTextArea(5, 5);

    private JButton calcularButton = new JButton("Concurrente");
    private JButton Secuencial = new JButton("Secuencial");
    private JButton Paralelo = new JButton("Paralelo");

    private JLabel resultado = new JLabel("Resultado: ");
    private JTextField resultadoLabel = new JTextField(" ");

   // private JTextField precisionLabel = new JTextField(" ");
   // private JLabel precision = new JLabel("Precision");

    private JTextField tiempoLabel = new JTextField(" ");
    private JLabel tiempo = new JLabel("Tiempo: ");

    private JPanel panel = new JPanel(new BorderLayout());


    Font font = new Font("Arial", Font.PLAIN, 14);
    //Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);


    public TrapezoidalRule() {
       super("Integración numérica");
       registrar();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        calcularButton.setBackground(new Color(0, 160, 255));
        calcularButton.setForeground(Color.white);

        Secuencial.setBackground(new Color(0, 160, 255));
        Secuencial.setForeground(Color.white);
        
        Paralelo.setBackground(new Color(0, 160, 255));
        Paralelo.setForeground(Color.white);

        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);


        JPanel panelNorte = new JPanel(new GridLayout(5, 2));
        panelNorte.add(LimSupLabel);
        panelNorte.add(LimSupTextField);
        panelNorte.add(LimInfLabel);
        panelNorte.add(LimInfTextField);
        panelNorte.add(InterLabel);
        panelNorte.add(InterTextField);
        panelNorte.add(operacionLabel);
        panelNorte.add(operacionTextField);
        panelNorte.add(HiloLabel);
        panelNorte.add(HiloTextField);

        JPanel panelCentro = new JPanel(new GridLayout(4, 2));
        panelCentro.add(resultado);
        panelCentro.add(resultadoLabel);
        panelCentro.add(tiempo);
        panelCentro.add(tiempoLabel);
        panelCentro.add(calcularButton);
        panelCentro.add(Secuencial);
        panelCentro.add(Paralelo);

        JPanel panelSur = new JPanel(new GridLayout(1, 1));

        panelSur.add(textArea);


        panel.add(panelNorte, BorderLayout.NORTH);
        panel.add(panelCentro, BorderLayout.CENTER);
        panel.add(panelSur, BorderLayout.SOUTH);

        calcularButton.addActionListener(this);
        Secuencial.addActionListener(this);
        Paralelo.addActionListener(this);

        frame.add(panel);
        frame.setSize(500, 500);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


	
    public static void main(String[] args) {
    	
    	
    	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TrapezoidalRule();
            }
        });
       
    }

    public void registrar(){
    
        try {

            registry = LocateRegistry.getRegistry("192.168.100.22", 1099);
            remoteCalculator = (TrapezoidalRuleRMI) registry.lookup("TrapezoidalRuleRMI");
            System.out.println(remoteCalculator);
            remoteCalculator.agregar(remoteCalculator);
            /*cont = remoteCalculator.agregar(remoteCalculator);
            System.out.println(registry);
            System.out.println(cont);
            System.out.println(remoteCalculator);
  */
            System.out.println("Conexion exitosa");

        } catch (Exception e) {
            System.err.println("Error send the message: " + e.toString());
        }
    
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		
	if (e.getSource() == calcularButton) {
		
	String tipo = operacionTextField.getText();
	long tiempo = System.currentTimeMillis();
	int a = Integer.parseInt(LimInfTextField.getText());// Definir los límites inferior y superior de la integración
	int b = Integer.parseInt(LimSupTextField.getText());
        double n = Integer.parseInt(InterTextField.getText());
        //double n =  2147483647; // Definir el número de subintervalos (más subintervalos = mayor precisión)
        int numThreads = Integer.parseInt(HiloTextField.getText());// Definir el número de hilos que se utilizarán
        double h = (b - a) / n; // Calcular el ancho del subintervalo
			
	 Function<Double, Double> function;
	        switch (tipo) {
	        
            case "X":
                function = x -> x;
                break;
                
            case "X2":
                function = x -> x*x;
                break;
                
            case "X3":
                function = x -> x*x*x;
                break;
                     
            case "1/X":
                function = x -> 1/x;
                if(a==0 || b==0) {
                	System.out.println("Funcion discontinua. Limites no pueden ser 0");
                }
                break;
                
            case "SEN":
            	function = x -> Math.sin(x);
            	break;
            	
            case "COS":
            	function = x -> Math.cos(x);
            	break;
            	
            case "TAN":
                function = x -> Math.tan(x);
                break;
                
            case "EXP":
                function = x -> Math.exp(x);
                break;
                
            case "Ln":
                function = x -> Math.log(x);
                if(a==0 || b==0) {
                	System.out.println("Funcion discontinua. Limites no puede ser 0");
                }
                break;
            	
            default:
                System.out.println("Seleccion invalida");
                function = x -> x * x;
                break;
        }



	        ExecutorService executor = Executors.newFixedThreadPool(numThreads); // Crear el ExecutorService con el número de hilos especificado

	  
	        Future<Double>[] futures = new Future[numThreads]; // Crear una lista de futuros para almacenar los resultados de cada hilo

	     
	        for (int i = 0; i < numThreads; i++) {    // Dividir el trabajo entre los hilos
	            int threadNumber = i;
	            System.out.println("Thread " + i + " Inicia");
	            futures[i] = executor.submit(() -> {
	                double sum = 0;
	                int startIndex = (int) (threadNumber * n / numThreads);
	                int endIndex = (int) ((threadNumber + 1) * n / numThreads);
	                
	                
	                if (threadNumber == numThreads - 1) {
	                    endIndex = (int) n;
	                }
	                
	                for (int j = startIndex + 1; j < endIndex; j++) {
	                    double x = a + j * h;
	                    sum += function.apply(x);
	                }
	                return sum;
	            }
	            
	          );
	       
	       }
	        
	        

	       
	        double sum = 0.5 * (function.apply((double) a) + function.apply((double) b));
	        try {
	            for (int i = 0; i < numThreads; i++) {
	                sum += futures[i].get();
		            System.out.println("Thread " + i + " termnina");
	            }
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }  // Esperar a que se completen todos los futuros y sumar los resultados

	       
	        double integral = h * sum; // Multiplicar por el ancho del subintervalo para obtener la integral
	        executor.shutdown();	        // Apagar el ExecutorService
	        String str = String.format("%f", integral);
            resultadoLabel.setText(str);
            //precisionLabel.setText(PRES + "%");
            tiempoLabel.setText("" + (System.currentTimeMillis() - tiempo) + " mS");
            textArea.setText("El area bajo la curva de " + tipo + " en el rango [" + a + ", " + b + "] es " + str + ". Se realizo en: " + (System.currentTimeMillis() - tiempo) + " mS \n");
            //textArea.setText("Se realizo en: " + (System.currentTimeMillis() - tiempo) + " mS \n");
		}
		
		
		//Calculo Secuencial
	else if(e.getSource() == Secuencial) {
            
            textArea.setText("Secuencial inicia\n");
            int a = Integer.parseInt(LimInfTextField.getText()); 
            int b = Integer.parseInt(LimSupTextField.getText());
            String tipo = operacionTextField.getText();
	    double n = Integer.parseInt(InterTextField.getText());
	  //double n = 2147483647;
                double h = (b - a) / n;
		double sum;
			long tiempo = System.currentTimeMillis();
			
			 Function<Double, Double> function;
		        switch (tipo) {
		        
	            case "X":
	                function = x -> x;
	                break;
	                
	            case "X2":
	                function = x -> x*x;
	                break;
	                
	            case "X3":
	                function = x -> x*x*x;
	                break;
	                     
	            case "1/X":
	                function = x -> 1/x;
	                if(a==0 || b==0) {
	                	System.out.println("Funcion discontinua. Limites no puede ser 0");
	                }
	                break;
	                
	            case "SEN":
	            	function = x -> Math.sin(x);
	            	break;
	            	
	            case "COS":
	            	function = x -> Math.cos(x);
	            	break;
	            	
	            case "TAN":
	                function = x -> Math.tan(x);
	                break;
	                
	            case "EXP":
	                function = x -> Math.exp(x);
	                break;
	                
	            case "Ln":
	                function = x -> Math.log(x);
	                if(a==0 || b==0) {
	                	System.out.println("Funcion discontinua. Limites no puede ser 0");
	                }
	                break;
	            	
	            default:
	                System.out.println("Seleccion invalida");
	                function = x -> x * x;
	                break;
	        }
		        
		        sum = 0.5 * (function.apply((double) a) + function.apply((double) b));
		        
		        for (int i = 1; i < n; i++) {
		            double x = a + i * h;
		            sum += function.apply(x);
		        }
			
		        double integral = h * sum;
		        String str = String.format("%f", integral);
	            resultadoLabel.setText(str);
	           // precisionLabel.setText(PRES + "%");
	            tiempoLabel.setText("" + (System.currentTimeMillis() - tiempo)/1000 + " Segundos");
	            textArea.setText("Se realizo en: " + (System.currentTimeMillis() - tiempo)/1000 + " Segundos");
	            textArea.setText("El area bajo la curva de " + tipo + " en el rango [" + a + ", " + b + "] es " + str + ". Se realizo en: " + (System.currentTimeMillis() - tiempo) + " mS \n");

		}
        
        else if(e.getSource() == Paralelo){
            
        try {

                cont = remoteCalculator.valor();
                System.out.println(cont);
                double a = Double.parseDouble(LimInfTextField.getText());
                double b = Double.parseDouble(LimSupTextField.getText());
                double n = Double.parseDouble(InterTextField.getText());
                long tiempo = System.currentTimeMillis();
                int numThreads = Integer.parseInt(HiloTextField.getText());
                String tipo = operacionTextField.getText();
                
                System.out.println(cont);
                n = n/cont;
                double result = remoteCalculator.calculate(a, b, n, tipo, numThreads);
     
                String str = String.format("%f", result);
                
                
                //n = n/numeroclientes;
                
                resultadoLabel.setText(str);
                //precisionLabel.setText(PRES + "%");
                tiempoLabel.setText("" + (System.currentTimeMillis() - tiempo) + " mS");
                textArea.setText("El area bajo la curva de " + tipo + " en el rango [" + a + ", " + b + "] es " + str + ". Se realizo en: " + (System.currentTimeMillis() - tiempo) + " mS \n");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
	}
    }
}

//   textArea.setText("PARALELO");