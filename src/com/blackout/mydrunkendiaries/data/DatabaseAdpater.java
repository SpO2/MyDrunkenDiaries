/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Interface for SQL Adapters.                *   
 *                                                      *   
 * Usage: Interface to implement in sql adapters.       *   
 *                                                      *   
 ********************************************************/
package com.blackout.mydrunkendiaries.data;

import java.util.ArrayList;

/**
 * Interface - contains common methods for entities.
 * @author romain
 *
 */
public interface DatabaseAdpater<T>
{
	public long create(T entity);
	public int update(T entity);
	public int delete(T entity);
	public T get(long id);
	public ArrayList<T> getAll();
}
