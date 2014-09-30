package ejb.administration;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import framework.AbstractEntity;

@Entity
@Table(name = "UserRoles")
public class UserRoles extends AbstractEntity implements Serializable {  

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
		@ManyToOne
		@JoinColumn(name="user", referencedColumnName="id", nullable = false)
		private User user;
         
		@ManyToOne
		@JoinColumn(name="role", referencedColumnName="id", nullable = false)
		private Role role;
         
         

	public UserRoles(){
	}


      public User getUser(){
           return user;
      }
      
      public void setUser(User user){
           this.user = user;
      }
      
      public Role getRole(){
           return role;
      }
      
      public void setRole(Role role){
           this.role = role;
      }
      
      

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public Object[] getValues() {	
		List<Object> list = new ArrayList<Object>();
		
		list.add(id);		
		if(user != null){
			list.add(user.toString());
		}else{
			list.add("");
		}
		if(role != null){
			list.add(role.toString());
		}else{
			list.add("");
		}
		 
		 return list.toArray();
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		return result.toString();
	}

}
