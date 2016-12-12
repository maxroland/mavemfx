/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxroland.mavenfx.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Rolando
 */
public final class EntityMan {
    
    private static EntityManagerFactory emf=Persistence.createEntityManagerFactory("mavenfxPU");
 
    public EntityMan(){}
    
    public static EntityManagerFactory getInstance(){
    return emf;}
    
}
