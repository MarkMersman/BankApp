package repos;

import java.util.ArrayList;


public interface DataRepository<T> {

	//CRUD methods
	
	//Create
	public boolean add(T t);
		
	//Read
	public T getById(Integer id);
	
	public T getByName(String name);
		
	public ArrayList<T> getAll();	
		
		//Update
	public void update(T t);
		
		//delete
	public void delete(T t);
}
