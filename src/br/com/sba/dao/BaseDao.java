package br.com.sba.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Antonio
 */
public class BaseDao<T> {
 private EntityManagerFactory emf;//Conexao com o Banco Mysql previamente criado
 protected EntityManager em;//Gerenciador das tabelas do Banco


 //Construtor da Classe BaseDao
 public BaseDao(){
     emf = Persistence.createEntityManagerFactory("AppolodorusPU");
     em = emf.createEntityManager();

 }


 //Método para Inserir registros
 public boolean insert(T obj){
     try{
         em.getTransaction().begin();
         em.persist(obj);//Executa o Insert Into
         em.getTransaction().commit();
         return true;
     }catch(Exception e){
         e.printStackTrace();
         em.getTransaction().rollback();
         return false;
     }finally{
        // em.close();
     }
 }
 /***********************************************************************/

 //Metodo para atualizar Registros
 public boolean update(T obj){
     try{
         em.getTransaction().begin();
         em.merge(obj);//Executa o update
         em.getTransaction().commit();
         return true;
     }catch(Exception e){
         e.printStackTrace();
         em.getTransaction().rollback();
         return false;
     }
 }
 /*****************************************************************************/

 //Método para excluir registros
 public boolean delete(T obj){
     try{
         em.getTransaction().begin();
         obj = em.merge(obj);
         em.remove(obj);
         em.getTransaction().commit();
         return true;
     }catch(Exception e){
         e.printStackTrace();
         em.getTransaction().rollback();
         return false;
     }
 }
}