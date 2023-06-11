/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto3erp;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author crisb
 */
public class ServerRMI   {
   
  
    public static void main(String[] args) {

        try {
                TrapezoidalRuleRMIImpl remoteCalculator = new TrapezoidalRuleRMIImpl();
                Registry registry = LocateRegistry.createRegistry(1099); // Número de puerto RMI
                registry.rebind("TrapezoidalRuleRMI", remoteCalculator);
                System.out.println("Servidor RMI iniciado. Esperando conexiones...");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }
}
