package com.hiep.todolist.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import com.hiep.todolist.entity.Item;

public class ItemDAO {
	private static SessionFactory factory;

//	public static void main(String[] args) {
//
//		List<Item> items = ItemDAO.listItem();
//		for (Item item : items) {
//			System.out.println(item.getId());
//			System.out.println(item.getItemName());
//		}
//		ItemDAO.addItem("absdfnasdn");
//	}

	// add Item
	public static void addItem(String itemName) {
		try {
			Configuration conf = new Configuration();
			Configuration conf1 = conf.configure();
			factory = conf1.buildSessionFactory();
		} catch (Throwable e) {

			e.printStackTrace();
		}
		Session session = factory.openSession();
		Transaction tx = null;
        
        try {
			tx = session.beginTransaction();
			Item item = new Item(itemName);
			session.save(item);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
                tx.rollback();
            e.printStackTrace();

		}finally {
			session.close();
		}
	}

	
	// list Item
	public static List<Item> listItem() {
		List<Item> itemList = new ArrayList<Item>();
		try {
			Configuration conf = new Configuration();
			Configuration conf1 = conf.configure();
			factory = conf1.buildSessionFactory();
		} catch (Throwable e) {

			e.printStackTrace();
		}
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List items = session.createQuery("FROM Item").list();
			for (Iterator iterator = items.iterator(); iterator.hasNext();) {
				Item item = (Item) iterator.next();
				itemList.add(item);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();

		} finally {
			session.close();
		}
		return itemList;
	}
	
	
	//delete item
	public static void delItem(int id) {
		try {
			Configuration conf = new Configuration();
			Configuration conf1 = conf.configure();
			factory = conf1.buildSessionFactory();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		Session session = factory.openSession();
		Transaction tx = null;
		try {
            tx = session.beginTransaction();
            Item item = (Item) session.get(Item.class, id);
            session.delete(item);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

	}
}
