package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.ToDo;

@Service
public class ToDoService {
	 private static List<ToDo> todos = new ArrayList<ToDo>();
	    private static int todoCount = 3;
	    static {
	        todos.add(new ToDo(1, "sajana", "Learn SpringBoot", new Date(),
	                false));
	        todos.add(new ToDo(2, "vandana", "Learn Angular", new Date(), false));
	        todos.add(new ToDo(3, "teja", "Learn typeScript", new Date(),
	                false));
	    }
	    public List<ToDo> retrieveTodos(String user) {
	        List<ToDo> filteredTodos = new ArrayList<ToDo>();
	        for (ToDo todo : todos) {
	            if (todo.getUser().equals(user)) {
	                filteredTodos.add(todo);
	            }
	        }
	        return filteredTodos;
	    }
	    public void addTodo(String name, String desc, Date targetDate,
	            boolean isDone)
	    {
	        todos.add(new ToDo(++todoCount, name, desc, targetDate, isDone));
	    }

	    public void deleteTodo(int id)
	    {
	        Iterator<ToDo> iterator = todos.iterator();
	        while (iterator.hasNext()) {
	            ToDo todo = iterator.next();
	            if (todo.getId() == id) {
	                iterator.remove();
	            }
	        }
	    }
}
