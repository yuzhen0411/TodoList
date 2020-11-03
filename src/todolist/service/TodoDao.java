package todolist.service;

import java.util.List;

import todolist.bean.TodoBean;

public interface TodoDao {

	void insertTodo(TodoBean todo);

	TodoBean selectTodo(long todoId);

	List<TodoBean> selectAllTodos(String uname);

	boolean deleteTodo(int id);

	boolean updateTodo(TodoBean todo);

}