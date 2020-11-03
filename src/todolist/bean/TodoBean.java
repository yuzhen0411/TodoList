package todolist.bean;

import java.time.LocalDate;

public class TodoBean
{
	private Long id;
	private String title;
	private String username;
	private String description;
	private LocalDate targetDate;
	private String status;
	
	protected TodoBean() 
	{	
	}
	
	public TodoBean(long id, String title, String username, String description, LocalDate targetDate, String isDone) 
	{
		super();
		this.id = id;
		this.title = title;
		this.username = username;
		this.description = description;
		this.targetDate = targetDate;
		this.status = isDone;
	}

	public TodoBean(String title, String username, String description, LocalDate targetDate, String isDone) 
	{
		super();
		this.title = title;
		this.username = username;
		this.description = description;
		this.targetDate = targetDate;
		this.status = isDone;
	}
	
	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public String getTitle() 
	{
		return title;
	}
	
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public LocalDate getTargetDate() 
	{
		return targetDate;
	}

	public void setTargetDate(LocalDate targetDate) 
	{
		this.targetDate = targetDate;
	}

	public String getStatus() 
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status = status;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TodoBean other = (TodoBean) obj;
		if (id != other.id)
			return false;
		return true;
	}
}